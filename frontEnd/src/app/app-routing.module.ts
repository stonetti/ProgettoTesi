import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './login/login.component'
import{PwdRecoveryComponent} from "./pwd-recovery/pwd-recovery.component";

const routes: Routes = [
  { path: '', component: LoginComponent},
  {path: 'password-recovery', component: PwdRecoveryComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
