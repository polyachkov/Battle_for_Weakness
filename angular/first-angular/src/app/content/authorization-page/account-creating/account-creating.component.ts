import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inputProp } from './constants';

@Component({
  selector: 'app-account-creating',
  templateUrl: './account-creating.component.html',
  styleUrls: ['./account-creating.component.scss'],
})
export class AccountCreatingComponent {
  value: string = '';
  response: any;

  answer: any;

  inputProps = [
    inputProp.EMAIL,
    inputProp.LOGIN,
    inputProp.PASSWORD,
    inputProp.PASSWORD_AGAIN,
  ];

  valueProps = ['', '', '', ''];

  emailData: string = '';
  loginData: string = '';
  passwordData: string = '';

  displayPassword: string = '';
  displayLogin: string = '';

  constructor(private http: HttpClient) {}

  getData() {
    this.displayPassword = this.loginData;
    this.displayLogin = this.passwordData;
  }
  search() {
    this.http
      .get('https://jsonplaceholder.typicode.com/users/' + this.value)
      .subscribe((response) => {
        this.response = response;
        this.answer = Object.entries(this.response);
        console.log(this.response);
      });
  }
}
