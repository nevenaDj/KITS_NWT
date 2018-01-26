import { Component, OnInit } from '@angular/core';
import { Meeting } from '../../models/meeting';
import { Router, ActivatedRoute } from '@angular/router';
import { MeetingsService } from '../meetings.service';
import { AgendaItem } from '../../models/agendaItem';
import { and } from '@angular/router/src/utils/collection';
import { Agenda } from '../../models/agenda';

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
    private router: Router) {
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
            type:''  
          }
          this.agenda.agendaPoints.push(item);
        }
        
        
        console.log(this.select)
        });

  }

  setNewNumber(value:number, item:AgendaItem){
    console.log("old items: "+JSON.stringify(this.agenda.agendaPoints))
    console.log("item: "+JSON.stringify(item.title)+', '+item.number+", number: "+value)

    let old_num:number=0;
    for (let temp of this.agenda.agendaPoints){
      if (temp.id===item.id){
        old_num=temp.number;
        break;
      }
    }

    for (let temp of this.agenda.agendaPoints){
      console.log('old_num:'+old_num+', value: '+value+', temp.number: '+temp.number);
      if (old_num>value){
        console.log(old_num+' > '+value);
        if (item.id==temp.id){
            console.log('temp -'+temp.number)
            temp.number=+value;
            console.log('temp -'+temp.number)
          }
        else{
          if (temp.number>=value && temp.number<old_num){
            console.log('temp before++ '+temp.number);
            temp.number++;
            console.log('temp after++ '+temp.number);
          } }
      }
      if (old_num<value){
        console.log(old_num+' < '+value)
        if (item.id==temp.id){
          console.log('temp -'+temp.number)
          temp.number=+value;
          console.log('temp -'+temp.number)
        }else{
          if (temp.number<=value && temp.number>old_num){
            console.log('temp before-- '+temp.number);
            temp.number--;
            console.log('temp after-- '+temp.number);
          } 
      }
      }
    }
    console.log("old items: "+JSON.stringify(this.agenda.agendaPoints))
    for (let temp of this.agenda.agendaPoints){
      for (let temp2 of this.meeting.agenda.agendaPoints){
        if (temp.id==temp2.id)
          temp2.number=temp.number;
      }
    }
    this.meeting.agenda.agendaPoints.sort((a,b)=>a.number-b.number);
    console.log("new items: "+JSON.stringify(this.meeting.agenda.agendaPoints))


  }

  save(){
    this.meetingService.updateOrder(this.meeting.agenda).then(
      agenda=>this.router.navigate(['/president/meetings',this.meeting.id])
    )
  }
}
