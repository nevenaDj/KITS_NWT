import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { BuildingsComponent } from './buildings/buildings.component';
import { BuildingDetailComponent } from './building-detail/building-detail.component';
import { AuthGuardService } from './guards/auth-guard.service';
import { RoleGuardService } from './guards/role-guard.service';


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
  { path: 'addBuilding', component: BuildingDetailComponent},
  { path: 'buildings', component: BuildingsComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routers)],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
