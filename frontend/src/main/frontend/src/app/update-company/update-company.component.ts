import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import * as decode from 'jwt-decode';

import { CompanyDataService } from '../home-company/company-data.service';
import { User } from '../models/user';

@Component({
  selector: 'app-update-company',
  templateUrl: './update-company.component.html',
  styleUrls: ['./update-company.component.css']
})
export class UpdateCompanyComponent implements OnInit {

  token = '';
  company: User;
  subscription: Subscription;
  
  constructor(private router: Router, private companyService: CompanyDataService) { 
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
    
  }
}
