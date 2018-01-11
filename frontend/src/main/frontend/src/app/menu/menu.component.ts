import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as decode from 'jwt-decode';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
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
