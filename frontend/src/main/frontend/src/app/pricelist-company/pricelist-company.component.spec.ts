import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PricelistCompanyComponent } from './pricelist-company.component';

describe('PricelistCompanyComponent', () => {
  let component: PricelistCompanyComponent;
  let fixture: ComponentFixture<PricelistCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PricelistCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PricelistCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
