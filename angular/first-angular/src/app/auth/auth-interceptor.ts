import {HTTP_INTERCEPTORS, HttpEvent} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';

import { TokenStorageService } from './token-storage.service';
import {Observable} from "rxjs";

const TOKEN_HEADER_KEY = 'Authorization';

// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {
//
//   constructor(private token: TokenStorageService) { }
//
//   intercept(req: HttpRequest<any>, next: HttpHandler) {
//     let authReq = req;
//     const token = this.token.getToken();
//     if (token != null) {
//       authReq = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, token) });
//     }
//     return next.handle(authReq);
//   }
// }
//

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private tokenStorageService: TokenStorageService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.tokenStorageService.getToken();
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    return next.handle(req);
  }
}

export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
];
