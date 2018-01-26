import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrentBuildingComponent } from './current-building.component';
import { BuildingService } from '../building.service';

describe('CurrentBuildingComponent', () => {
  let component: CurrentBuildingComponent;
  let fixture: ComponentFixture<CurrentBuildingComponent>;
  let buidlingService: BuildingService;

  let buildingServiceMock = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurrentBuildingComponent ],
      providers: [
        {provide: BuildingService, useValue: buildingServiceMock}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrentBuildingComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
