import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Trip} from "../../models/trip";
import {TravelService} from "../../services/travel.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {timepickerReducer} from "ngx-bootstrap/timepicker/reducer/timepicker.reducer";
import {setUTCOffset} from "ngx-bootstrap/chronos/units/offset";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  public travelList: Trip[];
  public isAdmin: boolean;
  public canceled: boolean = false;

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
    console.log("ID" + travelId)
    this.travelService.cancelTrip(travelId).subscribe(travels => {
      console.log(travels)
      this.travelList = travels;
      this.canceled = true;
      this.router.navigate(['/dashboard']);
      console.log("Success")
    })
  }

}
