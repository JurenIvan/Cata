import { Injectable } from '@angular/core';
import {environment} from "../environments/environment";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Trip} from "../models/trip";
import {BehaviorSubject, Observable} from "rxjs";
import {TripPlan} from "../models/trip-plan";

@Injectable({
  providedIn: 'root'
})
export class TravelService {

  private travelSource: BehaviorSubject<Trip[]> = new BehaviorSubject<Trip[]>([]);
  travels: Observable<Trip[]> = this.travelSource.asObservable();


  tripsURL = environment.apiUri + "/trips";
  tripDetailsURL = environment.apiUri + "/trip/";

  constructor(private httpClient: HttpClient) { }

  public loadTravels() {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")})
    };

    this.httpClient.get<Trip[]>(this.tripsURL, httpOptions).subscribe(
      response => {
        this.travelSource.next(response)
      }
    )
  }

  public getTripDetails(tripPlanId: number): Observable<TripPlan> {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")}),
    };

    return this.httpClient.get<TripPlan>(this.tripDetailsURL + tripPlanId.toString(), httpOptions)
  }
}
