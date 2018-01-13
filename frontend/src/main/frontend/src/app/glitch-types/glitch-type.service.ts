import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';

import { GlitchType } from '../models/glitch-type';

@Injectable()
export class GlitchTypeService {
  private glitchTypesUrl = '/api/glitchTypes';
  private headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token'),'Content-Type':'application/json'});

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) {}

   announceChange(){
    this.RegenerateData.next();
  }

  addGlitchType(glithType: GlitchType): Promise<GlitchType>{
    return this.http
          .post<GlitchType>(this.glitchTypesUrl, glithType, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  getGlitchTypes(): Promise<GlitchType[]>{
    return this.http
          .get(this.glitchTypesUrl, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  deleteGlitchType(id: number): Promise<{}>{
    const url = `${this.glitchTypesUrl}/${id}`;
    return this.http
          .delete(url, {headers: this.headers})
          .toPromise()
          .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
