import { Component, OnInit } from '@angular/core';
import { Building } from '../../models/building';
import { BuildingService } from '../building.service';

@Component({
  selector: 'app-current-building',
  templateUrl: './current-building.component.html',
  styleUrls: ['./current-building.component.css']
})
export class CurrentBuildingComponent implements OnInit {
  buildings: Building[];
  currentBuilding: Building;

  constructor(private buildingService: BuildingService) { }

  ngOnInit() {
    this.buildingService.getBuildingsOfPresident()
        .then(buildings => {
          this.buildings = buildings;
          if (buildings.length > 0){
            this.currentBuilding = this.buildingService.getMyBuilding();
            if (this.currentBuilding === undefined){
              this.buildingService.setMyBuilding(buildings[0]);
              this.currentBuilding = this.buildingService.getMyBuilding();
            }
          }
        });
  }

  chooseBuilding(building: Building){
    this.buildingService.setMyBuilding(building);
    this.currentBuilding = this.buildingService.getMyBuilding();

  }

}
