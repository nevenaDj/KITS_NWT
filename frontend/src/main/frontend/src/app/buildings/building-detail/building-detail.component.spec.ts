import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ToastOptions, ToastsManager } from 'ng2-toastr';

import { BuildingDetailComponent } from './building-detail.component';
import { Building } from '../../models/building';
import { ActivatedRouteStub } from '../../testing/router-stubs';
import { BuildingService } from '../building.service';
import { ApartmentService } from '../../apartments/apartment.service';


describe('BuildingDetailComponent', () => {
  let component: BuildingDetailComponent;
  let fixture: ComponentFixture<BuildingDetailComponent>;
  let buildingService: any;
  let apartmentService: any;
  let route: any;
  let router: any;

  beforeEach(() => {
    let buildingServiceMock = {
      getBuilding: jasmine.createSpy('getBuilding')
        .and.returnValue(Promise.resolve(new Building({
          id: 1,
          address: {
            id: 1,
            street: 'street',
            number: '1a',
            city: 'city',
            zipCode: 15000
          }
        }))),
      deleteBuilding: jasmine.createSpy('deleteBuilding')
        .and.returnValue(Promise.resolve())
    };
    let apartmentServiceMock = {
      getApartments: jasmine.createSpy('getApartments')
        .and.returnValue(Promise.resolve())
    };

    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1, idBuilding: 1 } ;

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [ BuildingDetailComponent ],
      imports: [ FormsModule ],
      providers: [
        {provide: BuildingService, useValue: buildingServiceMock},
        {provide: ApartmentService, useValue: apartmentServiceMock},
        {provide: Router, useValue: routerMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        ToastsManager,
        ToastOptions
      ]
    });

    fixture = TestBed.createComponent(BuildingDetailComponent);
    component = fixture.componentInstance;
    buildingService = TestBed.get(BuildingService);
    apartmentService = TestBed.get(ApartmentService);
    route = TestBed.get(ActivatedRoute);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to add apartment page', () => {
    component.building.id = 1;
    component.gotoAddApartment();
    expect(router.navigate).toHaveBeenCalledWith(['/buildings/1/addApartment']);
  });

  it('should navigate to add president page', () => {
    component.building.id = 1;
    component.gotoAddPresident();
    expect(router.navigate).toHaveBeenCalledWith(['/buildings/1/addPresident']);
  });

  it('should navigate to get apartment page', () => {
    component.building.id = 1;
    component.gotoGetApartment(1);
    expect(router.navigate).toHaveBeenCalledWith(['/buildings/1/apartments/1']);
  });

  it('should navigate to edit building page', () => {
    component.building.id = 1;
    component.gotoEditBuilding();
    expect(router.navigate).toHaveBeenCalledWith(['editBuilding', 1]);
  });

  it('should call deleteBuilding', fakeAsync(() => {
    component.building.id = 1;
    component.deleteBuilding();

    expect(buildingService.deleteBuilding).toHaveBeenCalledWith(1);

    tick();

    expect(router.navigate).toHaveBeenCalledWith(['buildings']);

  }));



});
