import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

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
              private buildingService: BuildingService,
              private apartmentService: ApartmentService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
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
        phoneNo:'',
        address: null
      }
    }
  }

  ngOnInit() {
    this.buildingService.getBuilding(+this.route.snapshot.params['id'])
        .then(building => {
          this.building = building;
          this.apartmentService.getApartments(building.id)
              .then(apartments => this.apartments = apartments);
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

  gotoEditBuilding(){
    this.router.navigate(['editBuilding', this.building.id]);
  }

  deleteBuilding(){
    this.buildingService.deleteBuilding(this.building.id)
        .then(() => this.router.navigate(['buildings']) )
        .catch(error => this.toastr.error('The building can not be deleted.'));
  }
}
