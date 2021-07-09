import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputComponent } from './shared/components/input/input.component';
import { HttpClientModule } from '@angular/common/http';
import { ProfileComponent } from './components/profile/profile.component';
import { ReportComponent } from './components/report/report.component';
import {AuthGuard} from "./shared/utilities/authGuard";
import { NavbarComponent } from './shared/components/navbar/navbar.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CalendarComponent } from './components/calendar/calendar.component';
import { MacroComponent } from './components/macro/macro.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {AuthInterceptor} from "./shared/utilities/auth.interceptor";
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import { MacroDetailComponent } from './components/macro/macro-detail/macro-detail.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import{BreadcrumbComponent} from "./shared/components/breadcrumb/breadcrumb.component";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    InputComponent,
    ProfileComponent,
    ReportComponent,
    NavbarComponent,
    CalendarComponent,
    MacroComponent,
    MacroDetailComponent,
    DashboardComponent,
    BreadcrumbComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    NoopAnimationsModule,
  ],
  providers: [AuthGuard,  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
