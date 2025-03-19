import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductCategories } from '../../../../dto/ProductCategories';
import { Router } from '@angular/router';
import { ProductService } from '../../../service/product.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-sub-category',
  standalone: false,
  templateUrl: './create-sub-category.component.html',
  styleUrl: './create-sub-category.component.scss'
})
export class CreateSubCategoryComponent implements OnInit {
  isSubmitting: boolean = false;
  subCategoryForm!: FormGroup;
  categories: ProductCategories[] = [];

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router,
    private toastrService: ToastrService) {
    this.subCategoryForm = this.fb.group({
      name: ['', [Validators.required]],
      productCategoryId: ['', Validators.required],
      thumbnail: [null, Validators.required],
      description: ['', Validators.required]
    });
  }
  ngOnInit(): void {
    this.getProductCategory();
  }

  getProductCategory() {
    this.productService.getAllCategories().subscribe((res) => {
      this.categories = res.result.content;
    })
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.subCategoryForm.get('thumbnail')?.setValue(file);
    }
  }

  onSubmit() {
    if (this.subCategoryForm.valid) {
      this.isSubmitting = true;
      const formData = new FormData();
      formData.append('name', this.subCategoryForm.get('name')?.value);
      formData.append('productCategoryId', this.subCategoryForm.get('productCategoryId')?.value);
      formData.append('description', this.subCategoryForm.get('description')?.value);
      const thumbnail = this.subCategoryForm.get('thumbnail')?.value;
      formData.append('thumbnail', thumbnail);
      this.productService.createNewSubCategory(formData).subscribe({
        next: (res) => {
          this.isSubmitting = false;
          this.router.navigate(['/admin/product']);
          this.toastrService.success(`created new sub category successfully`, 'Sub category notification')
        }, error: () => {
          this.isSubmitting = false;
          this.toastrService.error(`can't create new sub category, check again`, 'Sub category notification')
        }
      })
    }
  }
}