import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeResponsiblePersonComponent } from './change-responsible-person.component';

describe('ChangeResponsiblePersonComponent', () => {
  let component: ChangeResponsiblePersonComponent;
  let fixture: ComponentFixture<ChangeResponsiblePersonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeResponsiblePersonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeResponsiblePersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
