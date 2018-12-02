/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : db_vote

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 21/11/2018 20:47:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theme` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '投票主题',
  `option_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投票的选项id列表',
  `user_id` int(11) NOT NULL COMMENT '创建投票的用户id',
  `start_time` datetime(0) NOT NULL COMMENT '投票开始时间',
  `stop_time` datetime(0) NOT NULL COMMENT '投票结束时间',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '投票类型，1为单选，2为多选，3为评分制',
  `number` int(11) NOT NULL DEFAULT 1 COMMENT '可选选项的数目，默认一项，即单选',
  `count` int(11) NOT NULL DEFAULT 0 COMMENT '候选人人数',
  `is_stop` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '2' COMMENT '是否停止，默认启动',
  `is_waiver` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否能弃权，默认不能弃权',
  `is_oppose` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否可投反对票，默认不能反对',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (4, '选举班长', '10,11,12', 1, '2018-12-01 14:30:00', '2018-12-01 16:30:00', '1', 1, 3, '2', '0', '0');
INSERT INTO `item` VALUES (5, '选举舍长', '13,14', 1, '2018-11-12 10:30:00', '2018-11-12 11:00:00', '1', 1, 2, '2', '0', '0');
INSERT INTO `item` VALUES (6, '选举村长', '15,16,17', 1, '2018-11-20 10:00:00', '2018-11-20 12:00:00', '2', 2, 3, '2', '1', '1');
INSERT INTO `item` VALUES (7, '选择校长', '18,19,20', 1, '2018-11-10 00:00:12', '2018-11-29 00:13:56', '1', 1, 3, '2', '0', '0');
INSERT INTO `item` VALUES (9, '选举地球球长', '25,26,27,28', 1, '2018-11-10 10:10:10', '2018-11-30 10:10:10', '2', 2, 4, '2', '0', '0');
INSERT INTO `item` VALUES (10, '选举大大', '29,30,31', 1, '2018-10-30 10:10:10', '2018-10-30 10:30:40', '1', 1, 3, '2', '0', '0');

-- ----------------------------
-- Table structure for item_options
-- ----------------------------
DROP TABLE IF EXISTS `item_options`;
CREATE TABLE `item_options`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL COMMENT '投票主题id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '候选人名字',
  `sex` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '候选人性别',
  `birthday` date NULL DEFAULT NULL COMMENT '候选人出生日期',
  `age` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '候选人年龄',
  `nation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '候选人民族',
  `political` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '候选人政治面貌',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '候选人家庭地址',
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '候选人联系号码',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投票选项的内容，即人名，也可以不是人名',
  `recommend` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '候选人推荐意见',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `item_id`(`item_id`) USING BTREE,
  CONSTRAINT `item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_options
-- ----------------------------
INSERT INTO `item_options` VALUES (10, 4, '徐松博', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '徐松博', NULL);
INSERT INTO `item_options` VALUES (11, 4, '张纯菁', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '张纯菁', NULL);
INSERT INTO `item_options` VALUES (12, 4, '廖南山', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '廖南山', NULL);
INSERT INTO `item_options` VALUES (13, 5, '陈铭海', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '陈铭海', NULL);
INSERT INTO `item_options` VALUES (14, 5, '姚宇曦', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '姚宇曦', NULL);
INSERT INTO `item_options` VALUES (15, 6, '李大拿', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '李大拿', NULL);
INSERT INTO `item_options` VALUES (16, 6, '赵鑫鑫', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '赵鑫鑫', NULL);
INSERT INTO `item_options` VALUES (17, 6, '陈丹丹', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '陈丹丹', NULL);
INSERT INTO `item_options` VALUES (18, 7, '陈明明', '男', '1998-10-10', '20', '汉族', '党员', '广东省广州市石牌区', '1552134688', '陈明明', '是个好人');
INSERT INTO `item_options` VALUES (19, 7, '李海海', '女', '1998-09-10', '20', '汉族', '党员', '广东省广州市顺德区', '1552144789', '李海海', '是个美女');
INSERT INTO `item_options` VALUES (20, 7, '张冬冬', '女', '1998-01-01', '20', '汉族', '党员', '广东省广州市猎德区', '1334567537', '张冬冬', '人美心善');
INSERT INTO `item_options` VALUES (25, 9, '钢铁侠', '男', '1900-10-10', '118', '美国族', '党员', '美国华盛顿', '88888888', '钢铁侠', '是个好人，会飞，还有点帅');
INSERT INTO `item_options` VALUES (26, 9, '蜘蛛侠', '男', '1960-10-09', '58', '美国族', '党员', '美国加州', '9999999', '蜘蛛侠', '会吐丝，直男，受人尊敬，不知道帅不帅，但很会撩妹');
INSERT INTO `item_options` VALUES (27, 9, '令狐冲', '男', '1999-09-09', '19', '汉族', '共青团团员', '中国香港', '3333333', '令狐冲', '会轻功，武功高强，有很多女人喜欢');
INSERT INTO `item_options` VALUES (28, 9, '孙悟空', '不知道', '1000-10-10', '200', '石头族', '党员', '花果山水帘洞', '6666666', '孙悟空', '会七十二变，勇敢，不畏艰难');
INSERT INTO `item_options` VALUES (29, 10, '查候', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '查候', NULL);
INSERT INTO `item_options` VALUES (30, 10, '李妈妈', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '李妈妈', NULL);
INSERT INTO `item_options` VALUES (31, 10, '那么', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '那么', NULL);

-- ----------------------------
-- Table structure for result
-- ----------------------------
DROP TABLE IF EXISTS `result`;
CREATE TABLE `result`  (
  `user_id` int(11) NULL DEFAULT NULL,
  `item_id` int(11) NOT NULL,
  `select_id` int(11) NOT NULL,
  `score` int(11) NOT NULL DEFAULT 1,
  `vote_time` datetime(0) NULL DEFAULT NULL,
  `ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `item_id`(`item_id`) USING BTREE,
  INDEX `select_id`(`select_id`) USING BTREE,
  CONSTRAINT `result_ibfk_1` FOREIGN KEY (`select_id`) REFERENCES `item_options` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `result_item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of result
-- ----------------------------
INSERT INTO `result` VALUES (1, 6, 15, 1, '2018-11-20 10:05:50', '127.0.0.3');
INSERT INTO `result` VALUES (1, 6, 16, 1, '2018-11-20 10:05:50', '127.0.0.2');
INSERT INTO `result` VALUES (1, 9, 27, 1, '2018-11-20 17:12:39', '127.0.0.2');
INSERT INTO `result` VALUES (1, 9, 28, 1, '2018-11-20 17:12:39', '127.0.0.2');
INSERT INTO `result` VALUES (1, 7, 18, 1, '2018-11-21 09:50:02', '127.0.0.2');
INSERT INTO `result` VALUES (1, 9, 25, 1, '2018-11-21 09:51:36', '127.0.0.1');
INSERT INTO `result` VALUES (1, 9, 26, 1, '2018-11-21 09:51:36', '127.0.0.1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `realname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期，计算年龄',
  `create_time` bigint(20) NOT NULL COMMENT '创建日期',
  `level` int(11) NOT NULL DEFAULT 1 COMMENT '用户级别，0为管理员，1为普通用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'chenminghai', '202cb962ac59075b964b07152d234b70', '陈铭海', '15521441679', '男', '1998-10-10', 1542454877563, 0);
INSERT INTO `user` VALUES (2, '1234', '81dc9bdb52d04dc20036dbd8313ed055', '张三', NULL, NULL, NULL, 1542468463139, 1);
INSERT INTO `user` VALUES (3, '12345', '00c66aaf5f2c3f49946f15c1ad2ea0d3', '李四', NULL, NULL, NULL, 1542468778877, 1);
INSERT INTO `user` VALUES (4, '123456', 'e10adc3949ba59abbe56e057f20f883e', '王五', NULL, NULL, NULL, 1542469100973, 1);
INSERT INTO `user` VALUES (5, '123', '202cb962ac59075b964b07152d234b70', '陈明明', NULL, NULL, NULL, 1542524569560, 1);
INSERT INTO `user` VALUES (6, '1234567', 'fcea920f7412b5da7be0cf42b8c93759', '李世民', NULL, NULL, NULL, 1542524984302, 1);
INSERT INTO `user` VALUES (7, '4321', 'd93591bdf7860e1e4ee2fca799911215', '赵六', NULL, NULL, NULL, 1542527039523, 1);
INSERT INTO `user` VALUES (8, '321', 'caf1a3dfb505ffed0d024130f58c5cfa', '钱七', '15521441679', '男', '1998-10-10', 1542527687712, 1);
INSERT INTO `user` VALUES (9, 'qq123456789', 'e10adc3949ba59abbe56e057f20f883e', '陈铭海', '15521440000', '男', '1998-10-10', 1542704035387, 1);
INSERT INTO `user` VALUES (10, 'chenminghai233', 'e10adc3949ba59abbe56e057f20f883e', '陈铭海', '155214417890', '男', '1998-10-11', 1542704122361, 1);

SET FOREIGN_KEY_CHECKS = 1;
