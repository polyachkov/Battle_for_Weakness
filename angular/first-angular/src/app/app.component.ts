import { Component } from '@angular/core';
import { GameControlService } from './services/game-control.service';
import { PageContentService } from './services/page-content.service';
import { Pages } from './constants';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  Pages = Pages;

  constructor(
    public gameControlService: GameControlService,
    public pageContentService: PageContentService
  ) {}
}

// import { Component, OnInit  } from '@angular/core';
//
// import { TokenStorageService } from './auth/token-storage.service';
//
// @Component({
//   selector: 'app-root',
//   templateUrl: './app.component.html',
//   styleUrls: ['./app.component.scss']
// })
// export class AppComponent implements OnInit {
//
//   roles: string[] = [];
//   authority: string = '';
//
//   constructor(private tokenStorage: TokenStorageService) { }
//
//   ngOnInit() {
//     if (this.tokenStorage.getToken()) {
//       this.roles = this.tokenStorage.getAuthorities();
//       this.roles.every(role => {
//         if (role === 'ROLE_ADMIN') {
//           this.authority = 'admin';
//           return false;
//         } else if (role === 'ROLE_PM') {
//           this.authority = 'pm';
//           return false;
//         }
//         this.authority = 'user';
//         return true;
//       });
//     }
//   }
// }
