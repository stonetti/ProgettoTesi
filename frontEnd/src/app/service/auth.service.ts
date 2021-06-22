import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserLogin} from "../model/UserLogin";
import {User} from "../model/User";

const API_URL = 'http://localhost:8080/users/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(userLogin : UserLogin): Observable<any> {
    return this.http.post(API_URL + 'login', userLogin);
  }

  register(user : User): Observable<any> {
    return this.http.post(API_URL, {user}, httpOptions);
  }
}
