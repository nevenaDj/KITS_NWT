import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as decode from 'jwt-decode';

import { AuthService } from './auth.service';
import { User } from '../models/user';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User;

  constructor(private authService:AuthService,
              private router:Router) { 
    this.user = {
      id: 1,
      username: '',
      password: '',
      email: '',
      phoneNo: '',
      address: null
    };
                
  }

  ngOnInit() {
    
  }

  login(){
    this.authService.login(this.user.username, this.user.password)
      .then( res => {    
          if(this.authService.isAdmin()){
            this.router.navigate(['/buildings']);
          } else if (this.authService.isCompany()){
            this.router.navigate(['/company']);
          } else if (this.authService.isTenant()) {
            this.router.navigate(['tenant/apartments']);
          } else if(this.authService.isOwner()){
            this.router.navigate(['owner/apartments']);
          } else if(this.authService.isPresident()){
            this.router.navigate(['president']);
          }              
      })
      .catch(error => console.log(error));
  }

  gotoRegister(){
    this.router.navigate(['register']);
  }

}