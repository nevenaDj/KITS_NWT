import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Location } from '@angular/common';

import { ProfileUpdateComponent } from './profile-update.component';
import { UserService } from '../../user.service';


describe('ProfileUpdateComponent', () => {
  let component: ProfileUpdateComponent;
  let fixture: ComponentFixture<ProfileUpdateComponent>;
  let userService: any;
  let router: any;
  let location: any;

  let userServiceMock = {
    getCurrentUser: jasmine.createSpy('getCurrentUser')
      .and.returnValue(Promise.resolve()),
    updateUser: jasmine.createSpy('updateUser')
      .and.returnValue(Promise.resolve())
  };

  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  let locationMock = {
    back: jasmine.createSpy('back');
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfileUpdateComponent ],
      imports: [FormsModule],
      providers: [
        {provide: UserService, useValue: userServiceMock},
        {provide: Router, useValue: routerMock},
        {provide: Location, useValue: locationMock}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileUpdateComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    router = TestBed.get(Router);
    
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch current user', () => {
    component.ngOnInit();
    expect(userService.getCurrentUser).toHaveBeenCalled();
  });


  it('should save update user', fakeAsync(() => {
    component.save();
    expect(userService.updateUser).toHaveBeenCalled();
    tick();

    expect(router.navigate).toHaveBeenCalledWith(['/tenant/profile']);
  }));


});
