import { Component } from '@angular/core';
import { ProductDetailService } from '../../service/product-detail.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CartService } from '../../service/cart.service';
import { CartItem } from '../../../dto/CartItem';
import { Product, ProductColorDto, ProductMaterialDto } from '../../../dto/Product';
import { ProductDetail, ProductImageResponse } from '../../../dto/ProductDetail';
import { DomSanitizer } from '@angular/platform-browser';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-product-detail',
  standalone: false,
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss']
})
export class ProductDetailComponent {
  selectedColor: string | undefined;
  selectedMaterial: string | undefined;
  counter: number = 1;
  selectedImg: string | undefined;
  categoryName: string | undefined;
  subCategoryName: string | undefined;
  currentProductDetail: ProductDetail | undefined;
  productName: string | undefined;
  product: Product | undefined
  trustedHtml: any;
  productDetail: ProductDetail[] = [];
  productColors: ProductColorDto[] = [];
  productMaterials: ProductMaterialDto[] = [];
  filteredImages: ProductImageResponse[] = [];

  constructor(
    private productDetailService: ProductDetailService,
    private cartService: CartService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private toastrService: ToastrService) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.categoryName = param['productCategoryName'];
      this.subCategoryName = param['productSubCategoryName'];
      this.productName = param['productName'];
      this.getData();
      this.updateImage();
      this.getFilteredImages()
    });
  }

  getData() {
    if (this.productName)
      this.productDetailService.getProductDetailByProductName(this.productName).subscribe({
        next: (res) => {
          this.productDetail = res.result;

          this.productDetail.forEach(detail => {
            if (this.productColors.every(color => color.id != detail.productColorDto.id))
              this.productColors.push(detail.productColorDto);
          })

          this.productDetail.forEach(detail => {
            if (detail.productMaterialDto)
              if (this.productMaterials?.every(material => material.id != detail.productMaterialDto?.id)) {
                this.productMaterials?.push(detail.productMaterialDto);
              }
          })

          if (this.productDetail[0]?.productMaterialDto)
            this.selectedMaterial = this.productDetail[0]?.productMaterialDto?.material;

          this.selectedColor = this.productDetail[0]?.productColorDto.color;

          this.selectedImg = this.productDetail.filter(
            product => product.productColorDto?.color === this.selectedColor
          )[0]?.productImageResponseList[0]?.url || "";

          this.getFilteredImages();
        }
      })

    if (this.productName)
      this.productDetailService.getProductByProductName(this.productName).subscribe({
        next: (res) => {
          this.product = res.result;
          this.trustedHtml = this.sanitizer.bypassSecurityTrustHtml(this.product.description);
        }
      })
  }

  addToCart(): void {
    const matchingProduct = this.productDetail.find(productDetail =>
      productDetail.productColorDto.color === this.selectedColor &&
      productDetail.productMaterialDto?.material === this.selectedMaterial
    );
    if (matchingProduct) {
      const cartItem: CartItem = {
        productDetail: matchingProduct,
        amount: this.counter
      };
      this.cartService.addToCart(cartItem)
      this.toastrService.success(`Added to cart successfully`, `Cart Notification`)
    } else {
      this.toastrService.error(`Can't add to cart, check again`, `User Notification`)
    }
  }

  selectMaterial(material: string) {
    this.selectedMaterial = material;
  }

  selectColor(color: string) {
    this.selectedColor = color;
    this.updateImage();
    this.getFilteredImages()
  }

  selectImg(url: string) {
    this.selectedImg = url;
  }

  updateImage() {
    const matchingProduct = this.productDetail.find(productDetail =>
      productDetail.productColorDto?.color === this.selectedColor
    );
    this.selectedImg = matchingProduct?.productImageResponseList[0]?.url || '';
  }

  getFilteredImages() {
    this.filteredImages = this.productDetail.filter(productDetail =>
      productDetail.productColorDto?.color == this.selectedColor
    ).flatMap(product => product.productImageResponseList || []);
  }

  increase() {
    this.currentProductDetail = this.productDetail.find(productDetail =>
      productDetail.productColorDto?.color === this.selectedColor && productDetail.productMaterialDto?.material === this.selectedMaterial
    );

    if (this.currentProductDetail && this.currentProductDetail.stock > this.counter)
      this.counter++;
  }

  decrease() {
    const filteredProduct = this.productDetail.find(product =>
      product.productColorDto?.color === this.selectedColor && product.productMaterialDto?.material === this.selectedMaterial
    );

    if (this.counter > 0 && filteredProduct)
      this.counter--;
  }
}
