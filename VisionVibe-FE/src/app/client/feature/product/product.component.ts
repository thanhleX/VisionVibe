import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../service/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Product, ProductDetailDto } from '../../../dto/Product';

@Component({
  selector: 'app-product',
  standalone: false,
  templateUrl: './product.component.html',
  styleUrl: './product.component.scss'
})
export class ProductComponent implements OnInit {
  productSubCategoryName!: string;
  productCategoryName!: string;
  products: Product[] = [];
  productPage: number = 0;
  productSize: number = 10;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe((param) => {
      this.productCategoryName = param['productCategoryName'];
      this.productSubCategoryName = param['productSubCategoryName'];
      this.getData();

    })
  }

  uniqueMaterials(details: ProductDetailDto[]): ProductDetailDto[] {
    const uniqueDetailSet = new Set<string>();
    return details.filter(detail => {
      const material = detail.productMaterialDto?.material;
      if (material && !uniqueDetailSet.has(material)) {
        uniqueDetailSet.add(material);
        return true;
      }
      return false;
    })
  }

  uniqueColor(details: ProductDetailDto[]): ProductDetailDto[] {
    const uniqueDetailSet = new Set<string>();
    return details.filter(detail => {
      const color = detail.productColorDto.color;
      if (color && !uniqueDetailSet.has(color)) {
        uniqueDetailSet.add(color)
        return true;
      }
      return false;
    })
  }





  getData() {
    this.productService.findAllProductBySubCategoryName(this.productSubCategoryName, this.productPage, this.productSize).subscribe({
      next: (res) => {
        this.products = res.result.content;

      }
    })
  }


}




