import { Component, OnInit } from '@angular/core';
import { Meeting } from '../../models/meeting';
import { ActivatedRoute, Router } from '@angular/router';
import { MeetingsService } from '../meetings.service';
import { AgendaItem } from '../../models/agendaItem';
import { AuthService } from '../../login/auth.service';

@Component({
  selector: 'app-meating-details',
  templateUrl: './meating-details.component.html',
  styleUrls: ['./meating-details.component.css']
})
export class MeatingDetailsComponent implements OnInit {

  meeting:Meeting;

  // if the date is in the past (-1 day)
  expired:boolean=false; 

  //if there are more than twenty agendaPoints or user is not a president, then false
  agendaSize:boolean=true;

  allReported:boolean=false;




  constructor(private route: ActivatedRoute,
              private meetingService: MeetingsService,
              private router: Router,
              private authService: AuthService) {
      this.meeting={
        id:null,
        active:false,
        dateAndTime:new Date(),
        building:null,
        agenda:{
          id:null,
          surveys:[],
          agendaPoints:[]
        }
      }
    }

    ngOnInit() {
    this.meetingService.getMeeting(+this.route.snapshot.params['id'])
      .then(meeting => {
        this.meeting = meeting;
        console.log("meeting: "+JSON.stringify( meeting))
        let now= new Date();
        let date= new Date(this.meeting.dateAndTime);
        let diff= date.getTime()-now.getTime();
        if (diff<0)
          this.expired=true;
        if (!this.expired)
          if (!this.authService.isPresident())
            this.agendaSize=false;
          else
            if (this.meeting.agenda.agendaPoints.length>20 )
              this.agendaSize=false;
        this.meeting.agenda.agendaPoints.sort((a,b)=>a.number-b.number);
      });

    }

    goToItem(item: AgendaItem){
      this.router.navigate(['/president/meetings',+this.route.snapshot.params['id'], 'items',item.id]);
    }

    newItem(){
      let route:string= '/president/meetings/'+this.route.snapshot.params['id']+ '/items/add';
      console.log(route)
      this.router.navigate([route]);
    }

    deleteItem(item:AgendaItem){

      this.meetingService.deleteAgendaItem(+this.route.snapshot.params['id'], item.id)
      .then(meeting=>{
        const index = this.meeting.agenda.agendaPoints.indexOf(item);
        this.meeting.agenda.agendaPoints.splice(index, 1);
          
      });
    }


  activate(){
    this.meeting.active=true;
    this.meetingService.activateMeeting(this.meeting.building.id, this.meeting.id);
  }



  updateOrder(){
    this.router.navigate(['/president/meetings',+this.route.snapshot.params['id'], 'update']);
  }
}
