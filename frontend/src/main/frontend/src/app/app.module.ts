import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'ngx-dropdown';

import { UserService } from './users/user.service';
import { CompanyService } from './companies/company.service';
import { TenantService } from './tenants/tenant.service';
import { ApartmentService } from './apartments/apartment.service';
import { BuildingService } from './buildings/building.service';
import { AuthService } from './login/auth.service';
import { AuthGuardService } from './guards/auth-guard.service';
import { RoleGuardService } from './guards/role-guard.service';
import { AppRoutingModule } from './/app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { BuildingDetailComponent } from './building-detail/building-detail.component';
import { BuildingsComponent } from './buildings/buildings.component';
import { AddBuildingComponent } from './add-building/add-building.component';
import { ApartmentsComponent } from './apartments/apartments.component';
import { AddApartmentComponent } from './add-apartment/add-apartment.component';
import { AddPresidentComponent } from './add-president/add-president.component';
import { HomeCompanyComponent } from './home-company/home-company.component';
import { MenuComponent } from './menu/menu.component';
import { LoginLayoutComponent } from './login-layout/login-layout.component';
import { TenantsComponent } from './tenants/tenants.component';
import { ApartmentDetailComponent } from './apartment-detail/apartment-detail.component';
import { AddTenantComponent } from './add-tenant/add-tenant.component';
import { TenantDetailComponent } from './tenant-detail/tenant-detail.component';
import { AddOwnerComponent } from './add-owner/add-owner.component';
import { CompaniesComponent } from './companies/companies.component';
import { AddCompanyComponent } from './add-company/add-company.component';
import { CompanyDetailComponent } from './company-detail/company-detail.component';
import { UsersComponent } from './users/users.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HomeAdminComponent,
    BuildingDetailComponent,
    BuildingsComponent,
    AddBuildingComponent,
    ApartmentsComponent,
    AddApartmentComponent,
    AddPresidentComponent,
    HomeCompanyComponent,
    MenuComponent,
    LoginLayoutComponent,
    TenantsComponent,
    ApartmentDetailComponent,
    AddTenantComponent,
    TenantDetailComponent,
    AddOwnerComponent,
    CompaniesComponent,
    AddCompanyComponent,
    CompanyDetailComponent,
    UsersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    DropdownModule
    
  ],
  providers: [
    AuthGuardService,
    RoleGuardService,
    AuthService,
    BuildingService,
    ApartmentService,
    TenantService,
    CompanyService,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
