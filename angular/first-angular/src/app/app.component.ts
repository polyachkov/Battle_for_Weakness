import { Component } from '@angular/core';
import { GameControlService } from './services/game-control.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor(public gameControlService: GameControlService) {}
}
