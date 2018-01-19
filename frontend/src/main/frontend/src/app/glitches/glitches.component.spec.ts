import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlitchesComponent } from './glitches.component';

describe('GlitchesComponent', () => {
  let component: GlitchesComponent;
  let fixture: ComponentFixture<GlitchesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlitchesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlitchesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
