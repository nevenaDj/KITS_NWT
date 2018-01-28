import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BillsCompanyComponent } from './bills-company.component';

describe('BillsCompanyComponent', () => {
  let component: BillsCompanyComponent;
  let fixture: ComponentFixture<BillsCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BillsCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BillsCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
