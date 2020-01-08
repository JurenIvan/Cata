import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TravelService} from "../../services/travel.service";
import {TripPlan} from "../../models/trip-plan";
import {UserService} from "../../services/user.service";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap'
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {IDropdownSettings} from "ng-multiselect-dropdown/multiselect.model";
import {Location} from "../../models/location";
import {Trip} from "../../models/trip";

@Component({
  selector: 'app-trip-details',
  templateUrl: './trip-details.component.html',
  styleUrls: ['./trip-details.component.css']
})
export class TripDetailsComponent implements OnInit {

  public tripPlan: TripPlan;
  public isAdmin: boolean;
  closeResult: string;
  public rForm: FormGroup;
  tripPlanId: number;

  dropdownList = [];
  locations: Map<string, string> = new Map<string, string>();
  locationIndexes: Map<string, number> = new Map<string, number>();
  selected: Location[] = [];
  index: number = 0;

  dropdownSettings: IDropdownSettings = {};


  constructor(private route: ActivatedRoute, private travelService: TravelService, private userService: UserService,
              private router: Router, private modalService: NgbModal, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.rForm = this.formBuilder.group(
      {
        'description': [null, [Validators.required]],
        'minNumberOfPassangers': [null],
        'pictureUrl': [null],
        'price': [null],
        'dateStart': [null],
        'dateEnd': [null]
      }
    );

    this.userService.isUserAdmin().subscribe(isAdmin => {
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
      let location = new Location(item['item_id'], null, null, item['item_city'], this.locations.get(item['item_city']))
      this.selected.push(location);
      this.locationIndexes.set(location.name, this.index);

      this.index = this.index + 1;
    }
  }

  onItemDeselect(item: any) {
    if (this.locations.has(item['item_city'])) {
      let location = new Location(item['item_id'], null, null, item['item_city'], this.locations.get(item['item_city']));
      this.selected.splice(this.locationIndexes.get(location.name) , 1)
      this.index = this.index - 1;
    }
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.route.params.subscribe(params => {
        if (params['tripPlanId']) {
          this.tripPlanId = params['tripPlanId'];
          let tripPlan = new TripPlan(this.tripPlanId, this.rForm.get('description').value, this.selected,
            this.rForm.get('minNumberOfPassangers').value, this.rForm.get('pictureUrl').value)
          this.travelService.editTripPlan(tripPlan).subscribe( result => {

            this.travelService.loadTravels();
            this.travelService.travels.subscribe(
              travels => {
                travels.forEach( travel => {
                  if(travel.id == this.tripPlanId) {
                    let newTravel = new Trip(travel.id, new Date(this.rForm.get('dateStart').value),
                      new Date(this.rForm.get('dateEnd').value), this.rForm.get('price').value,
                      tripPlan.minNumberOfPassengers, tripPlan);
                    this.travelService.editTrip(newTravel).subscribe(res => {
                      console.log("Success")
                    })
                  }
                })
              }
            );
            this.router.navigate(['/dashboard'])
          })
        }
      });
    });
  }
}

