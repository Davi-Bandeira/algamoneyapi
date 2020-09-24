CREATE TABLE person(
    code BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (50) NOT NULL,
    place VARCHAR (50),
    number VARCHAR (50),
    complement VARCHAR (50),
    neighborhood VARCHAR (50),
    cep VARCHAR (50),
    city VARCHAR (50),
    state VARCHAR (50),
    active BOOL NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO person(name, place, number, complement, neighborhood, cep, city, state, active)
values ('Pedro', 'Rua 11', '22', 'casa 2', 'Campinas', '74000000', 'Goiânia', 'Goias', TRUE);

INSERT INTO person(name, place, number, complement, neighborhood, cep, city, state, active)
values ('Ana', 'Rua 3', '15', 'casa 1', 'Setor Sul', '74450000', 'Goiânia', 'Goias', TRUE);

INSERT INTO person(name, place, number, complement, neighborhood, cep, city, state, active)
values ('Roberto', 'Rua 6', '10', 'casa 1', 'Centro', '74555666', 'Goiânia', 'Goias', TRUE);

INSERT INTO person(name, place, number, complement, neighborhood, cep, city, state, active)
values ('Marcela', 'Rua 20', '22', 'casa 2', 'Campinas', '744500000', 'Goiânia', 'Goias', TRUE);