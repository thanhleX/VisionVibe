import { Component, OnInit } from '@angular/core';
import { HeaderService } from '../../service/header.service';
import { ProductCategories } from '../../../dto/ProductCategories';
import { ProductSubCategories } from '../../../dto/ProductSubCategories';
import { CartItem } from '../../../dto/CartItem';
import { CartService } from '../../service/cart.service';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  totalPrice: number = 0;
  cart: CartItem[] = [];
  cartOpen: boolean = false;
  menuOpen: boolean = false;
  categories: ProductCategories[] = [];
  subCategories: ProductSubCategories[] = [];
  activeCategoryId: number | null = null;

  constructor(
    private headerService: HeaderService,
    private cartService: CartService) { }

  ngOnInit(): void {
    this.getData()
    this.cartService.getCartObservable().subscribe((cartItems) => {
      this.cart = cartItems;
      this.cart.forEach(cart => console.log(cart))
      this.totalPrice = this.cart.reduce((sum, item) => {
        return sum + (item.productDetail.productDto.price * item.amount);
      }, 0);
    });
  }

  getData() {
    this.headerService.getAllProductCategories().subscribe((res) => {
      this.categories = res.result.content;
      this.categories.forEach((result, index) => {
        this.headerService.getAllProductSubCategories(result.id).subscribe((res) => {
          res.result.content.forEach((result: ProductSubCategories, index: any) => {
            this.subCategories.push(result);
          })
        })
      })
    })
  }

  filterSubCategories(): ProductSubCategories[] {
    return this.subCategories.filter(res => res.productCategoryDto.id === this.activeCategoryId);
  }

  toggleCart() {
    this.cartOpen = !this.cartOpen;
  }

  closeCart() {
    this.cartOpen = false;
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  closeMenu() {
    this.menuOpen = false;
    this.activeCategoryId = null;
  }

  onHoverCategory(categoryId: number) {
    this.activeCategoryId = categoryId;
    console.log(this.activeCategoryId);
  }

  increase(item: CartItem) {
    if (item.amount < item.productDetail.stock)
      item.amount++;
    this.updateCart()
  }

  decrease(item: CartItem) {
    if (item.amount > 1) {
      item.amount--;
      this.updateCart()
    }
  }

  updateCart() {
    this.cartService.updateCart(this.cart)
  }

  clearCart() {
    this.cartService.clearCart();
  }
}
