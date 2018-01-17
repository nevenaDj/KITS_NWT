import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AddOwnerComponent } from './add-owner.component';
import { ApartmentService } from '../apartment.service';
import { ActivatedRouteStub } from '../../testing/router-stubs';



describe('AddOwnerComponent', () => {
  let component: AddOwnerComponent;
  let fixture: ComponentFixture<AddOwnerComponent>;
  let apartmentService: any;
  let route: any;
  let location: any;

  beforeEach(() => {
    let apartmentServiceMock = {
      addOwner: jasmine.createSpy('addOwner')
        .and.returnValue(Promise.resolve())
    }

    let locationMock = {
      back: jasmine.createSpy('back')
    };

    let activateRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activateRouteStub.testParams = {id: 1};

    TestBed.configureTestingModule({
      declarations: [ AddOwnerComponent ],
      imports: [FormsModule],
      providers: [
        {provide: ApartmentService, useValue: apartmentServiceMock},
        {provide: ActivatedRoute, useValue: activateRouteStub},
        {provide: Location, useValue: locationMock}
      ]
    })

    fixture = TestBed.createComponent(AddOwnerComponent);
    component = fixture.componentInstance;
    apartmentService = TestBed.get(ApartmentService);
    route = TestBed.get(ActivatedRoute);
    location = TestBed.get(Location);
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should add owner', fakeAsync(() => {
    component.save();
    expect(apartmentService.addOwner).toHaveBeenCalled();
    tick();
    expect(location.back).toHaveBeenCalled();

  }));

  // a helper function to tell Angular that an event on the HTML page has happened
  function newEvent(eventName: string, bubbles = false, cancelable = false) {
    let evt = document.createEvent('CustomEvent');  // MUST be 'CustomEvent'
    evt.initCustomEvent(eventName, bubbles, cancelable, null);
    return evt;
  }

  it('should bind data from filds to owner', fakeAsync(() => {
    fixture.detectChanges();
    tick();

    expect(component.owner.username).not.toBe('owner');
    expect(component.owner.email).not.toBe('owner@gmail.com');
    expect(component.owner.phoneNo).not.toBe('123456');

    let usernameInput: any = fixture.debugElement.query(By.css('#username')).nativeElement;
    usernameInput.value = 'owner';
    let emailInput: any = fixture.debugElement.query(By.css('#email')).nativeElement;
    emailInput.value = 'owner@gmail.com';
    let phoneNoInput: any = fixture.debugElement.query(By.css('#phoneNo')).nativeElement;
    phoneNoInput.value = '123456';

    usernameInput.dispatchEvent(newEvent('input'));
    emailInput.dispatchEvent(newEvent('input'));
    phoneNoInput.dispatchEvent(newEvent('input'));

    expect(component.owner.username).toEqual('owner');
    expect(component.owner.email).toEqual('owner@gmail.com');
    expect(component.owner.phoneNo).toEqual('123456');

  }));

});
