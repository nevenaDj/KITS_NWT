import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AddApartmentComponent } from './add-apartment.component';
import { ApartmentService } from '../apartment.service';
import { Apartment } from '../../models/apartment';
import { ActivatedRouteStub } from '../../testing/router-stubs';


describe('AddApartmentComponent', () => {
  let component: AddApartmentComponent;
  let fixture: ComponentFixture<AddApartmentComponent>;
  let apartmentService: any;
  let route: any;
  let location: any;
  let router: any;

  beforeEach(() => {
    let apartmentServiceMock = {
      getApartment: jasmine.createSpy('getApartment')
        .and.returnValue(Promise.resolve(new Apartment({
          id: 1,
          number: 5,
          description: 'description',
          owner: null
        }))),
      addApartment: jasmine.createSpy('addApartment')
        .and.returnValue(Promise.resolve(new Apartment({
          id: 1,
          number: 5,
          description: 'description',
          owner: null
        }))),
      updateApartment: jasmine.createSpy('updateApartment')
        .and.returnValue(Promise.resolve()),
      announceChange: jasmine.createSpy('announceChange') 
    }

    let locationMock = {
      back: jasmine.createSpy('back')
    };

    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 } ;

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [ AddApartmentComponent ],
      imports: [FormsModule],
      providers: [
        {provide: ApartmentService, useValue: apartmentServiceMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub },
        {provide: Location, useValue: locationMock},
        {provide: Router, useValue: routerMock } ]
    });

    fixture = TestBed.createComponent(AddApartmentComponent);
    component = fixture.componentInstance;
    apartmentService = TestBed.get(ApartmentService);
    route = TestBed.get(ActivatedRoute);
    location = TestBed.get(Location);
    router = TestBed.get(Router);
  //  fixture.detectChanges();
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should featch apartment on init in edit mode', fakeAsync(() => {
    component.ngOnInit();
    expect(apartmentService.getApartment).toHaveBeenCalledWith(1);
    tick();

    expect(component.apartment.id).toBe(1);
    expect(component.apartment.number).toEqual(5);
    expect(component.apartment.description).toEqual('description');
    expect(component.apartment.owner).toBeNull();

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    let numberInput: any = fixture.debugElement.query(By.css('#number')).nativeElement;
    expect(numberInput.value).toEqual('5');
    let descriptionInput: any = fixture.debugElement.query(By.css('#description')).nativeElement;
    expect(descriptionInput.value).toEqual('description');

  }));

  // a helper function to tell Angular that an event on the HTML page has happened
  function newEvent(eventName: string, bubbles = false, cancelable = false) {
    let evt = document.createEvent('CustomEvent');  // MUST be 'CustomEvent'
    evt.initCustomEvent(eventName, bubbles, cancelable, null);
    return evt;
  }

  it('should bind data from update filds to apartment', fakeAsync(() => {
    fixture.detectChanges();
    tick();

    expect(component.apartment.number).not.toEqual(6);
    expect(component.apartment.description).not.toEqual('new description');

    let numberInpunt: any = fixture.debugElement.query(By.css('#number')).nativeElement;
    numberInpunt.value = '6';
    let descriptionInput: any = fixture.debugElement.query(By.css('#description')).nativeElement;
    descriptionInput.value = 'new description';

    numberInpunt.dispatchEvent(newEvent('input'));
    descriptionInput.dispatchEvent(newEvent('input'));

    expect(component.apartment.number).toEqual(6);
    expect(component.apartment.description).toEqual('new description');

  }));

  it('should add apartment', fakeAsync(() => {
    component.save();
    expect(apartmentService.addApartment).toHaveBeenCalled();
    tick();
    expect(router.navigate).toHaveBeenCalled();
  }));

  it('should update apartment', fakeAsync(() => {
    component.ngOnInit();
    component.save();
    expect(apartmentService.updateApartment).toHaveBeenCalled();
    tick();

    expect(location.back).toHaveBeenCalled();
  }));

});
