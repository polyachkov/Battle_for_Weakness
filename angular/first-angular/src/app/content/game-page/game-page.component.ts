import { Component } from '@angular/core';
import { GameControlService } from 'src/app/services/game-control.service';
import { GamePhases } from 'src/app/constants';

@Component({
  selector: 'app-game-page',
  templateUrl: './game-page.component.html',
  styleUrls: ['./game-page.component.scss'],
})
export class GamePageComponent {
  GamePhases = GamePhases;

  constructor(public gameControlService: GameControlService) {}
}
