import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

import { Apartment } from '../../models/apartment';
import { Notification } from '../../models/notification';
import { NotificationService } from '../notification.service';
import { ApartmentService } from '../../apartments/apartment.service';
import { Building } from '../../models/building';
import { BuildingService } from '../../buildings/building.service';


@Component({
  selector: 'app-add-notification',
  templateUrl: './add-notification.component.html',
  styleUrls: ['./add-notification.component.css']
})
export class AddNotificationComponent implements OnInit {

  apartment: Apartment;
  building: Building;
  notification: Notification;

  buidlingID: number;

  constructor(private notificationService: NotificationService,
              private apartmentService: ApartmentService,
              private buildingService: BuildingService,
              private location: Location,
              private router: Router) { 
    this.notification = {
      id: null,
      text: '',
      user: null,
      date: null
    }
  }

  ngOnInit() {
    if (this.router.url.startsWith("/tenant") || this.router.url.startsWith("/owner")){
      this.apartment = this.apartmentService.getMyApartment();
      this.buidlingID = this.apartment.building.id;
    }else if (this.router.url.startsWith("/president")){
      this.building = this.buildingService.getMyBuilding();
      this.buidlingID = this.building.id;
    }
  }

  save(){
    this.notificationService.addNotification(this.buidlingID, this.notification)
        .then(() => this.location.back());
  }

  cancel(){
    this.location.back();
  }

}
