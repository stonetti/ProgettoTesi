import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputComponent } from './shared/components/input/input.component';
import { HttpClientModule } from '@angular/common/http';
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './home/home.component';
import {AuthGuard} from "./shared/utilities/authGuard";
import { NavbarComponent } from './shared/components/navbar/navbar.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CalendarComponent } from './calendar/calendar.component';
import { MacroComponent } from './macro/macro.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {MatSelectModule} from "@angular/material/select";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    InputComponent,
    ProfileComponent,
    HomeComponent,
    NavbarComponent,
    CalendarComponent,
    MacroComponent,

  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    NoopAnimationsModule,
    MatSelectModule
  ],
  providers: [AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
