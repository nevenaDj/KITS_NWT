import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { User } from '../models/user';
import { CompanyService } from './company.service';
import { PagerService } from '../pagination/pager.service';

@Component({
  selector: 'app-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css']
})
export class CompaniesComponent implements OnInit {
  companies: User[];

  subscription: Subscription;

  companiesCount: number;

  pager: any = {};

  constructor(private router: Router,
              private companyService: CompanyService,
              private pagerService: PagerService) {
    this.subscription = companyService.RegenerateData$
                .subscribe(() => this.getData());
  }

  ngOnInit() {
    this.getData();
  }

  gotoAdd(){
    this.router.navigate(['addCompany']);
  }

  getData(){
    this.companyService.getCompaniesCount()
        .then(count => {
          this.companiesCount = count;
          this.setPage(1);
        })

  }

   getCompanies(page: number, size: number){
    this.companyService.getCompanies(page, size).then(
      companies => this.companies = companies
    );
  } 

  gotoGetCompany(id: number){
    this.router.navigate(['companies', id]);
  }

  setPage(page: number){
    if (page < 1 || page > this.pager.totalPages){
      return;
    }

    this.pager = this.pagerService.getPager(this.companiesCount, page);
    console.log(this.pager);
    console.log(this.companiesCount);

    this.getCompanies(this.pager.currentPage - 1, this.pager.pageSize);

  }

}
