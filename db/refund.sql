ALTER TABLE wxc_order ADD refund_type varchar(20) NULL;
ALTER TABLE order_detail ADD refunding TINYINT (1) NULL;
ALTER TABLE wxc_order ADD ele_refund_id varchar(50) NULL;