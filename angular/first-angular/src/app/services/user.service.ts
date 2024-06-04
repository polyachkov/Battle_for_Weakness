import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {hostName} from "../constants";
import {Game} from "../models/game-model";
import {ErrorService} from "./error.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl             = hostName + 'api/test/user';
  private modUrl              = hostName + 'api/test/mod';
  private adminUrl            = hostName + 'api/test/admin';
  private getActiveUsersUrl   = hostName + 'api/websocket-sessions';
  private getActiveGamesUrl   = hostName + 'api/test/admin';

  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) { }

  getUserBoard(): Observable<string> {
    return this.http.get(this.userUrl, { responseType: 'text' });
  }

  getModBoard(): Observable<string> {
    return this.http.get(this.modUrl, { responseType: 'text' });
  }

  getAdminBoard(): Observable<string> {
    return this.http.get(this.adminUrl, { responseType: 'text' });
  }

  getActiveUsers() {
    return this.http.get<number>(this.getActiveUsersUrl, {}).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  getActiveGames() {
    return this.http.get<number>(this.getActiveGamesUrl, {}).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  private errorHandler(error: HttpErrorResponse) {
    console.error('An error occurred while getting invites:', error.message);
    this.errorService.handle(error.message)
    return throwError(() => error.message)
  }
}
