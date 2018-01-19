import { async, ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { Router } from '@angular/router';

import { BuildingsComponent } from './buildings.component';
import { BuildingService } from './building.service';
import { PagerService } from '../services/pager.service';


describe('BuildingsComponent', () => {
  let component: BuildingsComponent;
  let fixture: ComponentFixture<BuildingsComponent>;
  let buildingService: any;
  let pagerService: any;
  let router: any;

  beforeEach(() => {
    let buildingServiceMock = {
      getBuildings: jasmine.createSpy('getBuildings')
        .and.returnValue(Promise.resolve()),
      getBuildingsCount: jasmine.createSpy('getBuildingsCount')
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
      declarations: [ BuildingsComponent ],
      providers: [
        {provide: BuildingService, useValue: buildingServiceMock},
        {provide: PagerService, useValue: pagerServiceMock},
        {provide: Router, useValue: routerMock}

      ]
    });

    fixture = TestBed.createComponent(BuildingsComponent);
    component = fixture.componentInstance;
    buildingService = TestBed.get(BuildingService);
    pagerService = TestBed.get(PagerService);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to add building page', () => {
    component.gotoAdd();
    expect(router.navigate).toHaveBeenCalledWith(['addBuilding']);
  });

  it('should navigate to get building page', () => {
    component.gotoGetBuilding(1);
    expect(router.navigate).toHaveBeenCalledWith(['buildings', 1]);
  });

  it('should call getBuildings()', () => {
    component.getBuildings(0,8);
    expect(buildingService.getBuildings).toHaveBeenCalledWith(0,8);
  });

  it('should call getData()', fakeAsync(() => {
    component.getData();
    expect(buildingService.getBuildingsCount).toHaveBeenCalled();
    tick();
    expect(buildingService.getBuildings).toHaveBeenCalled();
  }));

  it('should call getPage()', fakeAsync(() => {
    component.setPage(1);
    expect(pagerService.getPager).toHaveBeenCalled();
    tick();
    expect(buildingService.getBuildings).toHaveBeenCalled();
  }));

});
