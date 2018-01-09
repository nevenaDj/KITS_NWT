import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { BuildingsComponent } from './buildings/buildings.component';
import { BuildingDetailComponent } from './building-detail/building-detail.component';
import { AddBuildingComponent } from './add-building/add-building.component';
import { AuthGuardService } from './guards/auth-guard.service';
import { RoleGuardService } from './guards/role-guard.service';
import { AddApartmentComponent } from './add-apartment/add-apartment.component';
import { AddPresidentComponent } from './add-president/add-president.component';



const routers: Routes = [
  { path: 'login', component: LoginComponent},
  { 
    path: '', 
    component: HomeComponent, 
    canActivate: [AuthGuardService,RoleGuardService], 
    data: { 
      expectedRole: 'ROLE_ADMIN'
    }
   
  },
  { path: 'addBuilding', component: AddBuildingComponent},
  { path: 'buildings', component: BuildingsComponent},
  { path: 'buildings/:id',component: BuildingDetailComponent},
  { path: 'buildings/:id/addApartment', component: AddApartmentComponent},
  { path: 'buildings/:id/addPresident', component: AddPresidentComponent}
  

];

@NgModule({
  imports: [ RouterModule.forRoot(routers)],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
