import {Component, OnInit, SimpleChanges} from '@angular/core';
import {Trip} from "../../models/trip";
import {TravelService} from "../../services/travel.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public travelList: Trip[];
  public isAdmin: boolean;

  constructor(private travelService: TravelService, private userService: UserService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {

    this.userService.isUserAdmin().subscribe(
      isAdmin => {
        this.isAdmin = isAdmin;
        console.log(isAdmin)
      }
    );

    this.travelService.loadTravels();

    this.travelService.travels.subscribe(
      newTravels => {
        console.log(newTravels);
        this.travelList = newTravels;
        newTravels.forEach( travel => {
          console.log (travel.passengers.length)
        })
      }
    )
  }

  cancelTrip(travelId: number) {
    this.travelService.cancelTrip(travelId).subscribe(result => {
      console.log("Success")
    })

  }

}
