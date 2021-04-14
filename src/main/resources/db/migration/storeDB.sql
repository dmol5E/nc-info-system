begin;
drop schema if exists store cascade;
create schema store;

drop table if exists store.address;
drop table if exists store.order;
drop table if exists store.customer;
drop table if exists store.order_item;
drop table if exists store.product;
drop table if exists store.product_history;
drop table if exists store.user;
drop type if exists store.status;
drop type if exists store.status_order;

create type store.status AS enum ('ACTIVE', 'NOT_ACTIVE', 'DELETED');

create type store.status_order AS enum ('CREATED', 'CANCELED', 'SENT', 'DELIVERED');

create table store.address
(
    id                                  serial,
    zipcode                             varchar         unique,
    address                             varchar(50)     unique,
    status                              store.status    default 'ACTIVE',
    created                             timestamp       default now(),
    updated                             timestamp       default now(),
    primary key (id)
);

create table store.product
(
    id                                  serial,
    name                                varchar         not null,
    price                               real            not null check (price > 0),
    count                               integer         default 0,
    status                              store.status    default 'ACTIVE',
    created                             timestamp       default now(),
    updated                             timestamp       default now(),
    primary key (id)
);

create table store.customer
(
    id                                  serial,
    first_name                          varchar         not null,
    last_name                           varchar         not null,
    phone_number                        varchar         not null,
    date                                timestamp       not null,
    status                              store.status    default 'ACTIVE',
    created                             timestamp       default now(),
    updated                             timestamp       default now(),
    primary key (id)
);

create table store.product_history
(
    id                                  serial,
    name                                varchar         not null,
    price                               real            not null,
    status                              store.status    default 'ACTIVE',
    created                             timestamp       default now(),
    updated                             timestamp       default now(),
    primary key (id)
);

create table store.order
(
    id                                  serial,
    "customer"                          integer         references store.customer,
    "recipient"                         integer         references store.address,
    "sender"                            integer         references store.address,
    created_when                        timestamp       not null,
    sent_when                           timestamp       default null,
    sum                                 real            not null,
    status_order                        store.status    default 'CREATED',
    status                              store.status    default 'ACTIVE',
    created                             timestamp       default now(),
    updated                             timestamp       default now(),
    primary key (id)
);

create table store.order_item
(
    id                                  serial,
    name                                varchar         not null,
    price                               real            not null,
    count                               integer         not null,
    order_id                            integer,
    product_history_id                  integer,
    status                              store.status    default 'ACTIVE',
    created                             timestamp       default now(),
    updated                             timestamp       default now(),
    foreign key (product_history_id)                    references  store.product_history,
    foreign key (order_id)                              references store.order,
    primary key (id)
);

create table store.user
(
  id                                    serial,
  first_name                            varchar          not null,
  last_name                             varchar          not null,
  login                                 varchar          unique not null,
  password                              varchar          not null,
  status                                store.status     default 'ACTIVE',
  created                               timestamp        default now(),
  updated                               timestamp        default now(),
  primary key (id)
);

select * from store.order;
select * from store.product_history;
select * from store.product;
select * from store.customer;
select * from store.address;

select
    c.id,c.first_name, c.last_name,c.phone_number, c.date
from store.customer c
where position(lower('Да') in lower(concat(c.first_name,' ', c.last_name))) <> 0;


commit;