import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {catchError, map, Observable, retry, tap, throwError} from "rxjs";
import {UInfo} from "../models/user-model";
import {ErrorService} from "./error.service";

@Injectable({
  providedIn: 'root'
})
export class UserSearchService {
  private getAllUsersUrl = 'http://localhost:8081/get/all/users';
  private inviteUserUrl = 'http://localhost:8081/invite/create';

  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) { }

  // getAll(): Observable<UInfo[]> {
  //   return this.http.get<UInfo[]>(this.getAllUsersUrl, {
  //     params: new HttpParams({
  //       fromObject: {limit: 5}
  //     })
  //   }).pipe(
  //     retry(2),
  //     catchError(this.errorHandler.bind(this))
  //   )
  // }

  getAll(): Observable<UInfo[]> {
    return this.http.get<{ users: UInfo[] }>(this.getAllUsersUrl, {
      params: new HttpParams({
        fromObject: {limit: 5}
      })
    }).pipe(
      map(response => response.users), // извлекаем массив пользователей из объекта ответа
      catchError(this.errorHandler.bind(this))
    );
  }

  inviteUser(inviterId: number, invitedId: number, inviterRace: string): Observable<any> {
    const body = { inviter_id: inviterId, invited_id: invitedId, inviter_race: inviterRace };

    return this.http.post(this.inviteUserUrl, body).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  private errorHandler(error: HttpErrorResponse) {
    console.error('An error occurred while fetching users:', error.message);
    this.errorService.handle(error.message)
    return throwError(() => error.message)
  }
}
