import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';

import { AuthService } from './auth.service';
import { ApartmentService } from '../apartments/apartment.service';


describe('AuthService', () => {
  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  let apartmentServiceMock = {};

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [
        AuthService,
        {provide: Router, useValue: routerMock},
        {provide: ApartmentService, useValue: apartmentServiceMock}

      ]
    });

    this.authService = getTestBed().get(AuthService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  it('should be created', inject([AuthService], (service: AuthService) => {
    expect(service).toBeTruthy();
  }));

  it('login() should query url and get jwt token', () => {
    let username: string = 'user';
    let password: string = 'user';

    this.authService.login(username, password).then((data) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
     
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/login'
          && req.method === 'POST'      
        })
        .flush('token', { status: 200, statusText: 'OK' });

  });
});
