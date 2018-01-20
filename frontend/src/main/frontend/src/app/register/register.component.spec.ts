import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { RegisterComponent } from './register.component';
import { ApartmentService } from '../apartments/apartment.service';
import { UserService } from '../users/user.service';



describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let apartmentService: ApartmentService;
  let userService: UserService;
  let router: Router;

  let apartmentServiceMock = {
    findApartment: jasmine.createSpy('findApartment')
      .and.returnValue(Promise.resolve())
  };

  let userServiceMock = {
    registration: jasmine.createSpy('registration')
      .and.returnValue(Promise.resolve())
  };

  let routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterComponent ],
      imports: [FormsModule],
      providers: [
        {provide: ApartmentService, useValue: apartmentServiceMock},
        {provide: UserService, useValue: userServiceMock},
        {provide: Router, useValue: routerMock}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    apartmentService = TestBed.get(ApartmentService);
    userService = TestBed.get(UserService);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call findApartment', () => {
    component.findApartment();
    expect(apartmentService.findApartment).toHaveBeenCalled();
  });

  it('should call save()', fakeAsync(() => {
    component.apartment = {
      id: 1,
      description: 'apartment',
      number: 1,
      owner: null
    }
    component.save()
    expect(userService.registration).toHaveBeenCalled();
    tick();
    expect(router.navigate).toHaveBeenCalledWith(['login']);
  }));
});
