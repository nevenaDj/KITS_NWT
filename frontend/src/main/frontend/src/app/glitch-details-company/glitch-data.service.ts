import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Rx';
import { Glitch } from '../models/glitch';
import { HttpClient, HttpHeaders,  HttpParams } from '@angular/common/http';
import { Http, Headers } from "@angular/http";
import { Observable } from "rxjs/Observable";


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

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }
}
