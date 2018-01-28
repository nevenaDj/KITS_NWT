import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { NotificationDetailComponent } from './notification-detail.component';
import { NotificationService } from '../notification.service';
import { AuthService } from '../../login/auth.service';
import { ActivatedRouteStub } from '../../testing/router-stubs';


describe('NotificationDetailComponent', () => {
  let component: NotificationDetailComponent;
  let fixture: ComponentFixture<NotificationDetailComponent>;
  let notificationService: NotificationService;
  let authService: AuthService;
  let route: ActivatedRoute;
  let location: Location;

  let notificationServiceMock = {
    deleteNotification: jasmine.createSpy('deleteNotification')
      .and.returnValue(Promise.resolve()),
    getNotification: jasmine.createSpy('getNotification')
      .and.returnValue(Promise.resolve())
  };

  let authServiceMock = {
    getCurrentUser: jasmine.createSpy('getCurretUser')
      .and.returnValue(Promise.resolve())
  };

  let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 } ;

  let locationMock = {
    back: jasmine.createSpy('back')
      .and.returnValue(Promise.resolve())
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ NotificationDetailComponent ],
      providers: [
        {provide: NotificationService, useValue: notificationServiceMock},
        {provide: AuthService, useValue: authServiceMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        {provide: Location, useValue: locationMock}
      ]
    });
    fixture = TestBed.createComponent(NotificationDetailComponent);
    component = fixture.componentInstance;
    notificationService = TestBed.get(NotificationService);
    authService = TestBed.get(AuthService);
    route = TestBed.get(ActivatedRoute);
    location = TestBed.get(Location);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call get notification', () => {
    component.ngOnInit();
    expect(authService.getCurrentUser).toHaveBeenCalled();
    expect(notificationService.getNotification).toHaveBeenCalled();
  });

  it('should call deleteNotification', fakeAsync(() => {
    component.notification = {
      id: 1,
      date: null,
      text: '',
      user: null
    };
    component.deleteNotification();

    expect(notificationService.deleteNotification).toHaveBeenCalledWith(1);

    tick();

    expect(location.back).toHaveBeenCalled();

  }));
});
