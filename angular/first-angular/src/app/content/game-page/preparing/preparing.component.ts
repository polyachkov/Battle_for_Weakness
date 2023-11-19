import { Component } from '@angular/core';
import { GamePhases } from 'src/app/constants';
import { GameControlService } from 'src/app/services/game-control.service';
import { Fractions } from './constants';

@Component({
  selector: 'app-preparing',
  templateUrl: './preparing.component.html',
  styleUrls: ['./preparing.component.scss'],
})
export class PreparingComponent {
  GamePhases = GamePhases;

  Fraction = Fractions;

  constructor(public gameControlService: GameControlService) {}
}
