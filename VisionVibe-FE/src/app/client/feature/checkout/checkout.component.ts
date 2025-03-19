import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CartService } from '../../service/cart.service';
import { CartItem } from '../../../dto/CartItem';
import { PaymentMethod } from '../../../dto/PaymentMethod';
import { CheckoutService } from '../../service/checkout.service';
import { OrderForm } from '../../../dto/OrderForm';
import { Router } from '@angular/router';
import { Order } from '../../../dto/Order';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-checkout',
  standalone: false,
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.scss'
})
export class CheckoutComponent {
  order!: Order;
  isSubmitting = false;
  paymentMethods: PaymentMethod[] = []
  customerForm!: FormGroup;
  totalPrice: number = 0;
  cart: CartItem[] = [];

  constructor(
    private fb: FormBuilder,
    private cartService: CartService,
    private checkoutService: CheckoutService,
    private router: Router,
    private toastrService: ToastrService) { }

  ngOnInit(): void {
    this.getCartItem()
    this.getPaymentMethod()

    this.customerForm = this.fb.group({
      customerName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      address: ['', Validators.required],
      note: [''],
      paymentMethod: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.customerForm.valid) {
      let totalAmount = this.cart.reduce((total, item) => total + item.amount, 0);
      if (totalAmount === 0) {
        this.toastrService.error("Must select a product first", "Checkout Notification");
        return;
      }
      this.isSubmitting = true;
      const orderForm: OrderForm = {
        customerName: this.customerForm.get("customerName")?.value,
        email: this.customerForm.get("email")?.value,
        phoneNumber: this.customerForm.get("phoneNumber")?.value,
        address: this.customerForm.get("address")?.value,
        note: this.customerForm.get("note")?.value,
        paymentMethodId: this.customerForm.get("paymentMethod")?.value,
        createNewOrderDetailDtoList: this.cart.map(item => ({
          productDetailId: item.productDetail.id,
          amount: item.amount
        }))
      };
      console.log(orderForm);

      this.checkoutService.finishCheckout(orderForm).subscribe({
        next: (res) => {
          if (res.result.paymentUrl)
            window.location.href = res.result.paymentUrl;
          else
            this.router.navigate(['/payment', res.result.id])
        },
        error: () => this.toastrService.error(`can't finish checkout please check again`, `Checkout Notification`)
      });
    }
  }

  getCartItem() {
    this.cartService.getCartObservable().subscribe((cart => {
      this.cart = cart;
      this.totalPrice = this.cart.reduce((sum, item) => {
        return sum + (item.productDetail.productDto.price * item.amount);
      }, 0);
    }))
  }

  getPaymentMethod() {
    this.checkoutService.getAllPaymentMethod().subscribe((res => {
      this.paymentMethods = res.result;
    }))
  }
}
