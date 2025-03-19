import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { Blogs } from '../../dto/Blog';
import { PagedResult } from '../../dto/PagedResult';
import { BlogCategories } from '../../dto/BlogCategories';

@Injectable({
  providedIn: 'root'
})
export class FooterService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getAllBlog(blog: number, page: number, size: number): Observable<ApiResponse<PagedResult<Blogs[]>>> {
    return this.http.get<ApiResponse<PagedResult<Blogs[]>>>(`${this.url}/blog?blogCategoryId=${blog}&page=${page}&size=${size}`);
  }

  getAllBlogCategories(): Observable<ApiResponse<BlogCategories[]>> {
    return this.http.get<ApiResponse<BlogCategories[]>>(`${this.url}/blog-categories`);
  }
}
