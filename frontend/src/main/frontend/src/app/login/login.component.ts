import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';
    
import { AuthService } from './auth.service';
import { User } from '../models/user';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User;
  complexForm: FormGroup;

  constructor(private authService:AuthService,
              private router:Router,
              private formBuilder: FormBuilder,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
    this.user = {
      id: 1,
      username: '',
      password: '',
      email: '',
      phoneNo: '',
      address: null
    };

    this.complexForm = formBuilder.group({
      'username': [null, Validators.required], 
      'password': [null, Validators.required],
    })
                
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
            this.router.navigate(['president/buildings']);
          }              
      })
      .catch(error => {
        this.toastr.error('Invalid username or password.');
      });
  }

  gotoRegister(){
    this.router.navigate(['register']);
  }

}