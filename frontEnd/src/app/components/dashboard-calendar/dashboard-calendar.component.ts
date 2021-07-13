import {Component, Input, OnInit} from '@angular/core';
import {NgbCalendar, NgbDate, NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import * as FileSaver from "file-saver";
import {DateToString} from "../../shared/utilities/dateToString";
import {Day} from "../../model/day";
import {addDays, isSameDay, lastDayOfMonth, startOfMonth} from "date-fns";
import {Week} from "../../model/week";
import {Month} from "../../model/month";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-dashboard-calendar',
  templateUrl: './dashboard-calendar.component.html',
  styleUrls: ['./dashboard-calendar.component.css']
})
export class DashboardCalendarComponent implements OnInit {

  @Input () tableHeaders :Map<string,string> = new Map<string, string>();
  @Input() link :string = '';
  rows:number[]=[];
  i = 0;
  fromDate!: NgbDate;
  toDate : NgbDate | null | undefined;
  dashboardColumns: any[] = [];
  model ?: NgbDateStruct;
  daysCount : Day[]= [];
  columnRangeDisplay : boolean [] = [true, false, false, false];

  constructor(private calendar: NgbCalendar, private dateToString : DateToString) { }

  ngOnInit(): void {
    this.fromDate = this.calendar.getToday();
    this.toDate = this.calendar.getNext(this.fromDate);
    this.displayCurrentMonth();
    this.setRows();
  }

  private displayCurrentMonth() {
    let currentDate = new Date();
    let firstDay = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
    let lastDay = new Date( currentDate.getFullYear(), currentDate.getMonth()+1, 0)
    while(!isSameDay(lastDay,firstDay)){
      this.dashboardColumns.push(new Day(this.dateToString.toDayName(firstDay.getDay()),firstDay.getDate(), firstDay));
      firstDay = addDays(firstDay,1);
      this.i++;
    }
  }

  getNewToInput(data:any){
    this.fromDate = data.from;
    this.toDate= data.to;
    this.getSelectedDays();
  }

  private getSelectedDays() {
    this.daysCount = [];
    let firstDate = new Date(this.fromDate.year, this.fromDate.month - 1, this.fromDate.day);
     let dayName:string;
     let totalDays = 0;
     if(this.toDate){
       totalDays = (new Date(this.toDate.year+'-'+this.toDate.month+'-'+this.toDate.day).getTime()
        -new Date(this.fromDate.year+'-'+this.fromDate.month+'-'+this.fromDate.day).getTime())/(24*60*60*1000) +1}
    while(totalDays >0){
       dayName = this.dateToString.toDayName(firstDate.getDay());
       this.daysCount.push({dayName: dayName, dayNumber: firstDate.getDate(), fullDate: new Date(firstDate.getFullYear(), firstDate.getMonth(), firstDate.getDate()) })
      totalDays--;
      firstDate.setDate(firstDate.getDate()+1);
    }
    this.setColumns();
  }

  private setRows() {
    this.rows = [];
    for (let i=0; i<this.tableHeaders.size;i++){
      this.rows.push(i+1);
    }
    this.i=0;
  }

  private setColumns() {
    let lastIndex = this.daysCount.length-1;
    this.dashboardColumns = [];
    if (this.daysCount.length >= 30 && this.daysCount.length<90){
      this.columnRangeDisplay = [false,true,false,false]
   }
    else if (this.daysCount.length >= 90 && this.daysCount.length<1080) {
      let i = 0;
      let k = 0;
      this.columnRangeDisplay = [false, false, true, false]
      this.dashboardColumns.push({
        monthName: this.dateToString.toMonthName(this.daysCount[0].fullDate.getMonth()),
        monthNumber: this.daysCount[0].fullDate.getMonth() + 1
      })
      while (k < lastIndex) {
        while (this.daysCount[k].fullDate.getMonth() == this.daysCount[i].fullDate.getMonth()) {
          i++;
        }
        this.dashboardColumns.push({
          monthName: this.dateToString.toMonthName(this.daysCount[i].fullDate.getMonth()),
          monthNumber: this.daysCount[i].fullDate.getMonth() + 1
        })
        k=i;
      }
    }
    else if (this.daysCount.length >= 1080) {
      this.columnRangeDisplay = [false,false,false,true]
    }
    else {
      this.columnRangeDisplay = [true, false, false, false]
      this.dashboardColumns = this.daysCount;
    }
  }

  exportExcel() {
    import("xlsx").then(xlsx => {
      const worksheet = xlsx.utils.json_to_sheet(this.rows);
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

}
