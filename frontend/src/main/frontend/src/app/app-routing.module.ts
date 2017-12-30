import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { Router } from '@angular/router/src/router';
import { AuthGuardService } from './guards/auth-guard.service';
import { HomeComponent } from './home/home.component';

const routers: Routes = [
  { path: 'login', component: LoginComponent},
  { path: '', component: HomeComponent, canActivate: [AuthGuardService] }
];

@NgModule({
  imports: [ RouterModule.forRoot(routers)],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
