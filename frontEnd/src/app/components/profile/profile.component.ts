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

  constructor(private dbConnection: DbConnection,private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
   this.loadUser();
  }

  loadUser() : void{
    this.dbConnection.getUser(this.tokenStorage.getUser().id).subscribe(
      data=>{
        this.currentUser = data;
      })
  }

  changeRole(role: string) {
    this.dbConnection.changeRole(role).subscribe(
      data =>{
        // @ts-ignore
        this.tokenStorage.saveAccessToken(data.accessToken);
        // @ts-ignore
        this.tokenStorage.saveRefreshToken(data.refreshToken);
        this.loadUser();
      }
    )

  }
}
