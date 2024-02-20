import { Component } from '@angular/core';
import { GamePhases } from 'src/app/constants';
import { GameControlService } from 'src/app/services/game-control.service';

import { DynamicObject, field, idHandPictures } from './constants';

@Component({
  selector: 'app-data-field',
  templateUrl: './data-field.component.html',
  styleUrls: ['./data-field.component.scss'],
})
export class DataFieldComponent {
  GamePhases = GamePhases;

  myHand: number[] = [1, 2, 2, 2, 1];
  myLibraries: number[] = [0, 1, 2, 3, 4];
  oppsLibraries: number[] = [0, 1, 2, 3, 4];
  opponentsHand: number[] = [1, 1, 1];
  currentCard: number = 0;

  idHandPictures = idHandPictures;
  field = field;

  constructor(public gameControlService: GameControlService) {}

  pickCard(row: number, cell: number, card: number) {
    this.currentCard = card;
  }
  pickCardByHand(card: number) {
    this.currentCard = card;
  }

  dropCard(row: number, cell: number) {
    if (this.currentCard === 0) {
      return;
    }
    this.field[row][cell].name = this.currentCard;
    this.currentCard = 0;
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
