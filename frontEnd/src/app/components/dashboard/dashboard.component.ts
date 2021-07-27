import { Component, OnInit } from '@angular/core';
import {AppComponent} from "../../app.component";
import {TokenStorageService} from "../../service/token-storage.service";
import {DbConnection} from "../../service/dbConnection";
import {User} from "../../model/user";
import {Macro} from "../../model/macro";
import {Crumb} from "../../model/crumb";
import {Activity} from "../../model/activity";
import {LoginComponent} from "../login/login.component";

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
  elenco: any;
  user : any;
  title: Crumb[] = [];
  userRole: string = '';

  constructor(private loginComponent : LoginComponent, private appComponent : AppComponent, private token : TokenStorageService, private dbConnection : DbConnection) { }

  ngOnInit(): void {
    this.crumbList = [];
    this.getMacroList();
    this.currentUser = this.token.getUser();
    this.userRole = this.loginComponent.getUserRole();
  }

  getMacroList() {
    this.dbConnection.getMacroList().subscribe(
      data => {
        for(let doc in data) {
          this.macroList.push(data[doc]);
          this.setMacroCrumble();
          // this.fillTable();
        }
        this.setTableHeaders(this.macroList);
        },
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
   this.getNestedActivities(selectedCrumb, selectedCrumbIndex);

  }

  private pageUpdate(selectedCrumb: Macro|Activity, selectedCrumbIndex: number) {
    if (selectedCrumbIndex >= this.currentDisplay) {
      this.currentDisplay++;
      if ('activities' in selectedCrumb){
        this.title[selectedCrumbIndex] = {label: selectedCrumb.name, url: "/macro/"+selectedCrumb.id, dropdownItems : []}
        this.crumbList[this.currentDisplay] = {label: "Activity", url: "/macro", dropdownItems: this.activitiesList}
        this.setTableHeaders(selectedCrumb.assignedUsers);
      }else{
        this.title[selectedCrumbIndex] = {label: selectedCrumb.name, url: "/activity/"+selectedCrumb.id, dropdownItems : []}
        this.crumbList[this.currentDisplay] = {label: "Subactivity", url: "/macro", dropdownItems: this.activitiesList}
        this.setTableHeaders(selectedCrumb.users);}
    } else {
      this.currentDisplay = selectedCrumbIndex+1;
      if('activities' in selectedCrumb) {
        this.setTableHeaders(selectedCrumb.assignedUsers);
        this.title = [];
        this.crumbList[this.currentDisplay] = {label: "Activity", url: "/macro", dropdownItems: this.activitiesList}
        this.title[selectedCrumbIndex] = {label: selectedCrumb.name, url: "/macro/"+selectedCrumb.id, dropdownItems : []}
      }
      else {
        this.crumbList[this.currentDisplay] = {label: "Subactivity", url: "/macro", dropdownItems: this.activitiesList}
        this.setTableHeaders(selectedCrumb.users);
        this.title[selectedCrumbIndex] = {label: selectedCrumb.name, url: "/activity/"+selectedCrumb.id, dropdownItems : []}
      }
      console.log("crumblist prima di slice : "+ this.crumbList)
      this.crumbList = this.crumbList.slice(0, selectedCrumbIndex + 2);
      console.log("crumblist dopo di slice : "+ this.crumbList);
      this.title = this.title.slice(0, selectedCrumbIndex + 1);
    }

  }

  private getNestedActivities(selectedCrumb: any, selectedCrumbIndex: any) {
    if('activities' in selectedCrumb){
      selectedCrumb.activities.forEach((value : Activity ) =>
        this.activitiesList.push(value));
    }else {
      if(selectedCrumb.sub_activities!=null) {
        selectedCrumb.sub_activities.forEach((value: Activity) =>
          this.activitiesList.push(value))
      }
    }
    this.pageUpdate(selectedCrumb, selectedCrumbIndex);
  }

  private setTableHeaders(list: any[]) {
    this.tableHeaders.clear();
    list.forEach(value =>
    this.tableHeaders.set(value.id, value.name))
  }

  private fillTable() {
    this.dbConnection.getWorkingHours(this.macroList[0].id, "60a4dbf61abef92da7a17a91").subscribe(
      data => {
        this.elenco = data;
        console.log(data)
      },
      err => {
        this.errorMsg = err.error.message;
      }
    );
  }
}
