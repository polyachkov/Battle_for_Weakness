import { Injectable } from '@angular/core';
import {GamePhases, GameState, hostName} from '../constants';
import {
  idMoneyCollectorPictures,
} from '../gamefield/constants';
import {BehaviorSubject, catchError, map, Observable, switchMap, throwError} from "rxjs";
import {Card} from "../models/card-model";
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {ErrorService} from "./error.service";
import {ICell} from "../models/cell-model";
import {Game} from "../models/game-model";
import {IStatus} from "../models/status-model";
import {ILibrary} from "../models/library-model";
import { Stomp } from "@stomp/stompjs";
import * as SockJS from "sockjs-client";
import {TokenStorageService} from "../auth/token-storage.service";


@Injectable({
  providedIn: 'root',
})
export class GameControlService {
  isShowModal: boolean = false;
  cardPos: number[] = [-1, -1];
  opponentSelectedFraction: string = 'Mountains';
  gameState: GameState = 0;
  playButton: string = 'Play';

  private getGameUrl: string            = hostName + 'get/game/byid';
  private getHandUrl: string            = hostName + 'get/hand';
  private getOppHandUrl: string         = hostName + 'get/opp/hand';
  private getFieldUrl: string           = hostName + 'get/field';
  private getStatusUrl: string          = hostName + 'get/status';
  private getOppStatusUrl: string       = hostName + 'get/opp/status';
  private putCardInCellUrl: string      = hostName + 'putCardInCell';
  private putCollectorInCellUrl: string = hostName + 'putCollectorInCell';
  private nextTurnUrl: string           = hostName + 'nextTurn';
  private takeTurnUrl: string           = hostName + 'takeTurn';
  private moveCombatUrl: string         = hostName + 'moveCombat';
  private getLibrariesUrl: string       = hostName + 'get/libraries';
  private moveCardUrl: string           = hostName + 'moveCard';
  private openRarityUrl: string         = hostName + 'openRarity';
  private socketUrl: string             = hostName + 'websocket';

  private gameSubject: BehaviorSubject<Game | null> = new BehaviorSubject<Game | null>(null);
  private handSubject: BehaviorSubject<Card[]> = new BehaviorSubject<Card[]>([]);

  private connected: boolean = false;
  private stompClient: any

  private token!: string;


  constructor(
    private http: HttpClient,
    private errorService: ErrorService,
    private tokenStorageService: TokenStorageService
  ) {
    this.initConnectionSocket();
  }

  initConnectionSocket() {
    this.token = this.tokenStorageService.getToken();

    if (!this.token) {
      console.log('Token not found...');
      return;
    }
    const socket = new SockJS(this.socketUrl);
    this.stompClient = Stomp.over(socket);

    console.log('Connecting to WebSocket...');
    this.stompClient.connect({}, (frame: any) => {
      console.log('Connected to WebSocket:', frame);
      this.connected = true;
    }, (error: any) => {
      console.error('WebSocket connection error:', error);
    });
  }

  // this.hand = this.gameControlService.getHand(this.id_game);
  // this.oppHand = this.gameControlService.getOppHand(this.id_game);
  // this.status = this.gameControlService.getStatus(this.id_game, false);
  // this.oppStatus = this.gameControlService.getStatus(this.id_game, true);
  // this.field = this.gameControlService.getCells(this.id_game).pipe(
  //   map((cells) => cells.sort((a, b) => a.cell_num - b.cell_num)), // Сортируем ячейки по cell_num
  //   switchMap((cells) =>
  //     this.checkReverse(this.username).pipe(
  //       map((isReverse) => this.transformField(cells, isReverse))
  //     )
  //   )
  // );
  // this.isMoving = false;
  // this.highlightCell = [];

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

  putCollectorInCell(gameId: number, cellId: number): Observable<any> {
    const body = { gameId: gameId, cellId: cellId };

    return this.http.post(this.putCollectorInCellUrl, body).pipe(
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

  moveCombat(gameId: string): Observable<any> {
    const body = { gameId: gameId };

    return this.http.post(this.moveCombatUrl, body).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  openRarity(game_id: string): Observable<any> {
    const body = { game_id: game_id };

    return this.http.post(this.openRarityUrl, body).pipe(
      catchError(this.errorHandler.bind(this))
    );
  }

  moveCard(gameId: string, cellId1: number, cellId2: number): Observable<any> {
    const body = { gameId: gameId, cellId1: cellId1, cellId2: cellId2 };

    return this.http.post(this.moveCardUrl, body).pipe(
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


  /**
   * Game
   * @param id_game
   */
  subscribeToGameUpdates(id_game: string) {
    if (!this.connected) {
      console.warn('WebSocket is not connected yet');
      return;
    }
    const topic = `/topic/${id_game}`;
    console.log(`Subscribing to ${topic}`);
    this.stompClient.subscribe(topic, (messages: any) => {
      console.log('Received message:', messages);
      const gameContent = JSON.parse(messages.body);
      console.log('Parsed game content:', gameContent);
      this.gameSubject.next(gameContent);
    });
  }

  sendGame(id_game: string) {
    this.stompClient.send(`/app/get/game/byId/${id_game}`, {});
  }

  getGameSubject(): Observable<Game | null> {
    return this.gameSubject.asObservable();
  }

  /**
   * Hand
   * @param id_game
   */
  // public subscribeToHandUpdates(id_game: string): void {
  //   const topic = `/topic/hand/${id_game}`;
  //   this.stompClient.subscribe(topic, (messages: any) => {
  //     console.log('Received message:', messages);
  //     const handContent = JSON.parse(messages.body);
  //     console.log('Parsed hand content:', handContent);
  //     this.handSubject.next(handContent);
  //   });
  // }
  //
  // sendHand(id_game: string) {
  //   this.stompClient.send(`/app/get/hand/${id_game}`, {});
  // }
  //
  // public getHandSubject(): Observable<Card[]> {
  //   return this.handSubject.asObservable();
  // }
}
