import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './components/login/login.component'
import {ReportComponent} from "./components/report/report.component";
import {AuthGuard} from "./shared/utilities/authGuard";
import {AppComponent} from "./app.component";
import {ProfileComponent} from "./components/profile/profile.component"
import {MacroComponent} from "./components/macro/macro.component";
import {MacroDetailComponent} from "./components/macro/macro-detail/macro-detail.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";

const routes: Routes = [
  {path: 'login', component : LoginComponent},
  { path: '', component: AppComponent, canActivate: [AuthGuard] },
  {path: 'report', component: ReportComponent, canActivate: [AuthGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'macro', component: MacroComponent, canActivate: [AuthGuard]},
  {path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
  {path: 'macro/:id', component: MacroDetailComponent,canActivate: [AuthGuard]},
  {path: 'activities/:id', component: MacroComponent, canActivate: [AuthGuard]}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
