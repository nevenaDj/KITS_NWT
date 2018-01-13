import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';
import { User } from '../models/user';


@Injectable()
export class UserService {
  private userUrl = '/api/users';
  private headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token'),'Content-Type':'application/json'});

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) { }

  announceChange(){
    this.RegenerateData.next();
  }

  getUsers(): Promise<User[]>{
    return this.http.get<User[]>(this.userUrl, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }


}
