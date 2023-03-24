
    alter table customers
        drop constraint billingAddress_country_id;

    alter table product_reservations
        drop constraint fk_bp_reservation_id;

    alter table product_reservations
        drop constraint fk_product_reservations_2_product;

    alter table reservations
        drop constraint fk_reservation_customer;

    alter table service_reservations
        drop constraint fk_barber_id;

    alter table service_reservations
        drop constraint fk_bs_reservation_id;

    alter table service_reservations
        drop constraint fk_service_reservations_2_service;

    drop table if exists barbers CASCADE ;

    drop table if exists countries CASCADE ;

    drop table if exists customers CASCADE ;

    drop table if exists product_reservations CASCADE ;

    drop table if exists products CASCADE ;

    drop table if exists reservations CASCADE ;

    drop table if exists service_reservations CASCADE ;

    drop table if exists services CASCADE ;

    drop sequence if exists hibernate_sequence;

    drop table if exists flyway_schema_history;

