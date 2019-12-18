import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {TripDataComponent} from "./trip-data/trip-data.component";

const routes: Routes = [
  {
    path: '',
    component: TripDataComponent,
    children: [
      {
        path: 'addTrip',
        component: TripDataComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NewRoutingModule {
}
