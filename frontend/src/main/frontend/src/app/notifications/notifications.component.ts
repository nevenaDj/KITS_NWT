import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Notification } from '../models/notification';
import { NotificationService } from './notification.service';
import { Apartment } from '../models/apartment';
import { ApartmentService } from '../apartments/apartment.service';
import { PagerService } from '../services/pager.service';


@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  notifications: Notification[];

  notificationsCount: number;

  apartment: Apartment;

  pager: any = {};

  constructor(private notificationService: NotificationService,
              private apartmentService: ApartmentService,
              private pagerService: PagerService,
              private router: Router) { }

  ngOnInit() {
    this.apartment = this.apartmentService.getMyApartment();

    console.log(this.apartment);

    this.notificationService.getNotificationsCount(this.apartment.building.id)
        .then(count => {
          this.notificationsCount = count;
          this.setPage(1);
        });
  }

  getNotifications(page: number, size: number = 15){
    this.notificationService.getNotifications(this.apartment.building.id, page, size)
        .then(notifications => this.notifications = notifications);

  }

  setPage(page: number){
    if (page < 1 || page > this.pager.totalPages){
      return;
    }

    this.pager = this.pagerService.getPager(this.notificationsCount, page, 15);
    this.getNotifications(this.pager.currentPage - 1, this.pager.pageSize);
    console.log(this.notifications);
  }

  gotoAddNotification(){
    this.router.navigate(['/tenant/addNotification']);
  }
}

