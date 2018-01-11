import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';

import { User } from '../models/user';

@Injectable()
export class TenantService {
  headers: HttpHeaders;

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders();
    this.headers.set('Content-Type', 'application/json');
    this.headers.set('X-Auth-Token', localStorage.getItem('token'));
   }

   announceChange(){
     this.RegenerateData.next();
   }

   addTenant(apartmentID: number, tenant:User): Promise<User>{
     const url = `/api//aparments/${apartmentID}/tenants`;
     return this.http
          .post<User>(url, tenant, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
   }

   getTenantsApartment(apartmentID: number): Promise<User[]>{
     const url = `/api/aparments/${apartmentID}/tenants`;
     return this.http
          .get<User[]>(url, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
   }

   getTenant(id: number): Promise<User>{
     const url = `/api/users/${id}`;
     return this.http
          .get<User>(url, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
   }

   deleteTenant(id: number, apartmentID: number): Promise<{}>{
     const url = `/api/apartment/${apartmentID}/tenants/${id}`;
     return this.http
          .delete(url,  {headers: this.headers})
          .toPromise()
          .catch(this.handleError);
   }

   private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }
}
