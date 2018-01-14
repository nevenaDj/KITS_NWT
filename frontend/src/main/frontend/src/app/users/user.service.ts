import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';
import { User, UserRegister } from '../models/user';
import { UserPassword } from '../models/user-password';


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

  getUsers(page: number, size: number): Promise<User[]>{
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http
          .get<User[]>(this.userUrl, {headers: this.headers, params: httpParams})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  registration(user: UserRegister): Promise<{}>{
    const url = '/api/register';
    return this.http
          .post(url, user, {headers: this.headers})
          .toPromise()
          .catch(this.handleError);
  }

  getUsersCount(): Promise<number>{
    const url = '/api/users/count';
    return this.http
          .get(url, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  changePassword(user: UserPassword): Promise<{}>{
    const url = '/api/users/password';
    return this.http
          .put(url, user, {headers: this.headers})
          .toPromise()
          .catch(this.handleError);
  }

  getCurrentUser(): Promise<User>{
    const url = '/api/users/me';
    return this.http
          .get<User>(url, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  updateUser(user: User): Promise<User> {
    const url = '/api/users';
    return this.http
        .put<User>(url,user, {headers: this.headers})
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }


}
