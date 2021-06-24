import { Component, OnInit } from '@angular/core';
import {startOfMonth, getDaysInMonth, addDays, isSameDay, lastDayOfMonth,} from 'date-fns'
import {Day} from "../../../model/day";

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})

export class CalendarComponent implements OnInit {

  todayDate : Date = new Date();
  currentMonthDays = new Array<Day>();
  columns:string[]=[];
  rows:number[]=[];

  constructor() { }

  ngOnInit(): void {
    /*Imposta colonne tabella*/
    let i = 0;
    let dayToInsert = startOfMonth(this.todayDate);
    var result = addDays(lastDayOfMonth(this.todayDate),1);
    while(!isSameDay(result,dayToInsert)){
      this.currentMonthDays.push(new Day(this.toDayName(dayToInsert.getDay()),dayToInsert.getDate(), dayToInsert));
      dayToInsert = addDays(dayToInsert,1);
      i++;
    }

    /*Imposta righe tabella*/

    for (let i=0; i<5;i++){
      this.columns.push(String.fromCharCode(65+i));
    };
    let gridSize = i;
    for (let i=0; i<gridSize;i++){
      this.rows.push(i+1);
    }

  }

  toDayName(day: number){
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


  say(s: string) {
    alert(s)
  }
}
