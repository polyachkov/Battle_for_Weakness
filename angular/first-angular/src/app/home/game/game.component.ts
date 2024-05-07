import {Component, Input} from '@angular/core';
import {IGame} from "../../models/game-model";
import {GameService} from "../../services/game.service";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent {
  @Input() game!: IGame

  constructor(
    private gameService: GameService
  ) {
  }

  connectGame(name_player1: string, name_player2: string): void {

  }
}
