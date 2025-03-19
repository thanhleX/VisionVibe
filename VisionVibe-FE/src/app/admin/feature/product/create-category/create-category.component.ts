import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductService } from '../../../service/product.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-category',
  standalone: false,
  templateUrl: './create-category.component.html',
  styleUrl: './create-category.component.scss'
})
export class CreateCategoryComponent {
  isSubmitting = false;
  categoryForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router,
    private toastrService: ToastrService) {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required]],
    });
  }

  onSubmit() {
    if (this.categoryForm.valid) {
      this.isSubmitting = true;
      this.productService.createNewCategory(this.categoryForm.value).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.toastrService.success('Category created successfully', 'Category Notification');
          this.router.navigate(['admin/product']);
        },
        error: () => {
          this.isSubmitting = false;
          this.toastrService.error("Can't create new category", 'Category Notification');
        }
      });
    }
  }
}
