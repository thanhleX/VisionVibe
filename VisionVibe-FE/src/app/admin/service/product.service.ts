import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { PagedResult } from '../../dto/PagedResult';
import { environment } from '../../environment/environment';
import { FullProduct } from '../../dto/FullProduct';
import { ProductCategories } from '../../dto/ProductCategories';
import { ProductSubCategories } from '../../dto/ProductSubCategories';
import { Product } from '../../dto/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getAllProduct(): Observable<ApiResponse<FullProduct[]>> {
    return this.http.get<ApiResponse<FullProduct[]>>(`${this.url}/product-categories/all`);
  }

  getAllCategories(): Observable<ApiResponse<PagedResult<ProductCategories[]>>> {
    return this.http.get<ApiResponse<PagedResult<ProductCategories[]>>>(`${this.url}/product-categories`)
  }

  getProductById(id: number): Observable<ApiResponse<Product>> {
    return this.http.get<ApiResponse<Product>>(`${this.url}/product/${id}`)
  }

  getAllSubCategoriesByCategoryId(id: number): Observable<ApiResponse<PagedResult<ProductSubCategories[]>>> {
    return this.http.get<ApiResponse<PagedResult<ProductSubCategories[]>>>(`${this.url}/product-sub-categories?productCategoryId=${id}`)
  }

  createNewProduct(formData: FormData): Observable<any> {
    return this.http.post<any>(`${this.url}/product`, formData);
  }

  createNewCategory(data: any): Observable<any> {
    return this.http.post<any>(`${this.url}/product-categories`, data)
  }

  deleteProductById(id: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/product/${id}`);
  }

  updateProductById(id: number, formData: FormData): Observable<any> {
    return this.http.put<any>(`${this.url}/product/${id}`, formData)
  }

  deleteProductCategoryById(id: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/product-categories/${id}`)
  }

  deleteProductSubCategoryById(id: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/product-sub-categories/${id}`)
  }

  updateProductSubCategoryById(formData: FormData, id: number): Observable<any> {
    return this.http.put<any>(`${this.url}/product-sub-categories/${id}`, formData)
  }

  getProductSubCategoryById(id: number): Observable<ApiResponse<ProductSubCategories>> {
    return this.http.get<any>(`${this.url}/product-sub-categories/${id}`)
  }

  createNewSubCategory(formData: FormData): Observable<any> {
    return this.http.post<any>(`${this.url}/product-sub-categories`, formData)
  }
}
