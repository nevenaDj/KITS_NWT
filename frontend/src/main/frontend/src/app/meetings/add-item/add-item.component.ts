import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { ContentWithoutAgenda } from '../../models/content-without-agenda';
import { AgendaItem } from '../../models/agenda-item';
import { Glitch } from '../../models/glitch';
import { MeetingsService } from '../meetings.service';
import { Router,ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Meeting } from '../../models/meeting';
import { Notification } from '../../models/notification';
import { CommunalProblem } from '../../models/communal-problem';
import { ToastsManager } from 'ng2-toastr';

@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css']
})
export class AddItemComponent implements OnInit {

  selectedType:string;
  types:string[]=['TEXT','GLITCH','NOTIFICATION', 'COMMUNAL_PROBLEM'];
  contentWithoutMeeting:ContentWithoutAgenda;
  item:AgendaItem;

  selectedGlitch:Glitch;

  selectedNotification:Notification;

  selectedProblem:CommunalProblem;

  height:number=0;
  subscription: Subscription;
  meeting: Meeting;
  president: boolean;

  constructor(private router: Router, 
              private meetingSerivce: MeetingsService,
              private route: ActivatedRoute,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
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
    if (this.router.url.startsWith("/president")){
      this.president = true;
      this.types = ['TEXT','GLITCH','NOTIFICATION', 'COMMUNAL_PROBLEM'];
    }else if (this.router.url.startsWith("/owner")){
      this.president = false;
      this.types = ['TEXT'];
    }
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
    if (this.selectedType==null)
      this.toastr.error('You have to select a type!');
    else if (this.selectedType=="GLITCH" && this.selectedGlitch==null){
      this.toastr.error('You have to select a glitch!');
    }else if (this.selectedType=="NOTIFICATION" && this.selectedNotification==null){
      this.toastr.error('You have to select a notification!');
    }else if (this.selectedType=="COMMUNAL_PROBLEM" && this.selectedProblem==null){
      this.toastr.error('You have to select a communal problem!');
    }else if (this.item.title==''){
      this.toastr.error('You dont have title!');
    }else{
      this.item.type=this.selectedType;
      this.item.number=this.meeting.agenda.agendaPoints.length+1;
      this.item.notification=null;
      if (this.selectedType=="GLITCH")
        this.item.glitch=this.selectedGlitch;
      if (this.selectedType=="NOTIFICATION"){
        this.item.notification=this.selectedNotification;
      }
      if (this.selectedType=="COMMUNAL_PROBLEM")
        this.item.communalProblem=this.selectedProblem;
      
      this.meetingSerivce.addItem(  +this.route.snapshot.params['id'], this.item).then(
          item=>  {
            if (this.president )
              this.router.navigate(['/president/meetings',+this.route.snapshot.params['id'], 'items',item.id]) 
            else 
              this.router.navigate(['/owner/meetings',+this.route.snapshot.params['id'], 'items',item.id]) 
          })
  }}
}
