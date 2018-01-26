import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { CurrentApartmentComponent } from './current-apartment.component';
import { ApartmentService } from '../apartment.service';


describe('CurrentApartmentComponent', () => {
  let component: CurrentApartmentComponent;
  let fixture: ComponentFixture<CurrentApartmentComponent>;
  let apartmentService: ApartmentService;
  let router: Router;

  let apartmentServiceMock = {

  };

  let routerMock = {
    url : jasmine.createSpy('url')
      .and.returnValue(Promise.resolve())
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ CurrentApartmentComponent ],
      providers:[
        {provide: ApartmentService,useValue: apartmentServiceMock },
        {provide: Router, useValue: routerMock}
      ]
    });
    fixture = TestBed.createComponent(CurrentApartmentComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
