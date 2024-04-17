export class JwtResponse {
  id: number;
  token: string;
  type: string;
  username: string;
  roles: string[];

  constructor(id: number, token: string, type: string, username: string) {
    this.id = id;
    this.token = token;
    this.type = type;
    this.username = username;
    this.roles = ['user'];
  }
}
