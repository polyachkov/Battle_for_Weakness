export interface IGame {
  id_game: number
  name_player1: string
  name_player2: string
}

export interface Game {
  id_game: number
  name_player1: string
  name_player2: string
  name_turn: string
  is_ended: boolean
  non_reverse: string
  turn_ended: boolean
}
