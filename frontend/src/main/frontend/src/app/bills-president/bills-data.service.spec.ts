import { TestBed, inject, getTestBed } from '@angular/core/testing';

import { BillsDataService } from './bills-data.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';
import { Bill } from '../models/bill';
import { HttpRequest } from '@angular/common/http/src/request';

describe('BillsDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [BillsDataService]
    });

    this.billService = getTestBed().get(BillsDataService);
    this.backend = getTestBed().get(HttpTestingController);
  });

  afterEach(() => {
    this.backend.verify();
  });
  
  it('should be created', inject([BillsDataService], (service: BillsDataService) => {
    expect(service).toBeTruthy();
  }));

 
  it('getBill() should query url and get a bill', () => {
    let bill: Bill = new Bill({
      id: 1,
      approved: false,
      date: new Date('2017-12-03') ,
      items:[] ,
      price: 1000
    });

    this.billService.getBill(bill.id).then((data: Bill) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.approved).toEqual(false);
      expect(data.date).toEqual(new Date('2017-12-03'));
      expect(data.price).toEqual(1000);

    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/bills/${bill.id}`
          && req.method === 'GET'
    }).flush(bill, {status: 200, statusText: 'OK'});

  });

  it('approveBill() should query url and get a true from approve', () => {
    let bill: Bill = new Bill({
      id: 1,
      approved: false,
      date: new Date('2017-12-03') ,
      items:[] ,
      price: 1000
    });

    this.billService.getBill(bill.id).then((data: Bill) => {
      expect(data).toBeTruthy();
      expect(data).toBeDefined();
      expect(data.id).toEqual(1);
      expect(data.approved).toEqual(true);


    });

    this.backend.expectOne((req: HttpRequest<any>) => {
      return req.url === `/api/bills/${bill.id}`
          && req.method === 'PUT'
    }).flush(bill, {status: 200, statusText: 'OK'});

  });
  
});
