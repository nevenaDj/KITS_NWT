import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGlitchComponent } from './add-glitch.component';

describe('AddGlitchComponent', () => {
  let component: AddGlitchComponent;
  let fixture: ComponentFixture<AddGlitchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddGlitchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddGlitchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
