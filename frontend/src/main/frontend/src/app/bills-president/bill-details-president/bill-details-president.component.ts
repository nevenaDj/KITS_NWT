import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { User } from '../../models/user';
import { Bill } from '../../models/bill';
import { BillsDataService } from '../bills-data.service';

@Component({
  selector: 'app-bill-details-president',
  templateUrl: './bill-details-president.component.html',
  styleUrls: ['./bill-details-president.component.css']
})
export class BillDetailsPresidentComponent implements OnInit {

 
  token = '';
  user: User;
  subscription: Subscription;
  bill: Bill;
  height: number=0;

  constructor(private router: Router,
              private route:ActivatedRoute,
              private billService: BillsDataService) {
    this.user = {
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
    this.subscription = billService.RegenerateData$
      .subscribe(() => this.getUser());

  }

  ngOnInit() {
    this.token = localStorage.getItem('token');
    this.getUser();
    this.billService.getBill(+this.route.snapshot.params['id'])
        .then(bill => {
          this.bill = bill;
          this.height=50+bill.items.length*50;
          console.log(bill);
        }
      );
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);

  }

  getUser() {
    this.billService.getUser().then(
      user => this.user = user
    );
  }

  approveBill(){
    this.bill.approved=true;
    this.billService.approveBill(this.bill.id).then(
      bill=>this.billService.announceChange()
    );
  }
}
