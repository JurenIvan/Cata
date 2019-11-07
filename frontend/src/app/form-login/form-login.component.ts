import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from "../../services/authentication.service";
import {Router} from "@angular/router";
import {Logger} from "tslint/lib/runner";
import {print} from "util";

@Component({
  selector: 'app-form-login',
  templateUrl: './form-login.component.html',
  styleUrls: ['./form-login.component.css']
})
export class FormLoginComponent implements OnInit {

  username = 'javainuse';
  password = '';
  invalidLogin = false;

  constructor(private router: Router,
              private loginservice: AuthenticationService) { }

  ngOnInit() {
  }

  checkLogin() {
    if (this.loginservice.authenticate(this.username, this.password)
    ) {
      console.log("Success");
      this.router.navigate(['']);
      this.invalidLogin = false
    } else
      this.invalidLogin = true
  }

}
