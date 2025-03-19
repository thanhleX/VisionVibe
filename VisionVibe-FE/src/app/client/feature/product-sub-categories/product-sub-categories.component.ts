import { Component, OnInit } from '@angular/core';
import { ProductSubCategoriesService } from '../../service/product-sub-categories.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductSubCategories } from '../../../dto/ProductSubCategories';

@Component({
  selector: 'app-product-sub-categories',
  standalone: false,
  templateUrl: './product-sub-categories.component.html',
  styleUrl: './product-sub-categories.component.scss'
})
export class ProductSubCategoriesComponent implements OnInit {
  productCategoryName: string = "";
  productSubCategories: ProductSubCategories[] | undefined;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private productSubCategoriesService: ProductSubCategoriesService) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.productCategoryName = param['productCategoryName'];
      this.getAllProductSubCategories();
    })
  }

  getAllProductSubCategories() {
    this.productSubCategoriesService.getProductSubCategoryByName(this.productCategoryName).subscribe({
      next: (res) => {
        this.productSubCategories = res.result;
        const foundSubCategory = this.productSubCategories.find(subCategoy => subCategoy.productCategoryDto.name == this.productCategoryName);
        if (!foundSubCategory)
          this.router.navigate(['not-found'], { replaceUrl: true });
      },
      error: () => { }
    })
  }
}
