DROP TABLE IF EXISTS persona;
CREATE TABLE IF NOT EXISTS persona
(
    id_profile    INTEGER PRIMARY KEY ,
    name  VARCHAR(200) NOT NULL ,
    password VARCHAR(254) NOT NULL
);
DROP SEQUENCE IF EXISTS persona_id_seq;
CREATE SEQUENCE IF NOT EXISTS persona_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS card;
CREATE TABLE IF NOT EXISTS card
(
    id_card         INTEGER PRIMARY KEY ,
    name            VARCHAR(200) NOT NULL ,
    attack          INTEGER NOT NULL,
    health          INTEGER NOT NULL,
    cost            INTEGER NOT NULL,
    evasion         INTEGER NOT NULL,
    attack_speed    INTEGER NOT NULL,
    movement_speed  INTEGER NOT NULL,
    rarity          VARCHAR(200) NOT NULL,
    fraction        VARCHAR(200) NOT NULL,
    number_of_cards INTEGER NOT NULL
    );
DROP TABLE IF EXISTS game;
CREATE TABLE IF NOT EXISTS game
(
    id_game         INTEGER PRIMARY KEY,
    name_player1    VARCHAR(200) NOT NULL,
    name_player2    VARCHAR(200) NOT NULL,
    name_turn       VARCHAR(200) NOT NULL,
    is_ended        BOOLEAN NOT NULL,
    non_reverse     VARCHAR(200) NOT NULL,
    turn_ended      BOOLEAN NOT NULL,
    is_fight_phase  BOOLEAN NOT NULL
);
DROP SEQUENCE IF EXISTS games_id_seq;
CREATE SEQUENCE IF NOT EXISTS games_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS library;
CREATE TABLE library
(
    id_library      INTEGER PRIMARY KEY ,
    game_id         INTEGER  NOT NULL,
    player_name     VARCHAR(200) NOT NULL,
    cards_cnt       INTEGER NOT NULL,
    rarity          VARCHAR(200) NOT NULL,
    locked          BOOLEAN NOT NULL
);
DROP SEQUENCE IF EXISTS libraries_id_seq;
CREATE SEQUENCE IF NOT EXISTS libraries_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS library_composition;
CREATE TABLE library_composition
(
    id_card_lib     INTEGER PRIMARY KEY ,
    id_library      INTEGER  NOT NULL,
    id_card         INTEGER NOT NULL
);
DROP SEQUENCE IF EXISTS lib_comp_id_seq;
CREATE SEQUENCE IF NOT EXISTS lib_comp_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS hand;
CREATE TABLE hand
(
    id_hand         INTEGER PRIMARY KEY ,
    name_player     VARCHAR(200)  NOT NULL,
    id_game         INTEGER NOT NULL,
    cards_cnt       INTEGER NOT NULL
);
DROP SEQUENCE IF EXISTS hands_id_seq;
CREATE SEQUENCE IF NOT EXISTS hands_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS hand_composition;
CREATE TABLE hand_composition
(
    id_hand_card    INTEGER PRIMARY KEY ,
    id_hand         INTEGER  NOT NULL,
    id_card         INTEGER NOT NULL
);
DROP SEQUENCE IF EXISTS hand_comp_id_seq;
CREATE SEQUENCE IF NOT EXISTS hand_comp_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS cell;
CREATE TABLE cell
(
    id_cell         INTEGER PRIMARY KEY ,
    id_game         INTEGER NOT NULL,
    cell_num        INTEGER NOT NULL,
    id_card         INTEGER,
    name_owner      VARCHAR(200),
    sickness        INTEGER,
    card_name       VARCHAR(200),
    attack          INTEGER,
    health          INTEGER,
    cost            INTEGER,
    evasion         INTEGER,
    attack_speed    INTEGER,
    movement_speed  INTEGER,
    rarity          VARCHAR(200),
    fraction        VARCHAR(200),
    revenged        BOOLEAN,
    attacked      BOOLEAN
);
DROP SEQUENCE IF EXISTS cell_id_seq;
CREATE SEQUENCE IF NOT EXISTS cell_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS status;
CREATE TABLE IF NOT EXISTS status
(
    id_status       INTEGER PRIMARY KEY ,
    name_player     VARCHAR(200) NOT NULL ,
    id_game         INTEGER NOT NULL,
    babos           INTEGER NOT NULL,
    collectors      INTEGER NOT NULL,
    health          INTEGER NOT NULL
);
DROP SEQUENCE IF EXISTS status_id_seq;
CREATE SEQUENCE IF NOT EXISTS status_id_seq START WITH 1 INCREMENT BY 1;

DROP TYPE IF EXISTS ROLE CASCADE;
CREATE TYPE ROLE AS ENUM ('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN');

DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE IF NOT EXISTS roles
(
    role_id         SERIAL PRIMARY KEY,
    name            VARCHAR(200) NOT NULL
);
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
-- DROP SEQUENCE IF EXISTS role_id_seq;
-- CREATE SEQUENCE IF NOT EXISTS role_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users
(
    user_id         SERIAL PRIMARY KEY,
    username        VARCHAR(200) NOT NULL,
    email           VARCHAR(200) NOT NULL,
    password        VARCHAR(200) NOT NULL
);
DROP SEQUENCE IF EXISTS user_id_seq;
CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1 INCREMENT BY 1;

DROP TABLE IF EXISTS user_roles;
CREATE TABLE IF NOT EXISTS user_roles (
      user_id       INTEGER,
      role_id       INTEGER,
      FOREIGN KEY (user_id) REFERENCES users (user_id),
      FOREIGN KEY (role_id) REFERENCES roles (role_id)
);




DROP TABLE IF EXISTS invites;
CREATE TABLE IF NOT EXISTS invites
(
    invite_id       INTEGER PRIMARY KEY,
    inviter_name    VARCHAR(200) NOT NULL ,
    invited_name    VARCHAR(200) NOT NULL,
    inviter_fraction VARCHAR(200) NOT NULL
);
DROP SEQUENCE IF EXISTS invite_id_seq;
CREATE SEQUENCE IF NOT EXISTS invite_id_seq START WITH 1 INCREMENT BY 1;