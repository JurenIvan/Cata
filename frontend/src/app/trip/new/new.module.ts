import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NewRoutingModule} from "./new-routing.module";
import {NewTripComponent} from "./new-trip.component";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    NewTripComponent
  ],
  imports: [
    CommonModule,
    NewRoutingModule,
    ReactiveFormsModule
  ]
})
export class NewModule { }
