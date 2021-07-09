import {Component, Input,  EventEmitter, OnInit, Output} from '@angular/core';
import {Crumb} from "../../../model/crumb";
import {Activity} from "../../../model/activity";
import {Macro} from "../../../model/macro";

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.css']
})
export class BreadcrumbComponent implements OnInit {
  @Input () crumbs ?:Crumb[];
  @Output() dropdownClick = new EventEmitter ();
  constructor() { }

  ngOnInit(): void {
  }

  display(item: Macro|Activity, index:number) {
    this.dropdownClick.emit({item: item, index:index});
  }

  updateDashboard() {
    window.location.reload();
  }
}
