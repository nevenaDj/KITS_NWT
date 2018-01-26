import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Location } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

import { AddGlitchTypeComponent } from './add-glitch-type.component';
import { GlitchTypeService } from '../glitch-type.service';


describe('AddGlitchTypeComponent', () => {
  let component: AddGlitchTypeComponent;
  let fixture: ComponentFixture<AddGlitchTypeComponent>;
  let glitchTypeService: any;
  let location: any;

  beforeEach(() => {
    let glitchTypeServiceMock = {
      addGlitchType: jasmine.createSpy('addGlitchType')
        .and.returnValue(Promise.resolve()),
        announceChange: jasmine.createSpy('announceChange') 
    };

    let locationMock = {
      back: jasmine.createSpy('back')
    };

    TestBed.configureTestingModule({
      declarations: [ AddGlitchTypeComponent ],
      imports: [ FormsModule, ReactiveFormsModule ],
       providers:    [ 
        {provide: GlitchTypeService, useValue: glitchTypeServiceMock },
        { provide: Location, useValue: locationMock} ]
    });

    fixture = TestBed.createComponent(AddGlitchTypeComponent);
    component = fixture.componentInstance;
    glitchTypeService = TestBed.get(GlitchTypeService);
    location = TestBed.get(Location);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add glitch type', fakeAsync(() => {
    component.save();
    expect(glitchTypeService.addGlitchType).toHaveBeenCalled();
    tick();
    
    expect(glitchTypeService.announceChange).toHaveBeenCalled();
    
    expect(location.back).toHaveBeenCalled();
  }));

  function newEvent(eventName: string, bubbles = false, cancelable = false) {
    let evt = document.createEvent('CustomEvent');  // MUST be 'CustomEvent'
    evt.initCustomEvent(eventName, bubbles, cancelable, null);
    return evt;
  }

  it('should bind data from filds to glitchType', fakeAsync(() => {
    fixture.detectChanges();
    tick();

    expect(component.glitchType.type).not.toBe('type');

    let typeInput: any = fixture.debugElement.query(By.css('#type')).nativeElement;
    typeInput.value = 'type';
    

    typeInput.dispatchEvent(newEvent('input'));

    expect(component.glitchType.type).toEqual('type');
  }));

});
