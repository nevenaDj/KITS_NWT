import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SendToTheOtherCompanyComponent } from './send-to-the-other-company.component';

describe('SendToTheOtherCompanyComponent', () => {
  let component: SendToTheOtherCompanyComponent;
  let fixture: ComponentFixture<SendToTheOtherCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SendToTheOtherCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SendToTheOtherCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
