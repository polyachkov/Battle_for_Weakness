import {Component, Input} from '@angular/core';
import {InviteService} from "../../services/invite.service";
import {IUser} from "../../models/user-model";
import {IInvite} from "../../models/invite-model";

@Component({
  selector: 'app-invite-info',
  templateUrl: './invite-info.component.html',
  styleUrls: ['./invite-info.component.scss']
})
export class InviteInfoComponent {
  @Input() invite!: IInvite

  constructor(
    private inviteService: InviteService
  ) {
  }

  acceptInvite(inviterName: string): void {
    const invitedFraction: string = 'mountains';
    this.inviteService.acceptInvite(inviterName, invitedFraction)
      .subscribe(
        () => {
          console.log('Invitation accept successfully.');
          // Добавьте здесь обработку успешной отправки приглашения, если необходимо
        },
        error => {
          console.error('Failed to accept invitation:', error);
          // Добавьте здесь обработку ошибки при отправке приглашения, если необходимо
        }
      );
  }

  rejectInvite(inviterName: string): void {
    this.inviteService.rejectInvite(inviterName)
      .subscribe(
        () => {
          console.log('Invitation reject successfully.');
          // Добавьте здесь обработку успешной отправки приглашения, если необходимо
        },
        error => {
          console.error('Failed to reject invitation:', error);
          // Добавьте здесь обработку ошибки при отправке приглашения, если необходимо
        }
      );
  }
}
