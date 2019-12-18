import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TravelService} from "../../../../services/travel.service";
import {TripPlan} from "../../../../models/trip-plan";
import {Location} from "../../../../models/location";
import {Trip} from "../../../../models/trip";
import {Router} from "@angular/router";

@Component({
  selector: 'app-trip-data',
  templateUrl: './trip-data.component.html',
  styleUrls: ['./trip-data.component.css']
})
export class TripDataComponent implements OnInit {

  public rForm: FormGroup;
  public isPressed: boolean;
  public trip: Trip;

  constructor(private formBuilder: FormBuilder, private travelService: TravelService, private router: Router) { }

  ngOnInit() {
    this.rForm = this.formBuilder.group(
      {
        'dateStart': [null, [Validators.required]],
        'dateEnd': [null, [Validators.required]],
        'price': [null, [Validators.required]],
        'minNumberPassangers': [null, [Validators.required]],
        'description': [null, [Validators.required]],
        'pictureUrl': [null, [Validators.required]]
      }
    );
  }

  public isPressedButton() {
    this.isPressed = !this.isPressed;
    this.trip = new Trip(null, new Date(this.rForm.get('dateStart').value), new Date(this.rForm.get('dateEnd').value), this.rForm.get('price').value, this.rForm.get('minNumberPassangers').value, null)
  }

  public saveTripPlan(){

    if(this.rForm.valid) {
      let tripPlan =  new TripPlan(null, this.rForm.get('description').value, [new Location(1, 45.78, 67.99, "Pariz", "Francuska")], this.trip.passengerCount, this.rForm.get('pictureUrl').value);
      this.travelService.addNewTripPlan(tripPlan)
        .subscribe(
          response => {
            this.trip.tripPlanDto = response;
            this.travelService.addNewTrip(this.trip).subscribe(
              response => {
                this.router.navigate(['/dashboard']);
              },
              error =>
                console.log(error)
            )
          },
          error => {
            console.log(error)
          }
        )
    }
  }

}
