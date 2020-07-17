CREATE SCHEMA IF NOT EXISTS ims;
create table if not exists ims.customers(id int primary key auto_increment, first_name varchar(40), surname varchar(40));
create table if not exists ims.products(Product_id int primary key auto_increment, Product_name varchar(50), Price FLOAT(30, 2));
CREATE TABLE if not exists ims.Basket(Order_id int NOT NULL auto_increment,fk_cust_id int NOT NULL,fk_product_id int,orRef int not null,PRIMARY KEY(Order_id),FOREIGN KEY (fk_cust_id) REFERENCES customers(id),FOREIGN KEY (fk_product_id) REFERENCES products(Product_id));
