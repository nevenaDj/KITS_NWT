import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentBuildingComponent } from './current-building.component';

describe('CurrentBuildingComponent', () => {
  let component: CurrentBuildingComponent;
  let fixture: ComponentFixture<CurrentBuildingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurrentBuildingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrentBuildingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
