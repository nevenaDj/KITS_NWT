import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { User } from '../../models/user';
import { BuildingService } from '../../buildings/building.service';


@Component({
  selector: 'app-add-president',
  templateUrl: './add-president.component.html',
  styleUrls: ['./add-president.component.css']
})
export class AddPresidentComponent implements OnInit {

  president: User;
  buildingID: number;

  constructor(private router: Router,
              private route:ActivatedRoute,
              private buildingService: BuildingService) { 
    this.president ={
      id: null,
      username: '',
      password: '',
      email: '',
      phoneNo: ''
    }
    this.buildingID = this.route.snapshot.params['id'];
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

}
