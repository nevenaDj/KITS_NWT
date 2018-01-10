import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

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
import { CompanyDataService } from './home-company/company-data.service';
import { UpdateCompanyComponent } from './update-company/update-company.component';
import { PendingGlitchesCompanyComponent } from './pending-glitches-company/pending-glitches-company.component';
import { ActiveGlitchesCompanyComponent } from './active-glitches-company/active-glitches-company.component';
import { BillsCompanyComponent } from './bills-company/bills-company.component';
import { PricelistCompanyComponent } from './pricelist-company/pricelist-company.component';
import { ChangePasswordCompanyComponent } from './change-password-company/change-password-company.component';
import { BillDetailsCompanyComponent } from './bill-details-company/bill-details-company.component';
import { GlitchDetailsCompanyComponent } from './glitch-details-company/glitch-details-company.component'



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
    UpdateCompanyComponent,
    PendingGlitchesCompanyComponent,
    ActiveGlitchesCompanyComponent,
    BillsCompanyComponent,
    PricelistCompanyComponent,
    ChangePasswordCompanyComponent,
    BillDetailsCompanyComponent,
    GlitchDetailsCompanyComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
    
  ],
  providers: [
    AuthGuardService,
    RoleGuardService,
    AuthService,
    BuildingService,
    ApartmentService,
    CompanyDataService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
