import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as decode from 'jwt-decode';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  token: string = '';
  proba: string = '';

  constructor(private router: Router) { }

  ngOnInit() {
    this.token = localStorage.getItem('token');
    this.proba = decode(this.token);
    console.log(this.proba);
    
  }

  logout(){
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

}