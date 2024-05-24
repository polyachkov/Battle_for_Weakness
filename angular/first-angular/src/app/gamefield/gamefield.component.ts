import {booleanAttribute, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";


import {DynamicObject, field, idHandPictures, idMoneyCollectorPictures, idOppHandPictures} from './constants';
import {GameControlService} from "../services/game-control.service";
import {GamePhases, Pages} from "../constants";
import {PageContentService} from "../services/page-content.service";
import {Card} from "../models/card-model";
import {map, Observable, of, switchMap, tap} from "rxjs";
import {ICell} from "../models/cell-model";
import {Game, IGame} from "../models/game-model";
import {TokenStorageService} from "../auth/token-storage.service";
import {IStatus} from "../models/status-model";

@Component({
  selector: 'app-gamefield',
  templateUrl: './gamefield.component.html',
  styleUrls: ['./gamefield.component.scss']
})
export class GamefieldComponent implements OnInit {
  id_game!: string;
  hand!: Observable<Card[]>;
  oppHand!: Observable<number>;
  game!: Observable<Game>;
  status!: Observable<IStatus>;
  oppStatus!: Observable<IStatus>;
  username: string = this.token.getUsername();
  isShowModal: boolean = false;
  cardPos: number[] = [-1, -1];

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
    this.game = this.gameControlService.getGame(this.id_game);
    this.initializeState();
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

  protected readonly GamePhases = GamePhases;
  protected readonly Pages = Pages;
  protected readonly idOppHandPictures = idOppHandPictures;
  protected readonly Array = Array;
}
