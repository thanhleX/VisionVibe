import { Injectable } from '@angular/core';
import { CartItem } from '../../dto/CartItem';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartKey = 'cart';
  private cartSubject = new BehaviorSubject<CartItem[]>(this.loadCartFromSession());
  
  constructor() { }

  addToCart(cartItem: CartItem): void {
    const cart = this.loadCartFromSession();
    const existingCartItem = cart.find(item => item.productDetail.id === cartItem.productDetail.id);

    if (existingCartItem) {
      const newAmount = existingCartItem.amount += cartItem.amount;
      if (newAmount <= existingCartItem.productDetail.stock) {
        existingCartItem.amount = newAmount;
      } else {
        existingCartItem.amount = existingCartItem.productDetail.stock
        alert("can't add more");
      }
    } else {
      cart.push(cartItem);
    }

    this.saveCartToSession(cart);
    this.cartSubject.next(cart);
  }

  private saveCartToSession(cart: CartItem[]): void {
    sessionStorage.setItem(this.cartKey, JSON.stringify(cart));
  }

  private loadCartFromSession(): CartItem[] {
    const cartData = sessionStorage.getItem(this.cartKey);
    return cartData ? JSON.parse(cartData) : [];
  }

  getCartItems(): CartItem[] {
    return this.cartSubject.getValue();
  }

  getCartObservable() {
    return this.cartSubject.asObservable();
  }

  clearCart(): void {
    sessionStorage.removeItem(this.cartKey);
    this.cartSubject.next([]);
  }

  updateCart(cart: CartItem[]): void {
    this.cartSubject.next(cart);
    sessionStorage.setItem('cart', JSON.stringify(cart));
  }
}
