import {CompanyDataService} from '../home-company/company-data.service';
import {Glitch} from '../models/glitch';
import {User} from '../models/user';
import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-glitch-details-company',
  templateUrl: './glitch-details-company.component.html',
  styleUrls: ['./glitch-details-company.component.css']
})
export class GlitchDetailsCompanyComponent implements OnInit {

  token = '';
  company: User;
  subscription: Subscription;
  glitch: Glitch;

  constructor(private router: Router,
              private route:ActivatedRoute,
              private companyService: CompanyDataService) {
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

  }

  ngOnInit() {
    this.token = localStorage.getItem('token');
    this.getCompany();
    this.companyService.getGlitch(+this.route.snapshot.params['id'])
        .then(glitch => {
          this.glitch = glitch;
          console.log(JSON.stringify(glitch.state));
        }
      );
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


  gotoGetGlitch(id: number) {
    this.router.navigate(['company/pendingGlitches', id]);
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
