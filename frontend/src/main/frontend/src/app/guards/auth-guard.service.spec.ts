import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { AuthGuardService } from './auth-guard.service';


describe('AuthGuardService', () => {
  beforeEach(() => {
    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      
      providers: [
        AuthGuardService,
        {provide: Router, useValue: routerMock}
      ]
    });
    this.authGuardService = getTestBed().get(AuthGuardService);
    this.router = getTestBed().get(Router);
  });

  it('should be created', inject([AuthGuardService], (service: AuthGuardService) => {
    expect(service).toBeTruthy();
  }));

  it('be able to hit route when user is logged in', () => {
    localStorage.setItem('token', 'token');
    expect(this.authGuardService.canActivate()).toBeTruthy();
  });
  
  it('not be able to hit route when user is not logged in', () => {
    localStorage.removeItem('token');
    expect(this.authGuardService.canActivate()).toBeFalsy();
    expect(this.router.navigate).toHaveBeenCalledWith(['login']);
  });
});
