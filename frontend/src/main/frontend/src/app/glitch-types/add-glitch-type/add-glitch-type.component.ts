import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { GlitchType } from '../../models/glitch-type';
import { GlitchTypeService } from '../glitch-type.service';


@Component({
  selector: 'app-add-glitch-type',
  templateUrl: './add-glitch-type.component.html',
  styleUrls: ['./add-glitch-type.component.css']
})
export class AddGlitchTypeComponent implements OnInit {
  glitchType: GlitchType;

  constructor(private location: Location,
              private glitchTypeService: GlitchTypeService) {
    this.glitchType = {
      id: null,
      type: ''
    }
   }

  ngOnInit() {
  }

  save(): void{
    this.glitchTypeService.addGlitchType(this.glitchType)
        .then(glitchType => {
          this.glitchTypeService.announceChange();
          this.location.back();
        });
  }

}
