import { async, ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { ApartmentDetailComponent } from './apartment-detail.component';
import { Apartment } from '../../models/apartment';
import { ActivatedRouteStub } from '../../testing/router-stubs';
import { ApartmentService } from '../apartment.service';
import { TenantService } from '../../tenants/tenant.service';


describe('ApartmentDetailComponent', () => {
  let component: ApartmentDetailComponent;
  let fixture: ComponentFixture<ApartmentDetailComponent>;
  let apartmentService: any;
  let tenantService: any;
  let route: any;
  let router: any;

  beforeEach(() => {
    let apartmentServiceMock = {
      getApartment: jasmine.createSpy('getApartment')
        .and.returnValue(Promise.resolve(new Apartment({
          id: 1,
          number: 5,
          description: '',
          owner: null
        }))),
      deleteApartment: jasmine.createSpy('deleteApartment')
        .and.returnValue(Promise.resolve())
    };

    let tenantServiceMock = {
      getTenantsApartment: jasmine.createSpy('getTenantsApartment')
        .and.returnValue(Promise.resolve())
    };

    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1, idBuilding: 1 } ;

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };


    TestBed.configureTestingModule({
      declarations: [ ApartmentDetailComponent ],
      imports: [ FormsModule ],
      providers: [
        {provide: ApartmentService, useValue: apartmentServiceMock},
        {provide: TenantService, useValue: tenantServiceMock},
        {provide: Router, useValue: routerMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub}
      ]
    });

    fixture = TestBed.createComponent(ApartmentDetailComponent);
    component = fixture.componentInstance;
    apartmentService = TestBed.get(ApartmentService);
    tenantService = TestBed.get(TenantService);
    route = TestBed.get(ActivatedRoute);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to add tenant page', () => {
    component.apartment.id = 1;
    component.gotoAddTenant();
    expect(router.navigate).toHaveBeenCalledWith(['/apartments/1/addTenant']);
  });

  it('should navigate to add owner page', () => {
    component.apartment.id = 1;
    component.gotoAddOwner();
    expect(router.navigate).toHaveBeenCalledWith(['/apartments/1/addOwner']);
  });

  it('should navigate to get tenant page', () => {
    component.apartment.id = 1;
    component.gotoGetTenant(1);
    expect(router.navigate).toHaveBeenCalledWith(['/apartments/1/tenants/1']);
  });

  it('should navigate to edit apartment page', () => {
    component.apartment.id = 1;
    component.gotoEditApartment();
    expect(router.navigate).toHaveBeenCalledWith(['editApartment', 1]);
  });

  it('should call deleteApartment', fakeAsync(() => {
    component.apartment.id = 1;
    component.deleteApartment();

    expect(apartmentService.deleteApartment).toHaveBeenCalledWith(1);

    tick();

    expect(router.navigate).toHaveBeenCalledWith(['/buildings/1']);

  }));

});
