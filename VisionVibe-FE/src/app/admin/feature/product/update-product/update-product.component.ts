import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../service/product.service';
import { Product } from '../../../../dto/Product';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-product',
  standalone: false,
  templateUrl: './update-product.component.html',
  styleUrl: './update-product.component.scss'
})
export class UpdateProductComponent {
  isSubmitting: boolean = false;
  productForm!: FormGroup;
  selectedThumbnail: File | null = null;
  thumbnailError: string | null = null;
  product!: Product;
  productId!: number;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router,
    private route: ActivatedRoute,
    private toastrService: ToastrService) {
    this.productForm = this.fb.group({
      name: ['', [Validators.required]],
      price: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      thumbnail: [null],
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.getProduct()
  }

  getProduct() {
    this.route.params.subscribe((param) => {
      this.productId = param['id'];
      this.productService.getProductById(this.productId).subscribe({
        next: (res) => {
          this.product = res.result;
          this.initForm();
        },
        error: () => { }
      })
    })
  }

  initForm() {
    this.productForm.patchValue({
      name: this.product.name,
      price: this.product.price,
      description: this.product.description
    })
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
      formData.append('description', this.productForm.get('description')?.value);

      const thumbnail = this.productForm.get('thumbnail')?.value;
      if (thumbnail) {
        formData.append('thumbnail', thumbnail);
      }

      this.productService.updateProductById(this.productId, formData).subscribe({
        next: (res) => {
          this.isSubmitting = false;
          this.router.navigate(['/admin/product']);
          this.toastrService.success(`product updated successfully`, `Product Notification`)
        }, error: () => {
          this.isSubmitting = false;
          this.toastrService.error(`can't update product`, `Product Notification`)
        }
      });
    }
  }
}
