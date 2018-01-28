import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BillDetailsPresidentComponent } from './bill-details-president.component';

describe('BillDetailsPresidentComponent', () => {
  let component: BillDetailsPresidentComponent;
  let fixture: ComponentFixture<BillDetailsPresidentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BillDetailsPresidentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BillDetailsPresidentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
