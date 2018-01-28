import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs';
import { User } from '../models/user';
import { Building } from '../models/building';
import { CommunalProblem } from '../models/communal-problem';
import { Apartment } from '../models/apartment';

@Injectable()
export class CommunalProblemService {
  headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token'),'Content-Type':'application/json', 'Accept':'application/json'});

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) { }

  announceChange(){
    this.RegenerateData.next();
  }

  getPresident():Promise<User>{
    const url = `/api/me`;
      return this.http.get<User>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getBuildings():Promise<Building[]>{
    const url = `/api/buildings/president`;
      return this.http.get<Building[]>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getBuildingsOwner():Promise<Building[]>{
    const url = `/api/buildings/owner`;
      return this.http.get<Building[]>(url, {headers: this.headers})
            .toPromise()
            .then(res => {return res})
            .catch(this.handleError);
  }

  getCommunalProblems(page: number, size: number = 15, id:number): Promise<CommunalProblem[]>{
    const url= '/api/buildings/'+id+'/communalProblems';
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http
          .get<CommunalProblem[]>(url, {headers: this.headers, params: httpParams})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getCommunalProblemCount( id:number): Promise<number>{
    const url= '/api/buildings/'+id+'/communalProblems/count';
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getActiveCommunalProblems(page: number, size: number = 15, id:number): Promise<CommunalProblem[]>{
    const url= '/api/buildings/'+id+'/communalProblems/active';
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http
          .get<CommunalProblem[]>(url, {params: httpParams})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getActiveCommunalProblemCount( id:number): Promise<number>{
    const url= '/api/buildings/'+id+'/communalProblems/active/count';
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getCommunalProblem( id:number): Promise<CommunalProblem>{
    const url= '/api/communalProblems/'+id;
    return this.http
          .get<CommunalProblem>(url, {headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addCommunalProblem(id:number, problem: CommunalProblem ): Promise<CommunalProblem>{
    const url= '/api/buildings/'+id+'/communalProblems'
    return this.http
          .post<CommunalProblem>(url, problem)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addApartment(id_p:number, id_ap ): Promise<CommunalProblem>{
    const url= '/api/communalProblems/'+id_p+'/apartments/'+id_ap
    return this.http
          .put<CommunalProblem>(url, {headers: this.headers}) 
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  deleteApartment(id_p:number, id_ap ): Promise<CommunalProblem>{
    const url= '/api/communalProblems/'+id_p+'/apartments/'+id_ap
    return this.http
          .delete<CommunalProblem>(url, {headers: this.headers}) 
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getCompaniesByGlitch(id:number): Promise<User[]>{
    const url= '/api/glitchTypes/'+id+'/companies'
    return this.http
          .get<User[]>(url, {headers: this.headers}) 
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  myApartments(): Promise<Apartment[]>{
    const url= '/api/apartments/owner/my/'
    return this.http
          .get<Apartment[]>(url, {headers: this.headers}) 
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
