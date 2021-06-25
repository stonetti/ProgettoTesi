import { Component, OnInit } from '@angular/core';
import * as FileSaver from 'file-saver';
import {startOfMonth, addDays, addMonths, isSameDay, lastDayOfMonth, subMonths} from 'date-fns'
import {Day} from "../model/day";
import {DateToString} from "../shared/utilities/dateToString";


@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})

export class CalendarComponent implements OnInit {
  months : string[] = ["Gennaio",
    "Febbraio",
    "Marzo",
    "Aprile",
    "Maggio",
    "Giugno",
    "Luglio",
    "Agosto",
    "Settembre",
    "Ottobre",
    "Novembre",
    "Dicembre"]

  yearRange : number[] = [];

  todayDate : Date = new Date();
  currentMonthDays = new Array<Day>();
  columns:string[]=[];
  rows:number[]=[];
  i = 0;
  focusedMonth : Date = this.todayDate;
  macro : String [] = [];

  constructor(public dateToString : DateToString) { }

  ngOnInit(): void {
    let j = 0;
    this.setColumns(this.focusedMonth);
    this.setRows();
   // this.macro = getMacro();
    let year = this.todayDate.getFullYear();
    let k =3;
    for(j; j<3; j++) {
      this.yearRange.push(year-k)
      k--;
    }
    for(j =0; j<3; j++) {
      this.yearRange.push(year+j)
    }
  }

  private setRows() {
    this.rows = [];
    this.columns = [];
    /*Imposta righe tabella*/
    for (let i=0; i<5;i++){
      this.columns.push(String.fromCharCode(65+i));
    }
    let gridSize = this.i;
    for (let i=0; i<gridSize;i++){
      this.rows.push(i+1);
    }
    this.i=0;
  }

  private setColumns(month : Date) {
    this.currentMonthDays = [];
    /*Imposta colonne tabella*/
    let firstDay = startOfMonth(month);
    let lastDay = addDays(lastDayOfMonth(month),1);
    while(!isSameDay(lastDay,firstDay)){
      this.currentMonthDays.push(new Day(this.dateToString.toDayName(firstDay.getDay()),firstDay.getDate(), firstDay));
      firstDay = addDays(firstDay,1);
      this.i++;
    }
  }



  say(s: string) {
    alert(s)
  }


  exportExcel() {
    import("xlsx").then(xlsx => {
      const worksheet = xlsx.utils.json_to_sheet(this.currentMonthDays);
      const workbook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
      const excelBuffer: any = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' });
      this.saveAsExcelFile(excelBuffer, "products");
    });
  }

  saveAsExcelFile(buffer: any, fileName: string): void {
    let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
    let EXCEL_EXTENSION = '.xlsx';
    const data: Blob = new Blob([buffer], {
      type: EXCEL_TYPE
    });
    FileSaver.saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
  }

  displayPreviousMonth() {
    this.focusedMonth  = subMonths(this.focusedMonth, 1)
    this.setColumns(this.focusedMonth);
    this.setRows();
  }

  displayNextMonth() {
    this.focusedMonth = addMonths(this.focusedMonth, 1);
    this.setColumns(this.focusedMonth);
    this.setRows();
  }

  displayCurrentMonth() {
    this.focusedMonth = new Date();
    this.setColumns(this.focusedMonth);
    this.setRows();
  }

  displaySelectedMonth(month:number){
    this.focusedMonth.setMonth(month)
    this.setColumns(this.focusedMonth);
    this.setRows();
  }

  displaySelectedYear(year:number){
    this.focusedMonth.setFullYear(year)
    this.setColumns(this.focusedMonth);
    this.setRows();
  }
}
