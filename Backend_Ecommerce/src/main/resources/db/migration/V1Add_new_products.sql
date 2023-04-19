
create table Comapny(
                        ID_company  serial primary key ,
                        Name varchar (20) unique not null,
                        Address varchar (50)
);

create table Stock(
                      ID_stock serial primary key,
                      quantity int not null


);


create table Product(
                        ID_product serial primary key ,
                        Name varchar(20)  not null,
                        price decimal(5,2) not null,
                        ID_stock int not null ,
                        FOREIGN KEY (ID_stock) REFERENCES Stock(ID_stock)

);



