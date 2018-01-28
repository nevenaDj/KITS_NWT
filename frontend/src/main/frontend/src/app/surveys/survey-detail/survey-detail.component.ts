import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

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
  expired: boolean;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private surveyService: SurveyService,
              private toastr: ToastsManager, 
              private vcr: ViewContainerRef) { 
    this.toastr.setRootViewContainerRef(vcr);
    this.survey = {
      id: null,
      title: '',
      end: null,
      questions: []
    }            
  }

  ngOnInit() {
    if (this.router.url.indexOf("answers") === -1){
        this.surveyService.getSurvey(+this.route.snapshot.params['id'])
            .then(survey => {
              this.survey = survey;
              this.expired = false;
            });
    }else{
      this.surveyService.getAnswer(+this.route.snapshot.params['id'])
            .then(survey => {
              this.survey = survey;
              this.expired = true;
            });

    }
  }

  deleteSurvey(){
    let meetingID: number = +this.route.snapshot.params['idMeeting'];
    this.surveyService.deleteSurvey(this.survey.id)
        .then(() => this.router.navigate([`/president/meetings/${meetingID}`]))
        .catch(() => this.toastr.error('The survey can not be deleted.'));

  }

}
