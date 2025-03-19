import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  logout(token: string) {
    return this.http.post(`${this.url}/auth/logout`, { token }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    });
  }
}
