CREATE TABLE food_supplier
(
  id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  phone varchar(20) NOT NULL,
  password varchar(100),
  name varchar(100) NOT NULL,
  store_user_id int NOT NULL,
  create_time DATETIME NOT NULL,
  update_time DATETIME NOT NULL,
  status varchar(10)
);
ALTER TABLE food_supplier ADD address varchar(200) NULL;
ALTER TABLE food_supplier ADD price_increase_type varchar(30) NOT NULL;
CREATE UNIQUE INDEX food_supplier_phone_uindex ON food_supplier (phone);

ALTER TABLE store_user_food ADD food_supplier_id int NULL;
CREATE INDEX store_user_food_food_supplier_index ON store_user_food (food_supplier_id);

ALTER TABLE store_user_food ADD supplier_quote_price float DEFAULT 0 NULL;
ALTER TABLE store_user_food ADD supplier_increase float DEFAULT 0 NULL;
ALTER TABLE store_user_food ADD supplier_alter_quote_price float DEFAULT 0 NULL;

update store_user_food set supplier_increase = 10, supplier_quote_price = quote_price * 0.9,
  wxc.store_user_food.supplier_alter_quote_price = wxc.store_user_food.quote_price * 0.9;

ALTER TABLE order_detail ADD supplier_quote_price float DEFAULT 0 NULL;
ALTER TABLE order_detail ADD food_supplier_id int NULL;
CREATE INDEX order_detail_food_supplier_index ON order_detail (food_supplier_id);

ALTER TABLE order_detail ADD quote_unit_ratio float DEFAULT 0 NULL;


alter table store_food_application
    add food_supplier_id int null;
