import { Injectable } from '@angular/core';
import { CanActivateChild, Router } from '@angular/router';
import { AuthService } from '../../auth/service/auth.service';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivateChild {
  constructor(
    private authService: AuthService,
    private router: Router) { }

  async canActivateChild(): Promise<boolean> {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const res = await firstValueFrom(this.authService.introspect(token));
        if (res.result.valid) {
          return true;
        } else {
          alert("Token expired");
          this.authService.clearUserInfo();
          this.router.navigate(['auth/login']);
          return false;
        }
      } catch (err) {
        alert('something wen\'t wrong')
        this.router.navigate(['auth/login']);
        return false;
      }
    } else {
      this.router.navigate(['auth/login']);
      return false;
    }
  }
}
