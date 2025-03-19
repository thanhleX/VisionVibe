import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { JwtPayloadDto } from '../../dto/JwtPayloadDto';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userInfo: JwtPayloadDto | undefined;

  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  decodeToken(token: string) {
    this.userInfo = jwtDecode(token);
    localStorage.setItem('userInfo', JSON.stringify(this.userInfo));
  }

  getUserInfo() {
    if (!this.userInfo) {
      const storedUserInfo = localStorage.getItem('userInfo');
      this.userInfo = storedUserInfo ? JSON.parse(storedUserInfo) : undefined;
    }
    return this.userInfo;
  }

  clearUserInfo() {
    this.userInfo = undefined;
    localStorage.removeItem('userInfo');
    localStorage.clear();
  }

  introspect(token: string): Observable<ApiResponse<any>> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<ApiResponse<any>>(`${this.url}/auth/introspect`, { token }, { headers })
  }

  refreshToken(token: string): Observable<ApiResponse<any>> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<ApiResponse<any>>(`${this.url}/auth/refresh`, { token }, { headers })
  }
}
