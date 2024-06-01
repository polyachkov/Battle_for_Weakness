import { Injectable } from '@angular/core';
import {GamePhases, GameState, hostName} from '../constants';
import {
  Turn,
  idMoneyCollectorPictures,
} from '../gamefield/constants';
import {BehaviorSubject, catchError, map, Observable, throwError} from "rxjs";
import {Card} from "../models/card-model";
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {ErrorService} from "./error.service";
import {ICell} from "../models/cell-model";
import {Game} from "../models/game-model";
import {IStatus} from "../models/status-model";
import {ILibrary} from "../models/library-model";
import {WebSocketSubject} from "rxjs/internal/observable/dom/WebSocketSubject";
import {webSocket} from "rxjs/webSocket";
import { Stomp } from "@stomp/stompjs";
import * as SockJS from "sockjs-client";


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
  private socketUrl: string             = '//localhost:8081/websocket';

  private gameSubject: BehaviorSubject<Game | null> = new BehaviorSubject<Game | null>(null);


  getIdGame() {
    return this.id_game;
  }

  setIdGame(id_game: number) {
    this.id_game = id_game;
  }

  private stompClient: any

  constructor(
    private http: HttpClient,
    private errorService: ErrorService
  ) {
    this.initConnectionSocket();
  }

  initConnectionSocket() {
    const socket = new SockJS(this.socketUrl);
    this.stompClient = Stomp.over(socket);
    // this.stompClient.connect({}, () => {
    //   console.log('Connected to WebSocket');
    // });
    this.stompClient.connect({}, (frame: any) => {
      console.log('Connected to WebSocket:', frame);
      const topic = `/topic/1`;
      console.log(`Subscribing to ${topic}`);
      this.stompClient.subscribe(topic, (messages: any) => {
        console.log('Received message:', messages);
        const gameContent = JSON.parse(messages.body);
        console.log('Parsed game content:', gameContent);
        this.gameSubject.next(gameContent);
      });
    }, (error: any) => {
      console.error('WebSocket connection error:', error);
    });
  }

  sendGetGameByIdMessage(id_game: string) {
    const message = { id_game };
    this.stompClient.publish({
      destination: `/app/get/game/byId/${id_game}`,
      body: JSON.stringify(message)
    });
  }

  sendGame(id_game: string, gameId: string) {
    this.stompClient.send(`/app/get/game/byId/${id_game}`, {}, JSON.stringify(gameId))
  }

  // subscribeToGameUpdates(id_game: string, callback: (message: any) => void) {
  //   this.stompClient.subscribe(`/topic/${id_game}`, (message: any) => {
  //     callback(JSON.parse(message.body));
  //   });
  // }

  subscribeToGameUpdates(id_game: string) {
    this.stompClient.connect({}, (frame: any) => {
      console.log('Connected to WebSocket:', frame);
      const topic = `/topic/${id_game}`;
      console.log(`Subscribing to ${topic}`);
      this.stompClient.subscribe(topic, (messages: any) => {
        console.log('Received message:', messages);
        const gameContent = JSON.parse(messages.body);
        console.log('Parsed game content:', gameContent);
        this.gameSubject.next(gameContent);
      });
    }, (error: any) => {
      console.error('WebSocket connection error:', error);
    });
  }

  // joinRoom(roomId: string) {
  //   this.stompClient.connect({}, ()=>{
  //     this.stompClient.subscribe(`/topic/${roomId}`, (messages: any) => {
  //       const messageContent = JSON.parse(messages.body);
  //       const currentMessage = this.messageSubject.getValue();
  //       currentMessage.push(messageContent);
  //
  //       this.messageSubject.next(currentMessage);
  //
  //     })
  //   })
  // }

  getGameSubject(): Observable<Game | null> {
      return this.gameSubject.asObservable();
  }
  //
  // getGameById(id_game: number) {
  //   this.stompClient.send(`/app/get/game/byId/${id_game}`);
  // }

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


}
