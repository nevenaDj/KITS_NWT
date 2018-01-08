import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './/app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { BuildingDetailComponent } from './building-detail/building-detail.component';
import { BuildingsComponent } from './buildings/buildings.component';
import { AddBuildingComponent } from './add-building/add-building.component';
import { AuthService } from './login/auth.service';
import { AuthGuardService } from './guards/auth-guard.service';
import { RoleGuardService } from './guards/role-guard.service';
import { BuildingService } from './buildings/building.service';




@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HomeAdminComponent,
    BuildingDetailComponent,
    BuildingsComponent,
    AddBuildingComponent
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
    BuildingService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
