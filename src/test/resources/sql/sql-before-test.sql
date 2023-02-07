truncate table orders restart identity cascade ;
truncate table cars restart identity cascade;
truncate table user_role restart identity cascade;
truncate table passports restart identity cascade;
truncate table users restart identity cascade;

INSERT INTO users(username, password, active, email, created_at, updated_at)
VALUES ('admin', '11111111', true, 'stasonpokemon@icloud.com', now(), now()),
       ('user', '11111111', true, 'egorgus123@gmail.com', now(), now()),
       ('test', '11111111', true, 'test1@gmail.com', now(), now()),
       ('test1', '11111111', true, 'test2@gmail.com', now(), now());
INSERT INTO users(username, password, active, email, created_at, updated_at, activation_code)
VALUES ('test_code', '11111111', true, 'test_code@icloud.com', now(), now(), 'test-code');


INSERT INTO passports(address, birthday, name, patronymic, surname, created_at, updated_at, user_id)
VALUES ('dsfddsf', now(), 'asfdsad', 'asdsadasd', 'sadasdasd', now(), now(), 1),
       ('test', now(), 'test', 'test', 'test', now(), now(), 2),
       ('test1', now(), 'test1', 'test1', 'test1', now(), now(), 3);



INSERT INTO user_role(user_id, roles)
VALUES (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (4, 'USER');

INSERT INTO cars(producer, model, release_date, price_per_day, employment_status, damage_status, img_link,
                 is_deleted, is_broken, created_at, updated_at)
VALUES ('Audi', 'A7', '01-01-2015', 290, true, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=640acbdf28144e25d6bcb0910a8d5f36-5330388-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('BMW', 'm3', '01-01-2017', 400, true, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=45c37ba01c9998628492f293a45a110a-5887419-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('Mercedes', '600', '01-01-2003', 110, true, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=2a0000017a06d13e75e6bf48ed2fc6652583-4751583-images-thumbs&n=13', false,
        false, now(), now()),
       ('BMW', 'I8', '01-01-2021', 570, true, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=f5dc107fb114e6c831200ddd59d82a66-5503033-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('Bentley', 'Continental gt', '01-01-2021', 700, true, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=0c7086819b8e7e59b4655fa56210d526-5140197-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('Maclaren', '720s', ' 01-01-2021', 800, true, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=2935e7d83106b4c9d0e7e3ef32317107-5436885-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('Ferrari', '488 Pista', '01-01-2018', 500, false, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=a1a43df466e944e61a1d5b750adf2af2-4407062-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('Nissan', 'GTR', '01-01-2017', 700, false, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=0c11cd4e88b398ed905ce966735cfc58-4954930-images-thumbs&n=13', false, false,
        now(),
        now());

insert into orders(order_date, order_status, price, rental_period, car_id, user_id, created_at,
                   updated_at)
VALUES (now(), 'UNDER_CONSIDERATION', 1000, 2, 7, 2, now(), now()),
       (now(), 'UNDER_CONSIDERATION', 1400, 2, 8, 2, now(), now());

