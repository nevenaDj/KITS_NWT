import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../login/auth.service';
import { ApartmentService } from '../apartments/apartment.service';

@Component({
  selector: 'app-home-owner',
  templateUrl: './home-owner.component.html',
  styleUrls: ['./home-owner.component.css']
})
export class HomeOwnerComponent implements OnInit {
  username: string = '';
  roles: string[];

  constructor(private router: Router,
              private authService: AuthService,
              private apartmentService: ApartmentService) { }

  ngOnInit() {
    this.username = this.authService.getCurrentUser();
    this.roles = this.authService.getRoles();
  }

  goto(role: string){
    if (role === 'ROLE_TENANT'){
      this.apartmentService.setMyApartment(undefined);
      this.router.navigate(['tenant/apartments']);
    } else if (role === 'ROLE_OWNER'){
      this.router.navigate(['owner/apartments']);
    } else if (role === 'ROLE_PRESIDENT'){
      this.router.navigate(['president/buildings']);
    }
  }

  logout(){
    this.authService.logout();
  }

  gotoProfile(){
    this.router.navigate(['/owner/profile']);
  }
}
