import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operators';

@Injectable()
export class AuthService {

  private authUrl = 'https://localhost:8443/api/login';

  constructor(private http: HttpClient) { }

  login(username:string, password:string): Promise<any>{
    return this.http
    .post(this.authUrl, {username, password}, {responseType: 'text'})
    .toPromise()
    .then(res => { localStorage.setItem('token', res) })
    .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
      console.error('An error occurred', error);
      return Promise.reject(error.message || error);
  }

}
