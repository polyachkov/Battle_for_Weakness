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

  getUserButtonText(): string {
    if (this.authority === 'admin') {
      return "Admin Board";
    } else if (this.authority === 'mod') {
      return "Moder Board";
    }
    return "User Board";
  }
}
