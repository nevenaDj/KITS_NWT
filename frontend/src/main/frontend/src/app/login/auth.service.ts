import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import * as decode from 'jwt-decode';

@Injectable()
export class AuthService {

  private authUrl = 'api/login';

  private admin: boolean;
  private tenant: boolean;
  private president: boolean;
  private owner: boolean;
  private company: boolean;

  constructor(private http: HttpClient,
              private router: Router) { }

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
    return this.admin;
  }

  isTenant(): boolean {
    return this.tenant;
  }

  isOwner(): boolean {
    return this.owner;
  }

  isPresident(): boolean {
    return this.president;
  }

  isCompany(): boolean {
    return this.company;
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

    for(let role of tokenPayload.scopes){
      if(role === 'ROLE_ADMIN'){
        this.admin = true;
      } else if (role === 'ROLE_USER') {
        this.tenant = true;
      } else if (role === 'ROLE_PRESIDENT') {
        this.president = true;
      } else if (role === 'ROLE_OWNER') {
        this.owner = true;
      } else if (role === 'ROLE_COMPANY') {
        this.company = true;
      }
    }
  }

  private handleError(error: any): Promise<any> {
      console.error('An error occurred', error);
      return Promise.reject(error.message || error);
  }
}
