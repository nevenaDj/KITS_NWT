import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

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
  complexForm: FormGroup;

  constructor(private router:Router,
              private route: ActivatedRoute,
              private location: Location,
              private buildingService: BuildingService,
              private formBuilder: FormBuilder) { 
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
    this.mode = 'ADD';

    this.complexForm = formBuilder.group({
      'street': [null, Validators.required], 
      'number': [null, Validators.required],
      'city': [null, Validators.required],
      'zipCode': [null, Validators.required]
    })
  }

  ngOnInit() {
    if (this.route.snapshot.params['id']){
      this.mode = 'UPDATE';
      this.buildingService.getBuilding(+this.route.snapshot.params['id'])
          .then(building => this.building = building);
    }
  }

  save(): void {
   this.mode == 'ADD' ? this.add() : this.update();
  }

  add(): void {
    this.buildingService.addBuilding(this.building)
      .then( building => { this.router.navigate(['buildings', building.id])});
  }

  update(): void {
    this.buildingService.updateBuilding(this.building)
        .then(building => {
          this.buildingService.announceChange();
          this.location.back();
        });
  }

  cancel(){
    this.location.back();
  }
}
