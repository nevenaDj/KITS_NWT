import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { UsersComponent } from './users.component';
import { PagerService } from '../services/pager.service';
import { UserService } from './user.service';

describe('UsersComponent', () => {
  let component: UsersComponent;
  let fixture: ComponentFixture<UsersComponent>;
  let userService: any;
  let pagerService: any;

  let userServiceMock = {
    getUsers: jasmine.createSpy('getUsers')
      .and.returnValue(Promise.resolve()),
    getUsersCount: jasmine.createSpy('getUsersCount')
      .and.returnValue(Promise.resolve(5))
  };

  let pagerServiceMock = {
    getPager: jasmine.createSpy('getPager')
      .and.returnValue(Promise.resolve())
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsersComponent ],
      providers: [
        {provide: UserService, useValue: userServiceMock},
        {provide: PagerService, useValue: pagerServiceMock}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {  
    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    pagerService = TestBed.get(PagerService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getUsers()', () => {
    component.getUsers(0,8);
    expect(userService.getUsers).toHaveBeenCalledWith(0,8);
  });

  it('should call ngOnInit()', fakeAsync(() => {
    component.ngOnInit();
    expect(userService.getUsersCount).toHaveBeenCalled();
    tick();
    expect(userService.getUsers).toHaveBeenCalled();
  }));

  it('should call setPage()', fakeAsync(() => {
    component.setPage(1);
    expect(pagerService.getPager).toHaveBeenCalled();
    tick();
    expect(userService.getUsers).toHaveBeenCalled();
  }));
});
