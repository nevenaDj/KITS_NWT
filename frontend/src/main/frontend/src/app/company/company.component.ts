import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Router } from '@angular/router';

import { User } from '../models/user';
import { Address } from '../models/address';
import { CompanyDataService } from '../home-company/company-data.service';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent implements OnInit {

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
    this.getCompany();
  }

  
  getCompany(){
     this.companyService.getCompany().then(
      company => {
        this.company = company;
        console.log("companyR:"+JSON.stringify(company));
        console.log("companythis:"+JSON.stringify(this.company));
        this.company.username=company.username;
       }
    );
    console.log("company:"+JSON.stringify(this.company));
  }

  goToUpdate(){
    this.router.navigate(['/company/update']);
  }
  

  
    
  goToChangePass(){
    this.router.navigate(['/company/changePassword']);
  }
}
