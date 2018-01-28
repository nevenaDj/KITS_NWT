import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { Subscription } from 'rxjs/Subscription';
import * as decode from 'jwt-decode';

import { User } from '../../models/user';
import { CompanyDataService } from '../../home-company/company-data.service';


@Component({
  selector: 'app-update-company',
  templateUrl: './update-company.component.html',
  styleUrls: ['./update-company.component.css']
})
export class UpdateCompanyComponent implements OnInit {

  company: User;
  subscription: Subscription;
  
  constructor(private router: Router, private companyService: CompanyDataService,  private location: Location) { 
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
    this.companyService.updateCompany(this.company).then(company => {
          this.companyService.announceChange();
          this.router.navigate(['/company/profile']);
        });
  }
  
  cancel(){
    this.router.navigate(['/company/profile']);
  }
  
  goToChangePass(){
    this.router.navigate(['/company/changePassword']);
  }
}
