import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-trip',
  templateUrl: './new-trip.component.html',
  styleUrls: ['./new-trip.component.css']
})
export class NewTripComponent implements OnInit {

  public rForm: FormGroup;

  constructor(private router: Router) { }

  ngOnInit() {

    //this.rForm = this.formBuilder.group()
  }

}
