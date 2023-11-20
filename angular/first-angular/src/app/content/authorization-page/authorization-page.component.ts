import { Component } from '@angular/core';
import { IsGuest } from 'src/app/constants';
import { PageContentService } from 'src/app/services/page-content.service';

@Component({
  selector: 'app-authorization-page',
  templateUrl: './authorization-page.component.html',
  styleUrls: ['./authorization-page.component.scss'],
})
export class AuthorizationPageComponent {
  IsGuest = IsGuest;

  constructor(public pageContentService: PageContentService) {}
}
