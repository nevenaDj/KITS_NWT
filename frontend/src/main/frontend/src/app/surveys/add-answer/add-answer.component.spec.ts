import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { AddAnswerComponent } from './add-answer.component';
import { Survey } from '../../models/survey';
import { SurveyService } from '../survey.service';
import { ActivatedRouteStub } from '../../testing/router-stubs';


describe('AddAnswerComponent', () => {
  let component: AddAnswerComponent;
  let fixture: ComponentFixture<AddAnswerComponent>;
  let surveyService: SurveyService;

  let surveyServiceMock = {
    getSurvey: jasmine.createSpy('getSurvey')
      .and.returnValue(Promise.resolve(new Survey({
        id: 1,
        end: null,
        title: 'title',
        questions: []
      }))),
    addAnswer: jasmine.createSpy('addAnswer')
      .and.returnValue(Promise.resolve())
  };

  let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 } ;

  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAnswerComponent ],
      imports: [FormsModule],
      providers: [
        {provide: SurveyService, useValue: surveyServiceMock},
        {provide: Router, useValue: routerMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAnswerComponent);
    component = fixture.componentInstance;
    surveyService = TestBed.get(SurveyService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get survey', () => {
    component.ngOnInit();
    expect(surveyService.getSurvey).toHaveBeenCalledWith(1);
  });

  it('should save answer', () => {
    component.questionAnswer = [];
    component.save();
    expect(surveyService.addAnswer).toHaveBeenCalled();

  });


});
