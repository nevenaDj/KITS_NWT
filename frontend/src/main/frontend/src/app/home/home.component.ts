import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../login/auth.service';
import { ApartmentService } from '../apartments/apartment.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  username: string = '';
  roles: string[];

  constructor(private router: Router,
              private apartmentService: ApartmentService,
              private authService: AuthService) { }

  ngOnInit() {
    this.username = this.authService.getCurrentUser();
    this.roles = this.authService.getRoles();
    console.log(this.roles);
  }

  goto(role: string){
    if (role === 'ROLE_TENANT'){
      this.router.navigate(['tenant/apartments']);
    } else if (role === 'ROLE_OWNER'){
      this.apartmentService.setMyApartment(undefined);
      this.router.navigate(['owner/apartments']);
    } else if (role === 'ROLE_PRESIDENT'){
      this.router.navigate(['president/buildings']);
    }
  }

  logout(){
    this.authService.logout();
  }

  gotoProfile(){
    this.router.navigate(['/tenant/profile']);
  }
}
