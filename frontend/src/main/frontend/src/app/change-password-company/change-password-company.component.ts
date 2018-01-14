import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import * as decode from 'jwt-decode';

import { CompanyDataService } from '../home-company/company-data.service';
import { User } from '../models/user';
import { UserPassword } from '../models/user-password';

@Component({
  selector: 'app-change-password-company',
  templateUrl: './change-password-company.component.html',
  styleUrls: ['./change-password-company.component.css']
})
export class ChangePasswordCompanyComponent implements OnInit {

  token = '';
  company: User;
  subscription: Subscription;
  userPassword: UserPassword;
  
  constructor(private router: Router, private companyService: CompanyDataService) { 
     this.userPassword=
     {newPass:'',
      newPassAgain:'',
      oldPass:''}
    this.subscription = companyService.RegenerateData$
            .subscribe(() => this.getCompany());
  
  }

  ngOnInit() {
    this.token = localStorage.getItem('token');
    this.getCompany();
  }

  logout(){
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }
  
  getCompany(){
     this.companyService.getCompany().then(
      company => this.company = company
    );
  }
  
  saveCompany(){
    this.companyService.changePasswordCompany(this.userPassword).then(company => {
          this.companyService.announceChange();
          this.router.navigate(['company']);
        });
  }
  
  cancel(){
    this.router.navigate(['company']);
  }
  
    goToActiveGlitches(){
    this.router.navigate(['company/activeGlitches']);
  }
  
  goToPendingGlitches(){
    this.router.navigate(['company/pendingGlitches']);
  }
  
  goToBills(){
    this.router.navigate(['company/bills']);
  }
  
  goToPricelist(){
    this.router.navigate(['company/pricelist']);
  }
  
  goToChangePass(){
    this.router.navigate(['company/changePassword']);
  }

}
