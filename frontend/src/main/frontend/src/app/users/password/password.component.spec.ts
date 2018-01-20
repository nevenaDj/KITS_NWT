import { async, ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { PasswordComponent } from './password.component';
import { UserService } from '../user.service';


describe('PasswordComponent', () => {
  let component: PasswordComponent;
  let fixture: ComponentFixture<PasswordComponent>;
  let userService: any;
  let location: any;

  beforeEach(() => {
    let userServiceMock = {
      changePassword: jasmine.createSpy('changePassword')
        .and.returnValue(Promise.resolve())
    };

    let locationMock = {
      back: jasmine.createSpy('back')
    };

    TestBed.configureTestingModule({
      declarations: [ PasswordComponent ],
      imports: [FormsModule],
      providers: [
        {provide: UserService, useValue: userServiceMock},
        {provide: Location, useValue: locationMock}
      ]
    });

    fixture = TestBed.createComponent(PasswordComponent);
    component = fixture.componentInstance;
    userService = TestBed.get(UserService);
    location = TestBed.get(Location);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should change password', fakeAsync(() => {
    component.save();
    expect(userService.changePassword).toHaveBeenCalled();
    tick();
    expect(location.back).toHaveBeenCalled();
  }));

   // a helper function to tell Angular that an event on the HTML page has happened
   function newEvent(eventName: string, bubbles = false, cancelable = false) {
    let evt = document.createEvent('CustomEvent');  // MUST be 'CustomEvent'
    evt.initCustomEvent(eventName, bubbles, cancelable, null);
    return evt;
  }

  it('should bind data from filds to user', fakeAsync(() => {
    fixture.detectChanges();
    tick();

    expect(component.user.currentPassword).not.toBe('user');
    expect(component.user.newPassword1).not.toBe('pass');
    expect(component.user.newPassword2).not.toBe('pass');

    let currentPassInput: any = fixture.debugElement.query(By.css('#currentPassword')).nativeElement;
    currentPassInput.value = 'user';
    let newPass1Input: any = fixture.debugElement.query(By.css('#newPassword1')).nativeElement;
    newPass1Input.value = 'pass';
    let newPass2Input: any = fixture.debugElement.query(By.css('#newPassword2')).nativeElement;
    newPass2Input.value = 'pass';

    currentPassInput.dispatchEvent(newEvent('input'));
    newPass1Input.dispatchEvent(newEvent('input'));
    newPass2Input.dispatchEvent(newEvent('input'));

    expect(component.user.currentPassword).toBe('user');
    expect(component.user.newPassword1).toBe('pass');
    expect(component.user.newPassword2).toBe('pass');

  }));

});
