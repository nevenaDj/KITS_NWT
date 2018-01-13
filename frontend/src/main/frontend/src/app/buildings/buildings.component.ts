import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Building } from '../models/building';
import { BuildingService } from './building.service';

@Component({
  selector: 'app-buildings',
  templateUrl: './buildings.component.html',
  styleUrls: ['./buildings.component.css']
})
export class BuildingsComponent implements OnInit {

  buildings: Building[];

  subscription: Subscription;

  constructor(private router: Router,
              private buildingService: BuildingService) {
    this.subscription = buildingService.RegenerateData$
            .subscribe(() => this.getBuildings());
  }

  ngOnInit() {
    this.getBuildings();
  }

  gotoAdd(){
    this.router.navigate(['addBuilding']);
  }

  getBuildings(){
    this.buildingService.getBuildings().then(
      buildings => this.buildings = buildings
    );
  }

  gotoGetBuilding(id:number){
    this.router.navigate(['buildings', id]);
  }



}
