import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuardService } from './guards/auth-guard.service';
import { RoleGuardService } from './guards/role-guard.service';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { BuildingsComponent } from './buildings/buildings.component';
import { BuildingDetailComponent } from './buildings/building-detail/building-detail.component';
import { AddBuildingComponent } from './buildings/add-building/add-building.component';
import { AddApartmentComponent } from './apartments/add-apartment/add-apartment.component';
import { AddPresidentComponent } from './buildings/add-president/add-president.component';
import { HomeCompanyComponent } from './home-company/home-company.component';
import { UpdateCompanyComponent } from './update-company/update-company.component';
import { ActiveGlitchesCompanyComponent } from './active-glitches-company/active-glitches-company.component';
import { BillsCompanyComponent } from './bills-company/bills-company.component';
import { ChangePasswordCompanyComponent } from './change-password-company/change-password-company.component';
import { PendingGlitchesCompanyComponent } from './pending-glitches-company/pending-glitches-company.component';
import { PricelistCompanyComponent } from './pricelist-company/pricelist-company.component';
import { BillDetailsCompanyComponent } from './bill-details-company/bill-details-company.component';
import { GlitchDetailsCompanyComponent } from './glitch-details-company/glitch-details-company.component';
import { LoginLayoutComponent } from './login/login-layout/login-layout.component';
import { ApartmentDetailComponent } from './apartments/apartment-detail/apartment-detail.component';
import { AddTenantComponent } from './tenants/add-tenant/add-tenant.component';
import { TenantDetailComponent } from './tenants/tenant-detail/tenant-detail.component';
import { AddOwnerComponent } from './apartments/add-owner/add-owner.component';
import { CompaniesComponent } from './companies/companies.component';
import { CompanyDetailComponent } from './companies/company-detail/company-detail.component';
import { AddCompanyComponent } from './companies/add-company/add-company.component';
import { UsersComponent } from './users/users.component';
import { GlitchTypesComponent } from './glitch-types/glitch-types.component';
import { AddGlitchTypeComponent } from './glitch-types/add-glitch-type/add-glitch-type.component';
import { ChangeTypeCompanyComponent } from './change-type-company/change-type-company.component';
import { HomeOwnerComponent } from './home-owner/home-owner.component';
import { HomePresidentComponent } from './home-president/home-president.component';
import { RegisterComponent } from './register/register.component';
import { AdminProfileComponent } from './users/profile/admin-profile/admin-profile.component';
import { PasswordComponent } from './users/password/password.component';
import { ProfileComponent } from './users/profile/profile.component';
import { ProfileUpdateComponent } from './users/profile/profile-update/profile-update.component';
import { GlitchesComponent } from './glitches/glitches.component';
import { AddGlitchComponent } from './glitches/add-glitch/add-glitch.component';
import { GlitchDetailComponent } from './glitches/glitch-detail/glitch-detail.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { AddSurveyComponent } from './surveys/add-survey/add-survey.component';
import { SurveyDetailComponent } from './surveys/survey-detail/survey-detail.component';
import { AddAnswerComponent } from './surveys/add-answer/add-answer.component';
import { SendToTheOtherCompanyComponent } from './send-to-the-other-company/send-to-the-other-company.component';
import { SendBillComponent } from './send-bill/send-bill.component';
import { BillsPresidentComponent } from './bills-president/bills-president.component';
import { BillDetailsPresidentComponent } from './bill-details-president/bill-details-president.component';
import { ResponsibilitiesComponent } from './glitches/responsibilities/responsibilities.component';
import { ChangeResponsiblePersonComponent } from './glitches/change-responsible-person/change-responsible-person.component';
import { CurrentApartmentComponent } from './apartments/current-apartment/current-apartment.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { AddNotificationComponent } from './notifications/add-notification/add-notification.component';


const routers: Routes = [
  { 
    path: 'login', component: LoginComponent
  },
  {
    path: '',
    component: HomeAdminComponent,
    canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_ADMIN'
    },
    children: [
          { path: 'buildings', component: BuildingsComponent},
          { path: 'addBuilding', component: AddBuildingComponent},
          { path: 'editBuilding/:id', component: AddBuildingComponent},
          { path: 'buildings/:id',component: BuildingDetailComponent},
          { path: 'buildings/:idBuilding/addApartment', component: AddApartmentComponent},
          { path: 'buildings/:id/addPresident', component: AddPresidentComponent},
          { path: 'buildings/:idBuilding/apartments/:id', component: ApartmentDetailComponent},
          { path: 'apartments/:id/addTenant', component: AddTenantComponent},
          { path: 'apartments/:idApartment/tenants/:id', component: TenantDetailComponent},
          { path: 'apartments/:id/addOwner', component: AddOwnerComponent},
          { path: 'companies', component: CompaniesComponent},
          { path: 'editApartment/:id', component: AddApartmentComponent},
          { path: 'companies/:id', component: CompanyDetailComponent},
          { path: 'addCompany', component: AddCompanyComponent},
          { path: 'users', component: UsersComponent},
          { path: 'glitchTypes', component: GlitchTypesComponent},
          { path: 'addGlitchType', component: AddGlitchTypeComponent},
          { path: 'admin', component: AdminProfileComponent},
          { path: 'password', component: PasswordComponent}
    ]
  },
  { 
    path: 'company', 
    component: HomeCompanyComponent, 
   /* canActivate: [AuthGuardService, RoleGuardService] ,
    data: { 
      expectedRole: 'ROLE_COMPANY'
    }
   
  },*/
 // children: [
        },{ 
          path: 'company/update', 
          component: UpdateCompanyComponent, 
      
        },
        { 
          path: 'company/changePassword', 
          component: ChangePasswordCompanyComponent, 
      
        },
        { 
          path: 'company/bills', 
          component: BillsCompanyComponent, 
        },
        { 
          path: 'company/bills/:id', 
          component: BillDetailsCompanyComponent, 
        },
        { 
          path: 'company/activeGlitches', 
          component: ActiveGlitchesCompanyComponent, 
        },
        { 
          path: 'company/pendingGlitches', 
          component: PendingGlitchesCompanyComponent, 
        },
        { 
          path: 'company/pendingGlitches/:id', 
          component: GlitchDetailsCompanyComponent, 
        },
        { 
          path: 'company/activeGlitches/:id', 
          component: GlitchDetailsCompanyComponent, 
        },
        { 
          path: 'company/activeGlitches/:id/send', 
          component: SendToTheOtherCompanyComponent, 
        },
        { 
          path: 'company/activeGlitches/:id/bill', 
          component: SendBillComponent, 
        },
        { 
          path: 'company/pricelist', 
          component: PricelistCompanyComponent, 
        },
        { 
          path: 'company/pricelist/changeType', 
          component: ChangeTypeCompanyComponent, 
        },
  //]
 // },
  {
    path:'tenant',
    component: HomeComponent,
    /* canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_USER'
    }, */
    children: [
      { path: 'password', component: PasswordComponent},
      { path: 'profile', component: ProfileComponent},
      { path: 'update', component: ProfileUpdateComponent},
      { path: 'glitches', component: GlitchesComponent},
      { path: 'glitches/:id', component: GlitchDetailComponent},
      { path: 'addGlitch', component: AddGlitchComponent},
      { path: 'myResponsiblities', component:ResponsibilitiesComponent },
      { path: 'myResponsiblities/:id', component:GlitchDetailComponent },
      { path: 'apartments', component: CurrentApartmentComponent},
      { path: 'notifications', component: NotificationsComponent},
      { path: 'addNotification', component: AddNotificationComponent}
    ]
  },
  {
    path:'owner',
    component: HomeOwnerComponent,
    canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_OWNER'
    },
    children: [
      { path: 'password', component: PasswordComponent},
      { path: 'profile', component: ProfileComponent},
      { path: 'update', component: ProfileUpdateComponent},
      { path: 'surveys/:id/addAnswer', component: AddAnswerComponent},
      { path: 'apartments', component: CurrentApartmentComponent},
      { path: 'notifications', component: NotificationsComponent},
      { path: 'addNotification', component: AddNotificationComponent}
    ]
  },
  {
    path:'president',
    component: HomePresidentComponent,
    /* canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_PRESIDENT'
    }, */
    children: [
      { path: 'password', component: PasswordComponent},
      { path: 'profile', component: ProfileComponent},
      { path: 'update', component: ProfileUpdateComponent},
      { path: 'meeting/:id/addSurvey', component: AddSurveyComponent},
      { path: 'surveys/:id', component: SurveyDetailComponent},
      {path: 'bills', component: BillsPresidentComponent},
      {path: 'bills/:id', component: BillDetailsPresidentComponent},
      { path: 'responsiblities', component:ResponsibilitiesComponent },
      { path: 'responsiblities/:id', component:GlitchDetailComponent },
      { path: 'responsiblities/:id/change', component:ChangeResponsiblePersonComponent }
    ]
  },
  {
    path: '',
    component: LoginLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'register',
        component: RegisterComponent
      }
    ]
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [ RouterModule.forRoot(routers, {useHash:true})],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
