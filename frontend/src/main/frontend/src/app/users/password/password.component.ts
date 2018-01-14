import { Component, OnInit } from '@angular/core';

import { UserPassword } from '../../models/user-password';
import { UserService } from '../../users/user.service';
import { AuthService } from '../../login/auth.service';


@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {
  user: UserPassword = {
    currentPassword: '',
    newPassword1: '',
    newPassword2: ''
  }

  constructor(private userService: UserService,
              private authSerice: AuthService) { }

  ngOnInit() {
  }

  save(){
    this.userService.changePassword(this.user)
        .then(() => this.authSerice.logout());

  }

}
