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
