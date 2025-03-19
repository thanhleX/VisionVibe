import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../dto/ApiResponse';
import { environment } from '../../environment/environment';
import { User } from '../../dto/User';
import { Role } from '../../dto/Role';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;

  getUserById(id: number): Observable<ApiResponse<User>> {
    return this.http.get<ApiResponse<User>>(`${this.url}/user/${id}`)
  }

  getAllUser(): Observable<ApiResponse<User[]>> {
    return this.http.get<ApiResponse<User[]>>(`${this.url}/user`)
  }

  deleteUserById(id: number): Observable<ApiResponse<any>> {
    return this.http.delete<ApiResponse<any>>(`${this.url}/user/${id}`)
  }

  getAllRoles(): Observable<ApiResponse<Role[]>> {
    return this.http.get<ApiResponse<Role[]>>(`${this.url}/role`);
  }

  updateUserById(user: any, id: number): Observable<ApiResponse<User>> {
    return this.http.put<ApiResponse<User>>(`${this.url}/user/${id}`, user)
  }
}
