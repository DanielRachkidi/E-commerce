create table public.Role
(
    id_role   serial      not null primary key,
    role_name varchar(10) not null
);


create table public.User
(

    id_user    serial      not null primary key,
    username   varchar(50) not null,
    password   varchar(50) not null,
    first_name varchar(50) not null,
    last_name  varchar(50) not null,
    telephone  int
);

CREATE TABLE public.UserRole (
                                 id_user INT,
                                 id_role INT,
                                 username VARCHAR(255),
                                 PRIMARY KEY (id_user, id_role),
                                 FOREIGN KEY (id_user) REFERENCES public.User (id_user),
                                 FOREIGN KEY (id_role) REFERENCES public.Role (id_role)
);



create table public.UserInfo
(

    id_userinfo serial      not null primary key,
    id_user     serial      not null,
    address     varchar(100),
    city        varchar(50) not null,
    code_postal int,
    country     varchar(20) not null,
    FOREIGN KEY (id_user) REFERENCES public.User (id_user)

);

create table public.UserPayment
(

    id_payment   serial       not null primary key,
    id_user      serial       not null,
    payment_type varchar(100) not null,
    provider     varchar(50)  not null,
    accountNo    int          not null,
    expiry       DATE,
    FOREIGN KEY (id_user) REFERENCES public.User (id_user)
);

create table public.Shopping_Session
(
    id_session serial not null primary key,
    id_user    serial not null,
    total      int,
    FOREIGN KEY (id_user) REFERENCES public.User (id_user)
);


create table public.Stock
(
    id_stock serial primary key,
    quantity int not null
);

create table public.Product
(
    id_product serial primary key,
    Name       varchar(20) unique not null,
    price      decimal(5, 2)      not null,
    id_stock   serial             not null,
    FOREIGN KEY (id_stock) REFERENCES public.Stock (id_stock)

);


create table public.Cart_Product
(
    id_cart_product serial not null primary key,
    quantity        int,
    id_session      serial not null,
    id_product      serial not null,
    FOREIGN KEY (id_product) REFERENCES public.Product (id_product),
    FOREIGN KEY (id_session) REFERENCES public.Shopping_Session (id_session)

);


create table public.Order_Detail
(
    id_order_detail   serial not null primary key,
    total             int    not null,
    id_payment_detail serial not null,
    id_user           serial not null,
    FOREIGN KEY (id_user) REFERENCES public.User (id_user)
);

create table public.Order_List
(
    id_order_list   serial not null primary key,
    quantity        int    not null,
    id_order_detail serial not null,
    id_product      serial not null,
    FOREIGN KEY (id_order_detail) REFERENCES public.Order_Detail (id_order_detail),
    FOREIGN KEY (id_product) REFERENCES public.Product (id_product)
);



create table public.Payment_Detail
(
    id_payment_detail serial not null primary key,
    amount            int    not null,
    id_order_detail   serial not null,
    provider          int    not null

);
--    ALTER  FOREIGN KEY (id_payment_detail) REFERENCES Payment_Detail(id_payment_detail),
ALTER TABLE public.Order_Detail
    ADD FOREIGN KEY (id_payment_detail) REFERENCES public.Payment_Detail (id_payment_detail);
ALTER TABLE public.Payment_Detail
    ADD FOREIGN KEY (id_order_detail) REFERENCES public.Order_Detail (id_order_detail);

