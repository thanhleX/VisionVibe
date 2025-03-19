import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { Color } from '../../dto/Color';
import { Material } from '../../dto/Material';
import { environment } from '../../environment/environment';
import { Product } from '../../dto/Product';
import { ProductDetail } from '../../dto/ProductDetail';

@Injectable({
  providedIn: 'root'
})
export class ProductDetailService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getAllColors(): Observable<ApiResponse<Color[]>> {
    return this.http.get<ApiResponse<Color[]>>(`${this.url}/color`)
  }

  getAllMaterials(): Observable<ApiResponse<Material[]>> {
    return this.http.get<ApiResponse<Material[]>>(`${this.url}/material`)
  }

  getProductDetailById(id: number): Observable<ApiResponse<ProductDetail>> {
    return this.http.get<ApiResponse<ProductDetail>>(`${this.url}/product-detail/${id}`)
  }

  getProductByProductName(name: string): Observable<ApiResponse<Product>> {
    return this.http.get<ApiResponse<Product>>(`${this.url}/product/name?productName=${name}`)
  }

  createNewProductDetail(formData: FormData): Observable<any> {
    return this.http.post<any>(`${this.url}/product-detail`, formData)
  }

  updateProductDetailById(id: number, formData: FormData): Observable<any> {
    return this.http.put<any>(`${this.url}/product-detail/${id}`, formData)
  }

  deleteProductDetailById(id: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/product-detail/${id}`)
  }

  deleteProductImageById(id: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/image/${id}`)
  }
}
