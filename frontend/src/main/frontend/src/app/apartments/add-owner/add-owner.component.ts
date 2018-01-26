import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { User } from '../../models/user';
import { ApartmentService } from '../../apartments/apartment.service';
import { UserService } from '../../users/user.service';


@Component({
  selector: 'app-add-owner',
  templateUrl: './add-owner.component.html',
  styleUrls: ['./add-owner.component.css']
})
export class AddOwnerComponent implements OnInit {
  owner: User;
  apartmentID: number;
  username: string;
  user: User;
  complexForm: FormGroup;

  constructor(private route: ActivatedRoute,
              private location: Location,
              private apartmentService: ApartmentService,
              private userService: UserService,
              private formBuilder: FormBuilder) {
    this.owner = {
      id: null,
      username: '',
      password: '',
      email: '',
      phoneNo: '',
      address: null
    }

    this.user = null;
    this.apartmentID = this.route.snapshot.params['id'];

    this.complexForm = formBuilder.group({
      'username': [null, Validators.required]
    })
  }

  ngOnInit() {
  }

  save(): void {
    this.apartmentService.addOwner(this.apartmentID, this.owner)
          .then(owner => this.location.back());
  }

  find(): void {
    this.user = null;
    this.userService.findUser(this.username)
        .then(user => this.user = user);
  }

  add(): void {
    this.apartmentService.addOwner(this.apartmentID, this.user)
        .then(owner => this.location.back());
  }

  cancel(){
    this.location.back();
  }

}
