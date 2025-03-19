import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class UpdateUserService {
  constructor(private http: HttpClient) { }
  private url = environment.apiUrl;
}
