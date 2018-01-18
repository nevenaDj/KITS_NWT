import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Router } from '@angular/router';

import { CompaniesComponent } from './companies.component';
import { CompanyService } from './company.service';
import { PagerService } from '../services/pager.service';


describe('CompaniesComponent', () => {
  let component: CompaniesComponent;
  let fixture: ComponentFixture<CompaniesComponent>;
  let companyService: any;
  let pagerService: any;
  let router: any;

  beforeEach(() => {
    let companyServiceMock = {
      getCompanies: jasmine.createSpy('getCompanies')
        .and.returnValue(Promise.resolve()),
      getCompaniesCount: jasmine.createSpy('getCompaniesCount')
        .and.returnValue(Promise.resolve(5)),
      RegenerateData$: {
        subscribe: jasmine.createSpy('subscribe')
      }
    };

    let pagerServiceMock = {
      getPager: jasmine.createSpy('getPager')
        .and.returnValue(Promise.resolve())
    };

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [ CompaniesComponent ],
      providers: [
        {provide: CompanyService, useValue: companyServiceMock},
        {provide: PagerService, useValue: pagerServiceMock},
        {provide: Router, useValue: routerMock}
      ]
    });

    fixture = TestBed.createComponent(CompaniesComponent);
    component = fixture.componentInstance;
    companyService = TestBed.get(CompanyService);
    pagerService = TestBed.get(PagerService);
    router = TestBed.get(Router);

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to add company page', () => {
    component.gotoAdd();
    expect(router.navigate).toHaveBeenCalledWith(['addCompany']);
  });

  it('should navigate to get company page', () => {
    component.gotoGetCompany(1);
    expect(router.navigate).toHaveBeenCalledWith(['companies', 1]);
  });

  it('should call getCompanies()', () => {
    component.getCompanies(0,8);
    expect(companyService.getCompanies).toHaveBeenCalledWith(0,8);
  });

  it('should call getData()', fakeAsync(() => {
    component.getData();
    expect(companyService.getCompaniesCount).toHaveBeenCalled();
    tick();
    expect(companyService.getCompanies).toHaveBeenCalled();
  }));

  it('should call getPage()', fakeAsync(() => {
    component.setPage(1);
    expect(pagerService.getPager).toHaveBeenCalled();
    tick();
    expect(companyService.getCompanies).toHaveBeenCalled();
  }));

});
