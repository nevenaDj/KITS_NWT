import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs';
import { Meeting } from '../models/meeting';
import { User } from '../models/user';
import { Builder } from 'selenium-webdriver';
import { Building } from '../models/building';
import { ContentWithoutAgenda } from '../models/content-without-agenda';
import { AgendaItem } from '../models/agenda-item';
import { Agenda } from '../models/agenda';
import { ItemComment } from '../models/itemComment';

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

  getBuildingsOwner():Promise<Building[]>{
    const url = `/api/buildings/owner`;
      return this.http.get<Building[]>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getBuildingsTenant():Promise<Building[]>{
    const url = `/api/buildings/tenant`;
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
    const url= '/api/meetings/'+meeting_id+'/items/'+item_id
    return this.http
          .delete<Meeting>(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addMeeting(id:number, meeting: Meeting ): Promise<Meeting>{
    const url= '/api/buildings/'+id+'/meetings'
    return this.http
          .post<Meeting>(url, meeting)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addItem(id:number, item: AgendaItem ): Promise<AgendaItem>{
    const url= '/api/meetings/'+id+'/items'
    return this.http
          .post<AgendaItem>(url, item)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getItem(meeting_id:number, item_id: number ): Promise<AgendaItem>{
    const url= '/api/meetings/'+meeting_id+'/items/'+item_id;
    console.log(url);
    return this.http
          .get<AgendaItem>(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getContent(id:number): Promise<ContentWithoutAgenda>{
    const url= '/api/buildings/'+id+'/agendas/no_meeting'
    return this.http
          .get<ContentWithoutAgenda>(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }
  
  updateOrder(agenda:Agenda): Promise<Agenda>{
    const url= '/api/agendas';
    return this.http
          .put<Agenda>(url, agenda,{headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }  
  
  addConclusion(id_meeting:number, item:AgendaItem): Promise<AgendaItem>{
    const url= '/api/meetings/'+id_meeting+'/items/'+item.id+'/conclusion';
    return this.http
          .put<AgendaItem>(url, item,{headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }



  getComments(id_meeting:number, id_item:number): Promise<ItemComment[]>{
    const url = '/api/meetings/'+id_meeting+'/items/'+id_item+'/comments';
    return this.http
          .get<ItemComment[]>(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addComment(id_meeting:number, id_item:number, comment: ItemComment): Promise<ItemComment> {
    const url = '/api/meetings/'+id_meeting+'/items/'+id_item+'/comments';
    return this.http
          .post<ItemComment>(url, comment)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  activateMeeting(id_building:number, id_meeting:number): Promise<Meeting> {
    const url = '/api/buildings/'+id_building+'/meetings/'+id_meeting+'/active';
    return this.http
          .put<Meeting>(url, {headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getActiveMeeting(id:number): Promise<Meeting[]> {
    const url = '/api/owner/'+id+'/meetings';
    return this.http
          .get<Meeting[]>(url, {headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getUpcomingMeeting(id:number): Promise<Meeting[]> {
    const url = '/api/owner/'+id+'/meetings/upcoming';
    console.log('url: '+url)
    return this.http
          .get<Meeting[]>(url, {headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }
  
  getOwner():Promise<User>{
    const url = `/api/me`;
      return this.http.get<User>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }


  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
