import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth.service';
import { User } from '../models/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = {
    id: 1,
    username: '',
    password: '',
    email: '',
    phoneNo: ''
  };

  constructor(private authService:AuthService,
              private router:Router) { }

  ngOnInit() {
    localStorage.removeItem('token');
  }

  login(){
    this.authService.login(this.user.username, this.user.password)
      .then( res => { this.router.navigate(['']) })
      .catch(error => console.log(error));

  }

}
