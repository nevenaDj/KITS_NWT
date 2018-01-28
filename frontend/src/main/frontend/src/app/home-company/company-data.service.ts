import { Bill } from '../models/bill';
import { Glitch } from '../models/glitch';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders,  HttpParams } from '@angular/common/http';
import { Http, Headers } from "@angular/http";
import {Observable} from "rxjs/Observable";
import { Subject } from 'rxjs/Rx';


import { User } from '../models/user';
import { Pricelist } from '../models/pricelist';
import { UserPassword } from '../models/user-password';
import { ItemInPricelist } from '../models/item-in-pricelist';
import { GlitchType } from '../models/glitch-type';

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

  getActiveGlitches():Promise<Glitch[]>{
    const url = `/api/companies/activeGlitches`;
      return this.http.get<Glitch[]>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

    getBills(page: number, size: number = 10, id:number):Promise<Bill[]>{
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    const url = `/api/companies/`+id+`/bills`;
     console.log("id"+id);
     return this.http.get<Bill[]>(url, {params: httpParams})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getBillsCount(id:number): Promise<number>{
    const url =`/api/companies/`+id+`/bills/count`;
    return this.http
        .get(url)
        .toPromise()
        .then(res => res)
        .catch(this.handleError);
  }

  getPricelist(id:number):Promise<Pricelist>{
  const url = `/api/company/`+id+`/pricelist`;
   return this.http.get<Pricelist>(url, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
}


  getPendingGlitches():Promise<Glitch[]>{
    const url = `/api/companies/pendingGlitches`;
      return this.http.get<Glitch[]>(url, {headers: this.headers})
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

    getGlitch(id: number): Promise<Glitch>{
    const url = `/api/glitches/`+id;
    return this.http
          .get<Glitch>(url, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

    getBill(id: number): Promise<Bill>{
      const url = `/api/bills/` + id;
      return this.http
        .get<Bill>(url, {headers: this.headers})
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
    }

    changePasswordCompany(userPassword: UserPassword){
    const url = `/api/users/password`
      return this.http.put<UserPassword>(url, userPassword, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  addItem(id:number, newItem: ItemInPricelist){
    const url = '/api/company/'+id+'/pricelist/items'
      return this.http.post<ItemInPricelist>(url, newItem, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }


  deleteItem(id:number,id_item:number){
    const url = '/api/company/'+id+'/pricelist/items/'+id_item
      return this.http.delete<ItemInPricelist>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  updateItem(id:number,item:ItemInPricelist){
    console.log("update"+ JSON.stringify(item));
    const url = '/api/company/'+id+'/pricelist/items/'+item.id
      return this.http.put<ItemInPricelist>(url, item, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  updateGlitchType(id:number,type:GlitchType){
    console.log("update"+ JSON.stringify(type));
    const url = '/api/company/'+id+'/pricelist/types/'+type.id;
      return this.http.put<GlitchType>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

    private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }



}
