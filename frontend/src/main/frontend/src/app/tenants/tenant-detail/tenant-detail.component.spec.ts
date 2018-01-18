import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { TenantDetailComponent } from './tenant-detail.component';
import { TenantService } from '../tenant.service';
import { ActivatedRouteStub } from '../../testing/router-stubs';
import { User } from '../../models/user';


describe('TenantDetailComponent', () => {
  let component: TenantDetailComponent;
  let fixture: ComponentFixture<TenantDetailComponent>;
  let tenantService: any;
  let route: any;
  let location: any;

  beforeEach(() => {
    let tenantServiceMock = {
      getTenant: jasmine.createSpy('getTenant')
        .and.returnValue(Promise.resolve(new User({
          id: 1,
          username: 'tenant',
          password: 'tenant',
          email: 'tenant@gmail.com'
        }))),
      deleteTenant: jasmine.createSpy('deleteTenant')
        .and.returnValue(Promise.resolve())
    };

    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1, idApartment: 2 } ;

    let locationMock = {
      back: jasmine.createSpy('back')
    };

    TestBed.configureTestingModule({
      declarations: [ TenantDetailComponent ],
      providers: [
        {provide: TenantService, useValue: tenantServiceMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        {provide: Location, useValue: locationMock}
      ]
    });

    fixture = TestBed.createComponent(TenantDetailComponent);
    component = fixture.componentInstance;
    tenantService = TestBed.get(TenantService);
    route = TestBed.get(ActivatedRoute);
    location = TestBed.get(Location);

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch tenant', fakeAsync(() => {
    component.ngOnInit();

    expect(tenantService.getTenant).toHaveBeenCalledWith(1);

    tick();

    expect(component.tenant.id).toBe(1);
    expect(component.tenant.username).toEqual('tenant');
    expect(component.tenant.email).toEqual('tenant@gmail.com');
  }));

  it('should call deleteTenant', fakeAsync(() => {
    component.tenant.id = 1;
    component.deleteTenant();

    expect(tenantService.deleteTenant).toHaveBeenCalledWith(1,2);

    tick();

    expect(location.back).toHaveBeenCalled();

  }));
});
