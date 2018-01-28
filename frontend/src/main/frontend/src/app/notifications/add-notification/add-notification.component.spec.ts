import { async, ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { AddNotificationComponent } from './add-notification.component';
import { BuildingService } from '../../buildings/building.service';
import { ApartmentService } from '../../apartments/apartment.service';
import { NotificationService } from '../notification.service';

describe('AddNotificationComponent', () => {
  let component: AddNotificationComponent;
  let fixture: ComponentFixture<AddNotificationComponent>;
  let buildingService: BuildingService;
  let apartmentService: ApartmentService;
  let notificationService: NotificationService;
  let router: Router;
  let location: Location;

  let buildingServiceMock = {
    getMyBuilding: jasmine.createSpy('getMyBuilding')
      .and.returnValue(Promise.resolve())
  };

  let apartmentServiceMock = {
    getMyBuilding: jasmine.createSpy('getMyApartment')
      .and.returnValue(Promise.resolve())
  };

  let notificationServiceMock = {
    addNotification: jasmine.createSpy('addNotification')
      .and.returnValue(Promise.resolve())
  };

  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  let locationMock = {
    back: jasmine.createSpy('back')
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ AddNotificationComponent ],
      imports: [FormsModule, ReactiveFormsModule],
      providers: [
        {provide: BuildingService, useValue: buildingServiceMock},
        {provide: ApartmentService, useValue: apartmentServiceMock},
        {provide: NotificationService, useValue: notificationServiceMock},
        {provide: Router, useValue: routerMock},
        {provide: Location, useValue: locationMock}
      ]
    });
    fixture = TestBed.createComponent(AddNotificationComponent);
    component = fixture.componentInstance;
    buildingService = TestBed.get(BuildingService);
    apartmentService = TestBed.get(ApartmentService);
    notificationService = TestBed.get(NotificationService);
    location = TestBed.get(Location);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add notification', fakeAsync(() => {
    component.save();
    expect(notificationService.addNotification).toHaveBeenCalled();
    tick();
    expect(location.back).toHaveBeenCalled();
  }));

});
