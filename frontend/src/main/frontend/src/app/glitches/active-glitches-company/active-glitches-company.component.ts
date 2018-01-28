import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs/Subscription';
import * as decode from 'jwt-decode';
import { User } from '../../models/user';
import { Glitch } from '../../models/glitch';
import { CompanyDataService } from '../../home-company/company-data.service';


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
    this.router.navigate(['/company/activeGlitches', id]);
  }

}
