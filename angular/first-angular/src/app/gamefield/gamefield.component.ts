import {
  booleanAttribute,
  Component,
  HostListener,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import {
  DynamicObject,
  field,
  idHandPictures,
  idMoneyCollectorPictures,
  idOppHandPictures,
  Turn,
} from './constants';
import { GameControlService } from '../services/game-control.service';
import { GamePhases, Pages } from '../constants';
import { PageContentService } from '../services/page-content.service';
import { Card } from '../models/card-model';
import {
  BehaviorSubject,
  filter,
  interval,
  map,
  Observable,
  of,
  Subscription,
  switchMap,
  take,
  tap,
} from 'rxjs';
import { ICell } from '../models/cell-model';
import { Game, IGame } from '../models/game-model';
import { TokenStorageService } from '../auth/token-storage.service';
import { IStatus } from '../models/status-model';

@Component({
  selector: 'app-gamefield',
  templateUrl: './gamefield.component.html',
  styleUrls: ['./gamefield.component.scss'],
})
export class GamefieldComponent implements OnInit, OnDestroy {
  id_game!: string;
  hand!: Observable<Card[]>;
  collectorId: number = 49;
  oppHand!: Observable<number>;
  game!: Observable<Game | null>;
  status!: Observable<IStatus>;
  oppStatus!: Observable<IStatus>;
  username: string = this.token.getUsername();
  isShowModal: boolean = false;
  isShowChooseRarity: boolean = false;
  cardPos: number[] = [-1, -1];
  isCombat: boolean = false;
  isMoving: boolean = false;
  turnTitle = 'End The Turn';
  combatTitle = 'Move To Combat';
  highlightCell: boolean[][] = [];
  subscriptions: Subscription[] = [];
  currentCellVal: ICell | null = null;
  reverseField: boolean = false;
  libCount: number = 1;

  field!: Observable<ICell[][]>;

  constructor(
    private route: ActivatedRoute,
    public gameControlService: GameControlService,
    public pageContentService: PageContentService,
    private token: TokenStorageService
  ) {
    this.route.params.subscribe((params) => {
      this.id_game = params['id'];
    });
  }

  ngOnInit(): void {
    this.gameControlService.subscribeToGameUpdates(this.id_game);
    this.gameListener();
    this.sendGame();

    // this.gameControlService.subscribeToHandUpdates(this.id_game);
    // this.handListener();
    // this.sendHand();

    this.initializeState();
  }

  private initializeState(): void {
    this.unsubscribeAll();
    this.hand = this.gameControlService.getHand(this.id_game);
    this.oppHand = this.gameControlService.getOppHand(this.id_game);
    this.status = this.gameControlService.getStatus(this.id_game, false);
    this.oppStatus = this.gameControlService.getStatus(this.id_game, true);
    this.field = this.gameControlService.getCells(this.id_game).pipe(
      map((cells) => cells.sort((a, b) => a.cell_num - b.cell_num)), // Сортируем ячейки по cell_num
      switchMap((cells) =>
        this.checkReverse(this.username).pipe(
          map((isReverse) => this.transformField(cells, isReverse))
        )
      )
    );
    this.isMoving = false;
    this.highlightCell = [];
  }

  /**
   * Game Listener.
   */
  gameListener() {
    this.game = this.gameControlService.getGameSubject();

    this.game.subscribe((game: Game | null) => {
      if (game != null) {
        console.log('Updating...', game);
        if (game.name_turn == this.token.getUsername()) {
          this.isShowChooseRarity = game.turn_ended;
        }
        this.updateTurnButton(game);
        this.updateCombatButton(game);
        console.log('Updated', game);
      }
    });
  }

  sendGame() {
    this.gameControlService.sendGame(this.id_game);
  }
  /**
   * Hand Listener.
   */
  // handListener() {
  //   this.hand = this.gameControlService.getHandSubject();
  //   this.hand.subscribe();
  // }
  //
  // sendHand() {
  //   this.gameControlService.sendHand(this.id_game);
  // }

  @HostListener('document:click', ['$event'])
  handleClickOutside(event: Event) {
    if (!(event.target as HTMLElement).closest('.cardField')) {
      this.highlightOff();
    }
  }

  ngOnDestroy(): void {
    this.unsubscribeAll();
  }

  unsubscribeAll(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
    this.subscriptions = [];
  }

  transformField(cells: ICell[], isReverse: boolean): ICell[][] {
    this.reverseField = isReverse;
    const rows: ICell[][] = [];
    for (let i = 0; i < cells.length; i += 8) {
      let row = cells.slice(i, i + 8);
      if (isReverse) {
        row = row.reverse();
      }
      rows.push(row);
    }
    return isReverse ? rows.reverse() : rows;
  }

  setLibCount(num: number): void {
    if (num > 0) {
      this.libCount = num;
    }
  }

  myLibraries: number[] = [0, 1, 2, 3, 4];
  oppsLibraries: number[] = [0, 1, 2, 3, 4];
  currentCard: number = 0;

  idHandPictures = idHandPictures;

  pickCard(row: number, cell: number, card: number) {
    this.currentCard = card;
  }
  pickCardByHand(card: number) {
    this.currentCard = card;
  }

  dropCard(row: number, cell_id: number, cell: ICell) {
    if (this.currentCard === 0 || this.isMoving) {
      return;
    }

    this.unsubscribeAll();
    if (this.currentCard == 49) {
      const subscription = this.gameControlService
        .putCollectorInCell(Number(this.id_game), cell.cell_num)
        .subscribe(
          (response) => {
            console.log('Card placed successfully', response);
            this.initializeState();
            this.currentCard = 0;
          },
          (error) => {
            console.error('Error placing card', error);
          }
        );
      this.subscriptions.push(subscription);
    } else {
      const subscription = this.gameControlService
        .putCardInCell(Number(this.id_game), this.currentCard, cell.cell_num)
        .subscribe(
          (response) => {
            console.log('Card placed successfully', response);
            this.initializeState();
            this.currentCard = 0;
          },
          (error) => {
            console.error('Error placing card', error);
          }
        );
      this.subscriptions.push(subscription);
    }
  }

  determineCard(row: number, cell: number) {
    this.cardPos = [row, cell];
    this.isShowModal = true;
  }

  showModal(): Observable<string> {
    return this.field.pipe(
      map((cells: ICell[][]) => {
        const imgNum = cells[this.cardPos[0]][this.cardPos[1]].id_card;
        return idHandPictures[imgNum];
      })
    );
  }

  getCurrentCell(): Observable<ICell> {
    return this.field.pipe(
      map((cells: ICell[][]) => {
        return cells[this.cardPos[0]][this.cardPos[1]];
      })
    );
  }

  getRowColor(index: number): DynamicObject<boolean> {
    const rowColorObj: DynamicObject<boolean> = {};
    switch (index) {
      case 1:
      case 2:
      case 5:
      case 6:
        rowColorObj['yellowCircle'] = true;
        break;
      case 2:
      case 7:
        rowColorObj['redSquare'] = true;
        break;
      case 3:
      case 4:
        rowColorObj['blackSquare'] = true;
        break;
      case 0:
        rowColorObj['blueSquare'] = true;
        break;
    }
    return rowColorObj;
  }

  checkReverse(username: string): Observable<boolean> {
    return this.game.pipe(
      filter((game) => game !== null), // Фильтруем ненулевые значения
      map((game) => game!.non_reverse !== username) // Добавляем оператор !, чтобы обойти TS18047
    );
  }

  handleTurn() {
    this.unsubscribeAll();
    const gameSubscription = this.game.subscribe((game: Game | null) => {
      if (game !== null) {
        this.updateTurnButton(game);
      }
    });
    this.subscriptions.push(gameSubscription);
    const subscription = this.gameControlService
      .nextTurn(this.id_game)
      .subscribe(
        (response) => {
          console.log('Turn transmitted successfully', response);
          this.initializeState();
          this.currentCard = 0;
        },
        (error) => {
          console.error('Error transmitting turn', error);
        }
      );
    this.subscriptions.push(subscription);
  }

  handleEndGame() {
    this.unsubscribeAll();
    const subscription = this.gameControlService
      .endGame(this.id_game)
      .subscribe(
        (response) => {
          console.log('Game ended successfully', response);
          this.initializeState();
          this.currentCard = 0;
        },
        (error) => {
          console.error('Error ending game', error);
        }
      );
    this.subscriptions.push(subscription);
  }

  handleCombat() {
    this.unsubscribeAll();
    const gameSubscription = this.game.subscribe((game: Game | null) => {
      if (game !== null) {
        this.updateCombatButton(game);
      }
    });
    this.subscriptions.push(gameSubscription);
    const subscription = this.gameControlService
      .moveCombat(this.id_game)
      .subscribe(
        (response) => {
          console.log('Move to combat successfully', response);
          this.initializeState();
          this.currentCard = 0;
        },
        (error) => {
          console.error('Error move to combat', error);
        }
      );
    this.subscriptions.push(subscription);
  }

  private updateTurnButton(game: Game): void {
    if (game.name_turn === this.token.getUsername()) {
      this.turnTitle = 'End The Turn';
    } else {
      this.turnTitle = "Opponent's Turn";
    }
  }

  private updateCombatButton(game: Game): void {
    if (game.name_turn === this.token.getUsername()) {
      if (game.is_fight_phase) {
        this.combatTitle = 'Combat Phase';
        this.isCombat = true;
      } else {
        this.combatTitle = 'Move To Combat';
        this.isCombat = false;
      }
    } else {
      if (game.is_fight_phase) {
        this.combatTitle = "Opponent's Combat";
        this.isCombat = false;
      } else {
        this.combatTitle = "Opponent's Turn";
        this.isCombat = false;
      }
    }
  }

  handleLibraryChoose(rarity: string): void {
    this.unsubscribeAll();
    this.isShowChooseRarity = false;
    const subscription = this.gameControlService
      .takeTurn(this.id_game, rarity)
      .subscribe(
        (response) => {
          console.log('Turn taken successfully', response);
          this.initializeState();
          this.currentCard = 0;
        },
        (error) => {
          console.error('Error taking turn', error);
        }
      );
    this.subscriptions.push(subscription);
  }

  handleOpenRarity() {
    this.unsubscribeAll();
    const subscription = this.gameControlService
      .openRarity(this.id_game)
      .subscribe(
        (response) => {
          console.log('Open rarity successfully', response);
          this.initializeState();
          this.currentCard = 0;
        },
        (error) => {
          console.error('Error open rarity', error);
        }
      );
    this.subscriptions.push(subscription);
  }

  determineStat(row: number, cell: number, card: Card | null) {
    if (!card) return '';
    switch (true) {
      case row == 0 && cell == 0:
        return card.name;
      case row == 0 && cell == 1:
        return;
      case row == 1 && cell == 0:
        return 'Atk: ' + card.attack;
      case row == 1 && cell == 1:
        return 'HP: ' + card.health;
      case row == 2 && cell == 0:
        return '$: ' + card.cost;
      case row == 2 && cell == 1:
        return 'Evs: ' + card.evasion;
      case row == 3 && cell == 0:
        return 'A_sp: ' + this.attackSpeedToString(card.attack_speed);
      case row == 3 && cell == 1:
        return 'M_sp: ' + card.movement_speed;

      default:
        return undefined;
    }
  }

  public attackSpeedToString(attack_speed: number): string {
    switch (attack_speed) {
      case 1:
        return 'Slow';
      case 2:
        return 'Medium';
      case 3:
        return 'Fast';
      default:
        return 'None';
    }
  }

  handleCellClick(
    cell: ICell,
    rowIndex: number,
    cellIndex: number,
    isNotOpponent: boolean
  ): void {
    if (this.isCombat && isNotOpponent) {
      console.log('Is combat and is not opponent:', rowIndex);
      console.log('Current Row', rowIndex);
      console.log('Current Cell:', cellIndex);
      this.initializeHighlightCell();
      const neighbors = [
        { row: rowIndex - 1, column: cellIndex }, // Верхняя ячейка
        { row: rowIndex + 1, column: cellIndex }, // Нижняя ячейка
        { row: rowIndex, column: cellIndex - 1 }, // Левая ячейка
        { row: rowIndex, column: cellIndex + 1 }, // Правая ячейка
      ];

      neighbors.forEach((neighbor) => {
        if (this.isValidCell(neighbor.row, neighbor.column)) {
          this.highlightCell[neighbor.row][neighbor.column] = true;
        }
      });
      let cardPosTmp: number[] = this.cardPos;
      this.cardPos = [rowIndex, cellIndex];

      console.log('Current Row', this.cardPos[0]);
      console.log('Current Cell:', this.cardPos[1]);

      this.currentCellVal = cell;
      if (
        this.isMoving &&
        cardPosTmp[0] == this.cardPos[0] &&
        cardPosTmp[1] == this.cardPos[1]
      ) {
        this.determineCard(rowIndex, cellIndex);
      }
      this.isMoving = true;
    } else {
      console.log('Is not combat or is opponent:', rowIndex);
      console.log('Current Row', rowIndex);
      console.log('Current Cell:', cellIndex);
      this.determineCard(rowIndex, cellIndex);
    }
  }

  private isValidCell(rowIndex: number, cellIndex: number) {
    return !(rowIndex < 0 || rowIndex > 6 || cellIndex < 0 || cellIndex > 7);
  }

  highlightOff() {
    this.highlightCell = [];
    this.isMoving = false;
  }

  private initializeHighlightCell() {
    // Очищаем массив highlightCell перед инициализацией
    this.highlightCell = [];

    // Создаем массив highlightCell размером 8 на 8 и заполняем его значениями false
    for (let i = 0; i < 8; i++) {
      const newRow: boolean[] = [];
      for (let j = 0; j < 8; j++) {
        newRow.push(false);
      }
      this.highlightCell.push(newRow);
    }
  }

  chechHighlight(rowIndex: number, cellIndex: number) {
    if (this.highlightCell.length > 1) {
      return this.highlightCell[rowIndex][cellIndex];
    }
    return false;
  }

  handleMoveCard(nextCell: ICell) {
    console.log('Move Card method');
    if (this.currentCellVal) {
      console.log('Current Cell Num:', this.currentCellVal.cell_num);
      console.log('Next Cell Num:', nextCell.cell_num);
      this.unsubscribeAll();
      const subscription = this.gameControlService
        .moveCard(this.id_game, this.currentCellVal.cell_num, nextCell.cell_num)
        .subscribe(
          (response) => {
            console.log('Card placed successfully', response);
            this.initializeState();
            this.currentCard = 0;
          },
          (error) => {
            console.error('Error placing card', error);
          }
        );
      this.subscriptions.push(subscription);
    }
  }

  protected readonly GamePhases = GamePhases;
  protected readonly Pages = Pages;
  protected readonly idOppHandPictures = idOppHandPictures;
  protected readonly Array = Array;
}
