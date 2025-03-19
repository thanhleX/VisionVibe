import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './feature/dashboard/dashboard.component';
import { HeaderComponent } from './shared/header/header.component';
import { SideNavComponent } from './shared/side-nav/side-nav.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BlogComponent } from './feature/blog/blog.component';
import { ProductComponent } from './feature/product/product.component';
import { CreateBlogComponent } from './feature/blog/create-blog/create-blog.component';
import { EditorModule } from '@tinymce/tinymce-angular';
import { UpdateBlogComponent } from './feature/blog/update-blog/update-blog.component';
import { SignUpComponent } from './feature/sign-up/sign-up.component';
import { UsersComponent } from './feature/users/users.component';
import { UpdateUserComponent } from './feature/users/update-user/update-user.component';
import { CreateProductComponent } from './feature/product/create-product/create-product.component';
import { UpdateProductComponent } from './feature/product/update-product/update-product.component';
import { DivDraggerDirective } from './directive/div-dragger.directive';
import { UpdateProductDetailComponent } from './feature/product-detail/update-product-detail/update-product-detail.component';
import { CreateCategoryComponent } from './feature/product/create-category/create-category.component';
import { CreateSubCategoryComponent } from './feature/product/create-sub-category/create-sub-category.component';
import { UpdateSubCategoryComponent } from './feature/product/update-sub-category/update-sub-category.component';
import { OrderComponent } from './feature/order/order.component';
import { NotificationComponent } from './feature/notification/notification.component';
import { BaseChartDirective, provideCharts, withDefaultRegisterables } from 'ng2-charts';
import { CreateProductDetailComponent } from './feature/product-detail/create-product-detail/create-product-detail.component';


@NgModule({
  declarations: [
    DashboardComponent,
    HeaderComponent,
    SideNavComponent,
    LayoutComponent,
    BlogComponent,
    ProductComponent,
    CreateBlogComponent,
    UpdateBlogComponent,
    SignUpComponent,
    UsersComponent,
    UpdateUserComponent,
    CreateProductComponent,
    CreateProductDetailComponent,
    UpdateProductComponent,
    UpdateProductDetailComponent,
    DivDraggerDirective,
    CreateCategoryComponent,
    CreateSubCategoryComponent,
    UpdateSubCategoryComponent,
    OrderComponent,
    NotificationComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    EditorModule,
    ReactiveFormsModule,
    BaseChartDirective
  ],
  providers: [provideCharts(withDefaultRegisterables())],
})
export class AdminModule { }
