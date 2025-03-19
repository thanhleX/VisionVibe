import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../service/order.service';
import { Order } from '../../../dto/Order';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-order',
  standalone: false,
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent implements OnInit {
  isFound!: boolean
  order!: Order;

  constructor(
    private orderService: OrderService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe({
      next: (params) => {
        const orderId = params['id'];
        this.findOrderById(orderId);
      }
    })
  }

  totalPrice(order: Order): number {
    return order.orderDetailResponseList.reduce((total, detail) => {
      return total + (detail.productDetailResponse.productDto.price * detail.amount);
    }, 0);
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'PENDING':
        return 'status-pending';
      case 'DENIED':
        return 'status-denied';
      case 'CONFIRMED':
        return 'status-confirm';
      default:
        return '';
    }
  }

  findOrderById(id: number) {
    this.orderService.findOrderById(id).subscribe({
      next: (res) => {
        this.isFound = true;
        this.order = res.result;
      },
      error: () => {
        this.isFound = false;
      }
    })
  }
}
