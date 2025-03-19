import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Color } from '../../../../dto/Color';
import { Material } from '../../../../dto/Material';
import { ProductDetailService } from '../../../service/product-detail.service';
import { ToastrService } from 'ngx-toastr';
import { ProductDetail } from '../../../../dto/ProductDetail';

@Component({
  selector: 'app-update-product-detail',
  standalone: false,
  templateUrl: './update-product-detail.component.html',
  styleUrl: './update-product-detail.component.scss'
})
export class UpdateProductDetailComponent {

  productDetailForm!: FormGroup;
  colors: Color[] = [];
  materials: Material[] = [];
  selectedImages: string[] = [];
  isSubmitting = false;
  productDetailId!: number;
  productDetail!: ProductDetail;

  constructor(
    private fb: FormBuilder,
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
      images: [null]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe((param) => {
      this.productDetailId = param['id'];
      if (this.productDetailId) {
        this.fetchDropdownData();
        this.getProductDetail();
      }
    });
  }

  getProductDetail() {
    if (this.productDetailId) {
      this.productDetailService.getProductDetailById(this.productDetailId).subscribe({
        next: (res) => {
          this.productDetail = res.result;
          this.productDetailForm.patchValue({
            product: this.productDetail.productDto.name,
            colorId: this.productDetail.productColorDto.id,
            materialId: this.productDetail.productMaterialDto?.id,
            stock: this.productDetail.stock,
          });
        },
        error: () => {
          this.router.navigate(['not-found'], { replaceUrl: true });
        }
      });
    }
  }

  fetchDropdownData() {
    this.productDetailService.getAllColors().subscribe(res => this.colors = res.result);
    this.productDetailService.getAllMaterials().subscribe(res => this.materials = res.result);
  }

  deleteImage(id: number) {
    this.isSubmitting = true;
    this.productDetailService.deleteProductImageById(id).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.toastrService.success('Product image deleted successfully', 'Product Image Notification');
        this.getProductDetail();
      },
      error: () => {
        this.toastrService.error("Can't delete image, check again", 'Product Image Notification');
        this.isSubmitting = false;
      }
    });
  }

  onFileChange(event: any) {
    const files = event.target.files;
    if (files) {
      const fileArray: File[] = Array.from(files);
      this.productDetailForm.patchValue({ images: fileArray });

      // Preview selected images
      this.selectedImages = [];
      for (let file of fileArray) {
        const reader = new FileReader();
        reader.onload = () => {
          if (reader.result) this.selectedImages.push(reader.result as string);
        };
        reader.readAsDataURL(file);
      }
    }
  }

  onSubmit() {
    this.isSubmitting = true;
    if (this.productDetailForm.valid) {
      const formData = this.createFormData();
      this.updateProductDetail(formData);
    }
  }

  createFormData(): FormData {
    const formData = new FormData();

    const colorId = this.productDetailForm.get('colorId')?.value;
    if (colorId)
      formData.append('colorId', colorId);

    const materialId = this.productDetailForm.get('materialId')?.value;
    if (materialId)
      formData.append('materialId', materialId);

    const stock = this.productDetailForm.get('stock')?.value;
    if (stock)
      formData.append('stock', stock);

    const images = this.productDetailForm.get('images')?.value;
    if (images && images.length > 0) {
      images.forEach((file: File) => {
        formData.append('images', file, file.name);
      });
    }

    return formData;
  }

  updateProductDetail(formData: FormData) {
    this.productDetailService.updateProductDetailById(this.productDetailId, formData).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigate(['/admin/product']);
        this.toastrService.success('Product detail updated successfully', 'Product Detail Notification');
      },
      error: () => {
        this.isSubmitting = false;
        this.toastrService.error("Can't update, check again", 'Product Detail Notification');
      }
    });
  }
}
