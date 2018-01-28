
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Subscription } from 'rxjs/Subscription';
import { User } from '../../models/user';
import { Glitch } from '../../models/glitch';
import { CompanyDataService } from '../../home-company/company-data.service';


@Component({
  selector: 'app-pending-glitches-company',
  templateUrl: './pending-glitches-company.component.html',
  styleUrls: ['./pending-glitches-company.component.css']
})
export class PendingGlitchesCompanyComponent implements OnInit {

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
      .subscribe(() => this.getPendingGlitches());
  }

  ngOnInit() {
    this.token = localStorage.getItem('token');
    this.getCompany();
    this.getPendingGlitches()
  }

  getCompany() {
    this.companyService.getCompany().then(
      company => this.company = company
    );
  }

    getPendingGlitches() {
    this.companyService.getPendingGlitches().then(
      glitches => this.glitches = glitches
    );
  }
  
  gotoGetGlitch(id: number) {
    this.router.navigate(['/company/pendingGlitches', id]);
  }



}
