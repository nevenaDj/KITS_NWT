import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';

import { Building } from '../models/building';
import { User } from '../models/user';


@Injectable()
export class BuildingService {
  private buildingsUrl = '/api/buildings';
  private headers: HttpHeaders;

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

  addBuilding(building: Building): Promise<Building>{
    return this.http
        .post<Building>(this.buildingsUrl, building, {headers: this.headers})
        .toPromise()
        .then(res => {return res})
        .catch(this.handleError);
  }

  getBuilding(id: number): Promise<Building>{
    const url = `${this.buildingsUrl}/${id}`;
    return this.http.get<Building>(url, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  getBuildings(): Promise<Building[]>{
    return this.http.get(this.buildingsUrl, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  deleteBuilding(id: number): Promise<{}>{
    const url = `${this.buildingsUrl}/${id}`;
    return this.http.delete(url, {headers: this.headers})
        .toPromise()
        .catch(this.handleError);
  }

  addPresident(buildingID:number, president: User): Promise<User>{
    const url = `${this.buildingsUrl}/${buildingID}/president`;
    return this.http.post<User>(url, president, {headers: this.headers})
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

  

}
