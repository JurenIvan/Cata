import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormRegistrationComponent} from "./form-registration/form-registration.component";


const routes: Routes = [

  { path: "", component: FormRegistrationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
