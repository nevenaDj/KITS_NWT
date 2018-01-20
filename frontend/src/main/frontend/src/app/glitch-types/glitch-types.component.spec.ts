import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { GlitchTypesComponent } from './glitch-types.component';
import { GlitchTypeService } from './glitch-type.service';


describe('GlitchTypesComponent', () => {
  let component: GlitchTypesComponent;
  let fixture: ComponentFixture<GlitchTypesComponent>;
  let glitchTypeService: any;
  let router: any;

  beforeEach(() => {
    let glitchTypeServiceMock = {
      getGlitchTypes: jasmine.createSpy('getGlitchTypes')
        .and.returnValue(Promise.resolve()),
      deleteGlitchType: jasmine.createSpy('deleteGlitchType')
        .and.returnValue(Promise.resolve()),
      RegenerateData$: {
        subscribe: jasmine.createSpy('subscribe')
      }
    };

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [ GlitchTypesComponent ],
      providers: [
        {provide: GlitchTypeService, useValue: glitchTypeServiceMock},
        {provide: Router, useValue: routerMock}
      ]
    });

    fixture = TestBed.createComponent(GlitchTypesComponent);
    component = fixture.componentInstance;
    glitchTypeService = TestBed.get(GlitchTypeService);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to add glitch type page', () => {
    component.gotoAddGlitchType();
    expect(router.navigate).toHaveBeenCalledWith(['addGlitchType']);
  });

  it('should call getGlitchTypes()', () => {
    component.getGlitchTypes();
    expect(glitchTypeService.getGlitchTypes).toHaveBeenCalled();
  });

  it('should call deleteGlitchType()', () => {
    component.deleteGlitchType(1);
    expect(glitchTypeService.deleteGlitchType).toHaveBeenCalledWith(1);
  });

});
