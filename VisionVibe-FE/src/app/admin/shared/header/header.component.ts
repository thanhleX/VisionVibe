import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../auth/service/auth.service';
import { JwtPayloadDto } from '../../../dto/JwtPayloadDto';
import { Router } from '@angular/router';
import { HeaderService } from '../../service/header.service';
import { NotificationService } from '../../service/notification.service';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  showNotification = false;
  message: string = '';
  notification: string | null = null;
  userInfo: JwtPayloadDto | undefined | null;
  currentPage: string = "";

  notificationAlert = false;
  currentNotificationPage: number = 0;
  pageSize: number = 20;

  constructor(
    private authService: AuthService,
    private router: Router,
    private headerService: HeaderService,
    private notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.getNotificationFromWebSocket();
    this.getUserInfo();
    this.getCurrentPage();
    this.getAllNotifications();
  }

  getAllNotifications(): void {
    this.notificationService.getAllNotification(this.currentNotificationPage, this.pageSize, Number(this.userInfo?.sub)).subscribe({
      next: (res) => {
        this.notificationAlert = res.result.content.some(noti => noti.notificationStatus === 'NEW');
      },
      error: () => {
        // handle error if needed
      }
    });
  }

  navigateToNotification(): void {
    this.getAllNotifications();
    this.router.navigate(['/admin/notification']);
  }

  getUserInfo(): void {
    this.userInfo = this.authService.getUserInfo();
  }

  getCurrentPage(): void {
    this.router.events
      .subscribe(() => {
        const currentUrl = this.router.url;
        const segments = currentUrl.split('/');
        const adminIndex = segments.indexOf('admin');
        this.currentPage = segments.slice(adminIndex + 1).join('/');
      });
  }

  getNotificationFromWebSocket(): void {
    this.headerService.notifications$.subscribe(notification => {
      if (notification) {
        const orderId = notification.orderId;
        const orderStatus = notification.orderStatus;
        this.message = `New order with ID ${orderId} has been ${orderStatus}`;
        this.notificationAlert = true;
        this.showNotification = true;

        setTimeout(() => {
          this.showNotification = false;
        }, 5000);
      }
    });
  }
}
