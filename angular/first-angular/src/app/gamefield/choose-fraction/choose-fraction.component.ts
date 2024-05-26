import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {GamefieldComponent} from "../gamefield.component";
import {Observable} from "rxjs";
import {ILibrary} from "../../models/library-model";
import {GameControlService} from "../../services/game-control.service";

@Component({
  selector: 'app-choose-fraction',
  templateUrl: './choose-fraction.component.html',
  styleUrls: ['./choose-fraction.component.scss']
})
export class ChooseFractionComponent implements OnInit {
  libraries!: Observable<ILibrary[]>;

  constructor(
    public gamefieldComponent: GamefieldComponent,
    public gameControlService: GameControlService
  ) {}

  ngOnInit(): void {
    this.libraries = this.gameControlService.getLibraries(this.gamefieldComponent.id_game);
  }
}
