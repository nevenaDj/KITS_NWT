import { Component, OnInit } from '@angular/core';
import { ContentWithoutAgenda } from '../../models/content-without-agenda';
import { AgendaItem } from '../../models/agendaItem';
import { Glitch } from '../../models/glitch';
import { MeetingsService } from '../meetings.service';
import { Router,ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Meeting } from '../../models/meeting';

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
      glitch:null
    }
    this.contentWithoutMeeting={
      glitches:[]
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
        content => this.contentWithoutMeeting=content
      )}
    )
  }

  onSelectionChange(type){
    this.selectedType=type;
    console.log(JSON.stringify("change:"+this.selectedType));
    if (this.selectedType=="text")
      this.height=0;
    else if (this.selectedType=="glitch"){
      if (this.contentWithoutMeeting.glitches.length!=0){
        this.height=50+this.contentWithoutMeeting.glitches.length*50;
        this.selectedGlitch=this.contentWithoutMeeting.glitches[0];
      }
    }

  }

  onSelectionChangeGlitch(glitch){
    this.selectedGlitch=glitch;
  }

  addNewItem(){
    this.item.type=this.selectedType;
    this.item.number=this.meeting.agenda.agendaPoints.length+1;
    if (this.selectedType=="GLITCH")
      this.item.glitch=this.selectedGlitch;
    this.meetingSerivce.addItem(  +this.route.snapshot.params['id'], this.item).then(
        item=>  this.router.navigate(['/president/meetings',+this.route.snapshot.params['id'], 'items',item.id]) )
  }
}
