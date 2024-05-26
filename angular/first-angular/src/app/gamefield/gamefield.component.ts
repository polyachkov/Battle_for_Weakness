import {booleanAttribute, Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";


import {DynamicObject, field, idHandPictures, idMoneyCollectorPictures, idOppHandPictures, Turn} from './constants';
import {GameControlService} from "../services/game-control.service";
import {GamePhases, Pages} from "../constants";
import {PageContentService} from "../services/page-content.service";
import {Card} from "../models/card-model";
import {interval, map, Observable, of, Subscription, switchMap, tap} from "rxjs";
import {ICell} from "../models/cell-model";
import {Game, IGame} from "../models/game-model";
import {TokenStorageService} from "../auth/token-storage.service";
import {IStatus} from "../models/status-model";

@Component({
  selector: 'app-gamefield',
  templateUrl: './gamefield.component.html',
  styleUrls: ['./gamefield.component.scss']
})
export class GamefieldComponent implements OnInit, OnDestroy {
  id_game!: string;
  hand!: Observable<Card[]>;
  collectorId: number = 49;
  oppHand!: Observable<number>;
  game!: Observable<Game>;
  status!: Observable<IStatus>;
  oppStatus!: Observable<IStatus>;
  username: string = this.token.getUsername();
  isShowModal: boolean = false;
  isShowChooseFraction: boolean = false;
  cardPos: number[] = [-1, -1];
  turn: Turn = 0;
  turnTitle = 'End the Turn';
  private subscription!: Subscription;

  handCardId!: number[];
  field!: Observable<ICell[][]>;

  constructor(
    private route: ActivatedRoute,
    public gameControlService: GameControlService,
    public pageContentService: PageContentService,
    private token: TokenStorageService
  ) {
    this.route.params.subscribe(params => {
      this.id_game = params['id'];
      gameControlService.setIdGame(Number(this.id_game))
    });
  }

  ngOnInit(): void {
    this.game = interval(1000).pipe(
      switchMap(() => this.gameControlService.getGame(this.id_game)),
      tap((game: Game) => {
        if(game.name_turn == this.token.getUsername()) {
          this.isShowChooseFraction = game.turn_ended;
        }
      })
    );
    this.subscription = this.game.subscribe(
      () => {},
      (error) => {
        console.error('Error fetching game:', error);
      }
    );

    this.initializeState();
  }

  ngOnDestroy(): void {
    // Отписка от интервала при уничтожении компонента
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  transformField(cells: ICell[], isReverse: boolean): ICell[][] {
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
    if (this.currentCard === 0) {
      return;
    }

    if (this.currentCard == 49) {
      this.gameControlService.putCollectorInCell(Number(this.id_game), cell.cell_num).subscribe(
        response => {
          console.log('Card placed successfully', response);
          this.initializeState();
          this.currentCard = 0;
        },
        error => {
          console.error('Error placing card', error);
        }
      );
    } else {
      this.gameControlService.putCardInCell(Number(this.id_game), this.currentCard, cell.cell_num).subscribe(
        response => {
          console.log('Card placed successfully', response);
          this.initializeState();
          this.currentCard = 0;
        },
        error => {
          console.error('Error placing card', error);
        }
      );
    }
  }

  private updateField(row: number, cell_id: number) : void{
    this.field.pipe(
      map(field => {
        // Создаем копию текущего поля
        const newField = field.map(row => row.slice());
        newField[row][cell_id].id_card = this.currentCard;
        return newField;
      })
    ).subscribe(updatedField => {
      // Обновляем поле с новым значением
      this.field = of(updatedField);
    });
  }

  private initializeState(): void {
    this.hand = this.gameControlService.getHand(this.id_game);
    this.oppHand = this.gameControlService.getOppHand(this.id_game);
    this.status = this.gameControlService.getStatus(this.id_game, false);
    this.oppStatus = this.gameControlService.getStatus(this.id_game, true);
    this.field = this.gameControlService.getCells(this.id_game).pipe(
      map(cells => cells.sort((a, b) => a.cell_num - b.cell_num)), // Сортируем ячейки по cell_num
      switchMap(cells => this.checkReverse(this.username).pipe(
        map(isReverse => this.transformField(cells, isReverse))
      ))
    );
  }

  determineCard(row: number, cell: number) {
    this.cardPos = [row, cell];
    this.isShowModal = true;
  }

  showModal(): Observable<string> {
    return this.field.pipe(
      map((cells: ICell[][]) => {
        const imgNum = cells[this.cardPos[0]][this.cardPos[1]].id_card;
        return idMoneyCollectorPictures[imgNum];
      })
    );
  }

  currentCell(): Observable<ICell> {
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
      map(game => game.non_reverse !== username)
    );
  }

  handleTurn() {
    if (this.turn === 0) {
      this.turnTitle = "Opponent's Turn";
      this.turn = 1;
    } else {
      this.turnTitle = 'End the Turn';
      this.turn = 0;
    }
    this.gameControlService.nextTurn(this.id_game).subscribe(
      response => {
        console.log('Turn transmitted successfully', response);
        this.initializeState();
        this.currentCard = 0;
      },
      error => {
        console.error('Error transmitting turn', error);
      }
    );
  }

  handleLibraryChoose(rarity: string): void {
    this.isShowChooseFraction = false;
    this.gameControlService.takeTurn(this.id_game, rarity).subscribe(
      response => {
        console.log('Turn taken successfully', response);
        this.initializeState();
        this.currentCard = 0;
      },
      error => {
        console.error('Error taking turn', error);
      }
    );
  }

  determineStat(row: number, cell: number, card: Card | null) {
    if (!card) return '';
    switch (true) {
      case row == 0 && cell == 0:
        return card.name;
      case row == 0 && cell == 1:
        return;
      case row == 1 && cell == 0:
        return 'Attack: ' + card.attack;
      case row == 1 && cell == 1:
        return 'Health: ' + card.health;
      case row == 2 && cell == 0:
        return 'Cost: ' + card.cost;
      case row == 2 && cell == 1:
        return 'Evasion: ' + card.evasion;
      case row == 3 && cell == 0:
        return 'Attack_speed: ' + this.attackSpeedToString(card.attack_speed);
      case row == 3 && cell == 1:
        return 'Movement_speed: ' + card.movement_speed;

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

  protected readonly GamePhases = GamePhases;
  protected readonly Pages = Pages;
  protected readonly idOppHandPictures = idOppHandPictures;
  protected readonly Array = Array;
}
