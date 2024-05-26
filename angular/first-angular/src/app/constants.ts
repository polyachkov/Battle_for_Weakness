export enum Pages {
  MAIN_PAGE = 'MAIN_PAGE',
  DEVELOPERS_PAGE = 'DEVELOPERS_PAGE',
  GAME_PAGE = 'GAME_PAGE',
  RULES_PAGE = 'RULES_PAGE',
  AUTHORIZATION_PAGE = 'AUTHORIZATION_PAGE',
  GAMEFIELD_PAGE = 'GAMEFIELD_PAGE',
}
export enum GamePhases {
  PREPARING = 'PREPARING',
  START_GAME = 'START_GAME',
  FINISH_GAME = 'FINISH_GAME',
}

export enum IsGuest {
  NOT_GUEST = 'NOT_GUEST',
  GUEST = 'GUEST',
}

export enum GameState {
  NOPROCESS,
  PROCESS,
}

export const hostName: string = 'http://localhost:8081/'
