import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';

import { AddSurveyComponent } from './add-survey.component';
import { Survey } from '../../models/survey';
import { SurveyService } from '../survey.service';
import { ActivatedRouteStub } from '../../testing/router-stubs';


describe('AddSurveyComponent', () => {
  let component: AddSurveyComponent;
  let fixture: ComponentFixture<AddSurveyComponent>;
  let surveyService: SurveyService;

  let surveyServiceMock = {
    addSurvey: jasmine.createSpy('addSurvey')
      .and.returnValue(Promise.resolve())
  };

  let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 } ;

  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddSurveyComponent ],
      imports: [
        FormsModule,
        OwlDateTimeModule,
        OwlNativeDateTimeModule],
      providers: [
        {provide: SurveyService, useValue: surveyServiceMock},
        {provide: Router, useValue: routerMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSurveyComponent);
    component = fixture.componentInstance;
    surveyService = TestBed.get(SurveyService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should save survey', () => {
    component.save();
    expect(surveyService.addSurvey).toHaveBeenCalled();

  });
});
