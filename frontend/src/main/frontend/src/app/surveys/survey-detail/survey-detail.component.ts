import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { SurveyService } from '../survey.service';
import { Survey } from '../../models/survey';
import { QuestionAnswer } from '../../models/questionAnswer';
import { forEach } from '@angular/router/src/utils/collection';
import { OptionAnswer } from '../../models/optionAnswer';
import { Answer } from '../../models/answer';


@Component({
  selector: 'app-survey-detail',
  templateUrl: './survey-detail.component.html',
  styleUrls: ['./survey-detail.component.css']
})
export class SurveyDetailComponent implements OnInit {
  survey: Survey;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private surveyService: SurveyService) { 
    this.survey = {
      id: null,
      title: '',
      end: null,
      questions: []
    }            
  }

  ngOnInit() {
    this.surveyService.getSurvey(+this.route.snapshot.params['id'])
        .then(survey => this.survey = survey);
  }

  deleteSurvey(){
    console.log("delete");
    this.surveyService.deleteSurvey(this.survey.id)
        .then(() => this.router.navigate(['meetings']));

  }

}
