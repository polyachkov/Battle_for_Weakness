import { Injectable } from '@angular/core';
import { IsGuest, Pages } from '../constants';

@Injectable({
  providedIn: 'root',
})
export class PageContentService {
  currentPage: Pages = Pages.MAIN_PAGE;

  isGuest: IsGuest = IsGuest.GUEST;

  handleChangeContent(current: Pages) {
    this.currentPage = current;
  }
}
