import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import * as decode from 'jwt-decode';

import { ApartmentService } from '../apartments/apartment.service';

@Injectable()
export class AuthService {
  private authUrl = '/api/login';
  private roles: string[];

  constructor(private http: HttpClient,
              private router: Router,
              private apartmentService: ApartmentService) { }

  login(username:string, password:string): Promise<any>{
    return this.http
    .post(this.authUrl, {username, password}, {responseType: 'text'})
    .toPromise()
    .then(res => { 
      localStorage.setItem('token', res);
      this.setRoles(); 
    })
    .catch(this.handleError);
  }

  getToken(): String {
    const token = localStorage.getItem('token');
    return token ? token : "";
  }

  logout(): void {
    this.apartmentService.setMyApartment(undefined);
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

  getCurrentUser(): string {
    const token = localStorage.getItem('token');
    if (token){
      const tokenPayload = decode(token);
      const username = tokenPayload.sub;
      return username;
    }
    return "";
  }

  isAdmin(): boolean {
    return this.roles.includes('ROLE_ADMIN');
  }

  isTenant(): boolean {
    return this.roles.includes('ROLE_USER');
  }

  isOwner(): boolean {
    return this.roles.includes('ROLE_OWNER');
  }

  isPresident(): boolean {
    return this.roles.includes('ROLE_PRESIDENT');
  }

  isCompany(): boolean {
    return this.roles.includes('ROLE_COMPANY');
  }

  getRoles(): string[] {
    const token = localStorage.getItem('token');
    const tokenPayload = decode(token);
    const roles = tokenPayload.scopes;
    for(let index in roles){
      if (roles[index] === 'ROLE_USER'){
        roles.splice(index,1);
        roles.splice(index, 0, 'ROLE_TENANT');
      }
    }
    return roles;
  }

  private setRoles(): void {
    const token = localStorage.getItem('token');
    const tokenPayload = decode(token);
    this.roles = tokenPayload.scopes;
  }

  private handleError(error: any): Promise<any> {
      console.error('An error occurred', error);
      return Promise.reject(error.message || error);
  }

}

