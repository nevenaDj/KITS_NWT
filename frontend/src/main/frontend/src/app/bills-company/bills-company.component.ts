import { CompanyDataService } from '../home-company/company-data.service';
import { Bill } from '../models/bill';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../models/user'
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-bills-company',
  templateUrl: './bills-company.component.html',
  styleUrls: ['./bills-company.component.css']
})
export class BillsCompanyComponent implements OnInit {

token = '';
  company: User;
  subscription: Subscription;
  bills: Bill[];

  constructor(private router: Router, private companyService: CompanyDataService) {
    this.company = {
      id: null,
      password: '',
      username: '',
      email: '',
      phoneNo: '',
      address: {
        city: '',
        id: null,
        number: '',
        street: '',
        zipCode: 0
      }
    };
    this.subscription = companyService.RegenerateData$
      .subscribe(() => this.getCompany());
   // this.subscription = companyService.RegenerateData$
     // .subscribe(() => this.getBills());
  }

  ngOnInit() {
    this.token = localStorage.getItem('token');
    this.getCompany();
    //this.getBills()
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);

  }

  getCompany() {
    this.companyService.getCompany().then(
      company => {this.company = company;
        console.log(JSON.stringify(this.company));
        this.companyService.getBills(1, 10, this.company.id).then(
      bills => this.bills = bills);
      }
    );
  }

  
  gotoGetBill(id: number) {
    this.router.navigate(['company/bills', id]);
  }

  goToActiveGlitches() {
    this.router.navigate(['company/activeGlitches']);
  }

  goToPendingGlitches() {
    this.router.navigate(['company/pendingGlitches']);
  }

  goToBills() {
    this.router.navigate(['company/bills']);
  }

  goToPricelist() {
    this.router.navigate(['company/pricelist']);
  }

  goToChangePass() {
    this.router.navigate(['company/changePassword']);
  }
}
