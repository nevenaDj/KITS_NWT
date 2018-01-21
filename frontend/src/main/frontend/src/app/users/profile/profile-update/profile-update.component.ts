import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { User } from '../../../models/user';
import { UserService } from '../../../users/user.service';


@Component({
  selector: 'app-profile-update',
  templateUrl: './profile-update.component.html',
  styleUrls: ['./profile-update.component.css']
})
export class ProfileUpdateComponent implements OnInit {
  user: User ={
    id: null,
    username: '',
    password: '',
    email: '',
    phoneNo: '',
    address: null
  };

  constructor(private router: Router,
              private location: Location,
              private userService: UserService) { }

  ngOnInit() {
    this.userService.getCurrentUser()
        .then(user => this.user = user);
  }

  save() {
    this.userService.updateUser(this.user)
        .then(user => this.router.navigate(['/tenant/profile']));
  }

  cancel(){
    this.location.back();
  }

}
