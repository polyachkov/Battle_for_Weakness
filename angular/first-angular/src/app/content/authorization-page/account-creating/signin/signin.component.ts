import { Component } from '@angular/core';
import { PageContentService } from 'src/app/services/page-content.service';
import { ChapterMenu, inputProp } from '../constants';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss'],
})
export class SigninComponent {
  chapterMenu = ChapterMenu;

  valueProps: string[] = ['', ''];

  displayPassword: string = '';
  displayLogin: string = '';

  inputProps = [inputProp.LOGIN, inputProp.PASSWORD];

  constructor(public pageContentService: PageContentService) {}

  getData() {
    this.displayPassword = this.valueProps[0];
    this.displayLogin = this.valueProps[1];
  }
}
