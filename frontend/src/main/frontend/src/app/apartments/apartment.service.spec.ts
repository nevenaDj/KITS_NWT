import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ApartmentService } from './apartment.service';
import { Apartment } from '../models/apartment';
import { User } from '../models/user';

describe('ApartmentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [ApartmentService]
    });

    this.apartmentService = getTestBed().get(ApartmentService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  afterEach(() => {
    this.backend.verify();
  });

  it('should be created', inject([ApartmentService], (service: ApartmentService) => {
    expect(service).toBeTruthy();
  }));

  it('getApartments() send request', () => {
    let buildingID: number = 1;
    this.apartmentService.getApartments(buildingID).then((data:Apartment[]) => expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/buildings/${buildingID}/apartments`
          && req.method === 'GET' 
        });
      }
  );

  it('getApartments() should return some apartments', () => {
    let buildingID: number = 1;
    let apartments: Apartment[] = [
      {
        id: 1,
        number: 1,
        description: 'apartment',
        owner: null
      },
      {
        id: 2,
        number: 2,
        description: 'apartmet',
        owner: null
      }
    ];
    
    this.apartmentService.getApartments(buildingID).then((data:Apartment[]) => {
      expect(data.length).toBe(2);
      expect(data).toEqual(apartments);
    });

    this.backend.expectOne(`/api/buildings/${buildingID}/apartments`)
                .flush(apartments, { status: 200, statusText: 'OK' });

  });

  it('getApartment() should query url and get an apartment', () => {
    let apartment: Apartment = new Apartment({
      id: 1,
      number: 5,
      description: 'description',
      owner: null
    });

    this.apartmentService.getApartment(apartment.id).then((data: Apartment) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.number).toEqual(5);
      expect(data.description).toEqual('description');
      expect(data.owner).toEqual(null);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/apartments/${apartment.id}`
          && req.method === 'GET'
    }).flush(apartment, {status: 200, statusText: 'OK'});

  });

  it('addApartment() should query url and save an apartment', () => {
    let buildingID: number = 1;
    let apartment: Apartment = new Apartment({
      number: 5,
      description: 'description',
      owner: null
    });
    this.apartmentService.addApartment(buildingID,apartment).then((data:Apartment) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.description).toEqual('description');
      expect(data.number).toEqual(5);
     
    });

    apartment.id = 1;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/buildings/${buildingID}/apartments`
          && req.method === 'POST'      
        })
        .flush(apartment, { status: 201, statusText: 'CREATED' });

  });

  it('updateApartment() should query url and update an apartment', () => {
    let apartment: Apartment = new Apartment({
      id: 1,
      number: 5,
      description: 'description',
      owner: null
    });
    
    this.apartmentService.updateApartment(apartment).then((data: Apartment) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.number).toEqual(5);
      expect(data.description).toEqual('description');
      expect(data.owner).toEqual(null);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/apartments'
          && req.method === 'PUT' 
    })
    .flush(apartment, {status: 200, statusText: 'OK'});

  });

  it('deleteApartment() should query url and delete an apartment', () => {
    let apartmentID = 1;

    this.apartmentService.deleteApartment(apartmentID).then((data)=> expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/apartments/${apartmentID}`
          && req.method === 'DELETE'
    }).flush(null, {status: 200, statusText: 'OK'});

  });

  it('addOwner() should query url and save owner', () => {
    let apartmentID = 1;
    let owner: User = new User({
      username: 'owner',
      password: 'owner',
      email: 'owner@gmail.com',
      phoneNo: '123456'
    });

    this.apartmentService.addOwner(apartmentID, owner).then((data: User) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.username).toEqual('owner');
      expect(data.email).toEqual('owner@gmail.com');
      expect(data.phoneNo).toEqual('123456');
    });

    owner.id = 1;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/apartments/${apartmentID}/owner`
          && req.method === 'POST'
    }).flush(owner, {status: 201, statusText: 'CREATED'});

  });

  it('findApartment() should query url and return an apartment', () => {
    let apartment: Apartment = new Apartment({
      id: 1,
      number: 5,
      description: 'description',
      owner: null
    });


    this.apartmentService.findApartment('street', '1a', 'Novi Sad', 1).then((data: Apartment) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.number).toEqual(5);
      expect(data.description).toEqual('description')
      expect(data.owner).toEqual(null);
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === '/api/apartment'
          && params.set('street','street').set('number', '1a').set('city', 'Novi Sad').set('number_apartment', '1')
          && req.method === 'GET';
    }).flush(apartment, {status: 200, statusText: 'OK'});

  });

  it('getMyApartment() should query url and return an apartment', () =>{
    let apartment: Apartment = new Apartment({
      id: 1,
      number: 5,
      description: 'description',
      owner: null
    });


    this.apartmentService.getMyApartment().then((data: Apartment) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.number).toEqual(5);
      expect(data.description).toEqual('description')
      expect(data.owner).toEqual(null);
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/apartments/my'
          && req.method === 'GET';
    }).flush(apartment, {status: 200, statusText: 'OK'});

  });

  it('should call handleError if getApartment produces an error', () => {
    this.apartmentService.getApartment(1).then((data) => expect(data).toBeFalsy() );

    this.backend.expectOne('/api/apartments/1').flush(null, {status: 400, statusText:'Bad request'});
  });



});