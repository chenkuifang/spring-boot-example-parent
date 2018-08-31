

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pn_user
-- ----------------------------
DROP TABLE IF EXISTS `pn_user`;
CREATE TABLE `pn_user` (
  `id` bigint(20) NOT NULL,
  `user_name` varchar(20) DEFAULT NULL COMMENT '登陆用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '登陆密码',
  `department_id` bigint(20) DEFAULT NULL COMMENT '所属部门',
  `name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `email` varchar(20) DEFAULT NULL COMMENT '电子邮件',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `sex` varchar(2) DEFAULT '1' COMMENT '性别',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` varchar(2) DEFAULT '1' COMMENT '状态 1 正常, 0 停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 增加默认用户
INSERT INTO `pn_user` VALUES (10001, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, 10001, '管理员', '123555', '123456789', '1', 10001, '2017-12-4 15:12:06', '2017-12-4 15:12:09', 1);
INSERT INTO `pn_user` VALUES (10002, 'quifar', 'e10adc3949ba59abbe56e057f20f883e', 10001, 10001, 'quifar', '314', '158', '1', 10001, '2017-12-4 15:12:48', '2017-12-4 15:12:48', 1);


-- 商品信息表 --
DROP TABLE IF EXISTS `pn_goods`;
CREATE TABLE `pn_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_num` varchar(40) DEFAULT NULL COMMENT '商品编码',
  `goods_name` varchar(80) DEFAULT NULL COMMENT '商品名称',
  `sale_price` decimal(20,2) DEFAULT '0.0000' COMMENT '销售价格',
  `discount_price` decimal(20,2) DEFAULT '0.0000' COMMENT '折后价格',
  `stock` bigint(20) DEFAULT '0' COMMENT '库存',
  `sale_count` bigint(20) DEFAULT '0' COMMENT '销量',
  `status` int DEFAULT 1 COMMENT '状态  1:正常, 0:下架',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息表';


-- 订单信息表 --
DROP TABLE IF EXISTS `pn_order`;
CREATE TABLE `pn_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单编码',
  `order_sid` varchar(40) NOT NULL COMMENT '订单号',

  `pay_sid` varchar(40) NOT NULL COMMENT '支付单号(交易号)',
  `pay_way` tinyint(4) DEFAULT NULL COMMENT '付款方式 0 未知, 1 微信支付, 2,支付宝, 3 网银支付',
  `pay_time` datetime DEFAULT NULL COMMENT '订单支付时间',

  `goods_num` varchar(40) DEFAULT NULL COMMENT '商品编码',
  `goods_name` varchar(80) DEFAULT NULL COMMENT '商品名称',
  `price` decimal(20,2) DEFAULT '0.0000' COMMENT '拍下价格',
  `pay_price` decimal(20,2) DEFAULT '0.0000' COMMENT '支付价格',
  `amount` bigint(20) DEFAULT '0' COMMENT '商品数量',
  `buyer_name` varchar(80) DEFAULT NULL COMMENT '收货人',
  `buyer_address` varchar(80) DEFAULT NULL COMMENT '收货地址',
  `buyer_phone` varchar(80) DEFAULT NULL COMMENT '手机',
  `create_id` bigint(20) DEFAULT NULL COMMENT '订单创建账号ID',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '订单最后修改时间',
  `status` int(11) DEFAULT '0' COMMENT '订单状态 0:未付款，1：已付款，2：已发货，3：已完成，4：取消订单，5：支付失败',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';
