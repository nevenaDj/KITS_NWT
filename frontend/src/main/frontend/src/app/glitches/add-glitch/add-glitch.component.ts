import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Glitch } from '../../models/glitch';
import { GlitchService } from '../glitch.service';
import { GlitchType } from '../../models/glitch-type';
import { GlitchTypeService } from '../../glitch-types/glitch-type.service';
import { ApartmentService } from '../../apartments/apartment.service';


@Component({
  selector: 'app-add-glitch',
  templateUrl: './add-glitch.component.html',
  styleUrls: ['./add-glitch.component.css']
})
export class AddGlitchComponent implements OnInit {

  glitch: Glitch;

  apartmentID: number;

  glitchTypes: GlitchType[];

  constructor(private glitchService: GlitchService,
              private glitchTypeService: GlitchTypeService,
              private apartmentService: ApartmentService,
              private router: Router) {
    this.glitch = {
      id: null,
      description: '',
      dateOfReport: null,
      dateOfRepair: null,
      apartment: null,
      responsiblePerson: null,
      repairApproved:false,
      companyID:null,
      bill:null,
      type: {
        id: null,
        type: ''
      },
      state: null
    }
   }

  ngOnInit() {
    this.glitchTypeService.getGlitchTypes()
        .then(glitchTypes => {
          this.glitchTypes = glitchTypes;
          this.getMyApartment();
        });

  }

  getMyApartment(){
    this.apartmentID = this.apartmentService.getMyApartment().id;
  }

  save(){
    this.glitchService.addGlitch(this.apartmentID,this.glitch)
        .then(glitch => {
          this.glitch = glitch;
          this.router.navigate(['/tenant/glitches', this.glitch.id]);
        }); 
  }

}
