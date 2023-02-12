DROP TABLE IF EXISTS `bag`;
CREATE TABLE `bag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_user_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `bag_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bag_num` int(11) NOT NULL,
  `logistics` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fee` float NOT NULL,
  `context` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
   KEY `IDX_USER_ID` (`store_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29893 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
