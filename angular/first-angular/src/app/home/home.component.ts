import { Component, OnInit } from '@angular/core';

import { TokenStorageService } from '../auth/token-storage.service';
import {Observable, tap} from "rxjs";
import {IUser} from "../models/user-model";
import {IInvite} from "../models/invite-model";
import {UserSearchService} from "../services/user-search.service";
import {InviteService} from "../services/invite.service";
import {IGame} from "../models/game-model";
import {GameService} from "../services/game.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  info: any;
  loading = false
  games!: Observable<IGame[]>

  constructor(
    private token: TokenStorageService,
    private gameService: GameService
  ) { }

  ngOnInit(): void {
    this.info = {
      id: this.token.getId(),
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };
    this.loading = true
    this.games = this.gameService.getAll().pipe(
      tap(() => this.loading = false)
    )
  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }
}
