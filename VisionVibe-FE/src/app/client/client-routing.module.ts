import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './feature/home/home.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { BlogComponent } from './feature/blog/blog.component';

import { NewsComponent } from './feature/news/news.component';
import { ProductSubCategoriesComponent } from './feature/product-sub-categories/product-sub-categories.component';
import { ProductComponent } from './feature/product/product.component';
import { ProductDetailComponent } from './feature/product-detail/product-detail.component';
import { CheckoutComponent } from './feature/checkout/checkout.component';
import { PaymentComponent } from './feature/payment/payment.component';
import { OrderComponent } from './feature/order/order.component';

const routes: Routes = [
  {
    path: '', component: LayoutComponent, children: [
      { path: '', component: HomeComponent },
      { path: 'blog/:blogCategoryName/:blogId', component: BlogComponent },
      { path: 'blog/News', component: NewsComponent },
      { path: 'product/:productCategoryName', component: ProductSubCategoriesComponent },
      { path: 'product/:productCategoryName/:productSubCategoryName', component: ProductComponent },
      { path: 'product/:productCategoryName/:productSubCategoryName/:productName', component: ProductDetailComponent },
      { path: 'checkout', component: CheckoutComponent },
      { path: 'payment/:id', component: PaymentComponent },
      { path: 'order/:id', component: OrderComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
