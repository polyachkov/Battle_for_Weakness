import { Injectable } from '@angular/core';
import { IsGuest, Pages } from '../constants';
import { ChapterMenu } from '../content/authorization-page/account-creating/constants';

@Injectable({
  providedIn: 'root',
})
export class PageContentService {
  currentPage: Pages = Pages.MAIN_PAGE;

  isGuest: IsGuest = IsGuest.GUEST;

  currentMenu: ChapterMenu = ChapterMenu.SIGNIN;

  handleChangeContent(current: Pages) {
    this.currentPage = current;
  }
  handleCurrentMenu(current: ChapterMenu) {
    this.currentMenu = current;
  }
}
