import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingGlitchesCompanyComponent } from './pending-glitches-company.component';

describe('PendingGlitchesCompanyComponent', () => {
  let component: PendingGlitchesCompanyComponent;
  let fixture: ComponentFixture<PendingGlitchesCompanyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PendingGlitchesCompanyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingGlitchesCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
