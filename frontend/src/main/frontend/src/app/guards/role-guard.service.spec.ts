import { TestBed, inject } from '@angular/core/testing';
import { Router } from '@angular/router';

import { RoleGuardService } from './role-guard.service';


describe('RoleGuardService', () => {
  beforeEach(() => {
    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      providers: [
        RoleGuardService,
        {provide: Router, useValue: routerMock}
      ]
    });
  });

  it('should be created', inject([RoleGuardService], (service: RoleGuardService) => {
    expect(service).toBeTruthy();
  }));
});
