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
        const token = localStorage.getItem('token');
        const tokenPayload = decode(token);
        console.log(this.authService.isTenant());
        console.log(this.authService.isOwner());
        console.log(this.authService.isPresident());

        for(let role of tokenPayload.scopes){
          if(this.authService.isAdmin()){
            this.router.navigate(['/buildings']);
          } else if (this.authService.isCompany()){
            this.router.navigate(['/company']);
          } else if (this.authService.isTenant()) {
            this.router.navigate(['tenant']);
          } else if(this.authService.isOwner()){
            console.log('owner');
            this.router.navigate(['owner']);
          } else if(this.authService.isPresident()){
            console.log('president');
            this.router.navigate(['president']);

          }              
        }
      })
      .catch(error => console.log(error));
  }

  gotoRegister(){
    this.router.navigate(['register']);
  }

}