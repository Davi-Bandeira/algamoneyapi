CREATE TABLE releases(
    code BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    expiration_date DATE NOT NULL,
    payment_date DATE,
    value DECIMAL(10,2) NOT NULL,
    note VARCHAR(100),
    type VARCHAR(20) NOT NULL,
    category_code BIGINT(20) NOT NULL,
    person_code BIGINT(20) NOT NULL,
    FOREIGN KEY (category_code) REFERENCES category(code),
    FOREIGN KEY (person_code) REFERENCES person(code)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO releases(description, expiration_date, payment_date, value, note, type, category_code, person_code)
values ('Salário Mensal', '2020-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 5, 4);

INSERT INTO releases(description, expiration_date, payment_date, value, note, type, category_code, person_code)
values ('Café', '2020-05-10', null, 8.32, 'null', 'DESPESA', 2, 5);

INSERT INTO releases(description, expiration_date, payment_date, value, note, type, category_code, person_code)
values ('Salário Mensal', '2020-06-22', null, 7000.00, 'Distribuição de lucros', 'RECEITA', 5, 5);

INSERT INTO releases(description, expiration_date, payment_date, value, note, type, category_code, person_code)
values ('Jogos', '2020-11-22', null, 100.00, 'GASTOS', 'DESPESA', 12, 5);