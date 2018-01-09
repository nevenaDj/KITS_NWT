import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPresidentComponent } from './add-president.component';

describe('AddPresidentComponent', () => {
  let component: AddPresidentComponent;
  let fixture: ComponentFixture<AddPresidentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddPresidentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPresidentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
