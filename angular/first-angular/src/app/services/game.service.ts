import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import { ErrorService } from "./error.service";
import {catchError, map, Observable, throwError} from "rxjs";
import {IInvite} from "../models/invite-model";
import {IGame} from "../models/game-model";

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private getAllGamesUrl = 'http://localhost:8081/get/all/games';
  private errorService: any;

  constructor(
    private http: HttpClient,
    private error: ErrorService
  ) { }

  getAll(): Observable<IGame[]> {
    return this.http.get<{ games: IGame[] }>(this.getAllGamesUrl, {
      params: new HttpParams({
        fromObject: {limit: 5}
      })
    }).pipe(
      map(response => response.games), // извлекаем массив пользователей из объекта ответа
      catchError(this.errorHandler.bind(this))
    );
  }

  private errorHandler(error: HttpErrorResponse) {
    console.error('An error occurred while getting invites:', error.message);
    this.errorService.handle(error.message)
    return throwError(() => error.message)
  }
}
