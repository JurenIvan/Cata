import {Component, OnInit} from '@angular/core';
import {Trip} from "../../models/trip";
import {TravelService} from "../../services/travel.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public travelList: Trip[];

  constructor(private travelService: TravelService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {

    this.travelService.loadTravels();

    this.travelService.travels.subscribe(
      newTravels => {
        this.travelList = newTravels;
        newTravels.forEach( trip => {
          console.log(trip.tripPlanDto)
        })
      }
    )

  }

}
