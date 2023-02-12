ALTER TABLE `wxc`.`wxc_order`
  DROP COLUMN `settled`,
  ADD COLUMN `biz_status` VARCHAR(45) NOT NULL AFTER `extra_list_json`;

ALTER TABLE `wxc`.`wxc_order`
  CHANGE COLUMN `cost_total` `cost_total` FLOAT NOT NULL DEFAULT 0 ,
  CHANGE COLUMN `cost_refund` `cost_refund` FLOAT NOT NULL DEFAULT 0 ;


ALTER TABLE `wxc`.`order_detail`
  CHANGE COLUMN `sku_id` `sku_id` VARCHAR(45) NULL ,
  CHANGE COLUMN `spec` `spec` VARCHAR(45) NULL ,
  CHANGE COLUMN `quote_price` `quote_price` FLOAT NULL ,
  CHANGE COLUMN `food_id` `food_id` INT(11) NULL ;


update wxc_order set biz_status = 'WAIT_SETTLE' where biz_status = '';
update wxc_order set refund_money = 0 where refund_money is null;