import { Injectable } from '@angular/core';
import {environment} from "../environments/environment";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Trip} from "../models/trip";
import {BehaviorSubject, Observable, Observer} from "rxjs";
import {TripPlan} from "../models/trip-plan";

@Injectable({
  providedIn: 'root'
})
export class TravelService {

  private travelSource: BehaviorSubject<Trip[]> = new BehaviorSubject<Trip[]>([]);
  travels: Observable<Trip[]> = this.travelSource.asObservable();


  tripsURL = environment.apiUri + "/trips";
  postTripURL = environment.apiUri + "/trips/create";
  tripDetailsURL = environment.apiUri + "/trip/";
  postTripDetailsURL = environment.apiUri + "/trip/create";
  editTripPlanURL = environment.apiUri + "/trip/edit";
  editTripURL = environment.apiUri + "/trips/edit";
  joinTripURL = environment.apiUri + "/trips/join";
  userCancelTripURL = environment.apiUri + "/trips/cancel-registration";
  joinedTripsURL = environment.apiUri + "/trips/my";


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

  public addNewTripPlan(tripPlan: TripPlan): Observable<any>{
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")}),
    };
    return this.httpClient.post<TripPlan>(this.postTripDetailsURL, tripPlan,  httpOptions)
  }

  public addNewTrip(trip: Trip): Observable<any> {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")}),
    };
    return this.httpClient.post<Trip>(this.postTripURL, trip,  httpOptions)
  }

  public editTripPlan(tripPlan: TripPlan): Observable<any> {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")}),
    };
    return this.httpClient.post<TripPlan>(this.editTripPlanURL, tripPlan,  httpOptions)
  }

  public editTrip(trip: Trip): Observable<any> {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")}),
    };
    return this.httpClient.post<Trip>(this.editTripURL, trip,  httpOptions)
  }

  public joinTrip(tripId: number): Observable<any> {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")}),
      params: new HttpParams().set("id", tripId.toString())
    };
    return this.httpClient.get(this.joinTripURL, httpOptions)
  }


  public userCancelTrip(tripId: number): Observable<any> {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")}),
      params: new HttpParams().set("id", tripId.toString())
    };
    return this.httpClient.get(this.userCancelTripURL, httpOptions)
  }

  public getJoinedTrips(): Observable<any> {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")}),
    };
    return this.httpClient.get(this.joinedTripsURL, httpOptions);
  }

}
