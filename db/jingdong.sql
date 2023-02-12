ALTER TABLE `store_user_food` ADD COLUMN `jddj_publish_status` VARCHAR (32) NULL;
ALTER TABLE `store_user` ADD COLUMN `jddj_opened` TINYINT (1) NULL;
ALTER TABLE food_category ADD COLUMN jddj_id INT (11);
ALTER TABLE `wxc_order` ADD COLUMN `dada_accept` TINYINT (1) NULL;
ALTER TABLE `food`
  ADD COLUMN `jddj_sku_map_json` text NULL;
ALTER TABLE `food` ADD COLUMN `jd_picture` VARCHAR (1000) NULL;