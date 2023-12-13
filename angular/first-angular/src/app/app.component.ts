import { Component } from '@angular/core';
import { GameControlService } from './services/game-control.service';
import { PageContentService } from './services/page-content.service';
import { Pages } from './constants';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  Pages = Pages;

  constructor(
    public gameControlService: GameControlService,
    public pageContentService: PageContentService
  ) {}
}
