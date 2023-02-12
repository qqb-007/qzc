ALTER TABLE food_category ADD COLUMN LEVEL INT (11) NOT NULL;
ALTER TABLE food_category ADD COLUMN parent_id INT (11);
ALTER TABLE food ADD COLUMN parent_category_name VARCHAR (45);