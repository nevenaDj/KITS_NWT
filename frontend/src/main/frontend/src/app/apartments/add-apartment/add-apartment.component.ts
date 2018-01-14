import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { Apartment } from '../../models/apartment';
import { ApartmentService } from '../../apartments/apartment.service';


@Component({
  selector: 'app-add-apartment',
  templateUrl: './add-apartment.component.html',
  styleUrls: ['./add-apartment.component.css']
})
export class AddApartmentComponent implements OnInit {

  apartment: Apartment;
  buildingID: number;

  mode: string;

  constructor(private route:ActivatedRoute,
              private router: Router,
              private location: Location,
              private apartmentService: ApartmentService) { 
    this.apartment ={
      id: null,
      description: '',
      number:null,
      owner: {
        id: null,
        username: '',
        password: '',
        email: '',
        phoneNo: '',
        address: null
      }
    }
    this.buildingID = this.route.snapshot.params['idBuilding'];
    this.mode = 'ADD';
  }

  ngOnInit() {
    if(this.route.snapshot.params['id']){
      this.mode = 'UPDATE';
      this.apartmentService.getApartment(+this.route.snapshot.params['id'])
          .then(apartment => this.apartment = apartment);
    }
  }

  save(): void {
    this.mode == 'ADD' ? this.add() : this.update();
    

  }

  add(): void {
    this.apartmentService.addApartment(this.buildingID, this.apartment)
        .then(apartment => 
          this.router.navigate([`/buildings/${this.buildingID}/apartments/${apartment.id}`])
        );
  }

  update(): void {
    this.apartmentService.updateApartment(this.apartment)
        .then(apartment => {
          this.apartmentService.announceChange();
          this.location.back();
        });
  }

}
