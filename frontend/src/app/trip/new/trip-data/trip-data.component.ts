import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TravelService} from "../../../../services/travel.service";
import {TripPlan} from "../../../../models/trip-plan";
import {Location} from "../../../../models/location";
import {Trip} from "../../../../models/trip";
import {Router} from "@angular/router";
import {IDropdownSettings} from "ng-multiselect-dropdown";
import {User} from "../../../../models/user";

@Component({
  selector: 'app-trip-data',
  templateUrl: './trip-data.component.html',
  styleUrls: ['./trip-data.component.css']
})
export class TripDataComponent implements OnInit {

  public rForm: FormGroup;
  public isPressed: boolean;
  public trip: Trip;

  dropdownList = [];
  locations: Map<string, string> = new Map<string, string>();
  locationIndexes: Map<string, number> = new Map<string, number>();
  selected: Location[] = [];
  index: number = 0;

  dropdownSettings: IDropdownSettings = {};

  constructor(private formBuilder: FormBuilder, private travelService: TravelService, private router: Router) {
  }

  ngOnInit() {
    this.isPressed = false;
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

    this.dropdownList = [
      {item_id: 1, item_city: 'Mumbai'},
      {item_id: 2, item_city: 'Pariz'},
      {item_id: 3, item_city: 'London'},
      {item_id: 4, item_city: 'Zagreb'},
      {item_id: 5, item_city: 'Budimpešta'},
      {item_id: 6, item_city: 'Prag'},
      {item_id: 7, item_city: 'Sofija'},
      {item_id: 8, item_city: 'Istambul'},
      {item_id: 9, item_city: 'Moskva'}
    ];

    // preuzeti sve dostupne lokacije iz baze?
    this.locations.set('Mumbai', 'Maharashtra');
    this.locations.set('Pariz', 'Francuska');
    this.locations.set('London', 'Ujedinjeno Kraljevstvo');
    this.locations.set('Zagreb', 'Hrvatska');
    this.locations.set('Budimpešta', 'Mađarska');
    this.locations.set('Prag', 'Češka');
    this.locations.set('Sofija', 'Bugarska');
    this.locations.set('Istambul', 'Turska');
    this.locations.set('Moskva', 'Rusija');


    this.dropdownSettings = {
      singleSelection: false,
      enableCheckAll: false,
      idField: 'item_id',
      textField: 'item_city',
      itemsShowLimit: 4,
      allowSearchFilter: true,
    };
  }

  onItemSelect(item: any) {
    if (this.locations.has(item['item_city'])) {
      let location = new Location(item['item_id'], item['item_city'], this.locations.get(item['item_city']))
      this.selected.push(location);
      this.locationIndexes.set(location.name, this.index);

      this.index = this.index + 1;
    }
  }

  onItemDeselect(item: any) {
    if (this.locations.has(item['item_city'])) {
      let location = new Location(item['item_id'], item['item_city'], this.locations.get(item['item_city']));
      this.selected.splice(this.locationIndexes.get(location.name) , 1)
      this.index = this.index - 1;
    }
  }


  public isPressedButton() {
    this.isPressed = !this.isPressed;
  }

  public saveTripPlan() {

    if (this.rForm.valid) {
      this.trip = new Trip(null, new Date(this.rForm.get('dateStart').value),
        new Date(this.rForm.get('dateEnd').value), this.rForm.get('price').value,
        [], null,null);
      let tripPlan = new TripPlan(null, this.rForm.get('description').value, this.selected, this.rForm.get('minNumberPassangers').value, this.rForm.get('pictureUrl').value);
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
