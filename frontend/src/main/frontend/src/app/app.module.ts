import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { DropdownModule } from 'ngx-dropdown';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';

import { SurveyService } from './surveys/survey.service';
import { GlitchService } from './glitches/glitch.service';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { PagerService } from './services/pager.service';
import { GlitchTypeService } from './glitch-types/glitch-type.service';
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
import { BuildingDetailComponent } from './buildings/building-detail/building-detail.component';
import { BuildingsComponent } from './buildings/buildings.component';
import { AddBuildingComponent } from './buildings/add-building/add-building.component';
import { AddApartmentComponent } from './apartments/add-apartment/add-apartment.component';
import { AddPresidentComponent } from './buildings/add-president/add-president.component';
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
import { LoginLayoutComponent } from './login/login-layout/login-layout.component';
import { ApartmentDetailComponent } from './apartments/apartment-detail/apartment-detail.component';
import { AddTenantComponent } from './tenants/add-tenant/add-tenant.component';
import { TenantDetailComponent } from './tenants/tenant-detail/tenant-detail.component';
import { AddOwnerComponent } from './apartments/add-owner/add-owner.component';
import { CompaniesComponent } from './companies/companies.component';
import { AddCompanyComponent } from './companies/add-company/add-company.component';
import { CompanyDetailComponent } from './companies/company-detail/company-detail.component';
import { UsersComponent } from './users/users.component';
import { GlitchTypesComponent } from './glitch-types/glitch-types.component';
import { AddGlitchTypeComponent } from './glitch-types/add-glitch-type/add-glitch-type.component';
import { ChangeTypeCompanyComponent } from './change-type-company/change-type-company.component';
import { HomePresidentComponent } from './home-president/home-president.component';
import { HomeOwnerComponent } from './home-owner/home-owner.component';
import { RegisterComponent } from './register/register.component';
import { AdminProfileComponent } from './users/profile/admin-profile/admin-profile.component';
import { PasswordComponent } from './users/password/password.component';
import { ProfileComponent } from './users/profile/profile.component';
import { ProfileUpdateComponent } from './users/profile/profile-update/profile-update.component';
import { GlitchesComponent } from './glitches/glitches.component';
import { AddGlitchComponent } from './glitches/add-glitch/add-glitch.component';
import { GlitchDetailComponent } from './glitches/glitch-detail/glitch-detail.component';
import { AddSurveyComponent } from './surveys/add-survey/add-survey.component';
import { AddQuestionComponent } from './surveys/add-question/add-question.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HomeAdminComponent,
    BuildingDetailComponent,
    BuildingsComponent,
    AddBuildingComponent,
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
    GlitchDetailsCompanyComponent,
    HomeCompanyComponent,
    LoginLayoutComponent,
    ApartmentDetailComponent,
    AddTenantComponent,
    TenantDetailComponent,
    AddOwnerComponent,
    CompaniesComponent,
    AddCompanyComponent,
    CompanyDetailComponent,
    UsersComponent,
    GlitchTypesComponent,
    AddGlitchTypeComponent,
    ChangeTypeCompanyComponent,
    HomePresidentComponent,
    HomeOwnerComponent,
    RegisterComponent,
    AdminProfileComponent,
    PasswordComponent,
    ProfileComponent,
    ProfileUpdateComponent,
    GlitchesComponent,
    AddGlitchComponent,
    GlitchDetailComponent,
    AddSurveyComponent,
    AddQuestionComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    DropdownModule,
    ReactiveFormsModule,
    OwlDateTimeModule, 
    OwlNativeDateTimeModule
  ],
  providers: [
    AuthGuardService,
    RoleGuardService,
    AuthService,
    BuildingService,
    ApartmentService,
    CompanyDataService,
    ApartmentService,
    TenantService,
    CompanyService,
    UserService,
    GlitchTypeService,
    PagerService,
    GlitchService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
