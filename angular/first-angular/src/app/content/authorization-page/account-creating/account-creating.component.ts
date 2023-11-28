import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-account-creating',
  templateUrl: './account-creating.component.html',
  styleUrls: ['./account-creating.component.scss'],
})
export class AccountCreatingComponent {
  passwordData = '';
  loginData = '';
  value: string = '';
  response: any;
  answer: any;

  constructor(private http: HttpClient) {}

  getData() {
    this.passwordData = 'You are ';
    this.loginData = ' my hero!';
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
