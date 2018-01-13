import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../models/user'

@Component({
  selector: 'app-active-glitches-company',
  templateUrl: './active-glitches-company.component.html',
  styleUrls: ['./active-glitches-company.component.css']
})
export class ActiveGlitchesCompanyComponent implements OnInit {

  company: User;
  constructor(private router: Router) { }

  ngOnInit() {
  }

  logout(){
    localStorage.removeItem('token');
    this.router.navigate(['login']);

  }
}
