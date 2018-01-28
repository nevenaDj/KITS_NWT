import { Component, OnInit } from '@angular/core';
import { Building } from '../../models/building';
import { Meeting } from '../../models/meeting';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { MeetingsService } from '../meetings.service';
import { PagerService } from '../../services/pager.service';
import { AuthService } from '../../login/auth.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  buildings: Building[];
  selectedBuilding: Building=null;
  tempSelectedBuilding: Building=null;

  meetings:Meeting[];
  meetingsCount: number;

  subscription: Subscription;

  pager: any = {};

  canWeAddMeeting:boolean=false

  constructor(private router: Router, 
              private meetingSerivce: MeetingsService,
              private pagerService: PagerService,
              private authService: AuthService)  {

    this.subscription = meetingSerivce.RegenerateData$
      .subscribe(() => {
        this.getBuildings();

      });
  }
  ngOnInit() {
    this.getBuildings();

  }


  getBuildings(){
    if (this.authService.isOwner()){
      this.meetingSerivce.getBuildingsOwner().then(
        buildings=>{
          this.buildings=buildings;
          console.log("buildings: "+JSON.stringify(buildings));
          if (buildings.length==1){
            this.selectedBuilding=buildings[0];
            this.meetingSerivce.getMeetingsCount(this.selectedBuilding.id).then(
              count => {
                this.meetingsCount = count;
                this.setPage(1);
              }
            );
          }
          else{
            this.tempSelectedBuilding=buildings[0];
          }
        }
      )
    }
    if (this.authService.isTenant()){
      this.meetingSerivce.getBuildingsTenant().then(
        buildings=>{
          this.buildings=buildings;
          console.log("buildings: "+JSON.stringify(buildings));
          if (buildings.length==1){
            this.selectedBuilding=buildings[0];
            this.meetingSerivce.getMeetingsCount(this.selectedBuilding.id).then(
              count => {
                this.meetingsCount = count;
                this.setPage(1);
              }
            );
          }
          else{
            this.tempSelectedBuilding=buildings[0];
          }
        }
      )
    }
  }
 
  chooseBuilding(){
    
    this.selectedBuilding=this.tempSelectedBuilding;
    console.log("selected: "+JSON.stringify(this.selectedBuilding));
    this.meetingSerivce.getMeetingsCount(this.selectedBuilding.id).then(
      count => {
        this.meetingsCount = count;
        this.setPage(1);
      });
  }

  onSelectionChange(building:Building){
    console.log("on buildinf: "+JSON.stringify(building));
    this.tempSelectedBuilding=building;
    console.log("on selected-building: "+JSON.stringify(this.tempSelectedBuilding));
  }


  getMeetings(page: number, size: number){
    this.meetingSerivce.getMeetings(page,size,this.selectedBuilding.id)
        .then(meetings => {this.meetings=meetings;
        });
  }



  setPage(page: number){
    if (page < 1 || page > this.pager.totalPages){
      return;
    }

    this.pager = this.pagerService.getPager(this.meetingsCount, page);
    this.getMeetings(this.pager.currentPage - 1, this.pager.pageSize);
  }
  
  gotoGetMeeting(id: number) {
    if (this.authService.isOwner())
      this.router.navigate(['/owner/reports', id]);
    if (this.authService.isTenant())
      this.router.navigate(['/tenant/reports', id]);
  }

}
