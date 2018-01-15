import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs/Subject';
import { Glitch } from '../models/glitch';
import { Comment } from '../models/comment';


@Injectable()
export class GlitchService {
  private glitchesUrl = '/api/glitches';

  private RegenerateData = new Subject<void>();

  RegenerateData$ = this.RegenerateData.asObservable();

  constructor(private http: HttpClient) { }

  announceChange(){
    this.RegenerateData.next();
  }

  getGlitches(page: number, size: number = 15): Promise<Glitch[]>{
    const httpParams = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http
          .get<Glitch[]>(this.glitchesUrl, {params: httpParams})
          .toPromise()
          .then(res => res)
          .catch(this.handleError);
  }

  getGlitchesCount(): Promise<number>{
    const url =  `${this.glitchesUrl}/count`;
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

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }

}
