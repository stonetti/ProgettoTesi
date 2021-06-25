import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})

export class DateToString {

  constructor() {
  }
  public toDayName(day: number){
    switch (day) {
      case 0:
        return "DOM";
      case 1:
        return "LUN";
      case 2:
        return "MAR";
      case 3:
        return "MER";
      case 4:
        return "GIO";
      case 5:
        return "VEN";
      case 6:
        return "SAB";
      default : throw new Error("Day out of range!");
    }
  }

  public toMonthName(month: number){
    switch (month) {
      case 0:
        return "Gennaio";
      case 1:
        return "Febbraio";
      case 2:
        return "Marzo";
      case 3:
        return "Aprile";
      case 4:
        return "Maggio";
      case 5:
        return "Giugno";
      case 6:
        return "Luglio";
      case 7:
        return "Agosto"
      case 8:
        return "Settembre"
      case 9:
        return "Ottobre"
      case 10:
        return "Novembre"
      case 11:
        return "Dicembre"

      default : throw new Error("Month out of range!");
    }
  }
}
