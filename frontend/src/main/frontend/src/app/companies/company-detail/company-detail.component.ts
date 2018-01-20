import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { User } from '../../models/user';
import { CompanyService } from '../../companies/company.service';


@Component({
  selector: 'app-company-detail',
  templateUrl: './company-detail.component.html',
  styleUrls: ['./company-detail.component.css']
})
export class CompanyDetailComponent implements OnInit {
  company: User;

  constructor(private route: ActivatedRoute,
              private location: Location,
              private companyService: CompanyService) { 
    this.company = {
      id: null,
      username: '',
      password: '',
      email: '',
      phoneNo: '',
      address: {
        id: null,
        street: '',
        number: '',
        city: '',
        zipCode: null
      }
    }
  }

  ngOnInit() {
    this.companyService.getCompany(+this.route.snapshot.params['id'])
        .then(company => this.company = company);
  }

  deleteCompany(){
    this.companyService.deleteCompany(this.company.id)
        .then(() => this.location.back());
  }
}