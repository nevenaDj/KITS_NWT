import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlitchDetailComponent } from './glitch-detail.component';

describe('GlitchDetailComponent', () => {
  let component: GlitchDetailComponent;
  let fixture: ComponentFixture<GlitchDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlitchDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlitchDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
