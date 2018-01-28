import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { Subscription } from 'rxjs/Subscription';
import { Bill } from '../models/bill';
import { Router } from '@angular/router';
import { PagerService } from '../services/pager.service';
import { BillsDataService } from './bills-data.service';

@Component({
  selector: 'app-bills-president',
  templateUrl: './bills-president.component.html',
  styleUrls: ['./bills-president.component.css']
})
export class BillsPresidentComponent implements OnInit {

 
token = '';
president: User;
subscription: Subscription;
bills: Bill[];
billsCount: number;

pager: any = {};

constructor(private router: Router, private billsService: BillsDataService, private pagerService: PagerService)  {
  this.president = {
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
  this.subscription = billsService.RegenerateData$
    .subscribe(() => {this.getUser();
      this.getData();

    });
}

getData(){
  this.billsService.getBillsCount(this.president.id)
      .then(count => {
        this.billsCount = count;
        this.setPage(1);
      });
}

getBills(page: number, size: number){
  this.billsService.getBills(page,size, this.president.id)
  .then(bills =>{this.bills = bills;
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
  this.getUser();

}

logout() {
  localStorage.removeItem('token');
  this.router.navigate(['login']);

}

getUser() {
  this.billsService.getUser().then(
    president => {this.president = president;
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
  this.router.navigate(['president/bills', id]);
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
