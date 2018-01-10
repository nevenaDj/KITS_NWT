import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlitchDetailsCompanyComponent } from './glitch-details-company.component';

describe('GlitchDetailsCompanyComponent', () => {
  let component: GlitchDetailsCompanyComponent;
  let fixture: ComponentFixture<GlitchDetailsCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlitchDetailsCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlitchDetailsCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
