import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

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

  complexForm: FormGroup;

  constructor(private userService: UserService,
              private location: Location,
              private formBuilder: FormBuilder) {
    this.complexForm = formBuilder.group({
      'currentPassword': [null, Validators.required],
      'newPassword1': [null, Validators.required],
      'newPassword2': [null, Validators.required]
    })
    }

  ngOnInit() {
  }

  save(){
    this.userService.changePassword(this.user)
        .then(() => this.location.back());
  }

  cancel(){
    this.location.back();
  }

}
