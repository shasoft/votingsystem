INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANTS (name)
VALUES ('Ил Фаро'),
       ('Чайка'),
       ('Волга-Волга');

INSERT INTO MENU (restaurant_id, create_at, dishes)
VALUES (1,'2024-11-05','[{"name":"суп","price":1000},{"name":"компот","price":100}]'),
       (1,'2024-11-04','[{"name":"компот","price":100},{"name":"хлеб","price":15}]'),
       (2,'2024-11-05','[{"name":"хлеб","price":15},{"name":"суп","price":1000}]')
;

INSERT INTO VOTE (restaurant_id, create_at, user_id)
VALUES (1,'2024-11-05',1),
       (1,'2024-11-04',1)
;