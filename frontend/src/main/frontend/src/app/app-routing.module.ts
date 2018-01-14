import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuardService } from './guards/auth-guard.service';
import { RoleGuardService } from './guards/role-guard.service';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { BuildingsComponent } from './buildings/buildings.component';
import { BuildingDetailComponent } from './building-detail/building-detail.component';
import { AddBuildingComponent } from './add-building/add-building.component';
import { AddApartmentComponent } from './add-apartment/add-apartment.component';
import { AddPresidentComponent } from './add-president/add-president.component';
import { HomeCompanyComponent } from './home-company/home-company.component';
import { UpdateCompanyComponent } from './update-company/update-company.component';
import { ActiveGlitchesCompanyComponent } from './active-glitches-company/active-glitches-company.component';
import { BillsCompanyComponent } from './bills-company/bills-company.component';
import { ChangePasswordCompanyComponent } from './change-password-company/change-password-company.component';
import { PendingGlitchesCompanyComponent } from './pending-glitches-company/pending-glitches-company.component';
import { PricelistCompanyComponent } from './pricelist-company/pricelist-company.component';
import { BillDetailsCompanyComponent } from './bill-details-company/bill-details-company.component';
import { GlitchDetailsCompanyComponent } from './glitch-details-company/glitch-details-company.component';
import { LoginLayoutComponent } from './login-layout/login-layout.component';
import { ApartmentDetailComponent } from './apartment-detail/apartment-detail.component';
import { AddTenantComponent } from './add-tenant/add-tenant.component';
import { TenantDetailComponent } from './tenant-detail/tenant-detail.component';
import { AddOwnerComponent } from './add-owner/add-owner.component';
import { CompaniesComponent } from './companies/companies.component';
import { CompanyDetailComponent } from './company-detail/company-detail.component';
import { AddCompanyComponent } from './add-company/add-company.component';
import { UsersComponent } from './users/users.component';
import { GlitchTypesComponent } from './glitch-types/glitch-types.component';
import { AddGlitchTypeComponent } from './glitch-types/add-glitch-type/add-glitch-type.component';


const routers: Routes = [
  { 
    path: 'login', component: LoginComponent
  },
  {
    path: '',
    component: HomeAdminComponent,
    /* canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_ADMIN'
    }
*/
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
          path: 'company/pricelist', 
          component: PricelistCompanyComponent, 
        },
  //]
 // },
 {
    path: '',
    component: LoginLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent
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
