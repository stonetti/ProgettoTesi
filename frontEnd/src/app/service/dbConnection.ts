import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import{HoursSum} from "../model/hourSum";

const MACRO_API_URL = 'http://localhost:8080/macro/';
const REPORT_API_URL = 'http://localhost:8080/reports/';

@Injectable({
  providedIn: 'root'
})
export class DbConnection {

  constructor(private http: HttpClient) { }

  getMacroList():  Observable<any> {
    return this.http.post(MACRO_API_URL + 'list', {});
  }

  getMacro(id: string) {
    return this.http.get(MACRO_API_URL + id);
  }

  getWorkingHours(macroId: string | undefined, userId: string) : Observable<any>{
    return this.http.get(REPORT_API_URL + 'total_amount/'+ macroId + '/' + userId);
  }

  getMacroHours(macroId: string | undefined, from: Date, to: Date){
    return this.http.get<HoursSum[]>(REPORT_API_URL + 'total_macro_amount/'+ macroId + '/' + from + '/' + to);
  }
}
