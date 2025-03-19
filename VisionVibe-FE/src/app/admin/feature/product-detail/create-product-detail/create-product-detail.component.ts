import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ProductDetailService } from '../../../service/product-detail.service';
import { Color } from '../../../../dto/Color';
import { Material } from '../../../../dto/Material';
import { Product } from '../../../../dto/Product';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../service/product.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-product-detail',
  standalone: false,
  templateUrl: './create-product-detail.component.html',
  styleUrls: ['./create-product-detail.component.scss']
})
export class CreateProductDetailComponent {
  productDetailForm: FormGroup;
  colors: Color[] = [];
  materials: Material[] = [];
  selectedImages: string[] = [];
  product: Product | undefined;
  isSubmitting = false;
  productId: number | undefined;
  selectedColor: string = '';

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private productDetailService: ProductDetailService,
    private router: Router,
    private route: ActivatedRoute,
    private toastrService: ToastrService
  ) {
    this.productDetailForm = this.fb.group({
      product: ['', Validators.required],
      colorId: ['', Validators.required],
      materialId: [''],
      stock: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      images: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe((param) => {
      this.productId = param['id'];
      this.fetchDropdownData();
      if (this.productId) {
        this.getProductByProductId();
      } else {
        this.getProductByPreviousProduct();
      }
    });
  }

  updateSelectedColor(event: Event) {
    const selectedId = (event.target as HTMLSelectElement).value;
    const selectedColorObj = this.colors.find(color => color.id === Number(selectedId));
    this.selectedColor = selectedColorObj ? selectedColorObj.color : '';
  }

  fetchDropdownData() {
    this.productDetailService.getAllColors().subscribe(res => this.colors = res.result);
    this.productDetailService.getAllMaterials().subscribe(res => this.materials = res.result);
  }

  getProductByProductId() {
    if (this.productId) {
      this.productService.getProductById(this.productId).subscribe({
        next: (res) => {
          this.product = res.result;
          this.productDetailForm.patchValue({ product: this.product });
        }
      });
    }
  }

  getProductByPreviousProduct() {
    const productName = history.state.productName;
    this.productDetailService.getProductByProductName(productName).subscribe({
      next: (res) => {
        this.product = res.result;
        this.productDetailForm.patchValue({ product: this.product });
      }
    });
  }

  onFileChange(event: any) {
    const files = event.target.files;
    if (files) {
      const fileArray: File[] = Array.from(files);
      this.productDetailForm.patchValue({ images: fileArray });
      this.selectedImages = [];

      fileArray.forEach(file => {
        const reader = new FileReader();
        reader.onload = () => {
          if (reader.result) {
            this.selectedImages.push(reader.result as string);
          }
        };
        reader.readAsDataURL(file);
      });
    }
  }

  onSubmit() {
    this.isSubmitting = true;
    if (this.productDetailForm.valid) {
      const formData = this.createFormData();
      this.productDetailService.createNewProductDetail(formData).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.router.navigate(['/admin/product']);
          this.toastrService.success('Product created successfully', 'Product Notification');
        },
        error: () => {
          this.isSubmitting = false;
          this.toastrService.error('Cannot create product, please try again', 'Product Notification');
        }
      });
    }
  }

  private createFormData(): FormData {
    const formData = new FormData();
    const productId = this.productDetailForm.get('product')?.value.id;
    const colorId = this.productDetailForm.get('colorId')?.value;
    const materialId = this.productDetailForm.get('materialId')?.value;
    const stock = this.productDetailForm.get('stock')?.value;
    const images = this.productDetailForm.get('images')?.value;

    if (productId) formData.append('productId', productId);
    if (colorId) formData.append('colorId', colorId);
    if (materialId) formData.append('materialId', materialId);
    if (stock) formData.append('stock', stock);

    if (images && images.length > 0) {
      images.forEach((file: File) => {
        formData.append('images', file, file.name);
      });
    }
    return formData;
  }
}
