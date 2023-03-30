CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table cars
(
    id            UUID DEFAULT uuid_generate_v4()               NOT NULL,
    damage_status CHARACTER VARYING(255),
    is_deleted    BOOLEAN                                       NOT NULL,
    is_busy       BOOLEAN                                       NOT NULL,
    img_link      CHARACTER VARYING(1000)                       NOT NULL,
    model         CHARACTER VARYING(255)                        NOT NULL,
    price_per_day DOUBLE PRECISION CHECK ( price_per_day >= 0 ) NOT NULL,
    producer      CHARACTER VARYING(255)                        NOT NULL,
    release_date  DATE                                          NOT NULL,
    is_broken     BOOLEAN                                       NOT NULL,
    created_at    TIMESTAMP                                     NOT NULL,
    updated_at    TIMESTAMP                                     NOT NULL,
    CONSTRAINT car_pk PRIMARY KEY (id)
);

create table users
(
    id              UUID DEFAULT uuid_generate_v4() NOT NULL,
    activation_code CHARACTER VARYING(255),
    active          BOOLEAN                         NOT NULL,
    email           CHARACTER VARYING(255)          NOT NULL UNIQUE,
    password        CHARACTER VARYING(255)          NOT NULL,
    username        CHARACTER VARYING(255)          NOT NULL UNIQUE,
    created_at      TIMESTAMP                       NOT NULL,
    updated_at      TIMESTAMP                       NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

create table passports
(
    id         UUID DEFAULT uuid_generate_v4() NOT NULL,
    address    CHARACTER VARYING(500)          NOT NULL,
    birthday   DATE                            NOT NULL,
    name       CHARACTER VARYING(255)          NOT NULL,
    patronymic CHARACTER VARYING(255)          NOT NULL,
    surname    CHARACTER VARYING(255)          NOT NULL,
    created_at TIMESTAMP                       NOT NULL,
    updated_at TIMESTAMP                       NOT NULL,
    user_id    UUID UNIQUE,
    CONSTRAINT passport_pk PRIMARY KEY (id),
    CONSTRAINT passport_user_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

);

create table refunds
(
    id                 UUID DEFAULT uuid_generate_v4() NOT NULL,
    damage_description CHARACTER VARYING(500),
    damage_status      BOOLEAN                         NOT NULL,
    price              DOUBLE PRECISION                NOT NULL,
    refund_date        TIMESTAMP                       NOT NULL,
    created_at         TIMESTAMP                       NOT NULL,
    updated_at         TIMESTAMP                       NOT NULL,
    CONSTRAINT refund_pk PRIMARY KEY (id)
);

create table user_role
(
    user_id UUID         NOT NULL,
    roles   varchar(255) NOT NULL,
    CONSTRAINT user_role_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

create table orders
(
    id            UUID                                                 DEFAULT uuid_generate_v4() NOT NULL,
    order_date    TIMESTAMP                                                                       NOT NULL,
    order_status  INT CHECK ( order_status >= 0 AND order_status <= 3) DEFAULT 0                  NOT NULL,
    price         double precision                                                                NOT NULL,
    rental_period INT                                                                             NOT NULL,
    car_id        UUID                                                                            NOT NULL,
    refund_id     UUID UNIQUE,
    user_id       UUID                                                                            NOT NULL,
    created_at    TIMESTAMP                                                                       NOT NULL,
    updated_at    TIMESTAMP                                                                       NOT NULL,
    CONSTRAINT order_pk PRIMARY KEY (id),
    CONSTRAINT order_car_fk FOREIGN KEY (car_id) REFERENCES cars (id),
    CONSTRAINT order_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT order_refund_fk FOREIGN KEY (refund_id) REFERENCES refunds (id)
);

INSERT INTO users(id, username, password, active, email, created_at, updated_at)
VALUES ('011ebe9c-ced6-11ed-a81c-0242ac140002', 'admin', '11111111', true, 'stasonpokemon@icloud.com', now(), now()),
       ('c2dc3f28-c401-4cb0-8ce6-9501fb82e166', 'user', '11111111', true, 'egorgus123@gmail.com', now(), now()),
       ('d1c48c1e-99b4-41b5-8e50-1235ce728aaf', 'test', '11111111', true, 'test1@gmail.com', now(), now()),
       ('240184de-710e-4603-ac86-c150cca7b84b', 'test1', '11111111', true, 'test2@gmail.com', now(), now());


INSERT INTO passports(address, birthday, name, patronymic, surname, created_at, updated_at, user_id)
VALUES ('dsfddsf', now(), 'asfdsad', 'asdsadasd', 'sadasdasd', now(), now(), '011ebe9c-ced6-11ed-a81c-0242ac140002'),
       ('test', now(), 'test', 'test', 'test', now(), now(), 'c2dc3f28-c401-4cb0-8ce6-9501fb82e166'),
       ('test1', now(), 'test1', 'test1', 'test1', now(), now(), '240184de-710e-4603-ac86-c150cca7b84b');



INSERT INTO user_role(user_id, roles)
VALUES ('011ebe9c-ced6-11ed-a81c-0242ac140002', 'ADMIN'),
       ('011ebe9c-ced6-11ed-a81c-0242ac140002', 'USER'),
       ('c2dc3f28-c401-4cb0-8ce6-9501fb82e166', 'USER'),
       ('d1c48c1e-99b4-41b5-8e50-1235ce728aaf', 'USER'),
       ('240184de-710e-4603-ac86-c150cca7b84b', 'USER');

INSERT INTO cars(id, producer, model, release_date, price_per_day, is_busy, damage_status, img_link,
                 is_deleted, is_broken, created_at, updated_at)
VALUES ('695fedf4-bd75-4b38-bfcc-5f498b333028', 'Audi', 'A7', '01-01-2015', 290, false, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=640acbdf28144e25d6bcb0910a8d5f36-5330388-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('ba39515d-0da5-4af9-a398-f5637777608d', 'BMW', 'm3', '01-01-2017', 400, false, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=45c37ba01c9998628492f293a45a110a-5887419-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('32d0f45b-5927-48b9-bdb7-faf9fef4463e', 'Mercedes', '600', '01-01-2003', 110, false, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=2a0000017a06d13e75e6bf48ed2fc6652583-4751583-images-thumbs&n=13', false,
        false, now(), now()),
       ('940a67c2-32e1-40f4-b9e6-ebb557a9391c', 'BMW', 'I8', '01-01-2021', 570, false, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=f5dc107fb114e6c831200ddd59d82a66-5503033-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('d99e82f1-8bec-4961-b542-ce95ae1a287a', 'Bentley', 'Continental gt', '01-01-2021', 700, false, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=0c7086819b8e7e59b4655fa56210d526-5140197-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('6f93489f-6bdc-47ba-a04c-6cc47fb1533a', 'Maclaren', '720s', ' 01-01-2021', 800, false, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=2935e7d83106b4c9d0e7e3ef32317107-5436885-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('8c091ab8-ed54-4b64-9931-cf9fcb1081d5', 'Ferrari', '488 Pista', '01-01-2018', 500, true, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=a1a43df466e944e61a1d5b750adf2af2-4407062-images-thumbs&n=13', false, false,
        now(),
        now()),
       ('a61bf792-7646-4811-ba55-40a706f81094', 'Nissan', 'GTR', '01-01-2017', 700, true, 'Without damage',
        'https://avatars.mds.yandex.net/i?id=0c11cd4e88b398ed905ce966735cfc58-4954930-images-thumbs&n=13', false, false,
        now(),
        now());

insert into orders(order_date, order_status, price, rental_period, car_id, user_id, created_at,
                   updated_at)
VALUES (now(), 0, 1000, 2, '8c091ab8-ed54-4b64-9931-cf9fcb1081d5', '011ebe9c-ced6-11ed-a81c-0242ac140002', now(),
        now()),
       (now(), 0, 1400, 2, 'a61bf792-7646-4811-ba55-40a706f81094', 'c2dc3f28-c401-4cb0-8ce6-9501fb82e166', now(),
        now());
