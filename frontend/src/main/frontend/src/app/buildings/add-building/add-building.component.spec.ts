import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Location } from '@angular/common';
import { Router,ActivatedRoute } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { AddBuildingComponent } from './add-building.component';
import { BuildingService } from '../building.service';
import { Building } from '../../models/building';
import { ActivatedRouteStub } from '../../testing/router-stubs';


describe('AddBuildingComponent', () => {
  let component: AddBuildingComponent;
  let fixture: ComponentFixture<AddBuildingComponent>;
  let buildingService: any;
  let route: any;
  let location: any;
  let router: any;


  beforeEach(() => {
    let buildingServiceMock = {
      getBuilding: jasmine.createSpy('getBuilding')
        .and.returnValue(Promise.resolve(new Building({
          id: 1,
          address: {
            id: 1,
            street: 'street',
            number: '1a',
            city: 'city',
            zipCode: 15000
          }
        }))),
       addBuilding: jasmine.createSpy('addBuilding')
        .and.returnValue(Promise.resolve(new Building({
          id: 1,
          address: {
            id: 1,
            street: 'street',
            number: '1a',
            city: 'city',
            zipCode: 15000
          }
        }))),
        updateBuilding: jasmine.createSpy('updateBuilding')
          .and.returnValue(Promise.resolve()),
          announceChange: jasmine.createSpy('announceChange') 
    };

    let locationMock = {
      back: jasmine.createSpy('back')
    };

    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 } ;

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [ AddBuildingComponent ],
      imports: [FormsModule, ReactiveFormsModule],
      providers: [
        {provide: BuildingService, useValue: buildingServiceMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        {provide: Location, useValue: locationMock},
        {provide: Router, useValue: routerMock}
      ]
    })

    fixture = TestBed.createComponent(AddBuildingComponent);
    component = fixture.componentInstance;
    buildingService = TestBed.get(BuildingService);
    route = TestBed.get(ActivatedRoute);
    location = TestBed.get(Location);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should featch building on init in edit mode', fakeAsync(() => {
    component.ngOnInit();
    expect(buildingService.getBuilding).toHaveBeenCalledWith(1);
    tick();

    expect(component.building.id).toBe(1);
    expect(component.building.address.street).toEqual('street');
    expect(component.building.address.number).toEqual('1a');
    expect(component.building.address.city).toEqual('city');
    expect(component.building.address.zipCode).toEqual(15000);

    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    let streetInput: any = fixture.debugElement.query(By.css('#street')).nativeElement;
    expect(streetInput.value).toEqual('street');
    let numberInput: any = fixture.debugElement.query(By.css('#number')).nativeElement;
    expect(numberInput.value).toEqual('1a');
    let cityInput: any = fixture.debugElement.query(By.css('#city')).nativeElement;
    expect(cityInput.value).toEqual('city');
    let zipCodeInput: any = fixture.debugElement.query(By.css('#zipCode')).nativeElement;
    expect(zipCodeInput.value).toEqual('15000');

  }));

  // a helper function to tell Angular that an event on the HTML page has happened
  function newEvent(eventName: string, bubbles = false, cancelable = false) {
    let evt = document.createEvent('CustomEvent');  // MUST be 'CustomEvent'
    evt.initCustomEvent(eventName, bubbles, cancelable, null);
    return evt;
  }

  it('should bind data from update filds to building', fakeAsync(() => {
    fixture.detectChanges();
    tick();

    expect(component.building.address.street).not.toEqual('new street');
    expect(component.building.address.number).not.toEqual('20');
    expect(component.building.address.city).not.toEqual('new city');
    expect(component.building.address.zipCode).not.toEqual(21000);


    let streetInput: any = fixture.debugElement.query(By.css('#street')).nativeElement;
    streetInput.value = 'new street';
    let numberInput: any = fixture.debugElement.query(By.css('#number')).nativeElement;
    numberInput.value = '20';
    let cityInput: any = fixture.debugElement.query(By.css('#city')).nativeElement;
    cityInput.value = 'new city';
    let zipCodeInput: any = fixture.debugElement.query(By.css('#zipCode')).nativeElement;
    zipCodeInput.value = '21000';

    streetInput.dispatchEvent(newEvent('input'));
    numberInput.dispatchEvent(newEvent('input'));
    cityInput.dispatchEvent(newEvent('input'));
    zipCodeInput.dispatchEvent(newEvent('input'));

    expect(component.building.address.street).toEqual('new street');
    expect(component.building.address.number).toEqual('20');
    expect(component.building.address.city).toEqual('new city');
    expect(component.building.address.zipCode).toEqual(21000);

  }));

  it('should add building', fakeAsync(() => {
    component.save();
    expect(buildingService.addBuilding).toHaveBeenCalled();
    tick();
    expect(router.navigate).toHaveBeenCalled();
  }));

  it('should update building', fakeAsync(() => {
    component.ngOnInit();
    component.save();
    expect(buildingService.updateBuilding).toHaveBeenCalled();
    tick();

    expect(location.back).toHaveBeenCalled();
  }));


});
