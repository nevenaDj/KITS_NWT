import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';

import { Building } from '../models/building';
import { User } from '../models/user';


@Injectable()
export class BuildingService {
  private buildingsUrl = '/api/buildings';
  private headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token'),'Content-Type':'application/json'});

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) { 
  }

  announceChange(){
    this.RegenerateData.next();
  }

  addBuilding(building: Building): Promise<Building>{
    return this.http
        .post<Building>(this.buildingsUrl, building, {headers: this.headers})
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  getBuilding(id: number): Promise<Building>{
    const url = `${this.buildingsUrl}/${id}`;
    return this.http
          .get<Building>(url, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  getBuildings(page: number, size: number = 8): Promise<Building[]>{
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http
          .get(this.buildingsUrl, {headers: this.headers, params: httpParams})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  updateBuilding(building: Building): Promise<Building>{
    return this.http
          .put(this.buildingsUrl, building, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  deleteBuilding(id: number): Promise<{}>{
    const url = `${this.buildingsUrl}/${id}`;
    return this.http
        .delete(url, {headers: this.headers})
        .toPromise()
        .catch(this.handleError);
  }

  getBuildingsCount(): Promise<number>{
    const url = `${this.buildingsUrl}/count`;
    return this.http
        .get(url, {headers: this.headers})
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  addPresident(buildingID:number, president: User): Promise<User>{
    const url = `${this.buildingsUrl}/${buildingID}/president`;
    return this.http
          .post<User>(url, president, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }
}
