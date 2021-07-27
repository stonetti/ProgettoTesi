import { Component, OnInit } from '@angular/core';
import { TokenStorageService} from "../../service/token-storage.service";
import {User} from "../../model/user";
import {DbConnection} from "../../service/dbConnection";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser?: User;

  constructor(private dbConnection: DbConnection,private token: TokenStorageService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    console.log(this.currentUser)
  }

}
