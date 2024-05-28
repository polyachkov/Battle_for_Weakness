import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {catchError, map, Observable, retry, tap, throwError} from "rxjs";
import {IUser} from "../models/user-model";
import {ErrorService} from "./error.service";
import { hostName } from '../constants';

@Injectable({
  providedIn: 'root'
})
export class UserSearchService {
  private getAllUsersUrl = hostName + 'get/all/users';

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

  getAll(): Observable<IUser[]> {
    return this.http.get<{ users: IUser[] }>(this.getAllUsersUrl, {
      params: new HttpParams({
        fromObject: {limit: 5}
      })
    }).pipe(
      map(response => response.users), // извлекаем массив пользователей из объекта ответа
      catchError(this.errorHandler.bind(this))
    );
  }

  private errorHandler(error: HttpErrorResponse) {
    console.error('An error occurred while fetching users:', error.message);
    this.errorService.handle(error.message)
    return throwError(() => error.message)
  }
}
