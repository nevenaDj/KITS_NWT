import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../login/auth.service';

@Component({
  selector: 'app-home-president',
  templateUrl: './home-president.component.html',
  styleUrls: ['./home-president.component.css']
})
export class HomePresidentComponent implements OnInit {
  username: string = '';
  roles: string[];

  constructor(private router: Router,
              private authService: AuthService) { }

  ngOnInit() {
    this.username = this.authService.getCurrentUser();
    this.roles = this.authService.getRoles();
  }

  goto(role: string){
    if (role === 'ROLE_TENANT'){
      this.router.navigate(['tenant/apartments']);
    } else if (role === 'ROLE_OWNER'){
      this.router.navigate(['owner/apartments']);
    } else if (role === 'ROLE_PRESIDENT'){
      this.router.navigate(['president']);
    }
  }

  logout(){
    this.authService.logout();
  }

  gotoProfile(){
    this.router.navigate(['/president/profile']);
  }

}
