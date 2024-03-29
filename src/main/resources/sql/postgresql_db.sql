create database car_rental_spring_rest;
\c;

create table cars
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY       NOT NULL,
    damage_status     CHARACTER VARYING(1000),
    is_deleted        BOOLEAN                                       NOT NULL,
    employment_status BOOLEAN                                       NOT NULL,
    img_link          CHARACTER VARYING(1000),
    model             CHARACTER VARYING(255)                        NOT NULL,
    price_per_day     DOUBLE PRECISION CHECK ( price_per_day >= 0 ) NOT NULL,
    producer          CHARACTER VARYING(255)                        NOT NULL,
    release_date      DATE                                          NOT NULL,
    is_broken         BOOLEAN                                       NOT NULL,
    created_at        TIMESTAMP                                     NOT NULL,
    updated_at        TIMESTAMP                                     NOT NULL,
    CONSTRAINT car_pk PRIMARY KEY (id)
);


create table passports
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    address    CHARACTER VARYING(500)                  NOT NULL,
    birthday   DATE                                    NOT NULL,
    name       CHARACTER VARYING(255)                  NOT NULL,
    patronymic CHARACTER VARYING(255),
    surname    CHARACTER VARYING(255)                  NOT NULL,
    created_at TIMESTAMP                               NOT NULL,
    updated_at TIMESTAMP                               NOT NULL,
    CONSTRAINT passport_pk PRIMARY KEY (id)
);

create table refunds
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    damage_description CHARACTER VARYING(500),
    damage_status      BOOLEAN                                 NOT NULL,
    price              DOUBLE PRECISION                        NOT NULL,
    refund_date        TIMESTAMP                               NOT NULL,
    created_at         TIMESTAMP                               NOT NULL,
    updated_at         TIMESTAMP                               NOT NULL,
    CONSTRAINT refund_pk PRIMARY KEY (id)
);


create table users
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    activation_code CHARACTER VARYING(255),
    active          BOOLEAN                                 NOT NULL,
    email           CHARACTER VARYING(255) UNIQUE           NOT NULL,
    password        CHARACTER VARYING(255)                  NOT NULL,
    username        CHARACTER VARYING(255) UNIQUE           NOT NULL,
    passport_id     BIGINT UNIQUE,
    created_at      TIMESTAMP                               NOT NULL,
    updated_at      TIMESTAMP                               NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_passport_fk FOREIGN KEY (passport_id) REFERENCES passports (id) ON DELETE CASCADE
);

create table user_role
(
    user_id BIGINT       NOT NULL,
    roles   varchar(255) NOT NULL,
    CONSTRAINT user_role_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);


create type order_status_enum as enum ('UNDER_CONSIDERATION', 'REFUSAL', 'CONFIRMED');
create table orders
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    order_date    TIMESTAMP                               NOT NULL,
    order_status  order_status_enum                       NOT NULL,
    price         double precision                        NOT NULL,
    rental_period INT                                     NOT NULL,
    car_id        BIGINT                                  NOT NULL,
    refund_id     BIGINT UNIQUE,
    user_id       BIGINT                                  NOT NULL,
    created_at    TIMESTAMP                               NOT NULL,
    updated_at    TIMESTAMP                               NOT NULL,
    CONSTRAINT order_pk PRIMARY KEY (id),
    CONSTRAINT order_car_fk FOREIGN KEY (car_id) REFERENCES cars (id),
    CONSTRAINT order_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT order_refund_fk FOREIGN KEY (refund_id) REFERENCES refunds (id)
);

INSERT INTO passports(address, birthday, name, patronymic, surname, created_at, updated_at)
VALUES ('dsfddsf', now(), 'asfdsad', 'asdsadasd', 'sadasdasd', now(), now());

INSERT INTO users(username, password, active, email, passport_id, created_at, updated_at)
VALUES ('user', '11111111', true, 'egorgus123@gmail.com', 1, now(), now());

INSERT INTO users(username, password, active, email, created_at, updated_at)
VALUES ('admin', '11111111', true, 'stasonpokemon@icloud.com', now(), now());

INSERT INTO user_role(user_id, roles)
VALUES (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER');

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

select * from users;
select * from passports;