import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../../models/user';
import { UserService } from '../../users/user.service';
import { AuthService } from '../../login/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User = {
    id: null,
    username: '',
    password: '',
    email: '',
    phoneNo: '',
    address: null
  };

  constructor(private router: Router,
              private userService: UserService,
              private authService: AuthService) { }

  ngOnInit() {
    this.userService.getCurrentUser()
        .then(user => this.user = user);
  }

  gotoChangePassword(){
    if (this.authService.isTenant()){
      this.router.navigate(['/tenant/password']);
    } else if(this.authService.isPresident()){
      this.router.navigate(['/president/password']);
    } else if(this.authService.isOwner()){
      this.router.navigate(['/owner/password']);
    }
  }

  gotoUpdateUser(){
    if (this.authService.isTenant()){
      this.router.navigate(['/tenant/update']);
    } else if(this.authService.isPresident()){
      this.router.navigate(['/president/update']);
    } else if(this.authService.isOwner()){
      this.router.navigate(['/owner/update']);
    }
    
  }

}
