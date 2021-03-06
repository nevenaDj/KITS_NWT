import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { DropdownModule } from 'ngx-dropdown';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { Ng2OrderModule } from 'ng2-order-pipe';
import { ToastModule, ToastOptions} from 'ng2-toastr/ng2-toastr';
import { ImageUploadModule } from "angular2-image-upload";

import { NotificationService } from './notifications/notification.service';
import { SurveyService } from './surveys/survey.service';
import { GlitchDataService } from './glitches/glitch-details-company/glitch-data.service'
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
import { CommunalProblemService } from "./communal-problems/communal-problem.service";
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
import { UpdateCompanyComponent } from './company/update-company/update-company.component';
import { PendingGlitchesCompanyComponent } from './glitches/pending-glitches-company/pending-glitches-company.component';
import { ActiveGlitchesCompanyComponent } from './glitches/active-glitches-company/active-glitches-company.component';
import { BillsCompanyComponent } from './bills-president/bills-company/bills-company.component';
import { PricelistCompanyComponent } from './pricelist-company/pricelist-company.component';
import { ChangePasswordCompanyComponent } from './company/change-password-company/change-password-company.component';
import { BillDetailsCompanyComponent } from './bills-president/bill-details-company/bill-details-company.component';
import { GlitchDetailsCompanyComponent } from './glitches/glitch-details-company/glitch-details-company.component'
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
import { ChangeTypeCompanyComponent } from './glitch-types/change-type-company/change-type-company.component';
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
import { SurveyDetailComponent } from './surveys/survey-detail/survey-detail.component';
import { AddAnswerComponent } from './surveys/add-answer/add-answer.component';
import { SendToTheOtherCompanyComponent } from './glitches/send-to-the-other-company/send-to-the-other-company.component';
import { SendBillComponent } from './bills-president/send-bill/send-bill.component';
import { BillsPresidentComponent } from './bills-president/bills-president.component';
import { BillsDataService } from './bills-president/bills-data.service';
import { BillDetailsPresidentComponent } from './bills-president/bill-details-president/bill-details-president.component';
import { ResponsibilitiesComponent } from './glitches/responsibilities/responsibilities.component';
import { ChangeResponsiblePersonComponent } from './glitches/change-responsible-person/change-responsible-person.component';
import { MeetingsComponent } from './meetings/meetings.component';
import { AddMeetingComponent } from './meetings/add-meeting/add-meeting.component';
import { MeatingDetailsComponent } from './meetings/meating-details/meating-details.component';
import { AddItemComponent } from './meetings/add-item/add-item.component';
import { ItemDetailsComponent } from './meetings/item-details/item-details.component';
import { UpdateItemComponent } from './meetings/update-item/update-item.component';
import { MeetingsService } from './meetings/meetings.service';
import { CurrentApartmentComponent } from './apartments/current-apartment/current-apartment.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { AddNotificationComponent } from './notifications/add-notification/add-notification.component';
import { CurrentBuildingComponent } from './buildings/current-building/current-building.component';
import { NotificationDetailComponent } from './notifications/notification-detail/notification-detail.component';
import { ActiveMeetingOwnerComponent } from './meetings/active-meeting-owner/active-meeting-owner.component';
import { ReportsComponent } from './meetings/reports/reports.component';
import { CommunalProblemsComponent } from './communal-problems/communal-problems.component';
import { CommunalProblemDetailComponent } from './communal-problems/communal-problem-detail/communal-problem-detail.component';
import { AddCommunalProblemComponent } from './communal-problems/add-communal-problem/add-communal-problem.component';
import { ActivecommunalProblemComponent } from './communal-problems/activecommunal-problem/activecommunal-problem.component';
import { CompanyComponent } from './company/company.component';
import { UpcomingMeetingsComponent } from './meetings/upcoming-meetings/upcoming-meetings.component';


export class CustomOption extends ToastOptions {
  animate = 'flyRight'; // you can override any options available
  newestOnTop = false;
  showCloseButton = true;
}


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
    SurveyDetailComponent,
    AddAnswerComponent,
    GlitchDetailComponent,
    SendToTheOtherCompanyComponent,
    SendBillComponent,
    BillsPresidentComponent,
    BillDetailsPresidentComponent,
    ResponsibilitiesComponent,
    ChangeResponsiblePersonComponent,
    MeetingsComponent,
    AddMeetingComponent,
    MeatingDetailsComponent,
    AddItemComponent,
    ItemDetailsComponent,
    UpdateItemComponent,
    CurrentApartmentComponent,
    NotificationsComponent,
    AddNotificationComponent,
    CurrentBuildingComponent,
    NotificationDetailComponent,
    ActiveMeetingOwnerComponent,
    ReportsComponent,
    CommunalProblemsComponent,
    CommunalProblemDetailComponent,
    AddCommunalProblemComponent,
    ActivecommunalProblemComponent,
    CompanyComponent,
    UpcomingMeetingsComponent,
    
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
    OwlNativeDateTimeModule,
    Ng2OrderModule,
    ToastModule.forRoot(),
    ImageUploadModule.forRoot()
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
    SurveyService,
    GlitchDataService,
    BillsDataService,
    MeetingsService,
    NotificationService,
    CommunalProblemService,
    {provide: ToastOptions, useClass: CustomOption},
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
