import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FormRegistrationComponent} from "./form-registration/form-registration.component";
import {FormLoginComponent} from "./form-login/form-login.component";
import {HomeComponent} from "./home/home.component";
import {DashboardComponent} from "./dashboard/dashboard.component";


const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'register', component: FormRegistrationComponent },
  { path: 'login', component: FormLoginComponent},
  { path:  'dashboard', component: DashboardComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
