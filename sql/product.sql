CREATE TABLE `product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `product_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `product_img` varchar(64) DEFAULT NULL COMMENT '商品的图片',
  `product_detail` longtext COMMENT '商品的详情介绍',
  `product_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品单价',
  `product_stock` int(11) NOT NULL DEFAULT '0' COMMENT '商品库存,-1表示限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;