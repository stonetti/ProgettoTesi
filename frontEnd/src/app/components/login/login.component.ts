import { Component, OnInit } from '@angular/core';
import { AuthService} from "../../service/auth.service";
import { TokenStorageService} from "../../service/token-storage.service";
import {UserLogin} from "../../model/userLogin";
import {Router} from "@angular/router";
import {User} from "../../model/user";
import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})


@Injectable({
  providedIn: 'root'
})
export class LoginComponent implements OnInit {

  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  userLogin !: UserLogin;
  userRole : string = '';

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, protected router: Router) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
    this.userLogin = new UserLogin();
  }

  onSubmit(): void {

    this.authService.login(this.userLogin).subscribe(
      data => {
        this.tokenStorage.saveAccessToken(data.accessToken);
        this.tokenStorage.saveRefreshToken(data.refreshToken);
        let decodedUser = this.getTokenFields(data.accessToken);
        this.userRole = decodedUser.role;
        this.tokenStorage.saveUser(decodedUser);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        if(this.userRole == "USER")
          this.router.navigate(['report']);
        else
          this.router.navigate(['dashboard'])
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  private getTokenFields(accessToken : string) : User {
    return jwt_decode(accessToken);
  }


  getUserRole() {
    return this.userRole;
  }
}
