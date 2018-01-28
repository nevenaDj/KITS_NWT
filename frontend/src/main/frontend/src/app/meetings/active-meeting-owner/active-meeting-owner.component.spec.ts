import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveMeetingOwnerComponent } from './active-meeting-owner.component';

describe('ActiveMeetingOwnerComponent', () => {
  let component: ActiveMeetingOwnerComponent;
  let fixture: ComponentFixture<ActiveMeetingOwnerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActiveMeetingOwnerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActiveMeetingOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
