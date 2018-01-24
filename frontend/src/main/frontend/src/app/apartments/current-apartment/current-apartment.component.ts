import { Component, OnInit } from '@angular/core';
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

  constructor(private apartmentService: ApartmentService) { }

  ngOnInit() {
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
        })
  }

  chooseApartment(apartment: Apartment){
    this.apartmentService.setMyApartment(apartment);
    this.currentApartment = this.apartmentService.getMyApartment();

  }

}
