import {Component, Input} from '@angular/core';
import {IGame} from "../../models/game-model";
import {GameService} from "../../services/game.service";
import {GamePhases, Pages} from "../../constants";
import {GameControlService} from "../../services/game-control.service";
import {PageContentService} from "../../services/page-content.service";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent {
  @Input() game!: IGame

  constructor(
    private gameService: GameService,
    public gameControlService: GameControlService,
    public pageContentService: PageContentService
  ) {
  }

  connectGame(name_player1: string, name_player2: string): void {

  }

  protected readonly GamePhases = GamePhases;
  protected readonly Pages = Pages;
}
