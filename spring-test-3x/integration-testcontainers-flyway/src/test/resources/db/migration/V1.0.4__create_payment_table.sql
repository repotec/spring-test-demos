create table payment (id bigint not null,
                    order_id bigint unique,
                    credit_card_number varchar(255),
                    primary key (id))