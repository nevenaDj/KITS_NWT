import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomePresidentComponent } from './home-president.component';

describe('HomePresidentComponent', () => {
  let component: HomePresidentComponent;
  let fixture: ComponentFixture<HomePresidentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomePresidentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomePresidentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
});
