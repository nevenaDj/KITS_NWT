import { Component, OnInit } from '@angular/core';


import { AuthService } from '../login/auth.service';

@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.css']
})
export class HomeAdminComponent implements OnInit {
  username: string = '';

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.username = this.authService.getCurrentUser();
  }

  logout(){
    this.authService.logout();
  }

  

}
