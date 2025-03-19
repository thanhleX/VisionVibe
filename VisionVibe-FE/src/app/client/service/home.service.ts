import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { Blogs } from '../../dto/Blog';
import { PagedResult } from '../../dto/PagedResult';
import { BlogCategories } from '../../dto/BlogCategories';
import { ProductSubCategories } from '../../dto/ProductSubCategories';
import { ProductCategories } from '../../dto/ProductCategories';

@Injectable({
  providedIn: 'root'
})
export class HomeService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getAllBlogs(blog: number, page: number, size: number): Observable<ApiResponse<PagedResult<Blogs[]>>> {
    return this.http.get<ApiResponse<PagedResult<Blogs[]>>>(`${this.url}/blog?blogCategoryId=${blog}&page=${page}&size=${size}`);
  }

  getAllBlogCategories(): Observable<ApiResponse<BlogCategories[]>> {
    return this.http.get<ApiResponse<BlogCategories[]>>(`${this.url}/blog-categories`);
  }

  getAllCarousels(page: number, size: number): Observable<ApiResponse<PagedResult<Blogs[]>>> {
    return this.http.get<ApiResponse<PagedResult<Blogs[]>>>(`${this.url}/blog/carousel?page=${page}&size=${size}`)
  }

  getAllProductSubCategories(productSubCategoryId: number): Observable<ApiResponse<PagedResult<ProductSubCategories[]>>> {
    return this.http.get<ApiResponse<PagedResult<ProductSubCategories[]>>>(`${this.url}/product-sub-categories?productCategoryId=${productSubCategoryId}`);
  }

  getAllProductCategories(): Observable<ApiResponse<PagedResult<ProductCategories[]>>> {
    return this.http.get<ApiResponse<PagedResult<ProductCategories[]>>>(`${this.url}/product-categories`);
  }
}
