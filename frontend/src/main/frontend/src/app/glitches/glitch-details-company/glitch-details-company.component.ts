
import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Subscription';
import { GlitchDataService } from './glitch-data.service';
import * as decode from 'jwt-decode';

import { FormControl } from '@angular/forms';
import { User } from '../../models/user';
import { Glitch } from '../../models/glitch';
import { CompanyDataService } from '../../home-company/company-data.service';
import { Comment } from '../../models/comment';
import { ToastsManager } from 'ng2-toastr';

@Component({
  selector: 'app-glitch-details-company',
  templateUrl: './glitch-details-company.component.html',
  styleUrls: ['./glitch-details-company.component.css']
})
export class GlitchDetailsCompanyComponent implements OnInit {

  public selectedMoment = new Date();
  public min = new Date();
  
  token = '';
  company: User;
  subscription: Subscription;
  glitch: Glitch;

  comments: Comment[];
  comment: Comment;

  constructor(private router: Router,
              private route:ActivatedRoute,
              private companyService: CompanyDataService,
              private glitchService: GlitchDataService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
    this.company = {
      id: null,
      password: '',
      username: '',
      email: '',
      phoneNo: '',
      address: {
        city: '',
        id: null,
        number: '',
        street: '',
        zipCode: 0
      }
    };
    this.comment = {
      id: null,
      text: '',
      user: null,
    }
    this.subscription = companyService.RegenerateData$
      .subscribe(() => this.getCompany());
    this.subscription = glitchService.RegenerateData$
      .subscribe(() => this.acceptGlitch());


  }

  ngOnInit() {
    this.getCompany();
    this.companyService.getGlitch(+this.route.snapshot.params['id'])
        .then(glitch => {
          this.glitch = glitch;
          this.getComments();
        }
      );
  }

  public myFilter = (d: Date): boolean => {
    const day = d.getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
}

  acceptGlitch(){
    
    this.glitchService.updateState(this.glitch.id, 2)
      .then( glitch => {
        console.log(JSON.stringify(glitch));
        this.router.navigate(['/company/activeGlitches', this.glitch.id]).catch(error=>this.toastr.error('Error during approve.'));
    } 
  );
  }


  setTime(){
    this.glitch.dateOfRepair=this.selectedMoment;
    this.glitchService.setTime(this.glitch)
      .then( glitch => {
        this.router.navigate(['/company/activeGlitches', this.glitch.id]).catch(
          error=>this.toastr.error('Error during setting time.')
        );
    } 
  );
  }

  sendCompany(){
    this.router.navigate(['/company/activeGlitches', this.glitch.id,'send']);
  }

  sendBill(){
    this.router.navigate(['/company/activeGlitches', this.glitch.id,'bill']);
  }

  getCompany() {
    this.companyService.getCompany().then(
      company => this.company = company
    );
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

  gotoGetGlitch(id: number) {
    this.router.navigate(['/company/pendingGlitches', id]);
  }

}
