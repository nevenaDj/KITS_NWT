import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Meeting } from '../../models/meeting';
import { Router, ActivatedRoute } from '@angular/router';
import { MeetingsService } from '../meetings.service';
import { AgendaItem } from '../../models/agenda-item';
import { and } from '@angular/router/src/utils/collection';
import { Agenda } from '../../models/agenda';
import { ToastsManager } from 'ng2-toastr';

@Component({
  selector: 'app-update-item',
  templateUrl: './update-item.component.html',
  styleUrls: ['./update-item.component.css']
})
export class UpdateItemComponent implements OnInit {

  meeting:Meeting;
  select:number[]=[];
  agenda:Agenda;

  constructor(private route: ActivatedRoute,
    private meetingService: MeetingsService,
    private router: Router,
    private toastr: ToastsManager, 
    private vcr: ViewContainerRef) { 
      this.toastr.setRootViewContainerRef(vcr);
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
      this.agenda={
        agendaPoints:[],
        id:null,surveys:[]
      }
    }

  ngOnInit() {
    this.meetingService.getMeeting(+this.route.snapshot.params['id'])
      .then(meeting => {
        this.meeting = meeting;
        console.log("meeting: "+JSON.stringify( meeting))
        this.meeting.agenda.agendaPoints.sort((a,b)=>a.number-b.number);
        for (var i = 0; i < this.meeting.agenda.agendaPoints.length; i++) {
          this.select.push(i+1);
          let item:AgendaItem={
            id: this.meeting.agenda.agendaPoints[i].id,
            number :this.meeting.agenda.agendaPoints[i].number,          
            comments:[],
            conclusion:'',
            content:'',
            glitch:null,
            title:this.meeting.agenda.agendaPoints[i].title,
            type:''  ,
            communalProblem:null, 
            notification:null
          }
          this.agenda.agendaPoints.push(item);
        }
        
        
        console.log(this.select)
        });

  }

  setNewNumber(value:number, item:AgendaItem){
    let old_num:number=0;
    for (let temp of this.agenda.agendaPoints){
      if (temp.id===item.id){
        old_num=temp.number;
        break;
      }
    }

    for (let temp of this.agenda.agendaPoints){
      if (old_num>value){
        if (item.id==temp.id){
            temp.number=+value;
          }
        else{
          if (temp.number>=value && temp.number<old_num){
            temp.number++;
          } }
      }
      if (old_num<value){
        if (item.id==temp.id){
          temp.number=+value;
        }else{
          if (temp.number<=value && temp.number>old_num){
            temp.number--;
          } 
      }
      }
    }
    for (let temp of this.agenda.agendaPoints){
      for (let temp2 of this.meeting.agenda.agendaPoints){
        if (temp.id==temp2.id)
          temp2.number=temp.number;
      }
    }
    this.meeting.agenda.agendaPoints.sort((a,b)=>a.number-b.number);

  }

  save(){
    this.meetingService.updateOrder(this.meeting.agenda).then(
      agenda=>this.router.navigate(['/president/meetings',this.meeting.id])
    ).catch(error=> this.toastr.error('Error during update!'))
  }
}
