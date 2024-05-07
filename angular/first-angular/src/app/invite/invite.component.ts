import { Component } from '@angular/core';
import {UserSearchService} from "../services/user-search.service";
import {IUser} from "../models/user-model";
import {Observable, tap} from "rxjs";
import {IInvite} from "../models/invite-model";
import {InviteService} from "../services/invite.service";

@Component({
  selector: 'app-invite',
  templateUrl: './invite.component.html',
  styleUrls: ['./invite.component.scss']
})
export class InviteComponent {
  loading = false
  users!: Observable<IUser[]>
  invites!: Observable<IInvite[]>
  term = ''
  constructor(
    private userSearchService: UserSearchService,
    private inviteSearchService: InviteService
  ) { }

  ngOnInit(): void {
    this.loading = true
    this.users = this.userSearchService.getAll().pipe(
      tap(() => this.loading = false)
    )
  }
}
