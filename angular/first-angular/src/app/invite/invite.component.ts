import { Component } from '@angular/core';
import {UserSearchService} from "../services/user-search.service";
import {UInfo} from "../models/user-model";
import {Observable, tap} from "rxjs";

@Component({
  selector: 'app-invite',
  templateUrl: './invite.component.html',
  styleUrls: ['./invite.component.scss']
})
export class InviteComponent {
  loading = false
  users!: Observable<UInfo[]>
  term = ''
  constructor(private userSearchService: UserSearchService) { }

  ngOnInit(): void {
    this.loading = true
    this.users = this.userSearchService.getAll().pipe(
      tap(() => this.loading = false)
    )
  }
}
