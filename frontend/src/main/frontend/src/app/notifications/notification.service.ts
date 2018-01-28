import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';

import { Notification } from '../models/notification';

@Injectable()
export class NotificationService {
  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) { 
  }

  announceChange(){
    this.RegenerateData.next();
  }

  addNotification(buildingID: number, notification: Notification): Promise<Notification>{
    const url = `/api/buildings/${buildingID}/notifications`;
    return this.http
        .post<Notification>(url, notification)
        .toPromise()
        .then(res => res)
        .catch(this.handleError);
  }

  getNotifications(buildingID: number, page: number, size: number = 15): Promise<Notification[]>{
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    const url = `/api/buildings/${buildingID}/notifications`;
    return this.http
        .get<Notification[]>(url, {params: httpParams})
        .toPromise()
        .then(res => res)
        .catch(this.handleError);

  }

  getNotificationsCount(buildingID: number): Promise<number>{
    const url = `/api/buildings/${buildingID}/notifications/count`;
    return this.http
        .get(url)
        .toPromise()
        .then(res => res)
        .catch(this.handleError);
  }

  getNotification(id: number): Promise<Notification>{
    const url = `/api/notifications/${id}`;
    return this.http
        .get(url)
        .toPromise()
        .then(res => res)
        .catch(this.handleError);
  }

  deleteNotification(id: number): Promise<{}>{
    const url = `/api/notifications/${id}`;
    return this.http
        .delete(url)
        .toPromise()
        .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
