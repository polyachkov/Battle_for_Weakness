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
import { RulesPageComponent } from './content/rules-page/rules-page.component';
import { DevelopersPageComponent } from './content/developers-page/developers-page.component';
import { AuthorizationPageComponent } from './content/authorization-page/authorization-page.component';
import { AccountCreatingComponent } from './content/authorization-page/account-creating/account-creating.component';
import { AccountSettingsComponent } from './content/authorization-page/account-settings/account-settings.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { SigninComponent } from './content/authorization-page/account-creating/signin/signin.component';
import { SignupComponent } from './content/authorization-page/account-creating/signup/signup.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { ModComponent } from './mod/mod.component';
import { AdminComponent } from './admin/admin.component';
import { httpInterceptorProviders } from "./auth/auth-interceptor";
import { InviteComponent } from './invite/invite.component';
import { UserInfoComponent } from './invite/user-info/user-info.component';
import { FilterUsersPipe } from './pipes/filter-users.pipe';
import { InviteInfoComponent } from './invite/invite-info/invite-info.component';
import { GameComponent } from './home/game/game.component';
import { GamefieldComponent } from './gamefield/gamefield.component';
import { DetailsComponent } from './gamefield/details/details.component';
import { ChooseRarityComponent } from './gamefield/choose-rarity/choose-rarity.component';
import {GameOverComponent} from "./gamefield/game-over/game-over.component";

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    ContentComponent,
    FirstPageComponent,
    RulesPageComponent,
    DevelopersPageComponent,
    AuthorizationPageComponent,
    AccountCreatingComponent,
    AccountSettingsComponent,
    SigninComponent,
    SignupComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    UserComponent,
    ModComponent,
    AdminComponent,
    InviteComponent,
    UserInfoComponent,
    FilterUsersPipe,
    InviteInfoComponent,
    GameComponent,
    GamefieldComponent,
    DetailsComponent,
    ChooseRarityComponent,
    GameOverComponent,
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
    HttpClientModule,
    FormsModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent],
})
export class AppModule {}
