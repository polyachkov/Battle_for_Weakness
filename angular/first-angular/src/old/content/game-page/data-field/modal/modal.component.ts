  import { Component } from '@angular/core';
  import { GameControlService } from 'src/app/services/game-control.service';
  import { field } from '../constants';

  @Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.scss'],
  })
  export class ModalComponent {
    field: any[][] = field;
    pickedCard: number[] = [0, 0];

    constructor(public gameControlService: GameControlService) {}

    showModal() {
      this.pickedCard[0] = this.gameControlService.cardPos[0];
      this.pickedCard[1] = this.gameControlService.cardPos[1];
      let answer = this.gameControlService.showModal();
      return answer;
    }

    determineStat(row: number, cell: number) {
      switch (true) {
        case row == 0 && cell == 0:
          return field[this.pickedCard[0]][this.pickedCard[1]].attack;
        case row == 0 && cell == 1:
          return field[this.pickedCard[0]][this.pickedCard[1]].health;
        case row == 1 && cell == 0:
          return field[this.pickedCard[0]][this.pickedCard[1]].evasion;
        case row == 1 && cell == 1:
          return field[this.pickedCard[0]][this.pickedCard[1]].cost;
        case row == 2 && cell == 0:
          return field[this.pickedCard[0]][this.pickedCard[1]].attackSpeed;
        case row == 2 && cell == 1:
          return field[this.pickedCard[0]][this.pickedCard[1]].moveSpeed;

        default:
          break;
      }
    }
  }
