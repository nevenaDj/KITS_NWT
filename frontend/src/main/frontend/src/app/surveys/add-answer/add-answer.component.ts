import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { SurveyService } from '../survey.service';
import { Survey } from '../../models/survey';
import { QuestionAnswer } from '../../models/questionAnswer';
import { forEach } from '@angular/router/src/utils/collection';
import { OptionAnswer } from '../../models/optionAnswer';
import { Answer } from '../../models/answer';

@Component({
  selector: 'app-add-answer',
  templateUrl: './add-answer.component.html',
  styleUrls: ['./add-answer.component.css']
})
export class AddAnswerComponent implements OnInit {

  survey: Survey;
  questionAnswer : QuestionAnswer[];

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
        .then(survey => {
          this.survey = survey;
          this.questionAnswer = [];

          for(let question of survey.questions){
            this.questionAnswer.push(new QuestionAnswer({
              id: question.id,
              type: question.type,
              optionAnswers: []
            }));
            for(let option of question.options){
              this.questionAnswer[this.questionAnswer.length-1].optionAnswers.push(
                new OptionAnswer({
                  id: option.id,
                  isChecked: false
              }));
            }
          }

          console.log(this.questionAnswer);
        });
  }

  save(){
    let answers : Answer[] = [];
    let num: number = 0;
    let flag: boolean = true;
    console.log(this.questionAnswer);
    for(let qA of this.questionAnswer){
      answers.push(new Answer({
        questionID: qA.id,
        options: []
      }));
      num = 0;
      for(let oA of qA.optionAnswers){
        if (oA.isChecked){
          answers[answers.length-1].options.push(oA.id);
          num++;
        }
      }
      if (num === 0){
        console.log('nije odgovoreno na sva pitanja');
        flag = false;
      }
    }

    console.log(answers);

    if (flag){
      this.surveyService.addAnswer(this.survey.id, answers)
          .then(answers => console.log(answers));
    }
  }

  onChange(questionID: number, optionID: number){
    console.log(questionID);
    console.log(optionID);
    console.log(this.questionAnswer);
    for(let qA of this.questionAnswer){
      if (qA.id === questionID){
        for(let oA of qA.optionAnswers){
          if(oA.id === optionID){
            oA.isChecked = true;
          }else{
            oA.isChecked = false;
          }
        }
      }
    }
    console.log(this.questionAnswer);
  }

}
