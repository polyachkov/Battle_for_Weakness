import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {hostName} from "../constants";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl   = hostName + 'api/test/user';
  private modUrl    = hostName + 'api/test/mod';
  private adminUrl  = hostName + 'api/test/admin';

  constructor(private http: HttpClient) { }

  getUserBoard(): Observable<string> {
    return this.http.get(this.userUrl, { responseType: 'text' });
  }

  getModBoard(): Observable<string> {
    return this.http.get(this.modUrl, { responseType: 'text' });
  }

  getAdminBoard(): Observable<string> {
    return this.http.get(this.adminUrl, { responseType: 'text' });
  }
}
