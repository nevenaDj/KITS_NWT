import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';


import { SurveyService } from './survey.service';
import { Survey } from '../models/survey';
import { Answer } from '../models/answer';

describe('SurveyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [SurveyService]
    });
    this.surveyService = getTestBed().get(SurveyService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  afterEach(() => {
    this.backend.verify();
  });

  it('should be created', inject([SurveyService], (service: SurveyService) => {
    expect(service).toBeTruthy();
  }));

  it('getSurvey() send request', () => {
    let surveyID = 1;
    this.surveyService.getSurvey(surveyID).then((data:Survey) => expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/surveys/${surveyID}`
          && req.method === 'GET' 
        });
      }
  );

  it('getSurvey() should query url and get a survey', () => {
    let survey: Survey = new Survey({
      id: 1,
      title: 'title',
      end: null,
      questions: [
        {
          id: 1,
          text: 'question',
          type: 'radio',
          options: [
            {
              id:1,
              text: 'option',
              count: null
            }
          ]
        }
      ]
    });

    this.surveyService.getSurvey(survey.id).then((data: Survey) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.title).toEqual('title');
      expect(data.questions[0].id).toBe(1);
      expect(data.questions[0].text).toEqual('question');
      expect(data.questions[0].options[0].id).toBe(1);
      expect(data.questions[0].options[0].text).toEqual('option');

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/surveys/${survey.id}`
          && req.method === 'GET'
    }).flush(survey, {status: 200, statusText: 'OK'});

  });

  it('addSurvey() should query url and save an survey', () => {
    let meetingID: number = 1;
    let survey: Survey = new Survey({
      title: 'new survey',
      end: null,
      questions: []
    });
    this.surveyService.addSurvey(meetingID,survey).then((data:Survey) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.title).toEqual('new survey');
      expect(data.end).toBeNull();
     
    });

    survey.id = 1;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/meetings/${meetingID}/surveys`
          && req.method === 'POST'      
        })
        .flush(survey, { status: 201, statusText: 'CREATED'});
  });

  it('deleteSurvey() should query url and delete a survey', () => {
    let surveyID = 1;

    this.surveyService.deleteSurvey(surveyID).then((data)=> expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/surveys/${surveyID}`
          && req.method === 'DELETE'
    }).flush(null, {status: 200, statusText: 'OK'});

  });

  it('addAnswer() should query url and save a answer', () => {
    let surveyID: number = 1;
    let answers: Answer[]= [
      {
      questionID:1,
      options: [1,2,3]
    }];
    this.surveyService.addAnswer(surveyID,answers).then((data:Answer[]) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data[0].questionID).toEqual(1);
      expect(data[0].options[0]).toEqual(1);
      expect(data[0].options[1]).toEqual(2);
     
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/surveys/${surveyID}/answers`
          && req.method === 'POST'      
        })
        .flush(answers, { status: 201, statusText: 'CREATED'});
  });


});
