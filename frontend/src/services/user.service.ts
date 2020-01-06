import {Injectable} from '@angular/core';
import {environment} from "../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {RegistrationRequest} from "../models/registration-request";
import {Observable} from "rxjs";
import {LoginRequest} from "../models/login-request";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  registrationURL = environment.apiUri + '/register';
  loginURL = environment.apiUri + '/authenticate';
  isAdminURL = environment.apiUri + '/is-agent'

  constructor(private httpClient: HttpClient) { }

  public register(user: RegistrationRequest) : Observable<any>{
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    };
    return this.httpClient.post<RegistrationRequest>(this.registrationURL, user, httpOptions)
  }

  public login(user: LoginRequest) : Observable<any> {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    };
    return this.httpClient.post<any>(this.loginURL, user, httpOptions)
  }

  public isUserAdmin() : Observable<any> {
    let httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': "Bearer " + localStorage.getItem("token")}),
    };
    return this.httpClient.get<any>(this.isAdminURL, httpOptions);
  }

}
