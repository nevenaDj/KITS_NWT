import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Glitch } from '../../models/glitch';
import { GlitchService } from '../glitch.service';
import { Comment } from '../../models/comment';
import { AuthService } from '../../login/auth.service';
import { take } from 'rxjs/operators/take';
import { FileHolder } from 'angular2-image-upload';
import { HttpHeaders } from '@angular/common/http';
import { ToastsManager } from 'ng2-toastr';


@Component({
  selector: 'app-glitch-detail',
  templateUrl: './glitch-detail.component.html',
  styleUrls: ['./glitch-detail.component.css'],
})
export class GlitchDetailComponent implements OnInit {
  headers: HttpHeaders = new HttpHeaders({
    'X-Auth-Token': localStorage.getItem('token'),
    'Content-Type':'multipart/form-data',
    'Accept':'multipart/form-data'
  });
  
  logo='';
  glitch: Glitch;

  comments: Comment[];

  username: string;

  comment: Comment;

  photoRoute:string;

  changeResponsiblePerson:boolean;

  constructor(private route: ActivatedRoute,
              private glitchService: GlitchService,
              private router: Router,
              private authService: AuthService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
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
<<<<<<< HEAD
=======
          this.photoRoute= '/api/apartments/'+this.glitch.apartment.id+'/glitches/'+this.glitch.id+'/photo?image='
>>>>>>> branch 'config_angular' of https://github.com/nevenaDj/KITS_NWT.git
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
    if (this.comment.text===''){
      this.toastr.error('You have to write something!')
    }else{
    this.glitchService.addComment(this.glitch.id, this.comment)
        .then(() => {
          this.getComments();
          this.comment = {
            id: null,
            text: '',
            user: null
          }
        }).catch(error=>this.toastr.error('Error! Your comment didnt send.'));
      }
  }

  approve(){
    this.glitch.repairApproved=true;
    this.glitchService.approveRepair(this.glitch.id).catch(error=>this.toastr.error('Error during approve.'));
  }

  sendToOtherUser(){
    this.router.navigate(['/president/responsiblities', this.glitch.id,'change']);
  }

  localUrl: any[];
  showPreviewImage(event: any) {
      if (event.target.files && event.target.files[0]) {
          var reader = new FileReader();
          reader.onload = (event: any) => {
              this.localUrl = event.target.result;
              console.log("localUri: "+JSON.stringify(this.localUrl))
          }
          reader.readAsDataURL(event.target.files[0]);
      }
      
  }

  imageFinishedUploading(file: FileHolder) {
    console.log(JSON.stringify(file.file));
    this.glitchService.upload(this.glitch.apartment.id, this.glitch.id, file.file).then();
  }
  
  onRemoved(file: FileHolder) {
    // do some stuff with the removed file.
  }
  
  onUploadStateChanged(state: boolean) {
    console.log(JSON.stringify(state));
  }
}
