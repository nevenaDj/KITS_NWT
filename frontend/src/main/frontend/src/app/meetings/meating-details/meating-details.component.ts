import { Component, OnInit } from '@angular/core';
import { Meeting } from '../../models/meeting';
import { ActivatedRoute, Router } from '@angular/router';
import { MeetingsService } from '../meetings.service';
import { AgendaItem } from '../../models/agenda-item';
import { AuthService } from '../../login/auth.service';
import { Survey } from '../../models/survey';
import { SurveyService } from '../../surveys/survey.service';

@Component({
  selector: 'app-meating-details',
  templateUrl: './meating-details.component.html',
  styleUrls: ['./meating-details.component.css']
})
export class MeatingDetailsComponent implements OnInit {

  meeting:Meeting;
  surveys: Survey[];

  // if the date is in the past (-1 day)
  expired:boolean=false; 

  //if there are more than twenty agendaPoints or user is not a president, then false
  agendaSize:boolean=true;

  canActivate:boolean=false;

  president: boolean;

  constructor(private route: ActivatedRoute,
              private meetingService: MeetingsService,
              private surveyService: SurveyService,
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
      if (this.router.url.startsWith("/president")){
        this.president = true;
      }else if(this.router.url.startsWith("/owner")){
        this.president = false;
      }
    this.meetingService.getMeeting(+this.route.snapshot.params['id'])
      .then(meeting => {
        this.meeting = meeting;
        console.log("meeting: "+JSON.stringify( meeting))
        let now= new Date();
        let date= new Date(this.meeting.dateAndTime);
        let diff= date.getTime()-now.getTime();
        if (diff<0){
          let one_day:Date= new Date(date.getTime() + (1000 * 60 * 60 * 24));
          let diff2=one_day.getTime()-now.getTime();
          this.expired=true;
            if (diff2<0)
              this.canActivate=true;
        }
        if (!this.expired)
          /* if (!this.authService.isPresident())
            this.agendaSize=false;
          else */
            if (this.meeting.agenda.agendaPoints.length>20 )
              this.agendaSize=false;
        this.meeting.agenda.agendaPoints.sort((a,b)=>a.number-b.number);

        this.surveyService.getSurveys(+this.route.snapshot.params['id'])
            .then(surveys => this.surveys = surveys);

      });

    }

    goToItem(item: AgendaItem){
      if (this.president){
        this.router.navigate(['/president/meetings',+this.route.snapshot.params['id'], 'items',item.id]);
      }else{
        this.router.navigate(['/owner/meetings',+this.route.snapshot.params['id'], 'items',item.id]);
      }
    }

    newItem(){
      if (this.president){
        let route:string= '/president/meetings/'+this.route.snapshot.params['id']+ '/items/add';
        console.log(route);
        this.router.navigate([route]);
      }else{
        this.router.navigate([ `/owner/meetings/${this.route.snapshot.params['id']}/items/add`]);
      }
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

  addSurvey(){
    let meetingID = +this.route.snapshot.params['id'];
    this.router.navigate([`/president/meeting/${meetingID}/addSurvey`]);
  }

  gotoGetSurvey(id: number){
    let meetingID = +this.route.snapshot.params['id'];
    this.router.navigate([`/president/meeting/${meetingID}/surveys/${id}`]);
  }

  gotoGetSurveyAnswer(id:number){
    let meetingID = +this.route.snapshot.params['id'];
    if(this.president){
      this.router.navigate([`/president/meeting/${meetingID}/surveys/${id}/answers`]);
    }else{
      this.router.navigate([`/owner/meeting/${meetingID}/surveys/${id}/answers`]);
    }

  }

  gotoGetAnswer(id: number){
    this.router.navigate([`/owner/surveys/${id}/addAnswer`]);
  }
}
