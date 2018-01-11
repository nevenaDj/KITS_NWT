import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Headers } from "@angular/http";
import {Observable} from "rxjs/Observable";
import { Subject } from 'rxjs/Rx';

import { User } from '../models/user';

@Injectable()
export class CompanyDataService {
  private headers: HttpHeaders;
  private company: User;
  
  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();
  
  constructor(private http: HttpClient) {
      this.headers = new HttpHeaders();
      this.headers.append('Content-Type', 'application/json');
      //alert("token> "+JSON.stringify(localStorage.getItem('token')));
      this.headers.append('X-Auth-Token', localStorage.getItem('token'));
     // alert(JSON.stringify(this.headers));
  }
   
  
  getCompany():Promise<User>{
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
