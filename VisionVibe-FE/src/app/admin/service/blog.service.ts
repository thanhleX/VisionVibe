import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { Blogs } from '../../dto/Blog';
import { BlogCategories } from '../../dto/BlogCategories';
import { PagedResult } from '../../dto/PagedResult';

@Injectable({
  providedIn: 'root'
})
export class BlogService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getBlogById(id: number): Observable<ApiResponse<Blogs>> {
    return this.http.get<ApiResponse<Blogs>>(`${this.url}/blog/${id}`)
  }

  getAllBlogCategories(): Observable<ApiResponse<BlogCategories[]>> {
    return this.http.get<ApiResponse<BlogCategories[]>>(`${this.url}/blog-categories`);
  }

  getAllBlogs(page: number, size: number): Observable<ApiResponse<PagedResult<Blogs[]>>> {
    return this.http.get<ApiResponse<PagedResult<Blogs[]>>>(`${this.url}/blog/all?page=${page}&size=${size}`)
  }

  addNewBlog(formData: FormData): Observable<ApiResponse<Blogs>> {
    return this.http.post<ApiResponse<Blogs>>(`${this.url}/blog`, formData);
  }

  deleteBlogById(id: number): Observable<ApiResponse<any>> {
    return this.http.delete<ApiResponse<any>>(`${this.url}/blog/${id}`)
  }

  updateBlogById(id: number, formData: FormData): Observable<ApiResponse<Blogs>> {
    return this.http.put<ApiResponse<Blogs>>(`${this.url}/blog/${id}`, formData)
  }

  setCarousel(id: number): Observable<any> {
    return this.http.put<any>(`${this.url}/blog/carousel/${id}`, {})
  }
}
