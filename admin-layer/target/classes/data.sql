INSERT INTO users (email, password, surname, name, patronymic)
VALUES (
        'admin@mail.ru',
        '$2a$12$uqUEJ9BZ0Knb5FrN3cu7PO3Ko2VCjegb1t3isidrw4H1dtmW/YFgO',
        'Султанов',
        'Ильнар',
        'Наилевич'
);

INSERT INTO users (email, password, surname, name, patronymic)
VALUES (
           'user@mail.ru',
           '$2a$12$bOiiHDBFmVfOPZfqtLG0FOkQHsaK9Ly2Ezevs9RjFciRICDMdkRSa',
           'Сарычев',
           'Кирилл',
           'Игоревич'
       );

INSERT INTO roles (title) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1), (2, 2);