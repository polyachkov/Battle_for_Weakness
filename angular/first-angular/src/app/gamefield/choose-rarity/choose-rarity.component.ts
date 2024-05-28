import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {GamefieldComponent} from "../gamefield.component";
import {Observable} from "rxjs";
import {ILibrary} from "../../models/library-model";
import {GameControlService} from "../../services/game-control.service";

@Component({
  selector: 'app-choose-rarity',
  templateUrl: './choose-rarity.component.html',
  styleUrls: ['./choose-rarity.component.scss']
})
export class ChooseRarityComponent implements OnInit {
  libraries!: Observable<ILibrary[]>;

  constructor(
    public gamefieldComponent: GamefieldComponent,
    public gameControlService: GameControlService
  ) {}

  ngOnInit(): void {
    this.libraries = this.gameControlService.getLibraries(this.gamefieldComponent.id_game);
    this.gamefieldComponent.unsubscribeAll();
    const subscription = this.libraries.subscribe((libraries) => {
      this.gamefieldComponent.setLibCount(libraries.length);
    });
    this.gamefieldComponent.subscriptions.push(subscription);
  }
}
