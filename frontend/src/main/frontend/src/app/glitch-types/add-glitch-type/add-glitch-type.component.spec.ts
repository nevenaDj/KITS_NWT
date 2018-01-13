import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGlitchTypeComponent } from './add-glitch-type.component';

describe('AddGlitchTypeComponent', () => {
  let component: AddGlitchTypeComponent;
  let fixture: ComponentFixture<AddGlitchTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddGlitchTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddGlitchTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
