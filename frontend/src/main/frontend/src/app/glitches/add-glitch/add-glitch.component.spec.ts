import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { AddGlitchComponent } from './add-glitch.component';
import { GlitchTypeService } from '../../glitch-types/glitch-type.service';
import { GlitchService } from '../glitch.service';
import { ApartmentService } from '../../apartments/apartment.service';
import { Glitch } from '../../models/glitch';
import { Apartment } from '../../models/apartment';


describe('AddGlitchComponent', () => {
  let component: AddGlitchComponent;
  let fixture: ComponentFixture<AddGlitchComponent>;
  let glitchService: any;
  let glitchTypeService: any;
  let apartmentService: any;
  let router: any;

  beforeEach(() => {
    let glitchServiceMock = {
      addGlitch: jasmine.createSpy('addGlitch')
        .and.returnValue(Promise.resolve(new Glitch({
          id: 1,
          description: 'glitch',
          type: null
        })))
    };
    let glitchTypeServiceMock = {
      getGlitchTypes: jasmine.createSpy('getGlitchTypes')
        .and.returnValue(Promise.resolve([
          {
            id: 1,
            type: 'type1'
          },
          {
            id: 2,
            type: 'type2'
          }
        ]))
    };
    let apartmentServiceMock = {
      getMyApartment: jasmine.createSpy('getMyApartment')
        .and.returnValue(Promise.resolve(new Apartment({
          id: 1,
          description: 'apartment',
          number: 1,
          owner: null
        })))
    };
    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      declarations: [ AddGlitchComponent ],
      imports: [ FormsModule, ReactiveFormsModule ],
      providers:    [ 
        {provide: GlitchService, useValue: glitchServiceMock},
        {provide: GlitchTypeService, useValue: glitchTypeServiceMock },
        {provide: ApartmentService, useValue: apartmentServiceMock },
        {provide: Router, useValue: routerMock} ]
    })
    fixture = TestBed.createComponent(AddGlitchComponent);
    component = fixture.componentInstance;
    glitchService = TestBed.get(GlitchService);
    glitchTypeService = TestBed.get(GlitchTypeService);
    apartmentService = TestBed.get(ApartmentService);
    router = TestBed.get(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add glitch', fakeAsync(() => {
    component.save();
    expect(glitchService.addGlitch).toHaveBeenCalled();
    tick();
    
    expect(router.navigate).toHaveBeenCalled();
  }));

  it('should call getMyApartment()', () => {
    component.getMyApartment();
    expect(apartmentService.getMyApartment).toHaveBeenCalled();
  });

  it('should fetch glitch types', fakeAsync(() => {
    component.ngOnInit();

    expect(glitchTypeService.getGlitchTypes).toHaveBeenCalled();

    tick();

    expect(component.glitchTypes[0].id).toBe(1);
    expect(component.glitchTypes[1].id).toBe(2);
    expect(component.glitchTypes[0].type).toEqual('type1');
    expect(component.glitchTypes[1].type).toEqual('type2');

    expect(apartmentService.getMyApartment).toHaveBeenCalled();


  }));


  
});
