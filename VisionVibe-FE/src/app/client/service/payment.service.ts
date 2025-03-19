import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { environment } from '../../environment/environment';
import { Order } from '../../dto/Order';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  setPendingById(id: number, token: string): Observable<ApiResponse<Order>> {
    return this.http.get<ApiResponse<Order>>(`${this.url}/order/pending/${id}?token=${token}`);
  }
}
