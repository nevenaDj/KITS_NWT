import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import * as decode from 'jwt-decode';

import { User } from '../../models/user';
import { UserPassword } from '../../models/user-password';
import { CompanyDataService } from '../../home-company/company-data.service';
import { ToastsManager } from 'ng2-toastr';

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
  
  constructor(private router: Router, private companyService: CompanyDataService,
    private toastr: ToastsManager, 
    private vcr: ViewContainerRef) { 
  this.toastr.setRootViewContainerRef(vcr);
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
    if (this.userPassword.currentPassword==='')
      this.toastr.error('Current password is missing');
    if (this.userPassword.newPassword1==='')
      this.toastr.error('New password is missing');
    if (this.userPassword.newPassword2===''){
      this.toastr.error('Your repeated password is missing.');
    }
    if (this.userPassword.newPassword2!==this.userPassword.newPassword1){
      this.toastr.error('The new passwords are not same!');
    }
    this.companyService.changePasswordCompany(this.userPassword).then(company => {
          this.companyService.announceChange();
          this.router.navigate(['/company/profile']);
        }).catch(error=> this.toastr.error('Your current password is bad!')
      );
  }
  
  cancel(){
    this.router.navigate(['/company/profile']);
  }
  

}
