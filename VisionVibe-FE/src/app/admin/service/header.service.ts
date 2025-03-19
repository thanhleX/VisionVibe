import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { BehaviorSubject } from 'rxjs';
import { NewOrderNotification } from '../../dto/NewOrderNotification';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  private notificationsSubject = new BehaviorSubject<NewOrderNotification | null>(null);
  notifications$ = this.notificationsSubject.asObservable();

  constructor(private http: HttpClient, private rxStompService: RxStompService) {
    this.rxStompService.watch('/topic/notifications').subscribe((message) => {
      const notification = JSON.parse(message.body);
      console.log('New notification:', notification);
      this.notificationsSubject.next(notification);
    });
  }
}
