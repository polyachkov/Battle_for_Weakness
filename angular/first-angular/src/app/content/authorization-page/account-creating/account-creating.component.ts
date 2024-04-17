// import { Component } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { ChapterMenu } from './constants';
// import { PageContentService } from 'src/app/services/page-content.service';
// import { Pages } from 'src/app/constants';

// @Component({
//   selector: 'app-account-creating',
//   templateUrl: './account-creating.component.html',
//   styleUrls: ['./account-creating.component.scss'],
// })
// export class AccountCreatingComponent {
//   pages = Pages;
//   value: string = '';

//   chapterMenu = ChapterMenu;

//   answer: any;

//   valueProps = ['', '', ''];

//   constructor(
//     private http: HttpClient,
//     public pageContentService: PageContentService
//   ) {}

//   body = {
//     fraction1: 'mountains',
//     fraction2: 'mountains',
//     player1: 1,
//     player2: 2,
//   };

//   initUrl = 'http://193.109.79.58:8081/init';

//   search() {
//     console.log('Я отправил');
//     // 'https://jsonplaceholder.typicode.com/users/2' +

//     this.http.post<any>(this.initUrl, this.body).subscribe((response) => {
//       console.log(response);
//     });
//     // this.http.get(this.url).subscribe((response) => {
//     //   console.log(response);
//     // });
//   }
// }

// -------------------------------

import { Component, OnInit } from '@angular/core';
import { PageContentService } from 'src/app/services/page-content.service';
import { Pages } from 'src/app/constants';
import { ChapterMenu, inputProp } from './constants';
import { TokenStorageService } from '../../../auth/token-storage.service';

@Component({
  selector: 'app-account-creating',
  templateUrl: './account-creating.component.html',
  styleUrls: ['./account-creating.component.scss'],
})
export class AccountCreatingComponent implements OnInit {
  chapterMenu = ChapterMenu;
  Pages = Pages;
  roles: string[] = [];
  authority: string = '';

  constructor(
    private tokenStorage: TokenStorageService,
    public pageContentService: PageContentService
  ) {}

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every((role) => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        } else if (role === 'ROLE_MODERATOR') {
          this.authority = 'mod';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }
  }
}
