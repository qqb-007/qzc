ALTER TABLE food_category ADD COLUMN wante_id INT (11);
ALTER TABLE `store_user`
  ADD COLUMN `wante_opened` tinyint(1) NULL AFTER `ele_opened`;
ALTER TABLE `store_user_food`
  ADD COLUMN `wante_id` INT(11) NULL ;
ALTER TABLE `store_user_food`
  ADD COLUMN `wante_publish_status` varchar(32) NULL AFTER `category_id`;

ALTER TABLE `store_user_food`
  ADD COLUMN `wante_sku_map_json` text NULL;