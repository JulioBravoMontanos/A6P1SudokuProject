-- Creació del diagrama ER: https://app.diagrams.net/?src=about

DROP TABLE IF EXISTS Play;
DROP TABLE IF EXISTS Saved;
DROP TABLE IF EXISTS Player;
DROP TABLE IF EXISTS Board;
DROP TABLE IF EXISTS BoardLevel;


CREATE TABLE BoardLevel (
    id              serial          PRIMARY KEY,
    name            varchar(32),
    points          int
);

CREATE TABLE Board (
    id              serial          PRIMARY KEY,
    filename        varchar(64)     UNIQUE,
    id_level        int             REFERENCES BoardLevel(id)
);

CREATE TABLE Player (
    uname           varchar(32)     PRIMARY KEY,
    creation_date   date
);

CREATE TABLE Saved (
    id_board        int             REFERENCES Board(id),
    uname           varchar(32)     REFERENCES Player(uname),
    PRIMARY KEY(id_board, uname)
);

CREATE TABLE Play (
    play_date       date,
    id_board        int             REFERENCES Board(id),
    uname           varchar(32)     REFERENCES Player(uname),
    PRIMARY KEY(id_board, uname)
);


-- INSERT --
INSERT INTO BoardLevel (name, points) VALUES ('easy', 5);                   -- id: 1 --
INSERT INTO BoardLevel (name, points) VALUES ('medium', 10);                -- id: 2 --
INSERT INTO BoardLevel (name, points) VALUES ('hard', 15);                  -- id: 3 --
INSERT INTO BoardLevel (name, points) VALUES ('expert', 30);                -- id: 4 --
INSERT INTO BoardLevel (name, points) VALUES ('master', 60);                -- id: 5 --


INSERT INTO Board (filename, id_level) VALUES ('sudoku1.medium.txt', 2);    -- id: 1 --
