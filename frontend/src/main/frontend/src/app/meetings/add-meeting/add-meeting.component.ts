import { Component, OnInit } from '@angular/core';
import { Meeting } from '../../models/meeting';
import { MeetingsService } from '../meetings.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-meeting',
  templateUrl: './add-meeting.component.html',
  styleUrls: ['./add-meeting.component.css']
})
export class AddMeetingComponent implements OnInit {

  public selectedMoment = new Date();
  public min = new Date();
  
  meeting: Meeting;

  constructor(private meetingSerivce: MeetingsService, private router: Router, private route: ActivatedRoute ) { 
    this.min.setDate(this.min.getDate() + 1);
    this.selectedMoment=this.min;
    this.meeting={
      active:false,
      dateAndTime:this.min,
      agenda:null,
      id:null, 
      building:{
        id:+this.route.snapshot.params['id'],
        address:null,
        president:null
      } 
    }
  }

  ngOnInit() {
    this.min.setDate(this.min.getDate() + 1);
    this.selectedMoment=this.min;
  }

  addNewMeeting(){
    this.meeting.dateAndTime=this.selectedMoment;
    this.meetingSerivce.addMeeting(+this.route.snapshot.params['id'], this.meeting).then(
      meeting=> this.router.navigate(['president/meetings'])
    )
  }
}
