import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Headers } from "@angular/http";
import {Observable} from "rxjs/Observable";
import { Subject } from 'rxjs/Rx';

import { User } from '../models/user';
import { UserPassword } from '../models/user-password';

@Injectable()
export class CompanyDataService {
  headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token'),'Content-Type':'application/json'});
  private company: User;
  
  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();
  
  constructor(private http: HttpClient) {
  }
   
  announceChange(){
     this.RegenerateData.next();
   }
     
  getCompany():Promise<User>{
    const url = `/api/me`;
      return this.http.get<User>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }
  
   updateCompany(updateUser: User):Promise<User>{
    const url = `/api/users`
      return this.http.put<User>(url, updateUser, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }
  
    changePasswordCompany(userPassword: UserPassword){
    const url = `/api/users/password`
      return this.http.put<UserPassword>(url, userPassword, {headers: this.headers})
            .toPromise()
            .catch(this.handleError);
  }
  
    private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

  

}
