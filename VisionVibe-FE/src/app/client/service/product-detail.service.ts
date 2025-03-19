import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';

import { environment } from '../../environment/environment';
import { Product } from '../../dto/Product';
import { ProductDetail } from '../../dto/ProductDetail';


@Injectable({
  providedIn: 'root'
})
export class ProductDetailService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getProductDetailByProductName(productName: string): Observable<ApiResponse<ProductDetail[]>> {
    return this.http.get<ApiResponse<ProductDetail[]>>(`${this.url}/product-detail?productName=${productName}`)
  }

  getProductByProductName(productName: string): Observable<ApiResponse<Product>> {
    return this.http.get<ApiResponse<Product>>(`${this.url}/product/name?productName=${productName}`)
  }
}
