import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';
import { User } from '../models/user';

@Injectable()
export class CompanyService {
  private companiesUrl = '/api/companies';
  
  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) {  }

  announceChange(){
    this.RegenerateData.next();
  }

  addCompany(company: User): Promise<User>{
    return this.http
        .post<User>(this.companiesUrl, company)
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  getCompanies(page: number, size: number = 8): Promise<User[]>{
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http
        .get<User[]>(this.companiesUrl, { params: httpParams})
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  getCompany(id: number): Promise<User>{
    const url = `/api/users/${id}`;
    return this.http.get(url)
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  deleteCompany(id: number): Promise<{}>{
    const url = `${this.companiesUrl}/${id}`;
    return this.http.delete(url)
        .toPromise()
        .catch(this.handleError);
  }

  getCompaniesCount(): Promise<number>{
    const url = `${this.companiesUrl}/count`;
    return this.http
        .get(url)
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }
}