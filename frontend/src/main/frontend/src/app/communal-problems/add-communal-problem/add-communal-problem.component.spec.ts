import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCommunalProblemComponent } from './add-communal-problem.component';

describe('AddCommunalProblemComponent', () => {
  let component: AddCommunalProblemComponent;
  let fixture: ComponentFixture<AddCommunalProblemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCommunalProblemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCommunalProblemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
