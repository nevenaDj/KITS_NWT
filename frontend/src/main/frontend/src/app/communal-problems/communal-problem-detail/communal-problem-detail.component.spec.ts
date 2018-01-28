import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunalProblemDetailComponent } from './communal-problem-detail.component';

describe('CommunalProblemDetailComponent', () => {
  let component: CommunalProblemDetailComponent;
  let fixture: ComponentFixture<CommunalProblemDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommunalProblemDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommunalProblemDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
