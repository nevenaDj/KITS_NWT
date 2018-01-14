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
      address:{
        city:'',
        id:null,
        number: '',
        street: '',
        zipCode: 0
      }
    };
                
  }

  ngOnInit() {
    
  }

  login(){
    this.authService.login(this.user.username, this.user.password)
      .then( res => { 
        const token = localStorage.getItem('token');
        const tokenPayload = decode(token);
        for(let role of tokenPayload.scopes){
          console.log("role");
          console.log(role);
          if(role === 'ROLE_ADMIN'){
            this.router.navigate(['/buildings']);
          }
          else if (role === 'ROLE_COMPANY'){
            this.router.navigate(['/company']);
          } else {
            console.log('user');
          }
        }               
      })
      .catch(error => console.log(error));

  }

}