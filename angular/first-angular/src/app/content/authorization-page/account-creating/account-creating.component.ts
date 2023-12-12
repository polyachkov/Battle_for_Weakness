import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ChapterMenu, inputProp } from './constants';
import { PageContentService } from 'src/app/services/page-content.service';
import { Pages } from 'src/app/constants';

@Component({
  selector: 'app-account-creating',
  templateUrl: './account-creating.component.html',
  styleUrls: ['./account-creating.component.scss'],
})
export class AccountCreatingComponent {
  pages = Pages;
  value: string = '';

  chapterMenu = ChapterMenu;

  answer: any;

  valueProps = ['', '', ''];

  constructor(
    private http: HttpClient,
    public pageContentService: PageContentService
  ) {}

  body = {
    fraction1: 'mountains',
    fraction2: 'mountains',
    player1: 1,
    player2: 2,
  };

  url = 'http://193.109.79.58:8081/init';

  search() {
    console.log('Я отправил');
    // 'https://jsonplaceholder.typicode.com/users/2' +

    this.http.post<any>(this.url, this.body).subscribe((response) => {
      console.log(response);
    });
    // this.http.get(this.url).subscribe((response) => {
    //   console.log(response);
    // });
  }
}
