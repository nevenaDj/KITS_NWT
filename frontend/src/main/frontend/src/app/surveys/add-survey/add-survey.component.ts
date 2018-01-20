import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Survey } from '../../models/survey';

@Component({
  selector: 'app-add-survey',
  templateUrl: './add-survey.component.html',
  styleUrls: ['./add-survey.component.css']
})
export class AddSurveyComponent implements OnInit {
  survey: Survey;
  meetingID: number;

  constructor(private router: Router) { 
    this.survey = {
      id: null,
      title: '',
      end: null

    };
  }

  ngOnInit() {
  }

  gotoAddQuestion(){
    this.router.navigate([`/meeting/${this.meetingID}/survey/addQuestion`])

  }

}
