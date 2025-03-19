import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { PagedResult } from '../../dto/PagedResult';
import { environment } from '../../environment/environment';
import { ProductSubCategories } from '../../dto/ProductSubCategories';
import { ProductCategories } from '../../dto/ProductCategories';

@Injectable({
  providedIn: 'root'
})
export class ProductSubCategoriesService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getAllProductSubCategories(productSubCategoryId: number): Observable<ApiResponse<PagedResult<ProductSubCategories[]>>> {
    return this.http.get<ApiResponse<PagedResult<ProductSubCategories[]>>>(`${this.url}/product-sub-categories?productCategoryId=${productSubCategoryId}`);
  }

  getAllProductCategories(): Observable<ApiResponse<PagedResult<ProductCategories[]>>> {
    return this.http.get<ApiResponse<PagedResult<ProductCategories[]>>>(`${this.url}/product-categories`);
  }

  getProductSubCategoryByName(name: string): Observable<ApiResponse<ProductSubCategories[]>> {
    return this.http.get<ApiResponse<ProductSubCategories[]>>(`${this.url}/product-sub-categories/name?categoryName=${name}`);
  }
}
