import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { CompanyService } from './company.service';
import { User } from '../models/user';

describe('CompanyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [CompanyService]
    });

    this.companyService = getTestBed().get(CompanyService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  afterEach(() => {
    this.backend.verify();
  });

  it('should be created', inject([CompanyService], (service: CompanyService) => {
    expect(service).toBeTruthy();
  }));

  it('getCompanies() send request', () => {
    this.companyService.getCompanies(0).then((data: User[]) => expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === '/api/companies'
          && params.set('page','0').set('size', '8')
          && req.method === 'GET';
    });
  });

  it('getCompanies() should return some companies', () => {
    let companies: User[] = [
      {
        id: 1,
        username: 'company1',
        password: 'company1',
        email: 'compant1@gmail.com',
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
        username: 'company2',
        password: 'company2',
        email: 'company2@gmail.com',
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

    this.companyService.getCompanies(0).then((data: User[]) => {
      expect(data).toBeTruthy();
      expect(data.length).toBe(2);
      expect(data).toEqual(companies);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === '/api/companies'
          && params.set('page','0').set('size', '8')
          && req.method === 'GET'
      })
      .flush(companies, { status: 200, statusText: 'OK'});

  });

  it('getCompany() should query url and get a company', () => {
    let company: User = new User({
      id: 1,
      username: 'company',
      password: 'company',
      email: 'company@gmail.com',
      phoneNo:'123456',
      address: {
        id: 1,
        street: 'street',
        number: '1a',
        city: 'city',
        zipCode: 15000
      }
    });

    this.companyService.getCompany(company.id).then((data:User) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.username).toEqual('company');
      expect(data.email).toEqual('company@gmail.com');
      expect(data.address.street).toEqual('street');
      expect(data.address.number).toEqual('1a');
      expect(data.address.city).toEqual('city');
      expect(data.address.zipCode).toEqual(15000);
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/users/${company.id}`
          && req.method === 'GET'
    }).flush(company, {status: 200, statusText: 'OK'});
  });

  it('addCompany() should query url and save a company', () => {
    let company: User = new User({
      username: 'company',
      password: 'company',
      email: 'company@gmail.com',
      phoneNo:'123456',
    });

    this.companyService.addCompany(company).then((data: User) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.username).toEqual('company');
      expect(data.email).toEqual('company@gmail.com');
      expect(data.phoneNo).toEqual('123456');

    });

    company.id = 1;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/companies'
          && req.method === 'POST'
    })
      .flush(company, {status: 201, statusText: 'CREATED'});
  });

  it('deleteCompany() should query url and delete a company', () => {
    let companyID = 1;

    this.companyService.deleteCompany(companyID).then((data)=> expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/companies/${companyID}`
          && req.method === 'DELETE'
    }).flush(null, {status: 200, statusText: 'OK'});

  });

  it('getCompaniesCount() should query url and return number of companies', () => {
    let count: number = 2;

    this.companyService.getCompaniesCount().then(data => {
      expect(data).toBeTruthy();
      expect(data).toEqual(2);
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/companies/count'
          && req.method === 'GET'
    })
      .flush(count, {status: 200, statusText: 'OK'});

  });

  it('should call handleError if getCompany produces an error', () => {
    this.companyService.getCompany(1).then((data) => expect(data).toBeFalsy() );

    this.backend.expectOne('/api/users/1').flush(null, {status: 400, statusText:'Bad request'});
  });

  
});
