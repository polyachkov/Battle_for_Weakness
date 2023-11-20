import { Component } from '@angular/core';

@Component({
  selector: 'app-account-creating',
  templateUrl: './account-creating.component.html',
  styleUrls: ['./account-creating.component.scss'],
})
export class AccountCreatingComponent {
  passwordData = '';
  loginData = '';

  getData() {
    this.passwordData = 'You are ';
    this.loginData = ' my hero!';
  }
}
