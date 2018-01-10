import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangePasswordCompanyComponent } from './change-password-company.component';

describe('ChangePasswordCompanyComponent', () => {
  let component: ChangePasswordCompanyComponent;
  let fixture: ComponentFixture<ChangePasswordCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangePasswordCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangePasswordCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
