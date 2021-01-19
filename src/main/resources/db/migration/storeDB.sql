begin;
drop schema if exists store cascade;
create schema store;

drop table if exists store.address;
drop table if exists store.order;
drop table if exists store.customer;
drop table if exists store.order_item;
drop table if exists store.product;
drop type if exists store.status;

create type store.status AS ENUM ('CREATED', 'CANCELED', 'SENT', 'DELIVERED');

create table store.address
(
    id      serial,
    zipcode integer unique,
    address varchar(50) unique,
    primary key (id)
);

create table store.product
(
    id    serial,
    name  varchar not null,
    price real    not null,
    count integer default 0,
    primary key (id)
);

create table store.customer
(
    id           serial,
    first_name   varchar   not null,
    last_name    varchar   not null,
    phone_number varchar   not null,
    date         timestamp not null,
    primary key (id)
);

create table store.order
(
    id            serial,
    "customer"    serial references store.customer on delete cascade,
    "recipient"   serial references store.address on delete cascade,
    "sender"      serial references store.address on delete cascade,
    "order_items" json      not null,
    created_when  timestamp not null,
    sent_when     timestamp    default null,
    sum           real      not null,
    status        store.status default 'CREATED',
    primary key (id)
);

select *
from store.address;


select *
from store.customer;

select *
from store.product;

select *
from store.order;
commit;