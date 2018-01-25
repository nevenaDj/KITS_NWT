import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Apartment } from '../../models/apartment';
import { ApartmentService } from '../apartment.service';


@Component({
  selector: 'app-current-apartment',
  templateUrl: './current-apartment.component.html',
  styleUrls: ['./current-apartment.component.css']
})
export class CurrentApartmentComponent implements OnInit {
  apartments: Apartment[];
  currentApartment: Apartment;

  constructor(private apartmentService: ApartmentService,
              private router: Router) { }

  ngOnInit() {
    if (this.router.url.startsWith("/tenant")){
      this.getApartmentsOfTenant();
    console.log(this.router.url);
    }else if (this.router.url.startsWith("/owner")){
      this.getApartmentsOfOwner();
    }
   
  }

  getApartmentsOfTenant(){
    this.apartmentService.getApartmentsOfTenant()
    .then(apartments => {
      this.apartments = apartments;
      if (apartments.length > 0){
        this.currentApartment = this.apartmentService.getMyApartment();
        if (this.currentApartment === undefined){
          this.apartmentService.setMyApartment(apartments[0]);
          this.currentApartment = this.apartmentService.getMyApartment();
        }
      }
    });
  }

  getApartmentsOfOwner(){
    this.apartmentService.getApartmentsOfOwner()
    .then(apartments => {
      this.apartments = apartments;
      if (apartments.length > 0){
        this.currentApartment = this.apartmentService.getMyApartment();
        if (this.currentApartment === undefined){
          this.apartmentService.setMyApartment(apartments[0]);
          this.currentApartment = this.apartmentService.getMyApartment();
        }
      }
    });
  }



  chooseApartment(apartment: Apartment){
    this.apartmentService.setMyApartment(apartment);
    this.currentApartment = this.apartmentService.getMyApartment();

  }

}
