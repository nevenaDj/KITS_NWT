import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Building } from '../../models/building';
import { BuildingService } from '../../buildings/building.service';
import { Apartment } from '../../models/apartment';
import { ApartmentService } from '../../apartments/apartment.service';


@Component({
  selector: 'app-building-detail',
  templateUrl: './building-detail.component.html',
  styleUrls: ['./building-detail.component.css']
})
export class BuildingDetailComponent implements OnInit {
  building: Building;

  apartments: Apartment[];

  constructor(private router: Router,
              private route:ActivatedRoute,
              private location: Location,
              private buildingService: BuildingService,
              private apartmentService: ApartmentService) { 
    this.building = {
      id: null,
      address: {
        id: null,
        street:'',
        number: '',
        zipCode: null,
        city:''
      },
      president:{
        id: null,
        username:'',
        password:'',
        email:'',
        phoneNo:''
      }
    }
  }

  ngOnInit() {
    this.buildingService.getBuilding(+this.route.snapshot.params['id'])
        .then(building => {
          this.building = building;
          this.apartmentService.getApartments(building.id).then(apartments =>
              this.apartments = apartments);
        }
      );
  }

  gotoAddApartment(){
    this.router.navigate([`/buildings/${this.building.id}/addApartment`]);
  }

  gotoAddPresident(){
    this.router.navigate([`/buildings/${this.building.id}/addPresident`]);
  }

  gotoGetApartment(id: number){
    this.router.navigate([`/buildings/${this.building.id}/apartments/${id}`]);
  }

  deleteBuilding(){
    this.buildingService.deleteBuilding(this.building.id)
        .then(() => this.location.back() );

  }

  gotoEditBuilding(){
    this.router.navigate(['editBuilding', this.building.id]);
  }
 
}
