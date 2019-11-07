import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormRegistrationComponent} from "./form-registration/form-registration.component";
import {FormLoginComponent} from "./form-login/form-login.component";


const routes: Routes = [

  { path: "", component: FormLoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
