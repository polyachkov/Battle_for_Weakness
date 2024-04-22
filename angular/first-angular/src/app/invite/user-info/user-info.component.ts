import {Component, Input} from '@angular/core';
import {IUser} from "../../models/user-model";
import {InviteService} from "../../services/invite.service";

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.scss']
})
export class UserInfoComponent {
  @Input() user!: IUser

  invited = false

  constructor(
    private inviteService: InviteService
  ) {
  }

  inviteUser(invitedName: string): void {
    const inviterRace: string = 'mountains';
    this.inviteService.inviteUser(invitedName, inviterRace)
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
