create table Comapny(
                        ID_company  serial primary key ,
                        Name varchar (20) unique not null,
                        Address varchar (50)
);

create table Stock(
                      ID_stock serial primary key,
                      quantity int not null ,
                      ID_company int not null ,
                      FOREIGN KEY (ID_company) REFERENCES Comapny(ID_company)

);


create table Product(
                        ID_product serial primary key ,
                        Name varchar(20) unique not null,
                        Description varchar (100),
                        price decimal(5,2) not null,
                        ID_stock int not null ,
                        FOREIGN KEY (ID_stock) REFERENCES Stock(ID_stock)

);


