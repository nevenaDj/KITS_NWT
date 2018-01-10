import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BillDetailsCompanyComponent } from './bill-details-company.component';

describe('BillDetailsCompanyComponent', () => {
  let component: BillDetailsCompanyComponent;
  let fixture: ComponentFixture<BillDetailsCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BillDetailsCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BillDetailsCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
