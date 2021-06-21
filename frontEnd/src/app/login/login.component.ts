import { Component, OnInit } from '@angular/core';
import {UserLogin} from "../model/UserLogin";
import {UserService} from "../service/UserService";
import { map, filter, take } from "rxjs/operators";
import {Jwt} from "../model/Jwt";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: UserLogin;
  jwt:Jwt | undefined;

  constructor(private userService: UserService) {
    this.user = new UserLogin();
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit(): void {

  }

  onSubmit() {
    this.userService.login(this.user).subscribe((data: Jwt) => {
      this.jwt = data;
    },(msg) =>  {
      console.log('Errore durante il Login: ', msg);
    });

  }

}
