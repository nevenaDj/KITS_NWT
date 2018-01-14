import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuardService } from './guards/auth-guard.service';
import { RoleGuardService } from './guards/role-guard.service';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { BuildingsComponent } from './buildings/buildings.component';
import { BuildingDetailComponent } from './buildings/building-detail/building-detail.component';
import { AddBuildingComponent } from './buildings/add-building/add-building.component';
import { AddApartmentComponent } from './apartments/add-apartment/add-apartment.component';
import { AddPresidentComponent } from './buildings/add-president/add-president.component';
import { HomeCompanyComponent } from './home-company/home-company.component';
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
import { HomeOwnerComponent } from './home-owner/home-owner.component';
import { HomePresidentComponent } from './home-president/home-president.component';
import { RegisterComponent } from './register/register.component';



const routers: Routes = [
  {
    path: '',
    component: HomeAdminComponent,
    /* canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_ADMIN'
    }, */
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
          { path: 'addGlitchType', component: AddGlitchTypeComponent}
    ]
  },
  {
    path: 'home/company',
    component: HomeCompanyComponent,
    canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_COMPANY'
    },
  },
  {
    path:'tenant',
    component: HomeComponent,
    canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_USER'
    }
  },
  {
    path:'owner',
    component: HomeOwnerComponent,
    canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_OWNER'
    }
  },
  {
    path:'president',
    component: HomePresidentComponent,
    canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_PRESIDENT'
    }
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
  imports: [ RouterModule.forRoot(routers)],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
