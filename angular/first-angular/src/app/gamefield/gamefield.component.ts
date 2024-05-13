import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";


import { DynamicObject, field, idHandPictures } from './constants';
import {GameControlService} from "../services/game-control.service";
import {GamePhases, Pages} from "../constants";
import {PageContentService} from "../services/page-content.service";
import {Card} from "../models/card-model";
import {Observable} from "rxjs";

@Component({
  selector: 'app-gamefield',
  templateUrl: './gamefield.component.html',
  styleUrls: ['./gamefield.component.scss']
})
export class GamefieldComponent implements OnInit {
  id_game!: string;
  hand!: Observable<Card[]>;

  handCardId!: number[];

  constructor(
    private route: ActivatedRoute,
    public gameControlService: GameControlService,
    public pageContentService: PageContentService
  ) {
    this.route.params.subscribe(params => {
      this.id_game = params['id'];
      gameControlService.setIdGame(Number(this.id_game))
    });
  }

  ngOnInit(): void {
    this.hand = this.gameControlService.getHand(this.id_game);
  }

  myHand: number[] = [1, 2, 2, 2, 1];
  myLibraries: number[] = [0, 1, 2, 3, 4];
  oppsLibraries: number[] = [0, 1, 2, 3, 4];
  opponentsHand: number[] = [1, 1, 1];
  currentCard: number = 0;

  idHandPictures = idHandPictures;
  field = field;

  pickCard(row: number, cell: number, card: number) {
    this.currentCard = card;
  }
  pickCardByHand(card: number) {
    this.currentCard = card;
  }

  dropCard(row: number, cell: number) {
    if (this.currentCard === 0) {
      return;
    }
    this.field[row][cell].name = this.currentCard;
    this.currentCard = 0;
  }

  noReturnPredicate() {
    return false;
  }

  getRowColor(index: number): DynamicObject<boolean> {
    const rowColorObj: DynamicObject<boolean> = {};
    switch (index) {
      case 1:
      case 2:
      case 5:
      case 6:
        rowColorObj['yellowCircle'] = true;
        break;
      case 2:
      case 7:
        rowColorObj['redSquare'] = true;
        break;
      case 3:
      case 4:
        rowColorObj['blackSquare'] = true;
        break;
      case 0:
        rowColorObj['blueSquare'] = true;
        break;
    }
    return rowColorObj;
  }

  protected readonly GamePhases = GamePhases;
  protected readonly Pages = Pages;
}
