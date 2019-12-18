import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FormRegistrationComponent} from './form-registration/form-registration.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {FormLoginComponent} from './form-login/form-login.component';
import {HomeComponent} from './home/home.component';
import {DashboardComponent} from './trip/dashboard.component';
import {TripDetailsComponent} from './trip-details/trip-details.component';
import {TripDataComponent} from "./trip/new/trip-data/trip-data.component";

@NgModule({
  declarations: [
    AppComponent,
    FormRegistrationComponent,
    FormLoginComponent,
    HomeComponent,
    DashboardComponent,
    TripDetailsComponent,
    TripDataComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
