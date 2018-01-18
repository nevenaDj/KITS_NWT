import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { HttpClientModule, HttpRequest, HttpParams } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { BuildingService } from './building.service';
import { Building } from '../models/building';
import { Apartment } from '../models/apartment';
import { Address } from '../models/address';
import { User } from '../models/user';



describe('BuildingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [BuildingService]
    });

    this.buildingService = getTestBed().get(BuildingService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  afterEach(() => {
    this.backend.verify();
  });

  it('should be created', inject([BuildingService], (service: BuildingService) => {
    expect(service).toBeTruthy();
  }));

  it('getBuildings() send request', () => {
    this.buildingService.getBuildings(0).then((data: Building[]) => expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === '/api/buildings'
          && params.set('page','0').set('size', '8')
          && req.method === 'GET';
    });
  });

  it('getBuildings() should return some buildings', () => {
    let buildings: Building[] = [
      {
        id: 1,
        address: {
          id: 1,
          street: 'street',
          number: '1a',
          city: 'city',
          zipCode: 15000
        },
        president: null
      },
      {
        id: 2,
        address: {
          id: 1,
          street: 'street',
          number: '2a',
          city: 'city',
          zipCode: 15000
        },
        president: null
      },
    ];

    this.buildingService.getBuildings(0).then((data: Building[]) => {
      expect(data).toBeTruthy();
      expect(data.length).toBe(2);
      expect(data).toEqual(buildings);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      const params = new HttpParams({fromString: req.urlWithParams});
      return req.url === '/api/buildings'
          && params.set('page','0').set('size', '8')
          && req.method === 'GET'
      })
      .flush(buildings, { status: 200, statusText: 'OK'});

  });

  it('getBuilding() should query url and get a building', () => {
    let building: Building = new Building({
      id: 1,
      address: {
        id: 1,
        street: 'street',
        number: '1a',
        city: 'city',
        zipCode: 15000
      }
    });

    this.buildingService.getBuilding(building.id).then((data:Building) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.address.street).toEqual('street');
      expect(data.address.number).toEqual('1a');
      expect(data.address.city).toEqual('city');
      expect(data.address.zipCode).toEqual(15000);
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/buildings/${building.id}`
          && req.method === 'GET'
    }).flush(building, {status: 200, statusText: 'OK'});
  });

  it('addBuilding() should query url and save a building', () => {
    let building: Building = new Building({
      address:  new Address({
        street: 'street',
        number: '1a',
        city: 'city',
        zipCode: 15000
      })
    });

    this.buildingService.addBuilding(building).then((data: Building) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.address.street).toEqual('street');
      expect(data.address.number).toEqual('1a');
      expect(data.address.city).toEqual('city');
      expect(data.address.zipCode).toEqual(15000);

    });

    building.id = 1;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/buildings'
          && req.method === 'POST'
    })
      .flush(building, {status: 201, statusText: 'CREATED'});
  });

  it('updateBuilding() should query url and update a building', () => {
    let building: Building = new Building({
      id: 1,
      address:  new Address({
        id: 1,
        street: 'street',
        number: '1a',
        city: 'city',
        zipCode: 15000
      })
    });

    this.buildingService.updateBuilding(building).then((data: Building) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.address.street).toEqual('street');
      expect(data.address.number).toEqual('1a');
      expect(data.address.city).toEqual('city');
      expect(data.address.zipCode).toEqual(15000);

    });


    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/buildings'
          && req.method === 'PUT'
    })
      .flush(building, {status: 200, statusText: 'OK'});
  });

  it('deleteBuilding() should query url and delete a building', () => {
    let buildingID = 1;

    this.buildingService.deleteBuilding(buildingID).then((data)=> expect(data).toBeTruthy());

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/buildings/${buildingID}`
          && req.method === 'DELETE'
    }).flush(null, {status: 200, statusText: 'OK'});

  });

  it('addPresident() should query url and save president', () => {
    let buildingID = 1;
    let president: User = new User({
      username: 'president',
      password: 'president',
      email: 'president@gmail.com',
      phoneNo: '123456'
    });

    this.buildingService.addPresident(buildingID, president).then((data: User) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.username).toEqual('president');
      expect(data.email).toEqual('president@gmail.com');
      expect(data.phoneNo).toEqual('123456');
    });

    president.id = 1;

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/buildings/${buildingID}/president`
          && req.method === 'POST'
    }).flush(president, {status: 201, statusText: 'CREATED'});

  });

  it('getBuildingsCount() should query url and return number of buildings', () => {
    let count: number = 2;

    this.buildingService.getBuildingsCount().then(data => {
      expect(data).toBeTruthy();
      expect(data).toEqual(2);
    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === '/api/buildings/count'
          && req.method === 'GET'
    })
      .flush(count, {status: 200, statusText: 'OK'});

  });


});
