import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { Apartment } from '../../models/apartment';
import { Notification } from '../../models/notification';
import { NotificationService } from '../notification.service';
import { ApartmentService } from '../../apartments/apartment.service';


@Component({
  selector: 'app-add-notification',
  templateUrl: './add-notification.component.html',
  styleUrls: ['./add-notification.component.css']
})
export class AddNotificationComponent implements OnInit {

  apartment: Apartment;
  notification: Notification;

  constructor(private notificationService: NotificationService,
              private apartmentService: ApartmentService,
              private location: Location) { 
    this.notification = {
      id: null,
      text: '',
      user: null,
      date: null
    }
  }

  ngOnInit() {
    this.apartment = this.apartmentService.getMyApartment();
  }

  save(){
    this.notificationService.addNotification(this.apartment.building.id, this.notification)
        .then(() => this.location.back());
  }

  cancel(){
    this.location.back();
  }

}
