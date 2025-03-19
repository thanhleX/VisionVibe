import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { Order } from '../../dto/Order';
import { environment } from '../../environment/environment';
import { PagedResult } from '../../dto/PagedResult';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  findAllOrders(page: number, size: number): Observable<ApiResponse<PagedResult<Order[]>>> {
    const token = localStorage.getItem('token');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.get<ApiResponse<PagedResult<Order[]>>>(`${this.url}/order?page=${page}&size=${size}`, { headers });
  }

  confirmOrder(id: number): Observable<any> {
    return this.http.get<any>(`${this.url}/order/confirm/${id}`)
  }

  denyOrder(id: number): Observable<any> {
    return this.http.get<any>(`${this.url}/order/deny/${id}`)
  }
}
