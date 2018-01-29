import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Building } from '../models/building';
import { Meeting } from '../models/meeting';
import { Subscription } from 'rxjs/Subscription';
import { Router } from '@angular/router';
import { PagerService } from '../services/pager.service';
import { MeetingsService } from './meetings.service';
import { Builder } from 'selenium-webdriver';
import { ApartmentService } from '../apartments/apartment.service';
import { AuthService } from '../login/auth.service';
import { ToastsManager } from 'ng2-toastr';

@Component({
  selector: 'app-meetings',
  templateUrl: './meetings.component.html',
  styleUrls: ['./meetings.component.css']
})
export class MeetingsComponent implements OnInit {

  buildings: Building[];
  selectedBuilding: Building=null;
  tempSelectedBuilding: Building=null;

  meetings:Meeting[];
  meetingsCount: number;

  subscription: Subscription;

  pager: any = {};

  canWeAddMeeting:boolean=false;

  president: boolean;


  constructor(private router: Router, 
              private meetingSerivce: MeetingsService,
              private pagerService: PagerService,
              private apartmentService: ApartmentService,  
              private authService: AuthService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);

    this.subscription = meetingSerivce.RegenerateData$
      .subscribe(() => {
        this.getBuildings();

      });
  }
  ngOnInit() {
    if (this.router.url.startsWith("/president")){
      this.getBuildings();
      this.president= true;
    }else if (this.router.url.startsWith("/owner")){
      let apartment = this.apartmentService.getMyApartment();
      this.selectedBuilding = apartment.building;
      this.president = false;
      this.getMeetingsAll();
    }

  }


  getBuildings(){
    this.meetingSerivce.getBuildings().then(
      buildings=>{
        this.buildings=buildings;
        console.log("buildings: "+JSON.stringify(buildings));
        if (buildings.length==1){
          this.selectedBuilding=buildings[0];
          this.getMeetingsAll();
        }
      }
    )
  }

  getMeetingsAll(){
    this.meetingSerivce.getMeetingsCount(this.selectedBuilding.id).then(
      count => {
        this.meetingsCount = count;
        this.setPage(1);
      }
    );

  }
 
  chooseBuilding(){
    if (this.tempSelectedBuilding==null){
      this.toastr.error('You have to select a building!');
    }else{
    this.selectedBuilding=this.tempSelectedBuilding;
    this.meetingSerivce.getMeetingsCount(this.selectedBuilding.id).then(
      count => {
        this.meetingsCount = count;
        this.setPage(1);
      });
    }
  }

  onSelectionChange(building:Building){
    this.tempSelectedBuilding=building;
  }

  newMeeting(){
    let route: string= '/president/buildings/'+ this.selectedBuilding.id+'/meetings/add';
    this.router.navigate([route]);
  }

  getMeetings(page: number, size: number){
    this.meetingSerivce.getMeetings(page,size,this.selectedBuilding.id)
        .then(meetings => {this.meetings=meetings;
          this.canWeAddMeeting=true;
          for (let m of meetings){
            if (!m.active)
              this.canWeAddMeeting=false;
          }
          this.meetings.sort(function(x, y) {
            return (x.active === y.active)? 0 : y.active? -1 : 1;
        });
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
  
    if (this.authService.isPresident())
      this.router.navigate(['/president/meetings', id]);
    if (this.authService.isOwner())
      this.router.navigate(['/owner/meetings', id]);
    if (this.authService.isTenant())
      this.router.navigate(['/tenant/meetings', id]);
  }

  
}
