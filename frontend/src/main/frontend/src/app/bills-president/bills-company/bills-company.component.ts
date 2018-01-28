
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


import { Subscription } from 'rxjs/Subscription';
import { User } from '../../models/user';
import { Bill } from '../../models/bill';
import { CompanyDataService } from '../../home-company/company-data.service';
import { PagerService } from '../../services/pager.service';


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
  billsCount: number;

  pager: any = {};

  constructor(private router: Router, private companyService: CompanyDataService, private pagerService: PagerService)  {
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
      .subscribe(() => {this.getCompany();
        this.getData();

      });
  }

  getData(){
    this.companyService.getBillsCount(this.company.id)
        .then(count => {
          this.billsCount = count;
          this.setPage(1);
        });
  }

  getBills(page: number, size: number){
    this.companyService.getBills(page,size, this.company.id)
    .then(bills => {this.bills = bills;
      this.bills.sort(function(x, y) {
        // true values first
        return (x.approved === y.approved)? 0 : y.approved? -1 : 1;
        // false values first
        // return (x === y)? 0 : x? 1 : -1;
    });
    });
  }

  ngOnInit() {
    this.token = localStorage.getItem('token');
    this.getCompany();

  }


  getCompany() {
    this.companyService.getCompany().then(
      company => {this.company = company;
        this.getData();
       
      }
    );
  }

  setPage(page: number){
    if (page < 1 || page > this.pager.totalPages){
      return;
    }

    this.pager = this.pagerService.getPager(this.billsCount, page);
    this.getBills(this.pager.currentPage - 1, this.pager.pageSize);
  }
  
  gotoGetBill(id: number) {
    this.router.navigate(['/company/bills', id]);
  }

}
