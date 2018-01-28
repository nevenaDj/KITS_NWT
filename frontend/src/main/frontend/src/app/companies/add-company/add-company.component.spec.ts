import { async, ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { AddCompanyComponent } from './add-company.component';
import { CompanyService } from '../company.service';


describe('AddCompanyComponent', () => {
  let component: AddCompanyComponent;
  let fixture: ComponentFixture<AddCompanyComponent>;
  let companyService: any;
  let router: any;

  beforeEach(() => {
    let companyServiceMock = {
      addCompany: jasmine.createSpy('addCompany')
        .and.returnValue(Promise.resolve())
    };

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [ AddCompanyComponent ],
      imports: [FormsModule, ReactiveFormsModule],
      providers: [
        {provide: CompanyService, useValue: companyServiceMock},
        {provide: Router, useValue: routerMock}
      ]
    }); 

    fixture = TestBed.createComponent(AddCompanyComponent);
    component = fixture.componentInstance;
    companyService = TestBed.get(CompanyService);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add company', fakeAsync(() => {
    component.save();
    expect(companyService.addCompany).toHaveBeenCalled();
    tick();
    expect(router.navigate).toHaveBeenCalled();

  }));

  // a helper function to tell Angular that an event on the HTML page has happened
  function newEvent(eventName: string, bubbles = false, cancelable = false) {
    let evt = document.createEvent('CustomEvent');  // MUST be 'CustomEvent'
    evt.initCustomEvent(eventName, bubbles, cancelable, null);
    return evt;
  }

  it('should bind data from filds to company', fakeAsync(() => {
    fixture.detectChanges();
    tick();

    expect(component.company.username).not.toBe('company');
    expect(component.company.email).not.toBe('company@gmail.com');
    expect(component.company.phoneNo).not.toBe('123456');
    expect(component.company.address.street).not.toBe('street');
    expect(component.company.address.number).not.toBe('1');
    expect(component.company.address.city).not.toBe('city');
    expect(component.company.address.zipCode).not.toBe(123456);

    let usernameInput: any = fixture.debugElement.query(By.css('#username')).nativeElement;
    usernameInput.value = 'company';
    let emailInput: any = fixture.debugElement.query(By.css('#email')).nativeElement;
    emailInput.value = 'company@gmail.com';
    let phoneNoInput: any = fixture.debugElement.query(By.css('#phoneNo')).nativeElement;
    phoneNoInput.value = '123456';
    let streetInput: any = fixture.debugElement.query(By.css('#street')).nativeElement;
    streetInput.value = 'street';
    let numberInput: any = fixture.debugElement.query(By.css('#number')).nativeElement;
    numberInput.value = '1';
    let cityInput: any = fixture.debugElement.query(By.css('#city')).nativeElement;
    cityInput.value = 'city';
    let zipCodeInput: any = fixture.debugElement.query(By.css('#zipCode')).nativeElement;
    zipCodeInput.value = '123456';

    usernameInput.dispatchEvent(newEvent('input'));
    emailInput.dispatchEvent(newEvent('input'));
    phoneNoInput.dispatchEvent(newEvent('input'));
    streetInput.dispatchEvent(newEvent('input'));
    numberInput.dispatchEvent(newEvent('input'));
    cityInput.dispatchEvent(newEvent('input'));
    zipCodeInput.dispatchEvent(newEvent('input'));

    expect(component.company.username).toEqual('company');
    expect(component.company.email).toEqual('company@gmail.com');
    expect(component.company.phoneNo).toEqual('123456');
    expect(component.company.address.street).toBe('street');
    expect(component.company.address.number).toBe('1');
    expect(component.company.address.city).toBe('city');
    expect(component.company.address.zipCode).toBe(123456);

  }));

});
