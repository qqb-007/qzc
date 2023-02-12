ALTER TABLE store_user ADD lat varchar(30) NULL;
ALTER TABLE store_user ADD lng varchar(30) NULL;

ALTER TABLE store_user ADD ps_contact_name varchar(30) NULL;
ALTER TABLE store_user ADD ps_contact_phone varchar(20) NULL;

ALTER TABLE store_user ADD biz_start_time varchar(5) NULL;

ALTER TABLE store_user ADD biz_end_time varchar(5) NULL;
ALTER TABLE store_user ADD mtps_category int NULL;

ALTER TABLE store_user ADD mtps_second_category int NULL;

ALTER TABLE store_user ADD delivery_type varchar(20) NULL;

ALTER TABLE store_user ADD mtps_shop_id varchar(20) NULL;

update store_user set delivery_type='PLATFORM';

ALTER TABLE wxc_order ADD delivery_type varchar(20) NULL;

update wxc_order set delivery_type = 'PLATFORM';

ALTER TABLE store ADD delivery_type varchar(20) NULL;

update store set delivery_type = 'PLATFORM';

ALTER TABLE wxc_order ADD lat double NULL;
ALTER TABLE wxc_order ADD lng double NULL;
ALTER TABLE wxc_order ADD delivery_time int NULL;
ALTER TABLE wxc_order ADD expected_delivery_time int NULL;
ALTER TABLE wxc_order ADD pick_type varchar(10) NULL;

ALTER TABLE wxc_order ADD mt_peisong_id varchar(50) NULL;


ALTER TABLE wxc_order ADD delivery_status varchar(20) NULL;

ALTER TABLE wxc_order ADD ps_delivery_id VARCHAR(30) NULL;

ALTER TABLE wxc_order ADD ps_courier_name varchar(20) NULL;

ALTER TABLE wxc_order ADD ps_courier_phone varchar(20) NULL;

ALTER TABLE wxc_order ADD ps_cancel_reason_id int NULL;

ALTER TABLE wxc_order ADD ps_cancel_reason varchar(500) NULL;
ALTER TABLE wxc_order ADD cancel_reason varchar(500) NULL;
ALTER TABLE wxc_order ADD cancel_reason_code int NULL;

ALTER TABLE store_user ADD shipping_fee float NULL;
ALTER TABLE store_user ADD min_price float NULL;

ALTER TABLE `wxc_order`
  ADD  KEY `IDX_DELIVERY_STATUS`(`delivery_status`),
  ADD  KEY `IDX_EXPECTED_DELIVERY_TIME`(`expected_delivery_time`(13));
