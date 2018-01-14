import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Building } from '../../models/building';
import { BuildingService } from '../../buildings/building.service';


@Component({
  selector: 'app-add-building',
  templateUrl: './add-building.component.html',
  styleUrls: ['./add-building.component.css']
})
export class AddBuildingComponent implements OnInit {

  building: Building;

  mode: string;

  constructor(private router:Router,
              private route: ActivatedRoute,
              private location: Location,
              private buildingService: BuildingService) { 
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
    this.mode = 'ADD';
  }

  ngOnInit() {
    if (this.route.snapshot.params['id']){
      this.mode = 'EDIT';
      this.buildingService.getBuilding(+this.route.snapshot.params['id'])
          .then(building => this.building = building);
    }
  }

  save(): void {
   this.mode == 'ADD' ? this.add() : this.edit();
  }

  add(): void {
    this.buildingService.addBuilding(this.building)
      .then( building => { this.router.navigate(['buildings', building.id])});
  }

  edit(): void {
    this.buildingService.updateBuilding(this.building)
        .then(building => {
          this.buildingService.announceChange();
          this.location.back();
        });
  }
}
