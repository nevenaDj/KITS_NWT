import { Injectable } from '@angular/core';
import { HttpClient ,HttpHeaders } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';

import { Apartment } from '../models/apartment';

@Injectable()
export class ApartmentService {
  headers: HttpHeaders;

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http:HttpClient) {
    this.headers = new HttpHeaders();
    this.headers.set('Content-Type', 'application/json');
    this.headers.set('X-Auth-Token', localStorage.getItem('token'));
   }

   announceChange(){
     this.RegenerateData.next();
   }

   addApartment(buildingID: number,apartment:Apartment): Promise<Apartment>{
     const url = `/api/buildings/${buildingID}/apartments`;
     return this.http
          .post<Apartment>(url, apartment, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
   }

   getApartments(buildingID: number): Promise<Apartment[]>{
     const url = `/api/buildings/${buildingID}/apartments`;
     return this.http
          .get<Apartment[]>(url,{headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError)
   }

   getApartment(id: number):Promise<Apartment>{
     const url = `/api/apartments/${id}`;
     return this.http
          .get<Apartment>(url, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
   }

   updateApartment(apartment:Apartment): Promise<Apartment>{
     const url = "/api/apartments";
     return this.http
          .put(url, apartment, {headers: this.headers})
          .toPromise()
          .then(res => { return res})
          .catch(this.handleError);
   }

   deleteApartment(id: number): Promise<{}>{
     const url = `/api/apartments/${id}`;
     return this.http
          .delete(url, {headers: this.headers})
          .toPromise()
          .catch(this.handleError);

   }

   private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
