import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Rx';

import { HttpClient, HttpHeaders,  HttpParams } from '@angular/common/http';
import { Http, Headers } from "@angular/http";
import { Observable } from "rxjs/Observable";
import { Glitch } from '../../models/glitch';
import { User } from '../../models/user';
import { Bill } from '../../models/bill';
import { Comment } from '../../models/comment';



@Injectable()
export class GlitchDataService {
  headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token'),'Content-Type':'application/json'});

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();
  
  constructor(private http: HttpClient) { }

  announceChange(){
    this.RegenerateData.next();
  }

  updateState(id:number, id_state:number): Promise<Glitch>{
    const url = '/api/glitches/'+id+'/state/'+id_state;
      return this.http.put<Glitch>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  setTime( glitch: Glitch): Promise<Glitch>{
    console.log(glitch.dateOfRepair.toISOString());
    glitch.dateOfRepair.toISOString()
    const url = '/api/apartments/'+glitch.apartment.id+'/glitches/'+glitch.id+'/repair?date='+glitch.dateOfRepair.toISOString();
    console.log("url: "+url);
      return this.http.put<Glitch>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getCompanies(glitch:Glitch): Promise<User[]>{
    const url = '/api/glitches/'+glitch.id+'/company';
      return this.http.get<User[]>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  setCompany(glitch:Glitch, id_company:number): Promise<Glitch>{
    const url = '/api/glitches/'+glitch.id+'/company/'+id_company;
      return this.http.put<Glitch>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getComments(glitchID: number): Promise<Comment[]>{
    const url = `/api/glitches/${glitchID}/comments`;
    return this.http
          .get<Comment[]>(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addComment(glitchID: number, comment: Comment): Promise<Comment> {
    const url = `/api/glitches/${glitchID}/comments`;
    return this.http
          .post<Comment>(url, comment)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addBill(bill:Bill, glitch:Glitch): Promise<Bill> {
    console.log("bill: "+JSON.stringify(bill));
    const url = '/api/apartments/'+glitch.apartment.id+'/glitches/'+glitch.id+'/bill';
    return this.http
          .post<Bill>(url, bill, {headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }
}
