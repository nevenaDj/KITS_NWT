import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Router, ActivatedRoute } from '@angular/router';
import { GlitchDataService } from '../glitch-details-company/glitch-data.service';
import { User } from '../../models/user';
import { Glitch } from '../../models/glitch';
import { CompanyDataService } from '../../home-company/company-data.service';
import { ToastsManager } from 'ng2-toastr';

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
              private glitchService: GlitchDataService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
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
    this.getCompany();
    this.companyService.getGlitch(+this.route.snapshot.params['id'])
        .then(glitch => {

          this.glitch = glitch;
          this.glitchService.getCompanies(this.glitch).then(
            companies=>{
              this.companies=companies;
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
  }


  goBack() {
    this.router.navigate(['/company/pricelist']);
  }


  update(){
    if (this.selected==null){
      this.toastr.error('You have to select a company');
    }else{
    this.glitch.companyID=this.selected.id;
    this.glitchService.setCompany(this.glitch,this.selected.id).then(company => {
      this.glitchService.announceChange();
      this.router.navigate(['/company/pendingGlitches']);}
    ).catch(
      error=>  this.toastr.error('Error during updating.')
    );
  }
  }
}
