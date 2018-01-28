import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BillsPresidentComponent } from './bills-president.component';

describe('BillsPresidentComponent', () => {
  let component: BillsPresidentComponent;
  let fixture: ComponentFixture<BillsPresidentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BillsPresidentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BillsPresidentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
