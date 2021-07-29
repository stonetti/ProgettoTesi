import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserLogin} from "../model/userLogin";
import {User} from "../model/user";

const API_URL = 'http://localhost:8080/users/';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(userLogin : UserLogin): Observable<any> {
    return this.http.post(API_URL + 'login', userLogin);
  }

}
