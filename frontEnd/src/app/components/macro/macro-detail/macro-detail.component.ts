import { Component, OnInit } from '@angular/core';
import {Macro} from "../../../model/macro";
import {ActivatedRoute} from "@angular/router";
import {DbConnection} from "../../../service/dbConnection";
import {Crumb} from "../../../model/crumb";
import {Activity} from "../../../model/activity";


@Component({
  selector: 'app-macro-detail',
  templateUrl: './macro-detail.component.html',
  styleUrls: ['./macro-detail.component.css']
})
export class MacroDetailComponent implements OnInit {
  // @ts-ignore: Object is possibly 'null'.
  public macro : Macro;
  private id : string | null = '';
  private errorMsg: string = '';
  public assignedUsers : Map<string,string> = new Map<string, string>();
  crumbs : Crumb[] = [];
  activities : Activity[] = [];
  activityCrumb ?: Crumb;
  macroCrumb?: Crumb;
  macros : Macro[] = [];

  constructor(private route: ActivatedRoute, private dbConnection : DbConnection) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getMacro();
    this.getMacroList();
  }

  private setActivities() {
    this.macro.activities.forEach((activity) => {
      this.activities.push(activity);
    })
    this.activityCrumb = {label : "Activities", url : "/activities", dropdownItems: this.activities}
    if (this.activityCrumb) {
      this.crumbs?.push(this.activityCrumb);
    }
  }

  getMacro() {
    if (this.id != null) {
      this.dbConnection.getMacro(this.id).subscribe(
        data => {
          this.macro = data as Macro;
          this.setUsers();
          //this.getWorkingHours();
          // this.setActivities();
        },
        err => {
          this.errorMsg = err.error.message;
        }
      )
    }
  }

  getMacroList() {
    this.dbConnection.getMacroList().subscribe(
      data => {
        for(let doc in data) {
          this.macros.push(data[doc]);
          this.setMacroCrumble();
        }
      },
      err => {
        this.errorMsg = err.error.message;
      }
    )
  }

  private setMacroCrumble() {
    this.macroCrumb = {label: "Macro", url : "/macro", dropdownItems : this.macros}
  }


  private getWorkingHours() {
    this.dbConnection.getWorkingHours(this.macro?.id, '60a3dfb6ae5e58323c432b2b').subscribe(
      data =>{
        console.log("Get W HOURS: " + data[0].totalAmount);
      },
      err => {
        this.errorMsg = err.error.message;
      }
    )
  }

  private setUsers() {
    this.macro.subAssignedUsers.forEach((user) => {
      this.assignedUsers.set(user.id, user.lastname + ' ' + user.name);
    })
  }

}
