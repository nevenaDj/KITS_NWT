import { Injectable } from '@angular/core';
import { HttpClient ,HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';

import { Apartment } from '../models/apartment';
import { User } from '../models/user';

@Injectable()
export class ApartmentService {
  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http:HttpClient) {}

   announceChange(){
     this.RegenerateData.next();
   }

   addApartment(buildingID: number,apartment:Apartment): Promise<Apartment>{
     const url = `/api/buildings/${buildingID}/apartments`;
     return this.http
          .post<Apartment>(url, apartment)
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
   }

   getApartments(buildingID: number): Promise<Apartment[]>{
     const url = `/api/buildings/${buildingID}/apartments`;
     return this.http
          .get<Apartment[]>(url)
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError)
   }

   getApartment(id: number):Promise<Apartment>{
     const url = `/api/apartments/${id}`;
     return this.http
          .get<Apartment>(url)
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
   }

   updateApartment(apartment:Apartment): Promise<Apartment>{
     const url = "/api/apartments";
     return this.http
          .put(url, apartment)
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
   }

   deleteApartment(id: number): Promise<{}>{
     const url = `/api/apartments/${id}`;
     return this.http
          .delete(url)
          .toPromise()
          .catch(this.handleError);
   }

   addOwner(apartmentID: number, owner: User): Promise<User>{
     const url = `/api/apartments/${apartmentID}/owner`;
     return this.http
            .post<User>(url, owner, )
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
   }

   findApartment(street: string, number: string, city: string, apartmentNumber: string): Promise<Apartment>{
     const httpParams = new HttpParams().set('street', street).set('number', number).set('city', city).set('number_apartment', apartmentNumber);
     const url = `/api/apartment`;
     return this.http
          .get(url, { params: httpParams})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
   }

   getMyApartment(): Promise<Apartment>{
     const url = '/api/apartments/my';
     return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);

   }

   private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}