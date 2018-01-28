import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Survey } from '../models/survey';
import { Answer } from '../models/answer';

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

  getSurvey(id: number): Promise<Survey>{
    const url = `/api/surveys/${id}`;
    return this.http
        .get<Survey>(url)
        .toPromise()
        .then(res => res)
        .catch(this.handleError);
  }

  addAnswer(surveyID: number,answers: Answer[]): Promise<Answer[]>{
    const url = `/api/surveys/${surveyID}/answers`;
    return this.http
        .post<Answer[]>(url, answers)
        .toPromise()
        .then(res => res)
        .catch(this.handleError);
  }

  deleteSurvey(id: number): Promise<{}>{
    const url = `/api/surveys/${id}`;
    return this.http
        .delete(url)
        .toPromise()
        .catch(this.handleError);
  }

  getSurveys(meetingID: number): Promise<Survey[]>{
    const url = `/api/meetings/${meetingID}/surveys`;
    return this.http
        .get(url)
        .toPromise()
        .then(res => res)
        .catch(this.handleError);
  }

  hasAnswer(surveyID: number): Promise<boolean>{
    const url = `/api/surveys/${surveyID}/hasAnswer`;
    return this.http
        .get(url)
        .toPromise()
        .then(res => res)
        .catch(this.handleError);
  }

  getAnswer(surveyID: number): Promise<Survey>{
    const url = `/api/surveys/${surveyID}/answers`;
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
