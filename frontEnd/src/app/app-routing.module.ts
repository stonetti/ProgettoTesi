import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './login/login.component'
import {HomeComponent} from "./home/home.component";
import {AuthGuard} from "./shared/utilities/authGuard";
import {AppComponent} from "./app.component";
import {ProfileComponent} from "./profile/profile.component"

const routes: Routes = [
  {path: 'login', component : LoginComponent},
  { path: '', component: AppComponent, canActivate: [AuthGuard] },
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
