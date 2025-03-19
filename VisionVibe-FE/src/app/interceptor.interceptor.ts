import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, switchMap, throwError } from 'rxjs';
import { AuthService } from './auth/service/auth.service';
import { Router } from '@angular/router';
import { ApiResponse } from './dto/ApiResponse';

@Injectable()
export class Interceptor implements HttpInterceptor {
  excludedUrls: string[] = [
    '/auth/refresh',
    '/auth/introspect'
  ];
  constructor(
    private authService: AuthService,
    private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const isExcluded = this.excludedUrls.some((url) => req.url.includes(url));
    if (isExcluded) {
      return next.handle(req); // ignore token for these urls
    }
    const token = localStorage.getItem('token');
    let authReq = req;

    if (token) {
      authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
    return next.handle(authReq)
      .pipe(
        catchError((error) => {
          if (error.status === 401 && token) {
            return this.authService.refreshToken(token).pipe(
              switchMap((res: ApiResponse<{ token: string }>) => {
                const newToken = res.result.token;
                localStorage.setItem('token', newToken);
                const newReq = req.clone({ setHeaders: { Authorization: `Bearer ${res.result.token}` } });
                return next.handle(newReq);
              }),
              catchError(() => {
                return throwError(() => new Error('Session expired.'));
              })
            );
          }
          this.logout();
          return throwError(() => error);
        })
      );
  }

  private logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/auth/login']);
  }
}

