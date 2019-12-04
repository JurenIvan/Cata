import { Injectable } from '@angular/core';
import {environment} from "../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Trip} from "../models/trip";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TravelService {

  private travelSource: BehaviorSubject<Trip[]> = new BehaviorSubject<Trip[]>([]);
  travels: Observable<Trip[]> = this.travelSource.asObservable();

  travelURL = environment.apiUri + "/trips";

  constructor(private httpClient: HttpClient) { }

  public loadTravels() {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")})
    };

    this.httpClient.get<Trip[]>(this.travelURL, httpOptions).subscribe(
      response => {
        this.travelSource.next(response)
      }
    )
  }
}
