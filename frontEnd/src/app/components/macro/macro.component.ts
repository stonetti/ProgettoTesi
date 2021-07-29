import {Component, OnInit} from '@angular/core';
import{DbConnection} from "../../service/dbConnection";
import {Macro} from "../../model/macro";
import {Router} from "@angular/router";
import {User} from "../../model/user";
import {FormControl, FormGroup} from "@angular/forms";
import {LoginComponent} from "../login/login.component";

@Component({
  selector: 'app-macro',
  templateUrl: './macro.component.html',
  styleUrls: ['./macro.component.css']
})

export class MacroComponent implements OnInit {
  public macros: Macro[] = [];
  private errorMsg: string = '';
  pmInfos: User[] = [];
  allUsers: User[] = [];
  selectedUsers: User[] = [];
  validDate : Boolean = true;
  userRole !: string;
  myForm : FormGroup = new  FormGroup({
    inputDate: new FormControl(''),
    inputActivity : new FormControl(''),
    inputUsers : new FormControl(''),
    inputDescription : new FormControl(''),
    inputMacroName : new FormControl(''),
  })


  constructor(private loginComponent: LoginComponent, public dbConnection: DbConnection, protected router: Router) {
  }

  ngOnInit(): void {
    this.getMacroList();
    this.getUsersList();
    this.userRole = this.loginComponent.getUserRole();
  }

  private getMacroList() {
    this.dbConnection.getMacroList().subscribe(
      data => {
        for (let doc in data) {
          this.macros.push(data[doc]);
        }
      },
      err => {
        this.errorMsg = err.error.message;
      }
    )
  }


  private getUsersList() {
    this.dbConnection.getUsersList().subscribe(
      data => {
        this.allUsers =data.map(e=>{
          e.description = e.lastname + ' ' +e.name
          return e;
        });
        console.log(this.allUsers);
      },
      err => {
        this.errorMsg = err.error.message;
      }
    )
  }

  verifyExpDate(expDate: Event) {
    // @ts-ignore
    this.validDate = expDate.target.value as Date > new Date().toJSON().slice(0, 10);
  }


  resetForm() {
   this.myForm.reset();
    this.validDate = true;
  }

}
