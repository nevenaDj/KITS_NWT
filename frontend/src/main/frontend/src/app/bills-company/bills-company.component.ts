import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../models/user'

@Component({
  selector: 'app-bills-company',
  templateUrl: './bills-company.component.html',
  styleUrls: ['./bills-company.component.css']
})
export class BillsCompanyComponent implements OnInit {


  company: User;
  constructor(private router: Router) { }

  ngOnInit() {
  }

  logout(){
    localStorage.removeItem('token');
    this.router.navigate(['login']);

  }
}
