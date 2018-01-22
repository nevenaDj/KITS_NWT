import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { Subscription } from 'rxjs/Subscription';
import { Glitch } from '../models/glitch';
import { Router, ActivatedRoute } from '@angular/router';
import { CompanyDataService } from '../home-company/company-data.service';
import { GlitchDataService } from '../glitch-details-company/glitch-data.service';

@Component({
  selector: 'app-send-to-the-other-company',
  templateUrl: './send-to-the-other-company.component.html',
  styleUrls: ['./send-to-the-other-company.component.css']
})
export class SendToTheOtherCompanyComponent implements OnInit {

  public selectedMoment = new Date();
  public min = new Date();
  
  token = '';
  company: User;
  subscription: Subscription;
  glitch: Glitch;
  companies: User[];
  selected:User;

  constructor(private router: Router,
              private route:ActivatedRoute,
              private companyService: CompanyDataService,
              private glitchService: GlitchDataService) {
    this.company = {
      id: null,
      password: '',
      username: '',
      email: '',
      phoneNo: '',
      address: {
        city: '',
        id: null,
        number: '',
        street: '',
        zipCode: 0
      }
    };
    this.subscription = companyService.RegenerateData$
      .subscribe(() => this.getCompany());

  }
  ngOnInit() {
    this.token = localStorage.getItem('token');
    this.getCompany();
    this.companyService.getGlitch(+this.route.snapshot.params['id'])
        .then(glitch => {
          console.log("id:"+JSON.stringify(this.route.snapshot.params['id']));
          console.log("glitch"+JSON.stringify(glitch));
          this.glitch = glitch;
          this.glitchService.getCompanies(this.glitch).then(
            companies=>{
              this.companies=companies;
              console.log("companies:"+JSON.stringify(this.companies))
            }
          )
        }
      );
  }

  getCompany() {
;    this.companyService.getCompany().then(
      company => {this.company = company;
      }
    );
  }

  onSelectionChange(company){
    this.selected=company;
    console.log(JSON.stringify("change:"+this.selected.username));
  }


  goToActiveGlitches() {
    this.router.navigate(['company/activeGlitches']);
  }

  goToPendingGlitches() {
    this.router.navigate(['company/pendingGlitches']);
  }

  goToBills() {
    this.router.navigate(['company/bills']);
  }

  goToPricelist() {
    this.router.navigate(['company/pricelist']);
  }

  goBack() {
    this.router.navigate(['company/pricelist']);
  }


  update(){
    this.glitch.companyID=this.selected.id;
    this.glitchService.setCompany(this.glitch,this.selected.id).then(company => {
      this.glitchService.announceChange();
      this.router.navigate(['company/pendingGlitches']);}
    );
  }
}
