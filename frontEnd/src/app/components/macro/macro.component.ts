import { Component, OnInit } from '@angular/core';
import{DbConnection} from "../../service/dbConnection";
import {Macro} from "../../model/macro";
import {Router} from "@angular/router";
import {User} from "../../model/user";

@Component({
  selector: 'app-macro',
  templateUrl: './macro.component.html',
  styleUrls: ['./macro.component.css']
})
export class MacroComponent implements OnInit {
  public macros: Macro[] = [];
  private errorMsg: string = '';
  pmInfos: User[] = [];

  constructor(public dbConnection : DbConnection, protected router: Router) { }

  ngOnInit(): void {
    this.getMacroList();
  }

  private getMacroList() {
    this.dbConnection.getMacroList().subscribe(
      data => {
        console.log(data);
        for(let doc in data) {
          this.macros.push(data[doc]);
        }
      },
      err => {
        this.errorMsg = err.error.message;
      }
    )
  }

}
