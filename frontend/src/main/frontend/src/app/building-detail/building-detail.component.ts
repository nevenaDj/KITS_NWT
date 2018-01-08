import { Component, OnInit } from '@angular/core';
import { ParamMap, Router, ActivatedRoute } from '@angular/router';

import { Building } from '../models/building';
import { BuildingService } from '../buildings/building.service';


@Component({
  selector: 'app-building-detail',
  templateUrl: './building-detail.component.html',
  styleUrls: ['./building-detail.component.css']
})
export class BuildingDetailComponent implements OnInit {
  building: Building;

  constructor(private router:ActivatedRoute,
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
    this.buildingService.getBuilding(+this.router.snapshot.params['id'])
        .then(building => this.building = building);
  }

 
}
