ALTER TABLE `store_user_food`
  ADD COLUMN `meituan_publish_status` varchar(32) NULL AFTER `category_id`,
  ADD COLUMN `ele_publish_status` varchar(32) NULL AFTER `meituan_publish_status`;

ALTER TABLE `store_user_food`
  ADD  KEY `IDX_MEITUAN_PUBLISH_STATUS`(`meituan_publish_status`),
  ADD  KEY `IDX_ELE_PUBLISH_STATUS`(`ele_publish_status`);

update `store_user_food` set `quote_status` = 'VERIFY_SUCCESS' where `quote_status` = 'PUBLISH_SUCCESS';

update store_user_food set meituan_publish_status = 'SUCCESS' where `quote_status` = 'VERIFY_SUCCESS';

ALTER TABLE `store`
  ADD COLUMN `ele_category_map_json` text NULL AFTER `opening`;

ALTER TABLE `store_user_food`
  ADD COLUMN `ele_photos_json` text NULL AFTER `special_sku_id_list`;

ALTER TABLE `food`
  ADD COLUMN `ele_brand_name` varchar(32) NULL AFTER `warning_price`;

ALTER TABLE `store_user`
  ADD COLUMN `meituan_opened` tinyint(1) NULL AFTER `auto_receipt_order`,
  ADD COLUMN `ele_opened` tinyint(1) NULL AFTER `meituan_opened`;

update store_user set `meituan_opened`  = `opened` , `ele_opened` = 0;

ALTER TABLE `wxc_order`
  CHANGE COLUMN `wm_order_id_view` `wm_order_id_view` BIGINT(20) NULL ,
  CHANGE COLUMN `wm_poi_name` `wm_poi_name` VARCHAR(45) NULL ,
  CHANGE COLUMN `wm_poi_address` `wm_poi_address` VARCHAR(100) NULL ,
  CHANGE COLUMN `wm_poi_phone` `wm_poi_phone` VARCHAR(45) NULL ;
