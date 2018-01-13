import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as decode from 'jwt-decode';

@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.css']
})
export class HomeAdminComponent implements OnInit {
  username: string = '';

  constructor(private router: Router) { }

  ngOnInit() {
    const token = localStorage.getItem('token');
    const tokenPayload = decode(token);
    console.log(tokenPayload.sub);
    this.username = tokenPayload.sub;
  }

  logout(){
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

  

}
