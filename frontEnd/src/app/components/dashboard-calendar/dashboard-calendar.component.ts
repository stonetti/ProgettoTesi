import {Component, Input, OnInit} from '@angular/core';
import {NgbCalendar, NgbDate, NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import * as FileSaver from "file-saver";
import {DateToString} from "../../shared/utilities/dateToString";
import {Day} from "../../model/day";
import {addDays, isSameDay} from "date-fns";
import {DbConnection} from "../../service/dbConnection";
import {createLogErrorHandler} from "@angular/compiler-cli/ngcc/src/execution/tasks/completion";


@Component({
  selector: 'app-dashboard-calendar',
  templateUrl: './dashboard-calendar.component.html',
  styleUrls: ['./dashboard-calendar.component.css']
})
export class DashboardCalendarComponent implements OnInit {

  @Input () tableHeaders !:Map<string,string>;
  @Input() link :string = '';
  rows:number[]=[];
  i = 0;
  fromDate!: NgbDate;
  toDate : NgbDate | null | undefined;
  dashboardColumns: any[] = [];
  model ?: NgbDateStruct;
  daysCount : Day[]= [];
  workingHours !: Map<string, number[]>;
  totalAmountPerMacro : number[] = [];
  columnRangeDisplay : boolean [] = [true, false, false, false];
  totalSum: number = 0;
  totalPerDay: number[] = [];
  totalDays :number =  0;
  currentDate !: Date;

  constructor(private dbConnection: DbConnection, private calendar: NgbCalendar, private dateToString : DateToString) { }

  ngOnInit(): void {
    this.currentDate = new Date();
    this.fromDate = this.calendar.getToday();
    this.toDate = this.calendar.getNext(this.fromDate,"d",25);
    this.displayCurrentMonth();
    this.setRows();
    console.log("INNER: ", this.tableHeaders)
    this.getSelectedDays();

  }

  private displayCurrentMonth() {
    let firstDay = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth(), 1);
    let lastDay = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth()+1, 0)
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
    this.totalDays = 0;
    this.daysCount = [];
    let firstDate = new Date(this.fromDate.year, this.fromDate.month - 1, this.fromDate.day);
     let dayName:string;
     if(this.toDate){
       this.totalDays = (new Date(this.toDate.year+'-'+this.toDate.month+'-'+this.toDate.day).getTime()
        -new Date(this.fromDate.year+'-'+this.fromDate.month+'-'+this.fromDate.day).getTime())/(24*60*60*1000) +1
     }
    while(this.totalDays >0){
       dayName = this.dateToString.toDayName(firstDate.getDay());
       this.daysCount.push({dayName: dayName, dayNumber: firstDate.getDate(), fullDate: new Date(firstDate.getFullYear(), firstDate.getMonth(), firstDate.getDate()) })
      this.totalDays--;
      firstDate.setDate(firstDate.getDate()+1);
    }
    this.setColumns();
  }

  private setRows() {
    this.rows = [];
    for (let i=0; i<this.tableHeaders.size;i++){
      this.rows.push(i+1);
    }

  }

  private setColumns() {
    let lastIndex = this.daysCount.length-1;
    this.dashboardColumns = [];
    if (this.daysCount.length >= 30 && this.daysCount.length<90){
      this.columnRangeDisplay = [false,true,false,false]
      let i = 0;
      let k = 0;
      let weeksNo = Math.floor(this.daysCount.length/7);
      while(k<weeksNo-1){
        if(i+7<=lastIndex){
        this.dashboardColumns.push({firstDay: this.daysCount[i], lastDay:this.daysCount[i+7]})
        i = i+8;
        }else{
          this.dashboardColumns.push({firstDay: this.daysCount[i],  lastDay:this.daysCount[lastIndex]})
          i = i+8;
        }
        k++;
      }

      if(i<=lastIndex)
      this.dashboardColumns.push({firstDay: this.daysCount[i],  lastDay:this.daysCount[lastIndex]})

   }
    else if (this.daysCount.length >= 90 && this.daysCount.length<1095) {
      let i = 0;
      let k = 0;
      this.columnRangeDisplay = [false, false, true, false]
      this.dashboardColumns.push({
        monthName: this.dateToString.toMonthName(this.daysCount[k].fullDate.getMonth()),
        monthNumber: this.daysCount[k].fullDate.getMonth() + 1
      })
      while (k < lastIndex) {
        while (this.daysCount[k].fullDate.getMonth() == this.daysCount[i].fullDate.getMonth()) {
          i++;
        }
        k=i;
        this.dashboardColumns.push({
          monthName: this.dateToString.toMonthName(this.daysCount[k].fullDate.getMonth()),
          monthNumber: this.daysCount[k].fullDate.getMonth() + 1
        })

      }
    }
    else if (this.daysCount.length >= 1095) {
      this.columnRangeDisplay = [false,false,false,true];
      let i = 0;
      let k = 0;
      this.dashboardColumns.push({fullYear: this.daysCount[k].fullDate.getFullYear()})
      while (k < lastIndex) {
        while (this.daysCount[k].fullDate.getFullYear() == this.daysCount[i].fullDate.getFullYear()) {
          i++;
        }
        k=i;
        this.dashboardColumns.push({fullYear: this.daysCount[k].fullDate.getFullYear()})

      }
    }
    else {
      this.columnRangeDisplay = [true, false, false, false]
      this.dashboardColumns = this.daysCount;
    }
    this.displayReports(this.daysCount[0].fullDate, this.daysCount[lastIndex].fullDate);
  }

  displayReports(from: any, to: any){
    this.totalAmountPerMacro = [];
    this.totalSum = 0;
    this.totalPerDay = [];
    this.workingHours = new Map<string, number[]>();
    let hoursPerPeriodArray : number[] =  [];
    let calendarRange = this.daysCount;
    let i = 0;
    console.log("tableHeaders prima di keys: " + this.tableHeaders)
    let keys =  this.tableHeaders.keys() ;

    for (let l = 0; l<keys.length; l++) {
      this.workingHours.set(keys[l], hoursPerPeriodArray);

      this.dbConnection.getMacroHours(keys[l], from, to).subscribe(
        data => {

          let n = 0;
          hoursPerPeriodArray = [];

          if (!this.workingHours.get(keys[l]))
            this.workingHours.set(keys[i], hoursPerPeriodArray);

          if (data.length == 0) {

            while (n < calendarRange.length) {
              hoursPerPeriodArray[n] = 0;
              n++;
            }
            this.workingHours.set(keys[i], hoursPerPeriodArray);
          } else {
            let j = 0;
            data.forEach(element => {

              while (j < calendarRange.length) {
                calendarRange[j].fullDate.setDate(calendarRange[j].fullDate.getDate() + 1);
                if (element.id != calendarRange[j].fullDate.toJSON().slice(0, 10)) {
                  hoursPerPeriodArray[j] = 0;
                  this.workingHours.set(keys[i], hoursPerPeriodArray);
                } else {
                  hoursPerPeriodArray[j] = element.totalAmount;
                  this.workingHours.set(keys[i], hoursPerPeriodArray);
                }
                j++;
              }
            })
          }

          let sum = 0;
          for (let k = 0; k < calendarRange.length; k++) {
            sum = sum + hoursPerPeriodArray[k];
          }
          this.totalAmountPerMacro[i] = sum;
          this.totalSum += this.totalAmountPerMacro[i];
          i++;
        })
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
