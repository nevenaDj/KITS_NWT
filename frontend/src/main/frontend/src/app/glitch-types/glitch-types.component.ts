import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { GlitchType } from '../models/glitch-type';
import { GlitchTypeService } from './glitch-type.service';


@Component({
  selector: 'app-glitch-types',
  templateUrl: './glitch-types.component.html',
  styleUrls: ['./glitch-types.component.css']
})
export class GlitchTypesComponent implements OnInit {

  glitchTypes: GlitchType[];

  subscription: Subscription;

  constructor(private router: Router,
              private glitchTypeService: GlitchTypeService) {
    this.subscription = glitchTypeService.RegenerateData$
                .subscribe(() => this.getGlitchTypes());
  }

  ngOnInit() {
    this.getGlitchTypes();
  }

  getGlitchTypes(){
    this.glitchTypeService.getGlitchTypes()
        .then(glitchTypes => this.glitchTypes = glitchTypes);
  }

  gotoAddGlitchType(){
    this.router.navigate(['addGlitchType']);
  }

  deleteGlitchType(id: number){
    this.glitchTypeService.deleteGlitchType(id)
        .then(() => this.getGlitchTypes());
  }

}
