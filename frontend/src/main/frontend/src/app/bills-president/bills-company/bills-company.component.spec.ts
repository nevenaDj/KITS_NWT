import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BillsCompanyComponent } from './bills-company.component';
import { Bill } from '../../models/bill';
import { ActivatedRouteStub } from '../../testing/router-stubs';
import { CompanyDataService } from '../../home-company/company-data.service';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';


describe('BillsCompanyComponent', () => {
  let component: BillsCompanyComponent;
  let fixture: ComponentFixture<BillsCompanyComponent>;
  let router: any;
  let companyService:any;
  let pagerService: any;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [ BillsCompanyComponent ]
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

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };


    TestBed.configureTestingModule({
      providers: [
        {provide: CompanyDataService, useValue: billServiceMock},
        {provide: router, useValue: routerMock}
       // {provide: ActivatedRoute, useValue: activatedRouteStub}
      ]
    });


    fixture = TestBed.createComponent(BillsCompanyComponent);
    component = fixture.componentInstance;
    companyService = TestBed.get(CompanyDataService);
    //route = TestBed.get(ActivatedRoute);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
