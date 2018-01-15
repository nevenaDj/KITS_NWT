import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Glitch } from '../../models/glitch';
import { GlitchService } from '../glitch.service';
import { Comment } from '../../models/comment';


@Component({
  selector: 'app-glitch-detail',
  templateUrl: './glitch-detail.component.html',
  styleUrls: ['./glitch-detail.component.css']
})
export class GlitchDetailComponent implements OnInit {

  glitch: Glitch;

  comments: Comment[];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private glitchService: GlitchService) {
    this.glitch = {
      id: null,
      description: '',
      dateOfReport: null,
      dateOfRepair: null,
      apartment: null,
      responsiblePerson: {
        id: null,
        username: '',
        password: '',
        email: '',
        phoneNo: '',
        address: null
      },
      type: {
        id: null,
        type: ''
      },
      state: {
        id: null,
        state: ''
      }
    }
  }

  ngOnInit() {
    this.glitchService.getGlitch(+this.route.snapshot.params['id'])
        .then(glitch => {
          this.glitch = glitch;
          this.glitchService.getComments(glitch.id)
              .then(comments => this.comments = comments);
        });

    console.log(this.glitch);
    console.log(this.comments);
  }

}
