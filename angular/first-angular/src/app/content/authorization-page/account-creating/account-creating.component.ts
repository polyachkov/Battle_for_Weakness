import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-account-creating',
  templateUrl: './account-creating.component.html',
  styleUrls: ['./account-creating.component.scss'],
})
export class AccountCreatingComponent {
  value: string = '';
  response: any;

  answer: any;

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
