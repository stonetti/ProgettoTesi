import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserLogin} from "../model/UserLogin";

const MACRO_API_URL = 'http://localhost:8080/macro/';

@Injectable({
  providedIn: 'root'
})
export class DbConnection {

  constructor(private http: HttpClient) { }

  login(userLogin : UserLogin): Observable<any> {
    return this.http.post(MACRO_API_URL + 'list', userLogin);
  }

}
