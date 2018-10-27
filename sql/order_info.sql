CREATE TABLE `order_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `delivery_address_id` bigint(20) DEFAULT NULL COMMENT '收货地址ID',
  `product_name` varchar(16) DEFAULT NULL COMMENT '商品名称（冗余的）',
  `product_count` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `product_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品单价',
  `order_channel` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1:pc;2:android;3:ioc',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态：0:新建未支付；1:已支付；2:已发货；3:已收货；4:已退款; 5:已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;