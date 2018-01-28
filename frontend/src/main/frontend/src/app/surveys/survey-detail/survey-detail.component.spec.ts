import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { ToastOptions, ToastsManager } from 'ng2-toastr';

import { SurveyDetailComponent } from './survey-detail.component';
import { SurveyService } from '../survey.service';
import { ActivatedRouteStub } from '../../testing/router-stubs';
import { Survey } from '../../models/survey';


describe('SurveyDetailComponent', () => {
  let component: SurveyDetailComponent;
  let fixture: ComponentFixture<SurveyDetailComponent>;
  let surveyService: SurveyService;
  let route: ActivatedRoute;
  let router: Router;

  let surveyServiceMock = {
    getSurvey: jasmine.createSpy('getSurvey')
      .and.returnValue(Promise.resolve(new Survey({
        id: 1,
        end: null,
        title: 'title',
        questions: []
      }))),
    deleteSurvey: jasmine.createSpy('deleteSurvey')
      .and.returnValue(Promise.resolve())
  };

  let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 } ;

  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SurveyDetailComponent ],
      providers: [
        {provide: SurveyService, useValue: surveyServiceMock},
        {provide: Router, useValue: routerMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        ToastsManager,
        ToastOptions
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyDetailComponent);
    component = fixture.componentInstance;
    surveyService = TestBed.get(SurveyService);
    route = TestBed.get(ActivatedRoute);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call deleteSurvey', fakeAsync(() => {
    component.survey.id = 1;
    component.deleteSurvey();
    expect(surveyService.deleteSurvey).toHaveBeenCalledWith(1);

    tick();

    expect(router.navigate).toHaveBeenCalled();

  }));
});
