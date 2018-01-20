import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs/Subscription';
import * as decode from 'jwt-decode';

import {CompanyDataService} from '../home-company/company-data.service';
import {Glitch} from '../models/glitch';
import {User} from '../models/user';

@Component({
  selector: 'app-active-glitches-company',
  templateUrl: './active-glitches-company.component.html',
  styleUrls: ['./active-glitches-company.component.css']
})
export class ActiveGlitchesCompanyComponent implements OnInit {

  token = '';
  company: User;
  subscription: Subscription;
  glitches: Glitch[];

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
    this.subscription = companyService.RegenerateData$
      .subscribe(() => this.getActiveGlitches());
  }

  ngOnInit() {
    this.token = localStorage.getItem('token');
    this.getCompany();
    this.getActiveGlitches()
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);

  }

  getCompany() {
    this.companyService.getCompany().then(
      company => this.company = company
    );
  }

    getActiveGlitches() {
    this.companyService.getActiveGlitches().then(
      glitches => this.glitches = glitches
    );
  }
  
  gotoGetGlitch(id: number) {
    this.router.navigate(['company/activeGlitches', id]);
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
