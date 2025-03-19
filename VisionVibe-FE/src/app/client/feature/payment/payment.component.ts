import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from '../../service/payment.service';
import { CartService } from '../../service/cart.service';

@Component({
  selector: 'app-payment',
  standalone: false,
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.scss'
})
export class PaymentComponent implements OnInit {
  isSubmitting = false;
  isSuccess!: boolean;
  orderId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private paymentService: PaymentService,
    private cartService: CartService) { }

  ngOnInit(): void {
    this.isSubmitting = true;
    const orderId = this.route.snapshot.paramMap.get('id');
    const token = this.route.snapshot.queryParams['token'];
    if (orderId && token) {
      this.paymentService.setPendingById(Number(orderId), token).subscribe({
        next: (res) => {
          this.cartService.clearCart();
          this.isSubmitting = false;
          this.isSuccess = true;
          this.orderId = res.result.id;
        }, error: (err) => {
          console.log(err)
          this.isSuccess = false;
          this.isSubmitting = false;
        }
      })
    }
    if (orderId && !token) {
      this.cartService.clearCart();
      this.isSubmitting = false;
      this.isSuccess = true;
      this.orderId = Number(orderId);
    }
  }
}
