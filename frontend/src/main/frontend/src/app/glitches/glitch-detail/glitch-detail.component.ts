import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Glitch } from '../../models/glitch';
import { GlitchService } from '../glitch.service';
import { Comment } from '../../models/comment';
import { AuthService } from '../../login/auth.service';
import { take } from 'rxjs/operators/take';


@Component({
  selector: 'app-glitch-detail',
  templateUrl: './glitch-detail.component.html',
  styleUrls: ['./glitch-detail.component.css']
})
export class GlitchDetailComponent implements OnInit {

  glitch: Glitch;

  comments: Comment[];

  username: string;

  comment: Comment;

  changeResponsiblePerson:boolean;

  constructor(private route: ActivatedRoute,
              private glitchService: GlitchService,
              private router: Router,
              private authService: AuthService) {
    this.glitch = {
      id: null,
      description: '',
      dateOfReport: null,
      dateOfRepair: null,
      apartment: null,
      repairApproved:false,
      bill:null,
      companyID:null,
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
    this.changeResponsiblePerson=false;
    this.comment = {
      id: null,
      text: '',
      user: null
    }
  }

  ngOnInit() {
    this.glitchService.getGlitch(+this.route.snapshot.params['id'])
        .then(glitch => {
          this.glitch = glitch;
          this.getComments();
        });
    this.username = this.authService.getCurrentUser();
    if (this.authService.isPresident())
        this.changeResponsiblePerson=true;
  }

  getComments(){
    this.glitchService.getComments(this.glitch.id)
    .then(comments => this.comments = comments);
  }

  saveComment(){
    this.glitchService.addComment(this.glitch.id, this.comment)
        .then(() => {
          this.getComments();
          this.comment = {
            id: null,
            text: '',
            user: null
          }
        });
  }

  approve(){
    this.glitch.repairApproved=true;
    this.glitchService.approveRepair(this.glitch.id);
  }

  sendToOtherUser(){
    this.router.navigate(['/president/responsiblities', this.glitch.id,'change']);
  }
}
