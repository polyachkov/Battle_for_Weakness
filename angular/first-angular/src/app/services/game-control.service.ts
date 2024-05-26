import { Injectable } from '@angular/core';
import { GamePhases, GameState } from '../constants';
import {
  Turn,
  idMoneyCollectorPictures,
} from '../gamefield/constants';
import {catchError, map, Observable, throwError} from "rxjs";
import {Card} from "../models/card-model";
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {ErrorService} from "./error.service";
import {ICell} from "../models/cell-model";
import {Game} from "../models/game-model";
import {IStatus} from "../models/status-model";
import {ILibrary} from "../models/library-model";

@Injectable({
  providedIn: 'root',
})
export class GameControlService {
  currentPhase: GamePhases = GamePhases.PREPARING;
  isShowModal: boolean = false;
  cardPos: number[] = [-1, -1];

  // mySelectedFraction: Fractions | null = null;
  opponentSelectedFraction: string = 'Mountains';

  gameState: GameState = 0;

  secondPlayerIsReady: boolean = false;

  playButton: string = 'Play';
  opponentsState: string = 'Opponent is not ready';

  idMoneyCollectorPictures = idMoneyCollectorPictures;

  private id_game!: number;

  private getGameUrl: string = 'http://localhost:8081/get/game/byid';
  private getHandUrl: string = 'http://localhost:8081/get/hand';
  private getOppHandUrl: string = 'http://localhost:8081/get/opp/hand';
  private getFieldUrl: string = 'http://localhost:8081/get/field';
  private getStatusUrl: string = 'http://localhost:8081/get/status';
  private getOppStatusUrl: string = 'http://localhost:8081/get/opp/status';
  private putCardInCellUrl: string = 'http://localhost:8081/putCardInCell';
  private nextTurnUrl: string = 'http://localhost:8081/nextTurn';
  private takeTurnUrl: string = 'http://localhost:8081/takeTurn';
  private getLibrariesUrl: string = 'http://localhost:8081/get/libraries';

  getIdGame() {
    return this.id_game;
  }

  setIdGame(id_game: number) {
    this.id_game = id_game;
  }


  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) {
  }

  changeColor() {
    const colorForButton = document.getElementById('exampleButton');

    if (colorForButton != null) {
      this.opponentsState = 'Now opponent is ready';
      colorForButton.style.backgroundColor = 'green';
      this.playButton = 'Play';
      const loadCircle = document.getElementById('loadCircle');
      if (loadCircle != null) {
        loadCircle.style.visibility = 'hidden';
      }
    }
  }

  searchGame() {
    const loadCircle = document.getElementById('loadCircle');

    if (this.playButton == 'Play') {
      this.playButton = 'Find Game';

      if (loadCircle != null) {
        loadCircle.style.visibility = 'visible';
      }
    } else {
      this.playButton = 'Play';

      if (loadCircle != null) {
        loadCircle.style.visibility = 'hidden';
      }
    }
  }


  // handleFractionChoice(fractionName: Fractions) {
  //   this.mySelectedFraction = fractionName;
  // }

  changeSecondPlayerState() {
    this.secondPlayerIsReady = true;
  }

  handleChangeContent(current: GamePhases) {
    if (this.secondPlayerIsReady == true) {
      this.currentPhase = current;
      if (current == 'PREPARING') this.secondPlayerIsReady = false;
    } else {
    }
  }

  handleGameStarted() {
    if (this.secondPlayerIsReady == true) {
      this.gameState = 1;
    }
  }

  handleGameEnded() {
    this.opponentsState = 'Opponent is not ready';
    this.playButton = 'Play';
    this.gameState = 0;
  }

  // determineCard(row: number, cell: number) {
  //   if (field[row][cell].name === 0) {
  //     return;
  //   }
  //   this.cardPos = [row, cell];
  //   this.isShowModal = true;
  // }



  getCardImageUrl(row: number, cell: number) {
    let url = '';
    switch (row) {
      case 0:
      case 7:
        url = idMoneyCollectorPictures[cell];
        break;
      case 1:
      case 2:
        url = idMoneyCollectorPictures[cell];
        break;
      case 3:
      case 4:
        url = idMoneyCollectorPictures[cell];
        break;
      case 5:
      case 6:
        url = idMoneyCollectorPictures[cell];
        break;
    }
    return url;
  }

  getHand(id_game: string): Observable<Card[]> {
    const params = new HttpParams().set('id_game', id_game);
    return this.http.get<{ cards: Card[] }>(this.getHandUrl, { params }).pipe(
      map(response => response.cards),
      catchError(this.errorHandler.bind(this))
    );
  }

  getOppHand(id_game: string): Observable<number> {
    const params = new HttpParams().set('id_game', id_game);
    return this.http.get<{ cards_cnt: number}>(this.getOppHandUrl, { params }).pipe(
      map(response => response.cards_cnt),
      catchError(this.errorHandler.bind(this))
    );
  }

  getCells(id_game: string): Observable<ICell[]> {
    const params = new HttpParams().set('id_game', id_game);
    return this.http.get<{ cells: ICell[]}>(this.getFieldUrl, { params }).pipe(
      map(response => response.cells),
      catchError(this.errorHandler.bind(this))
    );
  }

  getGame(id_game: string): Observable<Game> {
    const params = new HttpParams().set('id_game', id_game);
    return this.http.get<Game>(this.getGameUrl, { params }).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  putCardInCell(gameId: number, cardId: number, cellId: number): Observable<any> {
    const body = { gameId: gameId, cardId: cardId, cellId: cellId };

    return this.http.post(this.putCardInCellUrl, body).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  getStatus(id_game: string, isOpponent: boolean): Observable<IStatus> {
    const params = new HttpParams().set('id_game', id_game);
    return this.http.get<{ status: IStatus }>(
      isOpponent ? this.getOppStatusUrl : this.getStatusUrl, { params }
    ).pipe(
      map(response => response.status),
      catchError(this.errorHandler.bind(this))
    );
  }

  nextTurn(gameId: string): Observable<any> {
    const body = { gameId: gameId };

    return this.http.post(this.nextTurnUrl, body).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  takeTurn(gameId: string, rarity: string): Observable<any> {
    const body = { gameId: gameId, rarity: rarity };

    return this.http.post(this.takeTurnUrl, body).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  getLibraries(id_game: string): Observable<ILibrary[]> {
    const params = new HttpParams().set('id_game', id_game);
    return this.http.get<{ libraries: ILibrary[]}>(this.getLibrariesUrl, { params }).pipe(
      map(response => response.libraries),
      catchError(this.errorHandler.bind(this))
    );
  }

  private errorHandler(error: HttpErrorResponse) {
    console.error('An error occurred while getting invites:', error.message);
    this.errorService.handle(error.message)
    return throwError(() => error.message)
  }


}
