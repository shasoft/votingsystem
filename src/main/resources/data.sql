INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('Ил Фаро'),
       ('Чайка'),
       ('Волга-Волга');

INSERT INTO MENU (restaurant_id, cooking_at, dishes)
VALUES (1, '2024-11-05', '[{"name":"суп","price":1000},{"name":"компот","price":100}]'),
       (1, '2024-11-04', '[{"name":"компот","price":100},{"name":"хлеб","price":15}]'),
       (2, CURRENT_DATE, '[{"name":"хлеб","price":15},{"name":"суп","price":1000}]'),
       (3, CURRENT_DATE, '[{"name":"хлеб","price":15},{"name":"компот","price":100},{"name":"суп","price":1000}]')
;

INSERT INTO VOTE (create_at, user_id, restaurant_id)
VALUES ('2024-11-05', 1, 1),
       ('2024-11-05', 2, 1),
       (CURRENT_DATE, 2, 2)
;