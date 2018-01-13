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
      this.headers.set('Content-Type', 'application/json');
      alert("token> "+JSON.stringify(localStorage.getItem('token')));
      this.headers.set('X-Auth-Token', localStorage.getItem('token'));
      console.log(this.headers.get('X-Auth-Token'));
  }
   
     
  getCompany():Promise<User>{
    const url = `/api/me`;
    //if (this.company==null){
      return this.http.get<User>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
    /*} else
    {
      return this.company;
    }*/
  }
  
   public ping() {
    this.http.get('/api/me')
      .subscribe(
        data => alert(data),
        err => alert(err)
      );
  }
  
    private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

  

}
