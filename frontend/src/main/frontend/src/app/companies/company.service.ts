import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';
import { User } from '../models/user';

@Injectable()
export class CompanyService {
  private companiesUrl = '/api/companies';
  private headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token'),'Content-Type':'application/json'});

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) {  }

  announceChange(){
    this.RegenerateData.next();
  }

  addCompany(company: User): Promise<User>{
    return this.http
        .post<User>(this.companiesUrl, company, {headers: this.headers})
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  getCompanies(): Promise<User[]>{
    return this.http
        .get<User[]>(this.companiesUrl, {headers: this.headers})
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  getCompany(id: number): Promise<User>{
    const url = `/api/users/${id}`;
    return this.http.get(url, {headers: this.headers})
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  deleteCompany(id: number): Promise<{}>{
    const url = `${this.companiesUrl}/${id}`;
    return this.http.delete(url, {headers: this.headers})
        .toPromise()
        .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }
}