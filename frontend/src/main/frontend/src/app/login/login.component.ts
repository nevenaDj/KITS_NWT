import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

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
      phoneNo: ''
    };
                
  }

  ngOnInit() {
    
  }

  login(){
    this.authService.login(this.user.username, this.user.password)
      .then( res => { this.router.navigate(['']) })
      .catch(error => console.log(error));

  }

}
