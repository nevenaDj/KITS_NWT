import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Ng2OrderModule } from 'ng2-order-pipe';

import { NotificationsComponent } from './notifications.component';
import { BuildingService } from '../buildings/building.service';
import { ApartmentService } from '../apartments/apartment.service';
import { NotificationService } from './notification.service';
import { PagerService } from '../services/pager.service';


describe('NotificationsComponent', () => {
  let component: NotificationsComponent;
  let fixture: ComponentFixture<NotificationsComponent>;
  let buildingService: BuildingService;
  let apartmentService: ApartmentService;
  let notificationService: NotificationService;
  let pagerService: PagerService;
  let router: Router;

  let buildingServiceMock = {
    getMyBuilding: jasmine.createSpy('getMyBuilding')
      .and.returnValue(Promise.resolve())
  };

  let apartmentServiceMock = {
    getMyBuilding: jasmine.createSpy('getMyApartment')
      .and.returnValue(Promise.resolve())
  };

  let notificationServiceMock = {
    getNotificationsCount: jasmine.createSpy('getNotificationsCount')
      .and.returnValue(Promise.resolve()),
    getNotifications: jasmine.createSpy('getNotifications')
      .and.returnValue(Promise.resolve())

  };

  let pagerServiceMock = {
    getPager: jasmine.createSpy('getPager')
      .and.returnValue(Promise.resolve())
  };

  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [Ng2OrderModule],
      declarations: [ NotificationsComponent ],
      providers: [
        {provide: BuildingService, useValue: buildingServiceMock},
        {provide: ApartmentService, useValue: apartmentServiceMock},
        {provide: NotificationService, useValue: notificationServiceMock},
        {provide: PagerService, useValue: pagerServiceMock},
        {provide: Router, useValue: routerMock}

      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationsComponent);
    component = fixture.componentInstance;
    buildingService = TestBed.get(BuildingService);
    apartmentService = TestBed.get(ApartmentService);
    notificationService = TestBed.get(NotificationService);
    pagerService = TestBed.get(PagerService);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getNotifications()', () => {
    //component.buildingID = 1
    component.getNotifications(0,15);
    expect(notificationService.getNotifications).toHaveBeenCalledWith(1,0,15);
  });


  it('should call setPage()', fakeAsync(() => {
    component.setPage(1);
    expect(pagerService.getPager).toHaveBeenCalled();
    tick();
    expect(notificationService.getNotifications).toHaveBeenCalled();
  }));
});
