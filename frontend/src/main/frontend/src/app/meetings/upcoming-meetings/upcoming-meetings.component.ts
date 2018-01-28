import { Component, OnInit } from '@angular/core';
import { Meeting } from '../../models/meeting';
import { Subscription } from 'rxjs/Subscription';
import { Router } from '@angular/router';
import { MeetingsService } from '../meetings.service';

@Component({
  selector: 'app-upcoming-meetings',
  templateUrl: './upcoming-meetings.component.html',
  styleUrls: ['./upcoming-meetings.component.css']
})
export class UpcomingMeetingsComponent implements OnInit {


  meetings:Meeting[];

  subscription: Subscription;


  constructor(private router: Router, 
              private meetingSerivce: MeetingsService)  {

  }
  
  ngOnInit() {
    this.meetingSerivce.getOwner().then(
      owner=>this.getActiveMeetings(owner.id)
    );
  }



  getActiveMeetings(id:number){
    this.meetingSerivce.getUpcomingMeeting(id)
        .then(meetings => {this.meetings=meetings;
        });
  }



  gotoGetMeeting(id: number) {
    this.router.navigate(['/owner/meetings', id]);
  }


}
