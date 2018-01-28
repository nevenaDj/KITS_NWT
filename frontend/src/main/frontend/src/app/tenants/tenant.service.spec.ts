import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TenantService } from './tenant.service';
import { User } from '../models/user';

describe('TenantService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [TenantService]
    });
    this.tenantService = getTestBed().get(TenantService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  afterEach(() => {
    this.backend.verify();
  });

  it('should be created', inject([TenantService], (service: TenantService) => {
    expect(service).toBeTruthy();
  }));

  it('getTenantsApartment() send request', () => {
    this.tenantService.getTenantsApartment(1).then((data: User[]) => expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/aparments/1/tenants'
          && req.method === 'GET';
    });
  });

  it('getTenants() should return some tenants', () => {
    let apartmentID: number = 1;
    let tenants: User[] = [
      {
        id: 1,
        username: 'tenant1',
        password: 'tenant1',
        email: 'tenant1@gmail.com',
        phoneNo:'123456',
        address: {
          id: 1,
          street: 'street',
          number: '1a',
          city: 'city',
          zipCode: 15000
        }
      },
      {
        id: 2,
        username: 'tenant2',
        password: 'tenant2',
        email: 'tenant2@gmail.com',
        phoneNo: '123456789',
        address: {
          id: 1,
          street: 'street',
          number: '2a',
          city: 'city',
          zipCode: 15000
        }
      },
    ];

    this.tenantService.getTenantsApartment(apartmentID).then((data: User[]) => {
      expect(data).toBeTruthy();
      expect(data.length).toBe(2);
      expect(data).toEqual(tenants);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/aparments/${apartmentID}/tenants`
          && req.method === 'GET'
      })
      .flush(tenants, { status: 200, statusText: 'OK'});

  });

  it('getTenant() should query url and get a tenant', () => {
    let tenant: User = new User({
      id: 1,
      username: 'tenant',
      password: 'tenant',
      email: 'tenant@gmail.com',
      phoneNo:'123456',
      address: {
        id: 1,
        street: 'street',
        number: '1a',
        city: 'city',
        zipCode: 15000
      }
    });

    this.tenantService.getTenant(tenant.id).then((data:User) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.username).toEqual('tenant');
      expect(data.email).toEqual('tenant@gmail.com');
      expect(data.address.street).toEqual('street');
      expect(data.address.number).toEqual('1a');
      expect(data.address.city).toEqual('city');
      expect(data.address.zipCode).toEqual(15000);
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/users/${tenant.id}`
          && req.method === 'GET'
    }).flush(tenant, {status: 200, statusText: 'OK'});
  });

  it('addTenant() should query url and save a tenant', () => {
    let apartmentID: number = 1;
    let tenant: User = new User({
      username: 'tenant',
      password: 'tenant',
      email: 'tenant@gmail.com',
      phoneNo:'123456',
    });

    this.tenantService.addTenant(apartmentID,tenant).then((data: User) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.username).toEqual('tenant');
      expect(data.email).toEqual('tenant@gmail.com');
      expect(data.phoneNo).toEqual('123456');

    });

    tenant.id = 1;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/aparments/${apartmentID}/tenants`
          && req.method === 'POST'
    })
      .flush(tenant, {status: 201, statusText: 'CREATED'});
  });

  it('deleteTenant() should query url and delete a tenant', () => {
    let apartmentID: number = 1;
    let tenantID: number = 1;

    this.tenantService.deleteTenant(tenantID, apartmentID).then((data)=> expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/apartment/${apartmentID}/tenants/${tenantID}`
          && req.method === 'DELETE'
    }).flush(null, {status: 200, statusText: 'OK'});

  });

});
