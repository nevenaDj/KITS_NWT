import { Component, OnInit } from '@angular/core';
import { MeetingsService } from '../meetings.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Meeting } from '../../models/meeting';

@Component({
  selector: 'app-active-meeting-owner',
  templateUrl: './active-meeting-owner.component.html',
  styleUrls: ['./active-meeting-owner.component.css']
})
export class ActiveMeetingOwnerComponent implements OnInit {

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
    this.meetingSerivce.getActiveMeeting(id)
        .then(meetings => {this.meetings=meetings;
        });
  }



  gotoGetMeeting(id: number) {
    this.router.navigate(['/owner/meetings', id]);
  }

}
