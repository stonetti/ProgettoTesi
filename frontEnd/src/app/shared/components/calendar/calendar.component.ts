import { Component, OnInit } from '@angular/core';
import {NgbDateStruct, NgbCalendar} from '@ng-bootstrap/ng-bootstrap';
import {formatDistance, getDaysInMonth, subDays} from 'date-fns'


@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  date ?: NgbDateStruct;

  constructor( private ngbCalendar : NgbCalendar) { }

  ngOnInit(): void {
  }

  showDate() {
    var days = getDaysInMonth(new Date(2000, 0))
    alert(days)
  }


}
