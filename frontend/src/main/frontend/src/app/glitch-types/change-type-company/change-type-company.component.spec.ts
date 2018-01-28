import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeTypeCompanyComponent } from './change-type-company.component';

describe('ChangeTypeCompanyComponent', () => {
  let component: ChangeTypeCompanyComponent;
  let fixture: ComponentFixture<ChangeTypeCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeTypeCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeTypeCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
