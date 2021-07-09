import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from "rxjs";
import {TokenStorageService} from "../../service/token-storage.service";


@Injectable()
export class AuthGuard implements CanActivate {

  constructor(protected router: Router, protected token : TokenStorageService)
  {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    let isLoggedIn = !!this.token.getToken();
    if (!isLoggedIn) {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }
}
