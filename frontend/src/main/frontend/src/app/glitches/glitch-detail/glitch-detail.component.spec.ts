import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { GlitchDetailComponent } from './glitch-detail.component';
import { GlitchService } from '../glitch.service';
import { AuthService } from '../../login/auth.service';
import { ActivatedRouteStub } from '../../testing/router-stubs';
import { Glitch } from '../../models/glitch';


describe('GlitchDetailComponent', () => {
  let component: GlitchDetailComponent;
  let fixture: ComponentFixture<GlitchDetailComponent>;
  let glitchService: any;
  let authService: any;
  let route: any;
  let router: any;

  beforeEach(() => {
    let glitchServiceMock = {
      getGlitch : jasmine.createSpy('getGlitch')
        .and.returnValue(Promise.resolve(new Glitch({
          id: 1,
          description: 'glitch',
          type: {
            id: 1,
            type: 'type'
          },
          apartment: {
              id: 1,
              building: null,
              description: '',
              number: 1,
              owner: null
          }
        }))),
      getComments: jasmine.createSpy('getComments')
        .and.returnValue(Promise.resolve([
          {
            id: 1,
            text: 'comment1',
            user: null
          },
          {
            id: 2,
            text: 'comment2',
            user: null
          }
        ])),
      addComment: jasmine.createSpy('addComment')
        .and.returnValue(Promise.resolve())
    };

    let authServiceMock = {
      getCurrentUser: jasmine.createSpy('getCurrentUser')
        .and.returnValue(Promise.resolve()),
      isPresident: jasmine.createSpy('isPresident')
        .and.returnValue(Promise.resolve())
    };

    let activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { id: 1 } ;

    let routerMock = {};

    TestBed.configureTestingModule({
      declarations: [ GlitchDetailComponent ],
      imports: [FormsModule],
      providers: [
        {provide: GlitchService, useValue: glitchServiceMock},
        {provide: AuthService, useValue: authServiceMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        {provide: Router, useValue: routerMock}
      ]
    });

    fixture = TestBed.createComponent(GlitchDetailComponent);
    component = fixture.componentInstance;
    glitchService = TestBed.get(GlitchService);
    authService = TestBed.get(AuthService);
    route = TestBed.get(ActivatedRoute);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch glitch and comments', fakeAsync(() => {
    component.ngOnInit();

    expect(glitchService.getGlitch).toHaveBeenCalledWith(1);

    tick();

    expect(component.glitch.id).toBe(1);
    expect(component.glitch.description).toEqual('glitch');
    expect(component.glitch.type.id).toEqual(1);
    expect(component.glitch.type.type).toEqual('type');

    expect(glitchService.getComments).toHaveBeenCalledWith(1);
    expect(component.comments[0].id).toBe(1);
    expect(component.comments[1].id).toBe(2);
    expect(component.comments[0].text).toEqual('comment1');
    expect(component.comments[1].text).toEqual('comment2');

    expect(authService.getCurrentUser).toHaveBeenCalled();

  }));

  it('should add comment', fakeAsync(() => {
    component.saveComment();
    expect(glitchService.addComment).toHaveBeenCalled();
    tick();
    
    expect(glitchService.getComments).toHaveBeenCalled();
    
  }));
});
