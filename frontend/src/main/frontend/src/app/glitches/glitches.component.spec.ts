import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Router } from '@angular/router';

import { GlitchesComponent } from './glitches.component';
import { GlitchService } from './glitch.service';
import { PagerService } from '../services/pager.service';


describe('GlitchesComponent', () => {
  let component: GlitchesComponent;
  let fixture: ComponentFixture<GlitchesComponent>;
  let glitchService: any;
  let pagerService: any;
  let router: any;

  beforeEach(() => {
    let glitchServiceMock = {
      getGlitches: jasmine.createSpy('getGlitches')
        .and.returnValue(Promise.resolve()),
      getGlitchesCount: jasmine.createSpy('getGlitchesCount')
        .and.returnValue(Promise.resolve()),
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
      declarations: [ GlitchesComponent ],
      providers: [
        {provide: GlitchService, useValue: glitchServiceMock},
        {provide: PagerService, useValue: pagerServiceMock},
        {provide: Router, useValue: routerMock}

      ]
    });

    fixture = TestBed.createComponent(GlitchesComponent);
    component = fixture.componentInstance;
    glitchService = TestBed.get(GlitchService);
    pagerService = TestBed.get(PagerService);
    router = TestBed.get(Router);
    
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to add glitch page', () => {
    component.gotoAddGlitch();
    expect(router.navigate).toHaveBeenCalledWith(['/tenant/addGlitch']);
  });


  it('should navigate to get glitch page', () => {
    component.gotoGetGlitch(1);
    expect(router.navigate).toHaveBeenCalledWith(['/tenant/glitches', 1]);
  });

  it('should call getGlitches()', () => {
    component.getGlitches(0,8);
    expect(glitchService.getGlitches).toHaveBeenCalledWith(0,8);
  });

  it('should call setPage()', fakeAsync(() => {
    component.setPage(1);
    expect(pagerService.getPager).toHaveBeenCalled();
    tick();
    expect(glitchService.getGlitches).toHaveBeenCalled();
  }));

 
});
