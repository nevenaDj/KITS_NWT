import { Component, OnInit } from '@angular/core';
import { ContentWithoutAgenda } from '../../models/content-without-agenda';
import { AgendaItem } from '../../models/agenda-item';
import { Glitch } from '../../models/glitch';
import { MeetingsService } from '../meetings.service';
import { Router,ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Meeting } from '../../models/meeting';
import { Notification } from '../../models/notification';
import { CommunalProblem } from '../../models/communal-problem';

@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css']
})
export class AddItemComponent implements OnInit {

  selectedType:string='TEXT';
  types:string[]=['TEXT','GLITCH','NOTIFICATION', 'COMMUNAL_PROBLEM'];
  contentWithoutMeeting:ContentWithoutAgenda;
  item:AgendaItem;

  selectedGlitch:Glitch;

  selectedNotification:Notification;

  selectedProblem:CommunalProblem;

  height:number=0;
  subscription: Subscription;
  meeting: Meeting;

  constructor(private router: Router, 
              private meetingSerivce: MeetingsService,
              private route: ActivatedRoute) {
    this.item={
      id:null,
      comments:[],
      conclusion:null,
      content:'',
      number:0,
      title:'',
      type:'text',
      glitch:null, 
      notification:null,
      communalProblem:null,
      
    }
    this.contentWithoutMeeting={
      glitches:[],
      notifications:[],
      communalProblems:[]
    }
    this.subscription = meetingSerivce.RegenerateData$
    .subscribe(() => {
      this.getContent();

    });
   }


  ngOnInit() {
    
    this.getContent();
  }

  getContent(){
    this.meetingSerivce.getMeeting(  +this.route.snapshot.params['id']).then(
      meeting=>  {
        this.meeting=meeting;
        this.meetingSerivce.getContent(meeting.building.id).then(
        content => {
          this.contentWithoutMeeting=content
          console.log('contnet not: '+JSON.stringify(content.notifications))
          console.log('comm p not: '+JSON.stringify(content.communalProblems))
        }
      )   
    }
      
    )
  }

  onSelectionChange(type){
    this.selectedType=type;
    console.log(JSON.stringify("change:"+this.selectedType));
    if (this.selectedType=="TEXT")
      this.height=0;
    else if (this.selectedType=="GLITCH"){
      if (this.contentWithoutMeeting.glitches.length!=0){
        this.selectedGlitch=this.contentWithoutMeeting.glitches[0];
      }
    }
    else if (this.selectedType=="NOTIFICATION"){
      if (this.contentWithoutMeeting.notifications.length!=0){
        this.selectedNotification=this.contentWithoutMeeting.notifications[0];
      }
    }
    else if (this.selectedType=="COMMUNAL_PROBLEM"){
      if (this.contentWithoutMeeting.communalProblems.length!=0){
        this.selectedProblem=this.contentWithoutMeeting.communalProblems[0];
      }
    }
  }

  onSelectionChangeGlitch(glitch){
    this.selectedGlitch=glitch;
  }

  onSelectionChangeNotification(notificaiton){
    this.selectedNotification=notificaiton;
  }

  onSelectionChangeProblem(problem){
    this.selectedProblem=problem;
  }

  addNewItem(){
    this.item.type=this.selectedType;
    this.item.number=this.meeting.agenda.agendaPoints.length+1;
    console.log('not1'+JSON.stringify(this.item.notification))
    this.item.notification=null;
    console.log('not2'+JSON.stringify(this.item.notification))
    if (this.selectedType=="GLITCH")
      this.item.glitch=this.selectedGlitch;
    if (this.selectedType=="NOTIFICATION"){
      console.log('selected'+JSON.stringify(this.selectedNotification))
      this.item.notification=this.selectedNotification;
    }
    if (this.selectedType=="COMMUNAL_PROBLEM")
      this.item.communalProblem=this.selectedProblem;
    
    this.meetingSerivce.addItem(  +this.route.snapshot.params['id'], this.item).then(
        item=>  this.router.navigate(['/president/meetings',+this.route.snapshot.params['id'], 'items',item.id]) )
  }
}
