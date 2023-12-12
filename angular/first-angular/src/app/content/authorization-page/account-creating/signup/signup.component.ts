import { Component } from '@angular/core';
import { PageContentService } from 'src/app/services/page-content.service';
import { ChapterMenu, inputProp } from '../constants';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent {
  chapterMenu = ChapterMenu;

  valueProps: string[] = ['', '', ''];
  displayPassword: string = '';
  displayLogin: string = '';
  inputProps = [inputProp.LOGIN, inputProp.PASSWORD, inputProp.PASSWORD_AGAIN];

  constructor(public pageContentService: PageContentService) {}

  getData() {
    this.displayPassword = this.valueProps[0];
    this.displayLogin = this.valueProps[1];
  }
}
