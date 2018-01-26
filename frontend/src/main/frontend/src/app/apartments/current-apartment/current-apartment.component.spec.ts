import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentApartmentComponent } from './current-apartment.component';

describe('CurrentApartmentComponent', () => {
  let component: CurrentApartmentComponent;
  let fixture: ComponentFixture<CurrentApartmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurrentApartmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrentApartmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
