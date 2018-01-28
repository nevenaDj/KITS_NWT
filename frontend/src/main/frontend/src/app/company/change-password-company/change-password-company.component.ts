import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import * as decode from 'jwt-decode';

import { User } from '../../models/user';
import { UserPassword } from '../../models/user-password';
import { CompanyDataService } from '../../home-company/company-data.service';

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
     {currentPassword:'',
      newPassword1:'',
      newPassword2:''}
     this.company = {
      id: null,
      password: '',
      username: '',
      email: '',
      phoneNo:'',
      address:{
        city:'',
        id:null,
        number: '',
        street: '',
        zipCode: 0
      }
      }
    this.subscription = companyService.RegenerateData$
            .subscribe(() => this.getCompany());
  
  }

  ngOnInit() {
    this.getCompany();
  }
  
  getCompany(){
     this.companyService.getCompany().then(
      company => this.company = company
    );
  }
  
  saveCompany(){
    this.companyService.changePasswordCompany(this.userPassword).then(company => {
          this.companyService.announceChange();
          this.router.navigate(['/company/profile']);
        });
  }
  

  
  cancel(){
    this.router.navigate(['/company/profile']);
  }
  

}
