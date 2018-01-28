import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs/Rx';

import { GlitchType } from '../models/glitch-type';

@Injectable()
export class GlitchTypeService {
  private glitchTypesUrl = '/api/glitchTypes';
  
  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) {}

   announceChange(){
    this.RegenerateData.next();
  }

  addGlitchType(glithType: GlitchType): Promise<GlitchType>{
    return this.http
          .post<GlitchType>(this.glitchTypesUrl, glithType)
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  getGlitchTypes(): Promise<GlitchType[]>{
    return this.http
          .get(this.glitchTypesUrl)
          .toPromise()
          .then(res => {return res})
          .catch(this.handleError);
  }

  deleteGlitchType(id: number): Promise<{}>{
    const url = `${this.glitchTypesUrl}/${id}`;
    return this.http
          .delete(url)
          .toPromise()
          .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
