import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { AddTenantComponent } from './add-tenant.component';
import { TenantService } from '../tenant.service';
import { ActivatedRouteStub } from '../../testing/router-stubs';

describe('AddTenantComponent', () => {
  let component: AddTenantComponent;
  let fixture: ComponentFixture<AddTenantComponent>;
  let tenantService: any;
  let location: any;
  let route: any;


  beforeEach(() => {
    let tenantServiceMock = { 
      addTenant: jasmine.createSpy('addTenant')
        .and.returnValue(Promise.resolve())
    };

    let locationMock = {
      back: jasmine.createSpy('back')
    };

    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1, idBuilding: 1 } ;

    TestBed.configureTestingModule({
      declarations: [ AddTenantComponent ],
      imports: [FormsModule],
      providers: [
        {provide: TenantService, useValue: tenantServiceMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        {provide: Location, useValue: locationMock}
      ]
    });

    
    fixture = TestBed.createComponent(AddTenantComponent);
    component = fixture.componentInstance;
    tenantService = TestBed.get(TenantService);
    route = TestBed.get(ActivatedRoute);
    location = TestBed.get(Location);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add tenant', fakeAsync(() => {
    component.save();
    expect(tenantService.addTenant).toHaveBeenCalled();
    tick();
    expect(location.back).toHaveBeenCalled();

  }));

  // a helper function to tell Angular that an event on the HTML page has happened
  function newEvent(eventName: string, bubbles = false, cancelable = false) {
    let evt = document.createEvent('CustomEvent');  // MUST be 'CustomEvent'
    evt.initCustomEvent(eventName, bubbles, cancelable, null);
    return evt;
  }

  it('should bind data from filds to tenant', fakeAsync(() => {
    fixture.detectChanges();
    tick();

    expect(component.tenant.username).not.toBe('tenant');
    expect(component.tenant.email).not.toBe('tenant@gmail.com');
    expect(component.tenant.phoneNo).not.toBe('123456');

    let usernameInput: any = fixture.debugElement.query(By.css('#username')).nativeElement;
    usernameInput.value = 'tenant';
    let emailInput: any = fixture.debugElement.query(By.css('#email')).nativeElement;
    emailInput.value = 'tenant@gmail.com';
    let phoneNoInput: any = fixture.debugElement.query(By.css('#phoneNo')).nativeElement;
    phoneNoInput.value = '123456';
   

    usernameInput.dispatchEvent(newEvent('input'));
    emailInput.dispatchEvent(newEvent('input'));
    phoneNoInput.dispatchEvent(newEvent('input'));
    

    expect(component.tenant.username).toEqual('tenant');
    expect(component.tenant.email).toEqual('tenant@gmail.com');
    expect(component.tenant.phoneNo).toEqual('123456');
   

  }));
});
