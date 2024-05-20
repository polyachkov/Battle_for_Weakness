import { Injectable } from '@angular/core';
import {catchError, map, Observable, throwError} from "rxjs";
import {IInvite} from "../models/invite-model";
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {ErrorService} from "./error.service";
import {IUser} from "../models/user-model";
import {Card} from "../models/card-model";

@Injectable({
  providedIn: 'root'
})
export class InviteService {
  private getAllInvitesUrl: string = 'http://localhost:8081/get/all/invites';
  private inviteUserUrl: string = 'http://localhost:8081/invite/create';
  private acceptInviteUrl: string = 'http://localhost:8081/invite/accept';
  private rejectInviteUrl: string = 'http://localhost:8081/invite/delete';
  private getHandUrl: string = 'http://localhost:8081/get/hand';
  private errorService: any;
  constructor(
    private http: HttpClient,
    private error: ErrorService
  ) { }

  getAll(): Observable<IInvite[]> {
    return this.http.get<{ invites: IInvite[] }>(this.getAllInvitesUrl, {
      params: new HttpParams({
        fromObject: {limit: 5}
      })
    }).pipe(
      map(response => response.invites), // извлекаем массив пользователей из объекта ответа
      catchError(this.errorHandler.bind(this))
    );
  }

  inviteUser(invitedName: string, inviterFraction: string): Observable<any> {
    const body = { invited_name: invitedName, inviter_fraction: inviterFraction };

    return this.http.post(this.inviteUserUrl, body).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  acceptInvite(inviterName: string, invitedFraction: string): Observable<any> {
    const body = { inviter_name: inviterName, invited_fraction: invitedFraction };

    return this.http.post(this.acceptInviteUrl, body).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  rejectInvite(inviterName: string): Observable<any> {
    const body = { inviter_name: inviterName };

    return this.http.post(this.rejectInviteUrl, body).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  private errorHandler(error: HttpErrorResponse) {
    console.error('An error occurred while getting invites:', error.message);
    this.errorService.handle(error.message)
    return throwError(() => error.message)
  }
}
