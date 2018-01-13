import { Injectable } from '@angular/core';
import { HttpClient ,HttpHeaders } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';

import { Apartment } from '../models/apartment';
import { User } from '../models/user';

@Injectable()
export class ApartmentService {
  headers: HttpHeaders;

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http:HttpClient) {
    this.headers = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token')});
    this.headers.set('Content-Type', 'application/json');
    this.headers.set('X-Auth-Token', localStorage.getItem('token'));
    console.log('Header:');
    console.log(this.headers.get('X-Auth-Token'));
   }

   announceChange(){
     this.RegenerateData.next();
   }

   addApartment(buildingID: number,apartment:Apartment): Promise<Apartment>{
     console.log('Header:');
     console.log(this.headers.get('X-Auth-Token'));
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
          .then(res => {return res})
          .catch(this.handleError);
   }

   deleteApartment(id: number): Promise<{}>{
     const url = `/api/apartments/${id}`;
     return this.http
          .delete(url, {headers: this.headers})
          .toPromise()
          .catch(this.handleError);
   }

   addOwner(apartmentID: number, owner: User): Promise<User>{
     const url = `/api/apartments/${apartmentID}/owner`;
     return this.http.post<User>(url, owner, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
   }

   private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
