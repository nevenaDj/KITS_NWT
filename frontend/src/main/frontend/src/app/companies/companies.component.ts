import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { User } from '../models/user';
import { CompanyService } from './company.service';

@Component({
  selector: 'app-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css']
})
export class CompaniesComponent implements OnInit {
  companies: User[];

  subscription: Subscription;

  constructor(private router: Router,
              private companyService: CompanyService) {
    this.subscription = companyService.RegenerateData$
                .subscribe(() => this.getCompanies());
  }

  ngOnInit() {
    this.getCompanies();
  }

  gotoAdd(){
    this.router.navigate(['addCompany']);
  }

  getCompanies(){
    this.companyService.getCompanies().then(
      companies => this.companies = companies
    );
  }

  gotoGetCompany(id: number){
    this.router.navigate(['companies', id]);
  }

}
