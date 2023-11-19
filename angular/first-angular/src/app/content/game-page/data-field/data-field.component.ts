import { Component, numberAttribute } from '@angular/core';
import { GamePhases } from 'src/app/constants';
import { GameControlService } from 'src/app/services/game-control.service';

import {
  DynamicObject,
  idCommonFieldPictures,
  idHandPictures,
  idMoneyCollectorPictures,
  idMyFieldPictures,
  idOppsFieldPictures,
} from './constants';

@Component({
  selector: 'app-data-field',
  templateUrl: './data-field.component.html',
  styleUrls: ['./data-field.component.scss'],
})
export class DataFieldComponent {
  GamePhases = GamePhases;

  myHand: number[] = [1, 2, 2, 2, 1];
  opponentsHand: number[] = [1, 1, 1];
  currentCard: number = 0;

  field: number[][] = [
    [0, 0, 1, 1, 0, 0, 0, 0],
    [0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
  ];
  field1: number[][] = [
    [0, 0, 1, 1, 0, 0, 0, 0],
    [0, 1, 1, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
  ];

  idHandPictures = idHandPictures;
  idMoneyCollectorPictures = idMoneyCollectorPictures;
  idCommonFieldPictures = idCommonFieldPictures;
  idOppsFieldPictures = idOppsFieldPictures;
  idMyFieldPictures = idMyFieldPictures;

  constructor(public gameControlService: GameControlService) {}

  getCardImageUrl(index: number, cell: number) {
    let url = '';
    switch (index) {
      case 0:
      case 7:
        url = idMoneyCollectorPictures[cell];
        break;
      case 1:
      case 2:
        url = idOppsFieldPictures[cell];
        break;
      case 3:
      case 4:
        url = idCommonFieldPictures[cell];
        break;
      case 5:
      case 6:
        url = idMyFieldPictures[cell];
        break;
    }
    return url;
  }

  pickCard(row: number, cell: number, card: number) {
    this.currentCard = card;
  }
  pickCardByHand(card: number) {
    this.currentCard = card;
  }

  dropCard(row: number, cell: number) {
    // this.field[row][cell] = this.currentCard;
    this.field = this.field1;
  }

  noReturnPredicate() {
    return false;
  }

  getRowColor(index: number): DynamicObject<boolean> {
    const rowColorObj: DynamicObject<boolean> = {};
    switch (index) {
      case 0:
      case 7:
        rowColorObj['yellowCircle'] = true;
        break;
      case 1:
      case 2:
        rowColorObj['redSquare'] = true;
        break;
      case 3:
      case 4:
        rowColorObj['whiteSquare'] = true;
        break;
      case 5:
      case 6:
        rowColorObj['blueSquare'] = true;
        break;
    }
    return rowColorObj;
  }
}
