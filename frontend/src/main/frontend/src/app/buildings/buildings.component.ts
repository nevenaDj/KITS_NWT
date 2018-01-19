import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Building } from '../models/building';
import { BuildingService } from './building.service';
import { PagerService } from '../services/pager.service';

@Component({
  selector: 'app-buildings',
  templateUrl: './buildings.component.html',
  styleUrls: ['./buildings.component.css']
})
export class BuildingsComponent implements OnInit {

  buildings: Building[];

  subscription: Subscription;

  buildingsCount: number;

  pager: any = {};

  constructor(private router: Router,
              private buildingService: BuildingService,
              private pagerService: PagerService) {
    this.subscription = buildingService.RegenerateData$
            .subscribe(() => this.getData());
  }

  ngOnInit() {
    this.getData();
  }

  gotoAdd(){
    this.router.navigate(['addBuilding']);
  }

  getData(){
    this.buildingService.getBuildingsCount()
        .then(count => {
          this.buildingsCount = count;
          this.setPage(1);
        });
  }

  getBuildings(page: number, size: number){
    this.buildingService.getBuildings(page,size)
        .then(buildings => this.buildings = buildings);
  }

  gotoGetBuilding(id:number){
    this.router.navigate(['buildings', id]);
  }

  setPage(page: number){
    if (page < 1 || page > this.pager.totalPages){
      return;
    }

    this.pager = this.pagerService.getPager(this.buildingsCount, page);
    this.getBuildings(this.pager.currentPage - 1, this.pager.pageSize);
  }
}
