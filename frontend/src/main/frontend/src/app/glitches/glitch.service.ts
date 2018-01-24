import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Subject } from 'rxjs/Subject';
import { Glitch } from '../models/glitch';
import { Comment } from '../models/comment';
import { User } from '../models/user';


@Injectable()
export class GlitchService {
  headers: HttpHeaders = new HttpHeaders({'X-Auth-Token': localStorage.getItem('token'),'Content-Type':'application/json'});
  private glitchesUrl = '/api/glitches';

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) { }

  announceChange(){
    this.RegenerateData.next();
  }

  getGlitches(apartmentID: number, page: number, size: number = 15): Promise<Glitch[]>{
    const url = `/api/apartments/${apartmentID}/glitches`;
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http
          .get<Glitch[]>(url, {params: httpParams})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getGlitchesCount(apartmentID: number): Promise<number>{
    const url =  `/api/apartments/${apartmentID}/glitches/count`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getMyResponsabilities(page: number, size: number = 15): Promise<Glitch[]>{
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    const url='/api/responsibleGlitches';
    return this.http
          .get<Glitch[]>(url, {params: httpParams})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getMyResponsabilitiesCount(): Promise<number>{
    const url='/api/responsibleGlitches/count';
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addGlitch(apartmentID: number, glitch: Glitch): Promise<Glitch> {
    const url = `/api/apartments/${apartmentID}/glitches`;
    return this.http
          .post<Glitch>(url,glitch)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getGlitch(id: number): Promise<Glitch> {
    const url = `${this.glitchesUrl}/${id}`;
    return this.http
          .get(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getComments(glitchID: number): Promise<Comment[]>{
    const url = `${this.glitchesUrl}/${glitchID}/comments`;
    return this.http
          .get<Comment[]>(url)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  addComment(glitchID: number, comment: Comment): Promise<Comment> {
    const url = `${this.glitchesUrl}/${glitchID}/comments`;
    return this.http
          .post<Comment>(url, comment)
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  approveRepair(glitchID: number): Promise<Glitch>{
    const url = `${this.glitchesUrl}/${glitchID}`;
    return this.http.put<Glitch>(url, {headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }
  
  getUsers(glitchID:number): Promise<User[]>{
    const url = `${this.glitchesUrl}/${glitchID}/users`;
    return this.http.get<User[]>(url, {headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }
  updateResponsiblePerson(glitchID:number, user:User): Promise<Glitch>{
    const url = `${this.glitchesUrl}/${glitchID}/responsiblePerson`;
    return this.http.put<Glitch>(url, user, {headers: this.headers})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
