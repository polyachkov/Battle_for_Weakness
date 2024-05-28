import { Component } from '@angular/core';
import {GamePhases, Pages} from 'src/app/constants';
import { GameControlService } from 'src/app/services/game-control.service';
import {PageContentService} from "../../services/page-content.service";
import {GamefieldComponent} from "../gamefield.component";

@Component({
  selector: 'app-game-over',
  templateUrl: './game-over.component.html',
  styleUrls: ['./game-over.component.scss'],
})
export class GameOverComponent {
  GamePhases = GamePhases;

  constructor(
    public gameControlService: GameControlService,
    public pageContentService: PageContentService,
    public gamefieldComponent: GamefieldComponent
  ) {}

  protected readonly Pages = Pages;
}
