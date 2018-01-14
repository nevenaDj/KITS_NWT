import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../models/user';
import { CompanyService } from '../companies/company.service';


@Component({
  selector: 'app-add-company',
  templateUrl: './add-company.component.html',
  styleUrls: ['./add-company.component.css']
})
export class AddCompanyComponent implements OnInit {
  company: User;

  constructor(private router: Router,
              private companyService: CompanyService) { 
    this.company = {
      id: null,
      username: '',
      password: '',
      email: '',
      phoneNo: '',
      address:{
        city:'',
        id:null,
        number: '',
        street: '',
        zipCode: 0
      }
    }
  }

  ngOnInit() {
  }

  save(): void {
    this.companyService.addCompany(this.company)
        .then(company => {
          console.log(company);
          this.router.navigate(['companies']);
        })

  }

}
