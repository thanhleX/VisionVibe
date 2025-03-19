import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PagedResult } from '../../dto/PagedResult';
import { ApiResponse } from '../../dto/ApiResponse';
import { Observable } from 'rxjs';
import { Blogs } from '../../dto/Blog';
import { environment } from '../../environment/environment';
import { BlogCategories } from '../../dto/BlogCategories';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getAllNews(blogId: number, page: number, size: number): Observable<ApiResponse<PagedResult<Blogs[]>>> {
    return this.http.get<ApiResponse<PagedResult<Blogs[]>>>(`${this.url}/blog?blogCategoryId=${blogId}&page=${page}&size=${size}`);
  }

  getAllBlogCategories(): Observable<ApiResponse<BlogCategories[]>> {
    return this.http.get<ApiResponse<BlogCategories[]>>(`${this.url}/blog-categories`);
  }
}
