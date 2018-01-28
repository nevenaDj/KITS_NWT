import { Component, OnInit } from '@angular/core';
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

  constructor(private location: Location,
              private userService: UserService) { }

  ngOnInit() {
    this.userService.getCurrentUser()
        .then(user => this.user = user);
  }

  save() {
    this.userService.updateUser(this.user)
        .then(user => this.location.back());
  }

  cancel(){
    this.location.back();
  }

}
