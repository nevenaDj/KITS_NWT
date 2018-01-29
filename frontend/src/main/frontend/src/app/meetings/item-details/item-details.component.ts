import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MeetingsService } from '../meetings.service';
import { ItemComment } from '../../models/itemComment';
import { AgendaItem } from '../../models/agenda-item';
import { AuthService } from '../../login/auth.service';
import { Meeting } from '../../models/meeting';
import { ToastsManager } from 'ng2-toastr';

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.css']
})
export class ItemDetailsComponent implements OnInit {


  expired:boolean=false;
  active:boolean=false;

  comments: ItemComment[];
  comment: ItemComment;

  item:AgendaItem;
  meeting:Meeting;

  username:string;

  conclusion:string='';



  constructor(private route: ActivatedRoute,
              private meetingService: MeetingsService,
              private router: Router, 
              private authService: AuthService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
    this.comment={
        id:null,
        writer:null,
        date:new Date(), 
        text:''
       }
      }


  ngOnInit() {
    this.getItem();
  }

  getItem(){
    this.meetingService.getItem(+this.route.snapshot.params['id'],+this.route.snapshot.params['id_item'] ).then(
      item=>{
        this.item=item;
        this.meetingService.getMeeting(+this.route.snapshot.params['id']).then(
          meeting=>{this.meeting=meeting        
          this.username= this.authService.getCurrentUser()
          let now= new Date();
          this.getComments();
          let date= new Date(this.meeting.dateAndTime);
          let diff= date.getTime()-now.getTime();
          if (diff<0){
            let one_day:Date= new Date(date.getTime() + (1000 * 60 * 60 * 24));
            let diff2=one_day.getTime()-now.getTime();
              if (diff2<0)
                this.expired=true;
              if (diff2>=0)
                this.active=true;
          }
          }
        )
      }

    )

  }

  getComments(){
    this.meetingService.getComments(this.meeting.id, this.item.id)
    .then(comments => this.comments = comments);
  }

  saveComment(){
    if (this.comment.text===''){
      this.toastr.error('Your comment is empty. Pleas, write something!')
    } else{
    this.comment.date=new Date()
    this.meetingService.addComment(this.meeting.id, this.item.id, this.comment)
        .then(() => {
          this.getComments();
          this.comment = {
            id: null,
            text: '',
            writer: null,
            date:new Date()
          }
        });
      }
  }

  goToGlitch(){
    if (this.authService.isPresident())
      this.router.navigate(['/president/glitches', this.item.glitch.id]);
    if (this.authService.isOwner())
      this.router.navigate(['/owner/glitches', this.item.glitch.id]);
    if (this.authService.isTenant())
      this.router.navigate(['/tenant/glitches', this.item.glitch.id]);
  }

  goToCommProblem(){
    if (this.authService.isPresident())
      this.router.navigate(['/president/buildings', this.meeting.building.id, 'communalProblems', this.item.communalProblem.id]);
    if (this.authService.isOwner())
      this.router.navigate(['/owner/buildings', this.meeting.building.id, 'communalProblems', this.item.communalProblem.id]);
    if (this.authService.isTenant())
      this.router.navigate(['/tenant/buildings', this.meeting.building.id, 'communalProblems', this.item.communalProblem.id]);
  }


  saveReport(){
    if (this.conclusion===''){
      this.toastr.error('Your conclusion is empty. Pleas, write something!')
    } else{
    this.item.conclusion=this.conclusion;
    this.meetingService.addConclusion(this.meeting.id, this.item).catch(
      error=>this.toastr.error('Error during update!')
    );
  }}
}
