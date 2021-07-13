import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AppComponent} from "../../../app.component";
import {Router} from "@angular/router";
import {Macro} from "../../../model/macro";
import {Activity} from "../../../model/activity";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private appComponent : AppComponent, private router : Router) { }

  ngOnInit(): void {
  }

  logout(){
    this.appComponent.logout();
  }

  updateDashboard() {
    this.router.navigate(["/dashboard"]);
    //TODO: svuotare la breadcrumb al click?
  }
}
