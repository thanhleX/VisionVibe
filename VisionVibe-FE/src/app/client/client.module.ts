import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientRoutingModule } from './client-routing.module';
import { HomeComponent } from './feature/home/home.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { LayoutComponent } from './shared/layout/layout.component';

import { DivDraggerDirective } from './directive/div-dragger.directive';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BlogComponent } from './feature/blog/blog.component';

import { NewsComponent } from './feature/news/news.component';
import { ProductSubCategoriesComponent } from './feature/product-sub-categories/product-sub-categories.component';
import { ProductComponent } from './feature/product/product.component';
import { ProductDetailComponent } from './feature/product-detail/product-detail.component';
import { CheckoutComponent } from './feature/checkout/checkout.component';
import { PaymentComponent } from './feature/payment/payment.component';
import { OrderComponent } from './feature/order/order.component';

@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    LayoutComponent,
    DivDraggerDirective,
    BlogComponent,
    NewsComponent,
    ProductSubCategoriesComponent,
    ProductComponent,
    ProductDetailComponent,
    CheckoutComponent,
    PaymentComponent,
    OrderComponent
  ],
  imports: [
    CommonModule,
    ClientRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class ClientModule { }
