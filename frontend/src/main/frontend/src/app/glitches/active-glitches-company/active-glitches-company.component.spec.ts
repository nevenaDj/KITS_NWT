import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveGlitchesCompanyComponent } from './active-glitches-company.component';

describe('ActiveGlitchesCompanyComponent', () => {
  let component: ActiveGlitchesCompanyComponent;
  let fixture: ComponentFixture<ActiveGlitchesCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActiveGlitchesCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActiveGlitchesCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
