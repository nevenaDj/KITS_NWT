import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Building } from '../models/building';
import { BuildingService } from '../buildings/building.service';


@Component({
  selector: 'app-building-detail',
  templateUrl: './building-detail.component.html',
  styleUrls: ['./building-detail.component.css']
})
export class BuildingDetailComponent implements OnInit {
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
   console.log(this.building);
   this.buildingService.addBuilding(this.building)
      .then( building => { console.log(building);
                           this.router.navigate(['buildings']);
      });
  }
}
