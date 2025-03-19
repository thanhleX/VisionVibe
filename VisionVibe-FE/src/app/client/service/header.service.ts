import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { PagedResult } from '../../dto/PagedResult';
import { environment } from '../../environment/environment';
import { ProductCategories } from '../../dto/ProductCategories';
import { ProductSubCategories } from '../../dto/ProductSubCategories';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getAllProductCategories(): Observable<ApiResponse<PagedResult<ProductCategories[]>>> {
    return this.http.get<ApiResponse<PagedResult<ProductCategories[]>>>(`${this.url}/product-categories`);
  }

  getAllProductSubCategories(productSubCategoryId: number): Observable<ApiResponse<PagedResult<ProductSubCategories[]>>> {
    return this.http.get<ApiResponse<PagedResult<ProductSubCategories[]>>>(`${this.url}/product-sub-categories?productCategoryId=${productSubCategoryId}`);
  }
}
