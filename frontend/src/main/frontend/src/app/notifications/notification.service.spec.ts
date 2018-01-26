import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';


import { NotificationService } from './notification.service';
import { Notification } from '../models/notification';

describe('NotificationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [NotificationService]
    });

    this.notificationService = getTestBed().get(NotificationService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  afterEach(() => {
    this.backend.verify();
  });

  it('should be created', inject([NotificationService], (service: NotificationService) => {
    expect(service).toBeTruthy();
  }));

  it('getNotifications() send request', () => {
    let buildingID: number = 1;
    this.notificationService.getNotifications(buildingID, 0).then((data: Notification[]) => expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === `/api/buildings/${buildingID}/notifications`
          && params.set('page','0').set('size', '8')
          && req.method === 'GET';
    });
  });

  it('getNotifications() should return some notifications', () => {
    let buildingID: number = 1;
    let notifications: Notification[] = [
      {
        id: 1,
        date: null,
        user: null,
        text: 'notification'
      },
      {
        id: 2,
        date: null,
        user: null,
        text: 'notification 2'
      },
    ];

    this.notificationService.getNotifications(buildingID, 0).then((data: Notification[]) => {
      expect(data).toBeTruthy();
      expect(data.length).toBe(2);
      expect(data).toEqual(notifications);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === `/api/buildings/${buildingID}/notifications`
          && params.set('page','0').set('size', '8')
          && req.method === 'GET'
      })
      .flush(notifications, { status: 200, statusText: 'OK'});

  });

  it('getNotificationsCount() should query url and return number of notifications', () => {
    let count: number = 2;
    let buildingID: number = 1;

    this.notificationService.getNotificationsCount(buildingID).then(data => {
      expect(data).toBeTruthy();
      expect(data).toEqual(2);
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/buildings/${buildingID}/notifications/count`
          && req.method === 'GET'
    })
      .flush(count, {status: 200, statusText: 'OK'});

  });

  it('addNotification() should query url and save a notification', () => {
    let buildingID: number = 1;
    let notification: Notification = new Notification({
      text: 'notification',
      date: null,
      user: null
    });

    this.notificationService.addNotification(buildingID, notification).then((data:  Notification) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.text).toEqual('notification');
    });

    notification.id = 1;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/buildings/${buildingID}/notifications`
          && req.method === 'POST'
    })
      .flush(notification, {status: 201, statusText: 'CREATED'});
  });

});
