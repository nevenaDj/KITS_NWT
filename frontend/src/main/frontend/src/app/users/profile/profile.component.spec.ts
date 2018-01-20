import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { ProfileComponent } from './profile.component';
import { UserService } from '../user.service';


describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let userService: any;
  let router: any;

  let userServiceMock = {
    getCurrentUser: jasmine.createSpy('getCurrentUser')
      .and.returnValue(Promise.resolve())
  };

  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfileComponent ],
      providers: [
        {provide: UserService, useValue: userServiceMock},
        {provide: Router, useValue: routerMock}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to update profile page', () => {
    component.gotoUpdateUser();
    expect(router.navigate).toHaveBeenCalledWith(['/tenant/update']);
  });

  it('should navigate to change password page', () => {
    component.gotoChangePassword();
    expect(router.navigate).toHaveBeenCalledWith(['/tenant/password']);
  });

  it('should fetch current user', () => {
    component.ngOnInit();
    expect(userService.getCurrentUser).toHaveBeenCalled();
  });
});
