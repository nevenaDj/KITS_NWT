import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivecommunalProblemComponent } from './activecommunal-problem.component';

describe('ActivecommunalProblemComponent', () => {
  let component: ActivecommunalProblemComponent;
  let fixture: ComponentFixture<ActivecommunalProblemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivecommunalProblemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivecommunalProblemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
