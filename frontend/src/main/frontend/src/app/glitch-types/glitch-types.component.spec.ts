import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlitchTypesComponent } from './glitch-types.component';

describe('GlitchTypesComponent', () => {
  let component: GlitchTypesComponent;
  let fixture: ComponentFixture<GlitchTypesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlitchTypesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlitchTypesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
