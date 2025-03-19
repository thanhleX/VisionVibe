import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../../service/product.service';
import { ProductSubCategories } from '../../../../dto/ProductSubCategories';
import { ProductCategories } from '../../../../dto/ProductCategories';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-product',
  standalone: false,
  templateUrl: './create-product.component.html',
  styleUrl: './create-product.component.scss'
})
export class CreateProductComponent implements OnInit {
  isSubmitting: boolean = false;
  productForm!: FormGroup;
  categories: ProductCategories[] = [];
  subCategories: ProductSubCategories[] = [];
  selectedThumbnail: File | null = null;
  thumbnailError: string | null = null;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router,
    private toastrService: ToastrService) { }

  ngOnInit(): void {
    this.initForm();
    this.getAllCategories();
  }

  initForm() {
    this.productForm = this.fb.group({
      name: ['', [Validators.required]],
      price: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      productCategoryId: ['', Validators.required],
      productSubCategoryId: ['', Validators.required],
      thumbnail: [null, Validators.required],
      description: ['', Validators.required]
    });
  }

  getAllCategories() {
    this.productService.getAllCategories().subscribe({
      next: (res) => {
        this.categories = res.result.content;
      }
    });
  }

  onCategoryChange() {
    const categoryId = this.productForm.get('productCategoryId')?.value;
    if (categoryId) {
      this.getSubCategoriesByCategoryId(categoryId);
      this.productForm.get('productSubCategoryId')?.reset();
    }
    if (categoryId == "") {
      this.subCategories = [];
    }
  }

  getSubCategoriesByCategoryId(id: number) {
    this.productService.getAllSubCategoriesByCategoryId(id).subscribe({
      next: (res) => {
        this.subCategories = res.result.content;
      }
    });
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.productForm.get('thumbnail')?.setValue(file);
    }
  }

  onSubmit() {
    if (this.productForm.valid) {
      this.isSubmitting = true;
      const formData = new FormData();
      formData.append('name', this.productForm.get('name')?.value);
      formData.append('price', this.productForm.get('price')?.value);
      formData.append('productSubCategoryId', this.productForm.get('productSubCategoryId')?.value);
      formData.append('description', this.productForm.get('description')?.value);
      const thumbnail = this.productForm.get('thumbnail')?.value;
      formData.append('thumbnail', thumbnail);
      this.productService.createNewProduct(formData).subscribe({
        next: (res) => {
          this.isSubmitting = false;
          this.router.navigate(['/admin/product/create/new-product-detail'], { state: { productName: this.productForm.get('name')?.value } });
        }, error: () => {
          this.isSubmitting = false;
          this.toastrService.error(`Can't create new product`, 'Product Notification');
        }
      });
    }
  }
}
