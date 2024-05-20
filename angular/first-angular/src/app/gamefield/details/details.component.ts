import { Component } from '@angular/core';
import {GamefieldComponent} from "../gamefield.component";
import {map} from "rxjs";
import {ICell} from "../../models/cell-model";
import {idMoneyCollectorPictures} from "../constants";

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent {
  pickedCard: number[] = [0, 0];

  constructor(
    public gamefieldComponent: GamefieldComponent
  ) {}

  showModal() {
    this.pickedCard[0] = this.gamefieldComponent.cardPos[0];
    this.pickedCard[1] = this.gamefieldComponent.cardPos[1];
    let answer = this.gamefieldComponent.showModal();
    return answer;
  }

  // determineStat(row: number, cell: number) {
  //   this.gamefieldComponent.field.pipe(
  //     map((cells: ICell[][]) => {
  //       switch (true) {
  //         case row == 0 && cell == 0:
  //           return cells[this.pickedCard[0]][this.pickedCard[1]].attack;
  //         case row == 0 && cell == 1:
  //           return cells[this.pickedCard[0]][this.pickedCard[1]].health;
  //         case row == 1 && cell == 0:
  //           return cells[this.pickedCard[0]][this.pickedCard[1]].evasion;
  //         case row == 1 && cell == 1:
  //           return cells[this.pickedCard[0]][this.pickedCard[1]].cost;
  //         case row == 2 && cell == 0:
  //           return cells[this.pickedCard[0]][this.pickedCard[1]].attackSpeed;
  //         case row == 2 && cell == 1:
  //           return cells[this.pickedCard[0]][this.pickedCard[1]].moveSpeed;
  //
  //         default:
  //           break;
  //       }
  //     })
  //   ).subscribe();
  //
  //   this.field.pipe(
  //     map((cells: ICell[][]) => {
  //       const imgNum = cells[this.cardPos[0]][this.cardPos[1]].id_card;
  //       url = idMoneyCollectorPictures[imgNum];
  //       return url;
  //     })
  //   ).subscribe();
  //
  // }
}
