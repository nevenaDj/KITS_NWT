import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastOptions, ToastsManager} from 'ng2-toastr';

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
      .and.returnValue(Promise.resolve()),
    hasAnswer: jasmine.createSpy('hasAnswer')
      .and.returnValue(Promise.resolve(false))
  };

  let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 } ;

  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  let locationMock = {
    back: jasmine.createSpy('back')
      .and.returnValue(Promise.resolve())
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAnswerComponent ],
      imports: [FormsModule],
      providers: [
        {provide: SurveyService, useValue: surveyServiceMock},
        {provide: Router, useValue: routerMock},
        {provide: Location, useValue: locationMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        ToastsManager,
        ToastOptions
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

  it('should save answer', () => {
    component.questionAnswer = [];
    component.save();
    expect(surveyService.addAnswer).toHaveBeenCalled();

  });


});
