import { Component } from '@angular/core'
import {IProduct} from './models/product'
import {products as data} from './data/products'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'opana app';

  products: IProduct[] = data;
}
