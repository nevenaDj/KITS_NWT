import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserRegister } from '../models/user';
import { Apartment } from '../models/apartment';
import { ApartmentService } from '../apartments/apartment.service';
import { UserService } from '../users/user.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user: UserRegister;
  street: string;
  number: string;
  city: string;
  apartmentNumber: string;

  apartment: Apartment;

  constructor(private apartmentService: ApartmentService,
              private userService: UserService,
              private router: Router) { 
    this.user = {
      username: '',
      password: '',
      password2: '',
      email: '',
      phoneNo: '',
      apartmentId: null
    }
    this.street = '';
    this.number = '';
    this.city = '';
    this.apartmentNumber = null;

  }

  ngOnInit() {
  }

  findApartment(){
    this.apartmentService.findApartment(this.street, this.number, this.city, this.apartmentNumber)
        .then(apartment => this.apartment = apartment);
  }

  save(){
    this.user.apartmentId = this.apartment.id;
    this.userService.registration(this.user)
        .then(() => this.router.navigate(['login']));

  }

}
