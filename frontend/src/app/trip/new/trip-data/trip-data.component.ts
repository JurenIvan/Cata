import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TravelService} from "../../../../services/travel.service";
import {TripPlan} from "../../../../models/trip-plan";
import {Location} from "../../../../models/location";
import {Trip} from "../../../../models/trip";

@Component({
  selector: 'app-trip-data',
  templateUrl: './trip-data.component.html',
  styleUrls: ['./trip-data.component.css']
})
export class TripDataComponent implements OnInit {

  public rForm: FormGroup;
  public isPressed: boolean;
  public trip: Trip;

  constructor(private formBuilder: FormBuilder, private travelService: TravelService) { }

  ngOnInit() {
    this.rForm = this.formBuilder.group(
      {
        'id': [null],
        'dateStart': [null, [Validators.required]],
        'dateEnd': [null, [Validators.required]],
        'price': [null, [Validators.required]],
        'minNumberPassangers': [null, [Validators.required]]
      }
    );
    this.trip = new Trip(0, this.rForm.get('dateStart').value, this.rForm.get('dateEnd').value, this.rForm.get('price').value, this.rForm.get('minNumberPassangers').value, null)
    this.rForm.setValue(this.trip);
  }

  public isPressedButton() {
    this.isPressed = !this.isPressed;
    this.rForm = this.formBuilder.group(
      {
        'id': [this.trip.id],
        'dateStart': [this.trip.startDateTime, [Validators.required]],
        'dateEnd': [this.trip.endDateTime, [Validators.required]],
        'price': [this.trip.price, [Validators.required]],
        'minNumberPassangers': [this.trip.passengerCount, [Validators.required]],
        'description': [null, [Validators.required]],
        'pictureUrl': [null, [Validators.required]]
      }
    );
  }

  public saveTripPlan(){

    if(this.rForm.valid) {
      let tripPlan =  new TripPlan(0, this.rForm.get('description').value, [new Location(1, 45.78, 67.99, "Pariz", "Francuska")], this.trip.passengerCount, this.rForm.get('pictureUrl').value);
      this.travelService.addNewTripPlan(tripPlan)
        .subscribe(
          response => {

            console.log("SUCCESS" + response)
          },
          error => {
            console.log(error)
          }
        )
    } else  {
      console.log("NOT VALID")
    }
  }

}
