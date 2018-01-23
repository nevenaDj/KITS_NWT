import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Survey } from '../../models/survey';
import { Question } from '../../models/question';
import { Option } from '../../models/option';
import { SurveyService } from '../survey.service';

@Component({
  selector: 'app-add-survey',
  templateUrl: './add-survey.component.html',
  styleUrls: ['./add-survey.component.css']
})
export class AddSurveyComponent implements OnInit {
  survey: Survey;
  meetingID: number;
 

  constructor(private router: Router,
              private route: ActivatedRoute,
              private surveyService: SurveyService) { 
    this.survey = {
      id: null,
      title: '',
      end: null,
      questions: [
        {
        id:null,
        text: '',
        type: 'checkbox',
        options: [
          {
            id: null,
            text: ''
          }
        ]
        }
      ]
    };

    this.meetingID = 1;


  }

  ngOnInit() {
   
  }

  gotoAddQuestion(){
    this.survey.questions.push(
      new Question({
        text: '',
        type: 'checkbox',
        options: [
          {
            id: null,
            text: ''
          }
        ]
      }));   
  }

  onKey(event,i,j){
    console.log(event);
    console.log(this.survey);
    let len = this.survey.questions[i].options.length;
    if (this.survey.questions[i].options[len-1].text !== ''){
      this.survey.questions[i].options.push(new Option({text: ''}))

    }
  }

  deleteOption(i,j){
    this.survey.questions[i].options.splice(j, 1);
  }

  deleteQuestion(i){
    this.survey.questions.splice(i, 1);

  }

  save(){
    console.log(this.meetingID);
    this.surveyService.addSurvey(this.meetingID, this.survey)
      .then(survey => this.router.navigate([`/president/surveys/${survey.id}`]));

  }

}
