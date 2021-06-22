import { Injectable } from '@angular/core';
import {User} from "../model/User";

const ACC_TOKEN_KEY = 'acc-auth-token';
const REF_TOKEN_KEY = 'ref-auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveAccessToken(token: string): void {
    window.sessionStorage.removeItem(ACC_TOKEN_KEY);
    window.sessionStorage.setItem(ACC_TOKEN_KEY, token);
  }

  public saveRefreshToken(token: string): void {
    window.sessionStorage.removeItem(REF_TOKEN_KEY);
    window.sessionStorage.setItem(REF_TOKEN_KEY, token);
  }

  public getToken(): string {
    return <string>sessionStorage.getItem(ACC_TOKEN_KEY);
  }

  public saveUser(user : User): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): User {
    return JSON.parse(<string>sessionStorage.getItem(USER_KEY));
  }
}
