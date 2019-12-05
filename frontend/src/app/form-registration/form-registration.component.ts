import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormControl, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {RegistrationRequest} from "../../models/registration-request";
import {LoginRequest} from "../../models/login-request";

@Component({
  selector: 'app-form-registration',
  templateUrl: './form-registration.component.html',
  styleUrls: ['./form-registration.component.css']
})
export class FormRegistrationComponent implements OnInit {

  public mForm: FormGroup;
  public user: RegistrationRequest;

  constructor(private formBuilder: FormBuilder, private userService: UserService,
              private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.mForm = this.formBuilder.group(
      {
        'email': [null, [Validators.required]],
        'username': [null, [Validators.required]],
        'password': [null, [Validators.required]],
      }
    );

    this.user = new RegistrationRequest('', '', '');
    this.updateForm(this.user)
  }

  private updateForm(user: RegistrationRequest) {
    this.mForm.setValue(user)
  }

  saveUser() {
    if(this.mForm.valid) {
      let user: RegistrationRequest = this.mForm.value;
      this.userService.register(user)
        .subscribe(
        _ => {
          this.userService.login(new LoginRequest(user.username, user.password))
            .subscribe(
              token => {
                localStorage.setItem("token", JSON.stringify(token).split(":")[1].substr(1, JSON.stringify(token).split(":")[1].length-3));
                this.router.navigate(['/dashboard'])
              }
            )
        }
      )
    }
  }


}
