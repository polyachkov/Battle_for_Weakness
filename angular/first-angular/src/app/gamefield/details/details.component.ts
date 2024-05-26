import { Component } from '@angular/core';
import {GamefieldComponent} from "../gamefield.component";
import {map, Observable} from "rxjs";
import {ICell} from "../../models/cell-model";
import {idMoneyCollectorPictures} from "../constants";

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent {
  pickedCard: number[] = [0, 0];
  url!: Observable<string>;
  currentCell!: Observable<ICell>;

  constructor(
    public gamefieldComponent: GamefieldComponent
  ) {}

  ngOnInit(): void {
    this.pickedCard[0] = this.gamefieldComponent.cardPos[0];
    this.pickedCard[1] = this.gamefieldComponent.cardPos[1];
    this.url = this.gamefieldComponent.showModal();
    this.currentCell = this.gamefieldComponent.currentCell();
  }

  determineStat(row: number, cell: number, cellObj: ICell | null) {
    if (!cellObj) return '';
    switch (true) {
      case row == 0 && cell == 0:
        return cellObj.card_name;
      case row == 0 && cell == 1:
        return;
      case row == 1 && cell == 0:
        return 'Attack: ' + cellObj.attack;
      case row == 1 && cell == 1:
        return 'Health: ' + cellObj.health;
      case row == 2 && cell == 0:
        return 'Cost: ' + cellObj.cost;
      case row == 2 && cell == 1:
        return 'Evasion: ' + cellObj.evasion;
      case row == 3 && cell == 0:
        return 'Attack_speed: ' + this.gamefieldComponent.attackSpeedToString(cellObj.attack_speed);
      case row == 3 && cell == 1:
        return 'Movement_speed: ' + cellObj.movement_speed;

      default:
        return undefined;
    }
  }
}
