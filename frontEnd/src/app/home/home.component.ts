import { Component, OnInit } from '@angular/core';
import {AppComponent} from "../app.component";
import {TokenStorageService} from "../service/token-storage.service";
import {User} from "../model/User";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currentUser?: User;

  constructor(private appComponent : AppComponent,private token : TokenStorageService) {
  }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
  }


}
