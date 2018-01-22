import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { AddPresidentComponent } from './add-president.component';
import { BuildingService } from '../building.service';
import { ActivatedRouteStub } from '../../testing/router-stubs';
import { UserService } from '../../users/user.service';

describe('AddPresidentComponent', () => {
  let component: AddPresidentComponent;
  let fixture: ComponentFixture<AddPresidentComponent>;
  let buildingService: any;
  let userService: any;
  let route: any;
  let router: any;

  beforeEach(() => {
    let buildingServiceMock = {
      addPresident: jasmine.createSpy('addPresident')
        .and.returnValue(Promise.resolve())
    };

    let userServiceMock = {
      findUser: jasmine.createSpy('findUser')
        .and.returnValue(Promise.resolve())
    };

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    let activateRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activateRouteStub.testParams = {id: 1};

    TestBed.configureTestingModule({
      declarations: [ AddPresidentComponent ],
      imports: [FormsModule],
      providers: [
        {provide: BuildingService, useValue: buildingServiceMock},
        {provide: UserService, useValue: userServiceMock},
        {provide: ActivatedRoute, useValue: activateRouteStub},
        {provide: Router, useValue: routerMock}
      ]
    });

    fixture = TestBed.createComponent(AddPresidentComponent);
    component = fixture.componentInstance;
    buildingService = TestBed.get(BuildingService);
    userService = TestBed.get(UserService);
    route = TestBed.get(ActivatedRoute);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add president', fakeAsync(() => {
    component.save();
    expect(buildingService.addPresident).toHaveBeenCalled();
    tick();
    expect(router.navigate).toHaveBeenCalled();
  }));

  it('should find user', () => {
    component.find();
    expect(userService.findUser).toHaveBeenCalled();
  });

  // a helper function to tell Angular that an event on the HTML page has happened
  function newEvent(eventName: string, bubbles = false, cancelable = false) {
    let evt = document.createEvent('CustomEvent');  // MUST be 'CustomEvent'
    evt.initCustomEvent(eventName, bubbles, cancelable, null);
    return evt;
  }

  it('should bind data from filds to president', fakeAsync(() => {
    fixture.detectChanges();
    tick();

    expect(component.president.username).not.toBe('president');
    expect(component.president.email).not.toBe('president@gmail.com');
    expect(component.president.phoneNo).not.toBe('123456');

    let usernameInput: any = fixture.debugElement.query(By.css('#username')).nativeElement;
    usernameInput.value = 'president';
    let emailInput: any = fixture.debugElement.query(By.css('#email')).nativeElement;
    emailInput.value = 'president@gmail.com';
    let phoneNoInput: any = fixture.debugElement.query(By.css('#phoneNo')).nativeElement;
    phoneNoInput.value = '123456';

    usernameInput.dispatchEvent(newEvent('input'));
    emailInput.dispatchEvent(newEvent('input'));
    phoneNoInput.dispatchEvent(newEvent('input'));

    expect(component.president.username).toEqual('president');
    expect(component.president.email).toEqual('president@gmail.com');
    expect(component.president.phoneNo).toEqual('123456');

  }));

});
