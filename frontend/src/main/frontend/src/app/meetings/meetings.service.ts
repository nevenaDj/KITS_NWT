import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs';
import { Meeting } from '../models/meeting';
import { User } from '../models/user';
import { Builder } from 'selenium-webdriver';
import { Building } from '../models/building';

@Injectable()
export class MeetingsService {

  headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token'),'Content-Type':'application/json'});

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) { }

  announceChange(){
    this.RegenerateData.next();
  }

  getPresident():Promise<User>{
    const url = `/api/me`;
      return this.http.get<User>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getBuildings():Promise<Building[]>{
    const url = `/api/buildings/president`;
      return this.http.get<Building[]>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getMeetings(page: number, size: number = 15, id:number): Promise<Meeting[]>{
    const url= '/api/buildings/'+id+'/meetings';
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http
          .get<Meeting[]>(url, {params: httpParams})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getMeetingsCount( id:number): Promise<number>{
    const url= '/api/buildings/'+id+'/meetings/count';
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getMeeting( id:number): Promise<Meeting>{
    const url= '/api/meetings/'+id;
    return this.http
          .get<Meeting>(url, {headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  deleteAgendaItem(meeting_id:number, item_id:number){
    //add
    const url= '/api/building/'+meeting_id+'/meetings'
    return this.http
          .get<Meeting>(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addMeeting(id:number, meeting: Meeting ): Promise<Meeting>{
    const url= '/api/building/'+id+'/meetings'
    return this.http
          .post<Meeting>(url, meeting)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }


  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
