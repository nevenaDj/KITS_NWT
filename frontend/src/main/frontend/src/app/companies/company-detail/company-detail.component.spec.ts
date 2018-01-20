import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { CompanyDetailComponent } from './company-detail.component';
import { CompanyService } from '../company.service';
import { User } from '../../models/user';
import { ActivatedRouteStub } from '../../testing/router-stubs';


describe('CompanyDetailComponent', () => {
  let component: CompanyDetailComponent;
  let fixture: ComponentFixture<CompanyDetailComponent>;
  let companyService: any;
  let route: any;
  let location: any;

  beforeEach(() => {
    let companyServiceMock = {
      getCompany: jasmine.createSpy('getCompany')
        .and.returnValue(Promise.resolve(new User({
          id: 1,
          username: 'company',
          password: 'company',
          email: 'company@gmail.com'
        }))),
      deleteCompany: jasmine.createSpy('deleteCompany')
        .and.returnValue(Promise.resolve())
    };

    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 } ;

    let locationMock = {
      back: jasmine.createSpy('back')
    };


    TestBed.configureTestingModule({
      declarations: [ CompanyDetailComponent ],
      imports: [FormsModule],
      providers: [
        {provide: CompanyService, useValue: companyServiceMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        {provide: Location, useValue: locationMock}
      ]
    });

    fixture = TestBed.createComponent(CompanyDetailComponent);
    component = fixture.componentInstance;
    companyService = TestBed.get(CompanyService);
    route = TestBed.get(ActivatedRoute);
    location = TestBed.get(Location);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch company', fakeAsync(() => {
    component.ngOnInit();

    expect(companyService.getCompany).toHaveBeenCalledWith(1);

    tick();

    expect(component.company.id).toBe(1);
    expect(component.company.username).toEqual('company');
    expect(component.company.email).toEqual('company@gmail.com');
  }));

  it('should call deleteCompany', fakeAsync(() => {
    component.company.id = 1;
    component.deleteCompany();

    expect(companyService.deleteCompany).toHaveBeenCalledWith(1);

    tick();

    expect(location.back).toHaveBeenCalled();

  }));

});
