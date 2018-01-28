import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { AdminProfileComponent } from './admin-profile.component';
import { UserService } from '../../user.service';


describe('AdminProfileComponent', () => {
  let component: AdminProfileComponent;
  let fixture: ComponentFixture<AdminProfileComponent>;
  let userService: any;
  let router: any;

  beforeEach(() => {
    let userServiceMock = {
      getCurrentUser: jasmine.createSpy('getCurrentUser')
        .and.returnValue(Promise.resolve())
    };

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [ AdminProfileComponent ],
      providers: [
        {provide: UserService, useValue: userServiceMock},
        {provide: Router, useValue: routerMock}
      ]
    });

    fixture = TestBed.createComponent(AdminProfileComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to change password page', () => {
    component.gotoChangePassword();
    expect(router.navigate).toHaveBeenCalledWith(['password']);
  });

  it('should fetch current user', () => {
    component.ngOnInit();
    expect(userService.getCurrentUser).toHaveBeenCalled();
  });

});
