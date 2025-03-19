import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../service/product.service';
import { FullProduct, ProductDto } from '../../../dto/FullProduct';
import { ProductDetailService } from '../../service/product-detail.service';
import { JwtPayloadDto } from '../../../dto/JwtPayloadDto';
import { AuthService } from '../../../auth/service/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-product',
  standalone: false,
  templateUrl: './product.component.html',
  styleUrl: './product.component.scss'
})
export class ProductComponent implements OnInit {
  userInfo: JwtPayloadDto | undefined;
  selectedCategoryId: number | null = null;
  selectedSubCategoryId: number | null = null;
  selectedProductId: number | null = null;
  data: FullProduct[] = [];
  isSubmitting = false;

  constructor(
    private productService: ProductService,
    private productDetailService: ProductDetailService,
    private authService: AuthService,
    private toastrService: ToastrService
  ) { }

  ngOnInit(): void {
    this.getAllProduct();
    this.getUserInfo();
  }

  getAllProduct() {
    this.productService.getAllProduct().subscribe({
      next: (res) => {
        this.data = res.result;
      }
    });
  }

  getUserInfo() {
    this.userInfo = this.authService.getUserInfo();
  }

  checkScope(roles: string[]): boolean {
    return roles.some(role => this.userInfo?.scope.includes(role));
  }

  deleteProductCategoryById(id: number) {
    this.isSubmitting = true;
    this.productService.deleteProductCategoryById(id).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.toastrService.success('Product category deleted successfully', 'Product category Notification');
        this.getAllProduct();
      },
      error: () => {
        this.isSubmitting = false;
        this.toastrService.error('Can\'t delete, please check again', 'Product category Notification');
      }
    });
  }

  deleteProductSubCategoryById(id: number) {
    this.isSubmitting = true;
    this.productService.deleteProductSubCategoryById(id).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.toastrService.success('Product sub-category deleted successfully', 'Product sub-category Notification');
        this.getAllProduct();
      },
      error: () => {
        this.isSubmitting = false;
        this.toastrService.error('Can\'t delete, please check again', 'Product sub-category Notification');
      }
    });
  }

  hasMaterial(product: ProductDto): boolean {
    return product.productDetailDtoList.some(detail => detail.productMaterialDto?.material);
  }

  toggleCategory(categoryId: number) {
    this.selectedCategoryId = this.selectedCategoryId === categoryId ? null : categoryId;
    this.selectedSubCategoryId = null;
    this.selectedProductId = null;
  }

  toggleSubCategory(subCategoryId: number) {
    this.selectedSubCategoryId = this.selectedSubCategoryId === subCategoryId ? null : subCategoryId;
    this.selectedProductId = null;
  }

  toggleProduct(productId: number) {
    this.selectedProductId = this.selectedProductId === productId ? null : productId;
  }

  deleteProduct(id: number) {
    this.isSubmitting = true;
    this.productService.deleteProductById(id).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.toastrService.success('Product deleted successfully', 'Product Notification');
        this.getAllProduct();
      },
      error: (err) => {
        this.isSubmitting = false;
        this.toastrService.error('Can\'t delete product, check again', 'Product Notification');
      }
    });
  }

  deleteProductDetail(id: number) {
    this.isSubmitting = true;
    this.productDetailService.deleteProductDetailById(id).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.toastrService.success('Product detail deleted successfully', 'Product detail Notification');
        this.getAllProduct();
      },
      error: () => {
        this.isSubmitting = false;
        this.toastrService.error('Can\'t delete product detail, check again', 'Product detail Notification');
      }
    });
  }
}
