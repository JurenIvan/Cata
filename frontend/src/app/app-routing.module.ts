import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FormRegistrationComponent} from "./form-registration/form-registration.component";
import {FormLoginComponent} from "./form-login/form-login.component";
import {HomeComponent} from "./home/home.component";
import {DashboardComponent} from "./trip/dashboard.component";
import {TripDetailsComponent} from "./trip-details/trip-details.component";
import {TripDataComponent} from "./trip/new/trip-data/trip-data.component";


const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'register', component: FormRegistrationComponent },
  { path: 'login', component: FormLoginComponent},
  { path: 'dashboard', component: DashboardComponent},
  { path: 'dashboard/tripDetails/:tripPlanId', component: TripDetailsComponent},
  { path: 'dashboard/newTrip', component: TripDataComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
