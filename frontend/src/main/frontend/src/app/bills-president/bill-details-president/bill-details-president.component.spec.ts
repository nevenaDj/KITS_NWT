import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BillDetailsPresidentComponent } from './bill-details-president.component';
import { ActivatedRouteStub } from '../../testing/router-stubs';
import { Bill } from '../../models/bill';
import { ToastsManager, ToastOptions } from 'ng2-toastr';
import { BillsDataService } from '../bills-data.service';
import { Router, ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

describe('BillDetailsPresidentComponent', () => {
  let component: BillDetailsPresidentComponent;
  let fixture: ComponentFixture<BillDetailsPresidentComponent>;
  let router: any;
  let route:any;
  let billService: any;
  
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [ BillDetailsPresidentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {

    let billServiceMock = {
      getBill: jasmine.createSpy('getBill')
        .and.returnValue(Promise.resolve(new Bill({
          id: 1,
          approved:false,
          date: new Date('2017-12-03'),
          items:[],
          price: 1000
        }))),
    };

    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1} ;

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    



    TestBed.configureTestingModule({
      providers: [
        ToastsManager,
        ToastOptions,
        {provide: BillsDataService, useValue: billServiceMock},
        {provide: Router, useValue: routerMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub}
      ]
    });


    fixture = TestBed.createComponent(BillDetailsPresidentComponent);
    component = fixture.componentInstance;
    billService = TestBed.get(BillsDataService);
    route = TestBed.get(ActivatedRoute);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
