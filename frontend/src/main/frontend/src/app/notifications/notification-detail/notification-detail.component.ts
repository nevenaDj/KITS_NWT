import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Notification } from '../../models/notification';
import { NotificationService } from '../notification.service';
import { AuthService } from '../../login/auth.service';


@Component({
  selector: 'app-notification-detail',
  templateUrl: './notification-detail.component.html',
  styleUrls: ['./notification-detail.component.css']
})
export class NotificationDetailComponent implements OnInit {
  notification: Notification;
  username: string;

  constructor(private notificationService: NotificationService,
              private route: ActivatedRoute,
              private authService: AuthService,
              private location: Location) { }

  ngOnInit() {
    this.username = this.authService.getCurrentUser();
    this.notificationService.getNotification(+this.route.snapshot.params['id'])
        .then(notification => this.notification = notification);
  }

  deleteNotification(){
    this.notificationService.deleteNotification(this.notification.id)
        .then(() => this.location.back());

  }

}
