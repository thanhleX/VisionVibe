import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './feature/dashboard/dashboard.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { BlogComponent } from './feature/blog/blog.component';
import { ProductComponent } from './feature/product/product.component';
import { NotificationComponent } from './feature/notification/notification.component';

import { CreateBlogComponent } from './feature/blog/create-blog/create-blog.component';
import { SignUpComponent } from './feature/sign-up/sign-up.component';
import { UsersComponent } from './feature/users/users.component';
import { UpdateUserComponent } from './feature/users/update-user/update-user.component';
import { UpdateBlogComponent } from './feature/blog/update-blog/update-blog.component';
import { CreateProductComponent } from './feature/product/create-product/create-product.component';
import { CreateProductDetailComponent } from './feature/product-detail/create-product-detail/create-product-detail.component';
import { UpdateProductComponent } from './feature/product/update-product/update-product.component';
import { UpdateProductDetailComponent } from './feature/product-detail/update-product-detail/update-product-detail.component';
import { CreateCategoryComponent } from './feature/product/create-category/create-category.component';
import { CreateSubCategoryComponent } from './feature/product/create-sub-category/create-sub-category.component';

import { UpdateSubCategoryComponent } from './feature/product/update-sub-category/update-sub-category.component';
import { OrderComponent } from './feature/order/order.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [{
  path: '', component: LayoutComponent, canActivateChild: [AuthGuard], children: [
    { path: 'dashboard', component: DashboardComponent },
    { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    { path: 'blog', component: BlogComponent },
    { path: 'blog/create', component: CreateBlogComponent },
    { path: 'blog/update/:id', component: UpdateBlogComponent },
    { path: 'product', component: ProductComponent },
    { path: 'product/create', component: CreateProductComponent },
    { path: 'product/create/new-product-detail', component: CreateProductDetailComponent },
    { path: 'product/create/new-product-detail/:id', component: CreateProductDetailComponent },
    { path: 'product/update/:id', component: UpdateProductComponent },
    { path: 'product/update/product-detail/:id', component: UpdateProductDetailComponent },
    { path: 'product/create/product-category', component: CreateCategoryComponent },
    { path: 'product/update/product-sub-category/:id', component: UpdateSubCategoryComponent },
    { path: 'product/create/product-sub-category', component: CreateSubCategoryComponent },
    { path: 'order', component: OrderComponent },
    { path: 'notification', component: NotificationComponent },
    { path: 'sign-up', component: SignUpComponent },
    { path: 'users', component: UsersComponent },
    { path: 'users/update/:id', component: UpdateUserComponent }
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  providers: [],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
