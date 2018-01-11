import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Building } from '../models/building';
import { BuildingService } from '../buildings/building.service';

@Component({
  selector: 'app-add-building',
  templateUrl: './add-building.component.html',
  styleUrls: ['./add-building.component.css']
})
export class AddBuildingComponent implements OnInit {

  building: Building;

  constructor(private router:Router,
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
  }

  ngOnInit() {
  }

  save(): void{
   this.buildingService.addBuilding(this.building)
      .then( building => { this.router.navigate(['buildings'])});
  }

}
