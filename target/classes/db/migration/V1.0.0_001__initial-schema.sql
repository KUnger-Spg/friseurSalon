create sequence hibernate_sequence start with 1 increment by 1;

    create table barbers (
       id bigint not null,
        first_name varchar(32) not null,
        last_name varchar(64) not null,
        password varchar(32) not null,
        user_name varchar(255),
        version integer not null,
        email_addess varchar(128),
        email_type varchar(1) CHECK (email_type IN ('B', 'P')),
        mobile_phone_number_area_code integer,
        mobile_phone_number_country_code integer,
        mobile_phone_number_serial_number varchar(16),
        nick_name varchar(32) not null,
        primary key (id)
    );

    create table countries (
       id bigint not null,
        iso2code varchar(2),
        name varchar(64),
        version integer not null,
        primary key (id)
    );

    create table customers (
       id bigint not null,
        first_name varchar(32) not null,
        last_name varchar(64) not null,
        password varchar(32) not null,
        user_name varchar(255),
        version integer not null,
        billing_address_city varchar(64),
        billing_address_street_number varchar(64),
        billing_address_zip_code varchar(16),
        email_address varchar(128),
        email_type varchar(1) CHECK (email_type IN ('B', 'P')),
        gender varchar(1),
        mobile_phone_number_area_code integer,
        mobile_phone_number_country_code integer,
        mobile_phone_number_serial_number varchar(16),
        billing_address_country_id bigint,
        primary key (id)
    );

    create table product_reservations (
       product_id bigint not null,
        product_amount integer,
        product_ts timestamp,
        reservation_id bigint
    );

    create table products (
       id bigint not null,
        creation_ts timestamp not null,
        product_currency varchar(16) not null,
        product_name varchar(64) not null,
        product_preis double precision not null,
        version integer not null,
        primary key (id)
    );

    create table reservations (
       id bigint not null,
        creation_ts timestamp not null,
        reservation_name varchar(64),
        version integer,
        customer_id bigint,
        primary key (id)
    );

    create table service_reservations (
       service_id bigint not null,
        barber_id bigint,
        reservation_id bigint,
        service_amount integer,
        service_ts timestamp
    );

    create table services (
       id bigint not null,
        creation_ts timestamp not null,
        service_currency varchar(16) not null,
        service_duration bigint not null,
        service_name varchar(64) not null,
        service_preis double precision not null,
        version integer not null,
        primary key (id)
    );

    alter table customers 
       add constraint billingAddress_country_id 
       foreign key (billing_address_country_id) 
       references countries;

    alter table product_reservations 
       add constraint fk_bp_reservation_id 
       foreign key (reservation_id) 
       references reservations;

    alter table product_reservations 
       add constraint fk_product_reservations_2_product 
       foreign key (product_id) 
       references products;

    alter table reservations 
       add constraint fk_reservation_customer 
       foreign key (customer_id) 
       references customers;

    alter table service_reservations 
       add constraint fk_barber_id 
       foreign key (barber_id) 
       references barbers;

    alter table service_reservations 
       add constraint fk_bs_reservation_id 
       foreign key (reservation_id) 
       references reservations;

    alter table service_reservations 
       add constraint fk_service_reservations_2_service 
       foreign key (service_id) 
       references services;


