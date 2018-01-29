import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { CommunalProblem } from '../../models/communal-problem';
import { CommunalProblemService } from '../communal-problem.service';
import { Router, ActivatedRoute } from '@angular/router';
import { GlitchType } from '../../models/glitch-type';
import { GlitchTypeService } from '../../glitch-types/glitch-type.service';
import { Glitch } from '../../models/glitch';
import { User } from '../../models/user';
import { ToastsManager } from 'ng2-toastr';

@Component({
  selector: 'app-add-communal-problem',
  templateUrl: './add-communal-problem.component.html',
  styleUrls: ['./add-communal-problem.component.css']
})
export class AddCommunalProblemComponent implements OnInit {

  public selectedMoment = new Date();
  public min = new Date();
  
  types:GlitchType[];
  companies:User[];

  selectedType:GlitchType;
  selectedCompany:User;

  problem: CommunalProblem;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private communalServiceProblem: CommunalProblemService,
              private glitchTypeService: GlitchTypeService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
      this.toastr.setRootViewContainerRef(vcr);
      this.min.setDate(this.min.getDate() + 1);
      this.problem={
        id:null,
        apartments:[],
        companyID:null,
        dateOfRepair: new Date(),
        description:'',
        type:null
      }
   }

  ngOnInit() {
    this.getTypes();
  }

  getTypes(){
    this.glitchTypeService.getGlitchTypes().then(
      types=> {this.types=types;
      }
  );
  }

  public myFilter = (d: Date): boolean => {
    const day = d.getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  }
  

  save(){
    if (this.selectedType==null){
      this.toastr.error('You have to select a type!');
    } else if (this.selectedCompany==null){
      this.toastr.error('You have to select a company!');
    } else if (this.problem.description==''){
      this.toastr.error('You dont have a description!');
    }else{
      this.problem.dateOfRepair=this.selectedMoment;
      this.problem.companyID=this.selectedCompany.id;
      this.problem.type=this.selectedType;
      this.communalServiceProblem.addCommunalProblem(+this.route.snapshot.params['id'], this.problem).then(
        prob => this.router.navigate(['/president/communalProblems'])
      )
    }
  }

  selectType(type:GlitchType){
    this.selectedCompany=null;
    this.selectedType=type;
    this.communalServiceProblem.getCompaniesByGlitch(type.id).then(
      companies=> this.companies=companies
    )

  }

  selectCompany(com:User){
    this.selectedCompany=com;
  }
}
