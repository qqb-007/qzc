ALTER TABLE `store_user_food` ADD COLUMN `quote_price_lock` TINYINT (1) NULL AFTER `quote_price`;
ALTER TABLE `store_user_food` ADD COLUMN `original_quote_price` FLOAT (10) NULL AFTER `quote_price`;