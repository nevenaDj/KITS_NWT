import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot } from '@angular/router';
import * as decode from 'jwt-decode';

@Injectable()
export class RoleGuardService implements CanActivate {

  constructor(private router:Router) { }

  canActivate(route: ActivatedRouteSnapshot):boolean{
    const expectedRole = route.data.expectedRole;
    const token = localStorage.getItem('token');
    const tokenPayload = decode(token);

    for(let role of tokenPayload.scopes){
      if(role === expectedRole){
        return true;
      }
    }

    this.router.navigate(['login']);
    return false;
  }
}
