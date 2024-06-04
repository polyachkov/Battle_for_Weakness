import {Component, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {TokenStorageService} from "../auth/token-storage.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  roles: string[] = [];
  authority: string = '';
  activeUsers!: Observable<number>;
  activeGames!: Observable<number>;

  constructor(
    private tokenStorage: TokenStorageService,
    public userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.getRoles();
    this.getInfoForRole();
  }

  getRoles(): void {
    this.roles = this.tokenStorage.getAuthorities();
    this.roles.every((role) => {
      if (role === 'ROLE_ADMIN') {
        this.authority = 'admin';
        return false;
      } else if (role === 'ROLE_MODERATOR') {
        this.authority = 'mod';
        return false;
      }
      this.authority = 'user';
      return true;
    });
  }

  getInfoForRole() {
    if (this.authority === 'admin') {
      this.activeUsers = this.getActiveUsers();
      this.activeGames = this.getActiveGames();
    } else if (this.authority === 'mod') {
      return;
    }
    return;
  }

  getActiveUsers() {
    return this.userService.getActiveUsers();
  }

  getActiveGames() {
    return this.userService.getActiveGames();
  }
}
