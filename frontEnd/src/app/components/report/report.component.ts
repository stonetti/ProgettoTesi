import { Component, OnInit } from '@angular/core';
import {AppComponent} from "../../app.component";
import {TokenStorageService} from "../../service/token-storage.service";
import {User} from "../../model/user";
import {DbConnection} from "../../service/dbConnection";
import {Macro} from "../../model/macro";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

  link :string = '/macro/'
  currentUser?: User;
  macro : Macro [] = [];
  private errorMsg: string = '';
  tableHeaders : Map<string,string> = new Map<string, string>();

  constructor(private appComponent : AppComponent,private token : TokenStorageService, private dbConnection : DbConnection) {
  }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.getMacroList();
  }


  getMacroList() {
    this.dbConnection.getMacroList().subscribe(
      data => {
        console.log(data);
        for(let doc in data) {
          this.macro.push(data[doc]);
          this.tableHeaders.set(data[doc].id, data[doc].name )
        }
      },
      err => {
        this.errorMsg = err.error.message;
      }
    )
  }

}
