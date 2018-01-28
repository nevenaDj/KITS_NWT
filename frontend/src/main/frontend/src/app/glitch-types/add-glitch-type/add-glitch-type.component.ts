import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { GlitchType } from '../../models/glitch-type';
import { GlitchTypeService } from '../glitch-type.service';


@Component({
  selector: 'app-add-glitch-type',
  templateUrl: './add-glitch-type.component.html',
  styleUrls: ['./add-glitch-type.component.css']
})
export class AddGlitchTypeComponent implements OnInit {
  glitchType: GlitchType;
  complexForm: FormGroup;

  constructor(private location: Location,
              private glitchTypeService: GlitchTypeService,
              private formBuilder: FormBuilder) {
    this.glitchType = {
      id: null,
      type: ''
    }
    this.complexForm = formBuilder.group({
      'type': [null, Validators.required]
    })
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

  cancel(){
    this.location.back();
  }

}
