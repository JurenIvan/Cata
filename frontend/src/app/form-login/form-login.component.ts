import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {RegistrationRequest} from "../../models/registration-request";
import {LoginRequest} from "../../models/login-request";

@Component({
  selector: 'app-form-login',
  templateUrl: './form-login.component.html',
  styleUrls: ['./form-login.component.css']
})
export class FormLoginComponent implements OnInit {

  public mForm: FormGroup;
  public user: LoginRequest;

  constructor(private formBuilder: FormBuilder, private userService: UserService,
              private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.mForm = this.formBuilder.group(
      {
        'username': [null, [Validators.required]],
        'password': [null, [Validators.required]],
      }
    );

    this.user = new LoginRequest('', '');
    this.updateForm(this.user)
  }

  private updateForm(user: LoginRequest) {
    this.mForm.setValue(user)
  }

  saveUser() {
    if(this.mForm.valid) {
      this.userService.login(this.mForm.value)
        .subscribe(
          token => {
            localStorage.setItem("token", JSON.stringify(token).split(":")[1].substr(1, JSON.stringify(token).split(":")[1].length-3));
            this.router.navigate(['/dashboard'])
          }
        )
    }
  }

}
