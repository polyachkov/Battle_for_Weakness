import {Component, Input} from '@angular/core';
import {UInfo} from "../models/user-model";
import {UserSearchService} from "../services/user-search.service";
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.scss']
})
export class UserInfoComponent {
  @Input() user!: UInfo

  invited = false

  constructor(
    private token: TokenStorageService,
    private userSearchService: UserSearchService
  ) {
  }

  inviteUser(invitedId: number): void {
    const inviterId = parseInt(this.token.getId(), 10);
    const inviterRace = 'mountains';
    this.userSearchService.inviteUser(inviterId, invitedId, inviterRace)
      .subscribe(
        () => {
          console.log('Invitation sent successfully.');
          // Добавьте здесь обработку успешной отправки приглашения, если необходимо
        },
        error => {
          console.error('Failed to send invitation:', error);
          // Добавьте здесь обработку ошибки при отправке приглашения, если необходимо
        }
      );
  }
}
