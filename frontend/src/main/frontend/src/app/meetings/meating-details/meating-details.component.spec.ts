import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeatingDetailsComponent } from './meating-details.component';

describe('MeatingDetailsComponent', () => {
  let component: MeatingDetailsComponent;
  let fixture: ComponentFixture<MeatingDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeatingDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeatingDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
