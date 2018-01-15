import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../../../models/user';
import { UserService } from '../../../users/user.service';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent implements OnInit {

  user: User = {
    id: null,
    username: '',
    password: '',
    email: '',
    phoneNo: '',
    address: null
  };

  constructor(private router: Router,
              private userService: UserService) { }

  ngOnInit() {
    this.userService.getCurrentUser()
        .then(user => this.user = user);
  }

  gotoChangePassword(){
    this.router.navigate(['password']);
  }

}
