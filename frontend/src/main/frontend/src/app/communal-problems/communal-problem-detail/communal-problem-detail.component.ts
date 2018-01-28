import { Component, OnInit } from '@angular/core';
import { CommunalProblem } from '../../models/communal-problem';
import { Apartment } from '../../models/apartment';
import { Router, ActivatedRoute } from '@angular/router';
import { CommunalProblemService } from '../communal-problem.service';
import { AuthService } from '../../login/auth.service';

@Component({
  selector: 'app-communal-problem-detail',
  templateUrl: './communal-problem-detail.component.html',
  styleUrls: ['./communal-problem-detail.component.css']
})
export class CommunalProblemDetailComponent implements OnInit {

  problem: CommunalProblem;

  myApartments: Apartment[];

  owner:boolean=false;

  height:number=0;


  constructor( private router: Router,
    private route: ActivatedRoute,
    private communalServiceProblem: CommunalProblemService,
    private authService: AuthService) { }

  ngOnInit() {
   this.getProblem();
  }

  getProblem(){
    this.communalServiceProblem.getCommunalProblem(+this.route.snapshot.params['id']).then(
      problem=> {this.problem=problem;
        console.log("prob: "+JSON.stringify(this.problem));
        if (this.authService.isOwner())
        this.communalServiceProblem.myApartments().then(
          apartments=> {
            this.myApartments=apartments;
            this.deleteApartmants()
          }
        )
      }
    );
  }

  deleteApartmants(){
    let building_id=+this.route.snapshot.params['id_b'];
    for (var i = 0; i < this.myApartments.length; i++) {
      if (this.myApartments[i].building.id!=building_id){
        this.myApartments.splice(i, 1);
      }
    }
    if (this.myApartments.length!=0)
      this.owner=true;
    this.height=50+50*this.myApartments.length;
  }

  selectAparment(apartment:Apartment){
    let index= this.problem.apartments.indexOf(apartment) 
    if (index > -1){
      this.problem.apartments.splice(index, 1);
      this.communalServiceProblem.deleteApartment(this.problem.id, apartment.id);
    } else{
      this.problem.apartments.push(apartment);
      this.communalServiceProblem.addApartment(this.problem.id, apartment.id);
    }

  }
}
