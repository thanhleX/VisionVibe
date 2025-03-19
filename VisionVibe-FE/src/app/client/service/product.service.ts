import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { PagedResult } from '../../dto/PagedResult';
import { Product } from '../../dto/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  findAllProductBySubCategoryName(subCategoryName: string, page: number, size: number): Observable<ApiResponse<PagedResult<Product[]>>> {
    return this.http.get<ApiResponse<PagedResult<Product[]>>>(`${this.url}/product?subCategoryName=${subCategoryName}&page=${page}&size=${size}`)
  }
}
