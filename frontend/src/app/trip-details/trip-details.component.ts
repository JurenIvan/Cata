import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TravelService} from "../../services/travel.service";
import {TripPlan} from "../../models/trip-plan";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-trip-details',
  templateUrl: './trip-details.component.html',
  styleUrls: ['./trip-details.component.css']
})
export class TripDetailsComponent implements OnInit {

  public tripPlan: TripPlan;
  public isAdmin: boolean;


  constructor(private route: ActivatedRoute, private travelService: TravelService, private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {

    this.userService.isUserAdmin().subscribe( isAdmin => {
      this.isAdmin = isAdmin
      }
    );

    this.route.params.subscribe(params => {
        if (params['tripPlanId']) {
          let tripPlanId = params['tripPlanId'];
          this.travelService.getTripDetails(parseInt(tripPlanId)).subscribe(
            tripPlan => {
              this.tripPlan = tripPlan;
            }
          )
        }
      }
    )
  }

}
