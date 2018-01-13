import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { GlitchType } from '../models/glitch-type';
import { GlitchTypeService } from './glitch-type.service';

@Component({
  selector: 'app-glitch-types',
  templateUrl: './glitch-types.component.html',
  styleUrls: ['./glitch-types.component.css']
})
export class GlitchTypesComponent implements OnInit {

  glitchTypes: GlitchType[];

  constructor(private router: Router,
              private glitchTypeService: GlitchTypeService) { }

  ngOnInit() {
    this.glitchTypeService.getGlitchTypes()
        .then(glitchTypes => this.glitchTypes = glitchTypes);
  }

  gotoAddGlitchType(){
    this.router.navigate(['addGlitchType']);
  }

}
