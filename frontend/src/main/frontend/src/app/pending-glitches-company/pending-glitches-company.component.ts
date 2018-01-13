import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../models/user'


@Component({
  selector: 'app-pending-glitches-company',
  templateUrl: './pending-glitches-company.component.html',
  styleUrls: ['./pending-glitches-company.component.css']
})
export class PendingGlitchesCompanyComponent implements OnInit {

  company: User;
  constructor(private router: Router) { }

  ngOnInit() {
  }

  logout(){
    localStorage.removeItem('token');
    this.router.navigate(['login']);

  }

}
