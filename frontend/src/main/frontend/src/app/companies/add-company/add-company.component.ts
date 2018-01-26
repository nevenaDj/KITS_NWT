import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { User } from '../../models/user';
import { CompanyService } from '../../companies/company.service';


@Component({
  selector: 'app-add-company',
  templateUrl: './add-company.component.html',
  styleUrls: ['./add-company.component.css']
})
export class AddCompanyComponent implements OnInit {
  company: User;
  complexForm: FormGroup;

  constructor(private router: Router,
              private companyService: CompanyService,
              private formBuilder: FormBuilder) { 
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
    this.complexForm = formBuilder.group({
      'username': [null, Validators.required],
      'street': [null, Validators.required],
      'number': [null, Validators.required],
      'city': [null, Validators.required],
      'zipCode': [null, Validators.required]
    })
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

  cancel(){
    this.router.navigate(['companies']);
  }

}
