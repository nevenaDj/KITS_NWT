import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as decode from 'jwt-decode';

import { AuthService } from '../login/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  username: string = '';
  roles: string[];

  constructor(private router: Router,
              private authService: AuthService) { }

  ngOnInit() {
    this.username = this.authService.getCurrentUser();
    this.roles = this.authService.getRoles();
  }

  goto(role: string){
    if (role === 'ROLE_TENANT'){
      this.router.navigate(['tenant']);
    } else if (role === 'ROLE_OWNER'){
      this.router.navigate(['owner']);
    } else if (role === 'ROLE_PRESIDENT'){
      this.router.navigate(['president']);
    }
  }

  logout(){
    this.authService.logout();
  }

  gotoProfile(){
    this.router.navigate(['/tenant/profile']);
  }
}
