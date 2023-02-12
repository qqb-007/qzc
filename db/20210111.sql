ALTER TABLE `food` ADD COLUMN `video` VARCHAR (1000) NULL;
ALTER TABLE `store_user_food` ADD COLUMN meituan_video_id INT (11);
ALTER TABLE `store_user_food` ADD COLUMN clbm_video_id INT (11);