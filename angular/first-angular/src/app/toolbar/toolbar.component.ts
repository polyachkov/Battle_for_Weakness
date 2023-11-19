import { Component, EventEmitter, Output } from '@angular/core';
import { Pages } from '../constants';
import { PageContentService } from '../services/page-content.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss'],
})
export class ToolbarComponent {
  Pages = Pages;

  constructor(public pageContentService: PageContentService) {}
}
