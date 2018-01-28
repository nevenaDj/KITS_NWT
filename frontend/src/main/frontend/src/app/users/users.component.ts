import { Component, OnInit } from '@angular/core';

import { User } from '../models/user';
import { UserService } from './user.service';
import { PagerService } from '../services/pager.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: User[];

  usersCount: number;

  pager: any = {};

  constructor(private userService: UserService,
              private pagerService: PagerService) { }

  ngOnInit() {
    this.userService.getUsersCount()
        .then(count => {
          this.usersCount = count;
          this.setPage(1);
          
        });
    
  }

  getUsers(page: number, size: number){
    this.userService.getUsers(page, size).then(
      users => this.users = users
    );
  }

  setPage(page: number){
    if (page < 1 || page > this.pager.totalPages){
      return;
    }

    this.pager = this.pagerService.getPager(this.usersCount, page, 15);
    this.getUsers(this.pager.currentPage - 1, this.pager.pageSize);

  }

}
