import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogoutService } from '../../../auth/service/logout.service';
import { AuthService } from '../../../auth/service/auth.service';
import { JwtPayloadDto } from '../../../dto/JwtPayloadDto';

@Component({
  selector: 'app-side-nav',
  standalone: false,
  templateUrl: './side-nav.component.html',
  styleUrl: './side-nav.component.scss'
})
export class SideNavComponent implements OnInit {
  userInfo: JwtPayloadDto | undefined;
  constructor(
    private logoutService: LogoutService,
    private router: Router,
    private authService: AuthService) { }
  ngOnInit(): void {
    this.getUserInfo();
  }

  getUserInfo() {
    this.userInfo = this.authService.getUserInfo();
    this.userInfo?.scope
  }

  checkScope(roles: string[]): boolean {
    return roles.some(role => this.userInfo?.scope.includes(role))
  }

  logout() {
    this.logoutService.logout(localStorage.getItem("token") as string).subscribe({
      next: (res) => {
        this.authService.clearUserInfo();
        this.router.navigate(['/auth/login']);
      }
    })
  }
}
