begin;
drop schema if exists store cascade;
create schema store;

drop table if exists store.address;
drop table if exists store.order;
drop table if exists store.customer;
drop table if exists store.order_item;
drop table if exists store.product;
drop table if exists store.product_history;
drop type if exists store.status;

create type store.status AS enum ('CREATED', 'CANCELED', 'SENT', 'DELIVERED');

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
    price real    not null check (price > 0),
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

create table store.product_history
(
    id    serial,
    name  varchar not null,
    price real    not null,
    primary key (id)
);

create table store.order
(
    id            serial,
    "customer"    integer references store.customer,
    "recipient"   integer references store.address,
    "sender"      integer references store.address,
    created_when  timestamp not null,
    sent_when     timestamp    default null,
    sum           real      not null,
    status        store.status default 'CREATED',
    primary key (id)
);

create table store.order_item
(
    id    serial,
    name  varchar not null,
    price real    not null,
    count integer not null,
    order_id integer,
    product_history_id integer,
    foreign key (product_history_id) references  store.product_history,
    foreign key (order_id) references store.order,
    primary key (id)
);

select * from store.order_item;
select * from store.order;
select * from store.product_history;
select * from store.product;
select * from store.customer;
select * from store.address;

commit;