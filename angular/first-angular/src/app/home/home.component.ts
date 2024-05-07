import { Component, OnInit } from '@angular/core';

import { TokenStorageService } from '../auth/token-storage.service';
import {Observable, tap} from "rxjs";
import {IInvite} from "../models/invite-model";
import {InviteService} from "../services/invite.service";
import {GameService} from "../services/game.service";
import {IGame} from "../models/game-model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  loading = false;

  info: any;

  invites!: Observable<IInvite[]>;
  games!: Observable<IGame[]>;

  constructor(
    private token: TokenStorageService,
    public inviteSearchService: InviteService,
    public gameService: GameService
  ) {
  }

  ngOnInit(): void {
    this.info = {
      id: this.token.getId(),
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities(),
    };
    this.invites = this.inviteSearchService.getAll()
    this.games = this.gameService.getAll().pipe(
      tap(() => this.loading = false)
    )
  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }

}
