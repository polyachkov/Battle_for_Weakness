CREATE TABLE IF NOT EXISTS persona
(
    id_profile    INTEGER PRIMARY KEY ,
    name  VARCHAR(200) NOT NULL ,
    password VARCHAR(254) NOT NULL
);
CREATE SEQUENCE IF NOT EXISTS persona_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS card;
CREATE TABLE IF NOT EXISTS card
(
    id_card    INTEGER PRIMARY KEY ,
    name  VARCHAR(200) NOT NULL ,
    attack INTEGER NOT NULL,
    health INTEGER NOT NULL,
    cost INTEGER NOT NULL,
    evasion INTEGER NOT NULL,
    attack_speed INTEGER NOT NULL,
    movement_speed INTEGER NOT NULL,
    rarity VARCHAR(200) NOT NULL,
    fraction VARCHAR(200) NOT NULL,
    number_of_cards INTEGER NOT NULL
    );
DROP TABLE IF EXISTS game;
CREATE TABLE IF NOT EXISTS game
(
    id_game    INTEGER PRIMARY KEY,
    id_player1    INTEGER NOT NULL,
    id_player2    INTEGER NOT NULL,
    id_turn    INTEGER NOT NULL,
    is_ended BOOLEAN NOT NULL
);
DROP SEQUENCE IF EXISTS games_id_seq;
CREATE SEQUENCE IF NOT EXISTS games_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS library;
CREATE TABLE library
(
    id_library    INTEGER PRIMARY KEY ,
    game_id    INTEGER  NOT NULL,
    player_id    INTEGER NOT NULL,
    cards_cnt    INTEGER NOT NULL,
    rarity VARCHAR(200) NOT NULL,
    locked BOOLEAN NOT NULL
);
DROP SEQUENCE IF EXISTS libraries_id_seq;
CREATE SEQUENCE IF NOT EXISTS libraries_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS library_composition;
CREATE TABLE library_composition
(
    id_card_lib    INTEGER PRIMARY KEY ,
    id_library    INTEGER  NOT NULL,
    id_card    INTEGER NOT NULL
);
DROP SEQUENCE IF EXISTS lib_comp_id_seq;
CREATE SEQUENCE IF NOT EXISTS lib_comp_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS hand;
CREATE TABLE hand
(
    id_hand    INTEGER PRIMARY KEY ,
    id_player    INTEGER  NOT NULL,
    id_game    INTEGER NOT NULL,
    cards_cnt INTEGER NOT NULL
);
DROP SEQUENCE IF EXISTS hands_id_seq;
CREATE SEQUENCE IF NOT EXISTS hands_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS hand_composition;
CREATE TABLE hand_composition
(
    id_hand_card    INTEGER PRIMARY KEY ,
    id_hand    INTEGER  NOT NULL,
    id_card    INTEGER NOT NULL
);
DROP SEQUENCE IF EXISTS hand_comp_id_seq;
CREATE SEQUENCE IF NOT EXISTS hand_comp_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS cell;
CREATE TABLE cell
(
    id_cell    INTEGER PRIMARY KEY ,
    id_game    INTEGER  NOT NULL,
    cell_num INTEGER NOT NULL ,
    id_card    INTEGER
);
DROP SEQUENCE IF EXISTS cell_id_seq;
CREATE SEQUENCE IF NOT EXISTS cell_id_seq START WITH 1 INCREMENT BY 1;