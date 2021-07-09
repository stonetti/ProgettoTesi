import { Component, OnInit } from '@angular/core';
import {AppComponent} from "../../app.component";
import {TokenStorageService} from "../../service/token-storage.service";
import {DbConnection} from "../../service/dbConnection";
import {User} from "../../model/user";
import {Macro} from "../../model/macro";
import {Crumb} from "../../model/crumb";
import {Activity} from "../../model/activity";
import {MacroComponent} from "../macro/macro.component";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  link :string = '/macro/'
  currentUser?: User;
  macroList : Macro [] = [];
  crumbList : Crumb[] = [];
  tableHeaders :  Map<string,string>  = new Map<string, string>();
  activitiesList : Activity [] = [];
  private errorMsg: string = '';
  private currentDisplay : number = 0;

  constructor(private appComponent : AppComponent, private token : TokenStorageService, private dbConnection : DbConnection) { }

  ngOnInit(): void {
    this.crumbList = [];
    this.getMacroList();
    this.currentUser = this.token.getUser();
  }

  getMacroList() {
    this.dbConnection.getMacroList().subscribe(
      data => {
        for(let doc in data) {
          this.macroList.push(data[doc]);
          this.setTableHeaders();
          this.setMacroCrumble();
        }},
      err => {
        this.errorMsg = err.error.message;
      })
  }

  private setMacroCrumble() {
    this.crumbList[this.currentDisplay] = {label: "Macro", url : "/macro", dropdownItems : this.macroList}
  }

  display($event:any){
    this.activitiesList = [];
   let selectedCrumb = $event.item;
   let selectedCrumbIndex =  $event.index;
   this.getNestedActivities(selectedCrumb);
   this.pageUpdate(selectedCrumb, selectedCrumbIndex);
  }

  private pageUpdate(selectedCrumb: Macro|Activity, selectedCrumbIndex: number) {
    if (selectedCrumbIndex >= this.currentDisplay) {
      this.currentDisplay++;
      if ('activities' in selectedCrumb)
        this.crumbList[this.currentDisplay] = {label: "Activity", url: "/macro", dropdownItems: this.activitiesList}
      else
        this.crumbList[this.currentDisplay] = {label: "Subactivity", url: "/macro", dropdownItems: this.activitiesList}
    } else {
      this.currentDisplay = selectedCrumbIndex;
      this.crumbList = this.crumbList.slice(0, selectedCrumbIndex + 2);
    }
  }

  private getNestedActivities(selectedCrumb: any) {
    if('activities' in selectedCrumb){
      selectedCrumb.activities.forEach((value : Activity ) =>
        this.activitiesList.push(value));
    }else {
      selectedCrumb.sub_activities.forEach((value: Activity) =>
        this.activitiesList.push(value))
    }
  }

  private setTableHeaders() {
    this.macroList.forEach(value =>
    this.tableHeaders.set(value.id, value.name))
  }
}
