import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

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

  constructor(private route: ActivatedRoute,
              private location: Location,
              private apartmentService: ApartmentService,
              private userService: UserService) {
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

}
