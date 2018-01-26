import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { DropdownModule } from 'ngx-dropdown';

import { HomeAdminComponent } from './home-admin.component';
import { AuthService } from '../login/auth.service';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { ApartmentService } from '../apartments/apartment.service';




describe('HomeAdminComponent', () => {
  let component: HomeAdminComponent;
  let fixture: ComponentFixture<HomeAdminComponent>;
  let apartmentServiceMock = {};

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeAdminComponent ],
      imports: [ 
        RouterTestingModule,
        DropdownModule,
      ],
      providers: [
        AuthService,
        HttpClient,
        HttpHandler,
        {provide: ApartmentService, useValue: apartmentServiceMock}
      ]
      
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
