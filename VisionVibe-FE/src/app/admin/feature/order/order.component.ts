import { Component, OnInit } from '@angular/core';
import { Order } from '../../../dto/Order';
import { OrderService } from '../../service/order.service';
import { AuthService } from '../../../auth/service/auth.service';
import { JwtPayloadDto } from '../../../dto/JwtPayloadDto';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-order',
  standalone: false,
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent implements OnInit {
  isSubmitting = false;
  userInfo: JwtPayloadDto | undefined;
  currentPage: number = 1;
  pageSize: number = 9;
  allOrders: Order[] | undefined;
  pageAmount: number[] = [];
  totalPages: number | undefined;
  targetPage: number | undefined;

  constructor(
    private orderService: OrderService,
     private authService: AuthService,
      private toastrService: ToastrService) { }

  ngOnInit(): void {
    this.getAllOrders();
    this.getUserInfo();
  }

  getAllOrders() {
    this.isSubmitting = true;
    this.orderService.findAllOrders(this.currentPage - 1, this.pageSize).subscribe({
      next: (res) => {
        this.isSubmitting = false;
        this.allOrders = res.result.content;
        this.totalPages = res.result.totalPages;
        this.pageAmount = this.getPageAmount();
      },
      error: () => {
        this.isSubmitting = false;
        this.toastrService.error(`Can't open orders`, 'Order Notification');
      }
    });
  }

  getUserInfo() {
    this.userInfo = this.authService.getUserInfo();
  }

  checkScope(roles: string[]): boolean {
    return roles.some(role => this.userInfo?.scope.includes(role));
  }

  confirmOrder(id: number) {
    this.isSubmitting = true;
    this.orderService.confirmOrder(id).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.getAllOrders();
        this.toastrService.success(`Order ID ${id} is confirmed`, 'Order Notification');
      },
      error: () => {
        this.isSubmitting = false;
        this.toastrService.error(`Can't confirm Order ID ${id}, please try again`, 'Order Notification');
      }
    });
  }

  denyOrder(id: number) {
    this.isSubmitting = true;
    this.orderService.denyOrder(id).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.getAllOrders();
        this.toastrService.success(`Order ID ${id} is denied`, 'Order Notification');
      },
      error: () => {
        this.isSubmitting = false;
        this.toastrService.error(`Can't deny Order ID ${id}, please try again`, 'Order Notification');
      }
    });
  }
  totalPrice(order: Order): number {
    return order.orderDetailResponseList.reduce((total, detail) => {
      return total + (detail.productDetailResponse.productDto.price * detail.amount);
    }, 0);
  }

  getPageAmount(): number[] {
    this.pageAmount = []; // Reset array before add
    if (this.totalPages) {
      for (let i = 1; i <= this.totalPages; i++) {
        this.pageAmount.push(i);
      }
    }
    return this.pageAmount;
  }

  changePage(page: number) {
    this.currentPage = page;
    this.getAllOrders();
  }

  goToPage() {
    if (this.totalPages && this.targetPage && this.targetPage >= 1 && this.targetPage <= this.totalPages) {
      this.changePage(this.targetPage);
      this.targetPage = undefined;  // Corrected this line to reset targetPage
    } else {
      this.toastrService.error(`Can't navigate, please enter a valid page number`, 'Navigation Notification');
    }
  }

  navigateNewWindow(id: number) {
    window.open(`/order/${id}`, '_blank');
  }
}

