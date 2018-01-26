import { async, ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastsManager, ToastOptions } from 'ng2-toastr';

import { LoginComponent } from './login.component';
import { AuthService } from './auth.service';


describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let router: Router;

  let authServiceMock = {
    login: jasmine.createSpy('login')
      .and.returnValue(Promise.resolve()),
    isAdmin: jasmine.createSpy('isAdmin')
      .and.returnValue(Promise.resolve(true))
  };

  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [FormsModule, ReactiveFormsModule],
      providers: [
        {provide: AuthService, useValue: authServiceMock},
        {provide: Router, useValue: routerMock},
        ToastOptions,
        ToastsManager
      ]

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.get(AuthService);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to register page', () => {
    component.gotoRegister();
    expect(router.navigate).toHaveBeenCalledWith(['register']);
  });

  it('should call login()', fakeAsync(() => {
    component.login();
    expect(authService.login).toHaveBeenCalled();
    tick();
    expect(router.navigate).toHaveBeenCalledWith(['/buildings']);
  }));
});
