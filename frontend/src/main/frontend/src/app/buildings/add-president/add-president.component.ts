import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { User } from '../../models/user';
import { BuildingService } from '../../buildings/building.service';
import { UserService } from '../../users/user.service';


@Component({
  selector: 'app-add-president',
  templateUrl: './add-president.component.html',
  styleUrls: ['./add-president.component.css']
})
export class AddPresidentComponent implements OnInit {
  president: User;
  buildingID: number;
  username: string;
  user: User;
  complexForm: FormGroup;

  constructor(private router: Router,
              private route:ActivatedRoute,
              private buildingService: BuildingService,
              private userService: UserService,
              private formBuilder: FormBuilder) { 
    this.president ={
      id: null,
      username: '',
      password: '',
      email: '',
      phoneNo: '',
      address: null
    }
    this.buildingID = this.route.snapshot.params['id'];
    this.user = null;

    this.complexForm = formBuilder.group({
      'username': [null, Validators.required]
    })
  }

  ngOnInit() {

  }

  save():void {
    this.buildingService.addPresident(this.buildingID, this.president)
          .then(president =>{
            console.log(president);
            this.router.navigate([`/buildings/${this.buildingID}`]);
          }
        );
  }

  find(): void {
    this.user = null;
    this.userService.findUser(this.username)
        .then(user => this.user = user);
  }

  add(): void {
    this.buildingService.addPresident(this.buildingID, this.user)
        .then(president => this.router.navigate([`/buildings/${this.buildingID}`]));
  }

  cancel(){
    this.router.navigate([`/buildings/${this.buildingID}`]);
  }

}
