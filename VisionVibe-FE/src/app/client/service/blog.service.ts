import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { Blogs } from '../../dto/Blog';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class BlogService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getBlogById(id: number): Observable<ApiResponse<Blogs>> {
    return this.http.get<ApiResponse<Blogs>>(`${this.url}/blog/${id}`)
  }
}
