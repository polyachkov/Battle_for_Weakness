import { Component, OnInit } from '@angular/core';
import { GameControlService } from 'src/app/services/game-control.service';
import { GamePhases, Pages } from 'src/app/constants';
import { Fractions } from './preparing/constants';

@Component({
  selector: 'app-game-page',
  templateUrl: './game-page.component.html',
  styleUrls: ['./game-page.component.scss'],
})
export class GamePageComponent implements OnInit {
  GamePhases = GamePhases;

  constructor(public gameControlService: GameControlService) {}

  protected readonly Pages = Pages;
  protected readonly Fraction = Fractions;

  ngOnInit(): void {
    this.gameControlService.handleGameStarted();
  }
}
