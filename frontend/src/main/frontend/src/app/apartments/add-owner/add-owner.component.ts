import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { User } from '../../models/user';
import { ApartmentService } from '../../apartments/apartment.service';


@Component({
  selector: 'app-add-owner',
  templateUrl: './add-owner.component.html',
  styleUrls: ['./add-owner.component.css']
})
export class AddOwnerComponent implements OnInit {
  owner: User;
  apartmentID: number;

  constructor(private route: ActivatedRoute,
              private location: Location,
              private apartmentService: ApartmentService) {
    this.owner = {
      id: null,
      username: '',
      password: '',
      email: '',
      phoneNo: '',
      address: null
    }
    this.apartmentID = this.route.snapshot.params['id'];
  }

  ngOnInit() {
  }

  save(): void {
    this.apartmentService.addOwner(this.apartmentID, this.owner)
          .then(owner => this.location.back());
  }

}
