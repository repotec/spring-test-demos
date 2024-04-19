 create table orders (amount float(53),
                     paid boolean, date timestamp(6),
                     id bigint not null,
                     primary key (id))