import { Component } from '@angular/core';
import { PageContentService } from '../services/page-content.service';
import { Pages } from '../constants';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.scss'],
})
export class ContentComponent {
  Pages = Pages;

  constructor(public pageContentService: PageContentService) {}
}
