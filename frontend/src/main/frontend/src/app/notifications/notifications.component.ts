import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Notification } from '../models/notification';
import { NotificationService } from './notification.service';
import { Apartment } from '../models/apartment';
import { ApartmentService } from '../apartments/apartment.service';
import { PagerService } from '../services/pager.service';
import { BuildingService } from '../buildings/building.service';
import { Building } from '../models/building';


@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  notifications: Notification[];

  notificationsCount: number;

  apartment: Apartment;
  building: Building;
  buildingID: number;

  pager: any = {};

  constructor(private notificationService: NotificationService,
              private apartmentService: ApartmentService,
              private buildingService: BuildingService,
              private pagerService: PagerService,
              private router: Router) { }

  ngOnInit() {
    if (this.router.url.startsWith("/tenant") || this.router.url.startsWith("/owner")){
      this.apartment = this.apartmentService.getMyApartment();
      this.buildingID = this.apartment.building.id;
    }
    if (this.router.url.startsWith("/president")){
      this.building = this.buildingService.getMyBuilding();
      this.buildingID = this.building.id;
    }

    console.log(this.apartment);
    console.log(this.building);

    this.notificationService.getNotificationsCount(this.buildingID)
        .then(count => {
          this.notificationsCount = count;
          this.setPage(1);
        });
  }

  getNotifications(page: number, size: number = 15){
    this.notificationService.getNotifications(this.buildingID, page, size)
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
    if (this.router.url.startsWith("/tenant")){
      this.router.navigate(['/tenant/addNotification']);
    }else if (this.router.url.startsWith("/owner")){
      this.router.navigate(['/owner/addNotification']);
    }else if(this.router.url.startsWith("/president")){
      this.router.navigate(['/president/addNotification']);
    }
  }
}

