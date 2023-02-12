ALTER TABLE `food` ADD COLUMN `clb_picture` VARCHAR (1000) NULL;
ALTER TABLE `store_user_food` ADD COLUMN `clbm_publish_status` VARCHAR (32) NULL;
ALTER TABLE `store_user` ADD COLUMN `clbm_opened` TINYINT (1) NULL;
ALTER TABLE `food` ADD COLUMN `clb_picture_id` VARCHAR (100) NULL;