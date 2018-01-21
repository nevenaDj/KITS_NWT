import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Survey } from '../models/survey';

@Injectable()
export class SurveyService {

  constructor(private http: HttpClient) { }

  addSurvey(meetingID: number,survey: Survey): Promise<Survey>{
    const url = `/api/meetings/${meetingID}/surveys`;
    return this.http
        .post<Survey>(url, survey)
        .toPromise()
        .then(res => res)
        .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error("Error... ", error);
    return Promise.reject(error.message || error);
  }



}
