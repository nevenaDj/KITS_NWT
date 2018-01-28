import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { User } from '../../models/user';
import { Bill } from '../../models/bill';
import { CompanyDataService } from '../../home-company/company-data.service';

@Component({
  selector: 'app-bill-details-company',
  templateUrl: './bill-details-company.component.html',
  styleUrls: ['./bill-details-company.component.css']
})
export class BillDetailsCompanyComponent implements OnInit {

  token = '';
  company: User;
  subscription: Subscription;
  bill: Bill;
  height: number=0;

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
    this.companyService.getBill(+this.route.snapshot.params['id'])
        .then(bill => {
          this.bill = bill;
          this.height=50+bill.items.length*50;
          console.log(bill);
        }
      );
  }


  getCompany() {
    this.companyService.getCompany().then(
      company => this.company = company
    );
  }


  gotoGetGlitch(id: number) {
    this.router.navigate(['/company/pendingGlitches', id]);
  }


}
