import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import * as decode from 'jwt-decode';

import { CompanyDataService } from './company-data.service'
import { User } from '../models/user'
import { Address } from '../models/address'



@Component({
  selector: 'app-home-company',
  templateUrl: './home-company.component.html',
  styleUrls: ['./home-company.component.css']
})
export class HomeCompanyComponent implements OnInit {

  token = '';
  company: User;
  subscription: Subscription;
  address: Address;
  
  constructor(private router: Router, private companyService: CompanyDataService) { 
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
    this.token = localStorage.getItem('token');
    
    this.getCompany();
  }

  logout(){
    localStorage.removeItem('token');
    this.router.navigate(['login']);

  }
  
  getCompany(){
     this.companyService.getCompany().then(
      company => {
        this.company = company;
        console.log("companyR:"+JSON.stringify(company));
        console.log("companythis:"+JSON.stringify(this.company));
        this.company.username=company.username;
        this.router.navigate(['/company/profile']);
       }
    );
    console.log("company:"+JSON.stringify(this.company));
  }

  goToUpdate(){
    this.router.navigate(['/company/update']);
  }
  
  gotoProfile(){
    this.router.navigate(['/company/profile']);
  }

  
    
  goToChangePass(){
    this.router.navigate(['company/changePassword']);
  }
}
