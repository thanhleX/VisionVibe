import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { Order } from '../../dto/Order';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  findAllOrdersInMonth(): Observable<ApiResponse<Order[]>> {
    const token = localStorage.getItem('token');
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.get<ApiResponse<Order[]>>(`${this.url}/order/month`, {headers});
  }
}
