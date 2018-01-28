import { Component, OnInit } from '@angular/core';
import { Building } from '../models/building';
import { CommunalProblem } from '../models/communal-problem';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { PagerService } from '../services/pager.service';
import { AuthService } from '../login/auth.service';
import { CommunalProblemService } from './communal-problem.service';

@Component({
  selector: 'app-communal-problems',
  templateUrl: './communal-problems.component.html',
  styleUrls: ['./communal-problems.component.css']
})
export class CommunalProblemsComponent implements OnInit {

  buildings: Building[];
  selectedBuilding: Building=null;
  tempSelectedBuilding: Building=null;

  problems:CommunalProblem[];
  problemsCount: number;

  subscription: Subscription;

  pager: any = {};

  canWeAddCommunalProblem:boolean=false

  constructor(private router: Router, 
              private problemSerivce: CommunalProblemService,
              private pagerService: PagerService,
              private authService: AuthService)  {

    this.subscription = problemSerivce.RegenerateData$
      .subscribe(() => {
        this.getBuildings();

      });
  }
  ngOnInit() {
    this.getBuildings();

  }


  getBuildings(){
    this.problemSerivce.getBuildings().then(
      buildings=>{
        this.buildings=buildings;
        console.log("buildings: "+JSON.stringify(buildings));
        if (buildings.length==1){
          this.selectedBuilding=buildings[0];
          this.problemSerivce.getCommunalProblemCount(this.selectedBuilding.id).then(
            count => {
              this.problemsCount = count;
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
 
  chooseBuilding(){
    
    this.selectedBuilding=this.tempSelectedBuilding;
    console.log("selected: "+JSON.stringify(this.selectedBuilding));
    this.problemSerivce.getCommunalProblemCount(this.selectedBuilding.id).then(
      count => {
        this.problemsCount = count;
        this.setPage(1);
      });
  }

  onSelectionChange(building:Building){
    console.log("on buildinf: "+JSON.stringify(building));
    this.tempSelectedBuilding=building;
    console.log("on selected-building: "+JSON.stringify(this.tempSelectedBuilding));
  }

  getCommunalProblems(page: number, size: number){
    this.problemSerivce.getCommunalProblems(page,size,this.selectedBuilding.id)
        .then(problems => {this.problems=problems;
          console.log("this.problems: "+JSON.stringify(this.problems));
          this.problems.sort(function(x, y) {
            // true values first
            return (x.dateOfRepair === y.dateOfRepair)? 0 : y.dateOfRepair? -1 : 1;
            // false values first
            // return (x === y)? 0 : x? 1 : -1;
        });
          console.log("active : "+JSON.stringify(this.canWeAddCommunalProblem));
        });
  }



  setPage(page: number){
    if (page < 1 || page > this.pager.totalPages){
      return;
    }

    this.pager = this.pagerService.getPager(this.problemsCount, page);
    this.getCommunalProblems(this.pager.currentPage - 1, this.pager.pageSize);
  }
  
  gotoGetProblem(id: number) {
      this.router.navigate(['/president/buildings/'+this.selectedBuilding.id+'/communalProblems', id]);
  }


  newProblem(){
    let route: string= '/president/buildings/'+this.selectedBuilding.id+'/communalProblems/add';
    console.log(route);
    this.router.navigate([route]);
  }
}
