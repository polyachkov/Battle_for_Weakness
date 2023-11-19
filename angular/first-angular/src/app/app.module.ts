import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';

import { ToolbarComponent } from './toolbar/toolbar.component';
import { ContentComponent } from './content/content.component';
import { FirstPageComponent } from './content/first-page/first-page.component';
import { GamePageComponent } from './content/game-page/game-page.component';
import { ThirdPageComponent } from './content/third-page/third-page.component';
import { DevelopersPageComponent } from './content/developers-page/developers-page.component';
import { DataFieldComponent } from './content/game-page/data-field/data-field.component';
import { PreparingComponent } from './content/game-page/preparing/preparing.component';
import { GameOverComponent } from './content/game-page/game-over/game-over.component';
import { FieldCardRendererComponent } from './content/game-page/data-field/field-card-renderer/field-card-renderer.component';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    ContentComponent,
    FirstPageComponent,
    GamePageComponent,
    ThirdPageComponent,
    DevelopersPageComponent,
    DataFieldComponent,
    PreparingComponent,
    GameOverComponent,
    FieldCardRendererComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatMenuModule,
    MatCardModule,
    MatButtonModule,
    DragDropModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
