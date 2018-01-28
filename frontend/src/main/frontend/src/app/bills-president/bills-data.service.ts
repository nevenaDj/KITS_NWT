import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Bill } from '../models/bill';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs';

@Injectable()
export class BillsDataService {

  headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token'),'Content-Type':'application/json'});
  private company: User;

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) {
  }

  announceChange(){
     this.RegenerateData.next();
   }

  getUser():Promise<User>{
    const url = `/api/me`;
      return this.http.get<User>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getBills(page: number, size: number = 10, id:number):Promise<Bill[]>{
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    const url = `/api/users/`+id+`/bills`;
     console.log("id"+id);
     return this.http.get<Bill[]>(url, {params: httpParams})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getBillsCount(id:number): Promise<number>{
    const url =`/api/users/`+id+`/bills/count`;
    return this.http
        .get(url)
        .toPromise()
        .then(res => res)
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

     
    approveBill(id: number): Promise<Bill>{
      const url = `/api/bills/` + id;
      
      return this.http
        .put<Bill>(url, {headers: this.headers})
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
    }


    private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
