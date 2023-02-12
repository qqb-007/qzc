ALTER TABLE wxc_order ADD `cansun` float DEFAULT '0';
ALTER TABLE wxc_order ADD `cansun_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL;
ALTER TABLE settlement_sheet_detail ADD `cansun_num` int(11) DEFAULT '0';
ALTER TABLE settlement_sheet_detail ADD `cansun_amount` int(11) DEFAULT '0';
ALTER TABLE settlement_sheet ADD `cansun_num` int(11) DEFAULT '0';
ALTER TABLE settlement_sheet ADD `cansun_amount` int(11) DEFAULT '0';
