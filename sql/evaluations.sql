/*
 Navicat Premium Data Transfer

 Source Server         : local-db-mysql
 Source Server Type    : MySQL
 Source Server Version : 50741
 Source Host           : localhost:3306
 Source Schema         : personal_health

 Target Server Type    : MySQL
 Target Server Version : 50741
 File Encoding         : 65001

 Date: 16/07/2024 20:45:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for evaluations
-- ----------------------------
DROP TABLE IF EXISTS `evaluations`;
CREATE TABLE `evaluations`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级评论ID',
  `commenter_id` int(11) NULL DEFAULT NULL COMMENT '评论者ID',
  `replier_id` int(11) NULL DEFAULT NULL COMMENT '回复者ID',
  `content_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '内容类型',
  `content_id` int(11) NULL DEFAULT NULL COMMENT '内容ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '评论内容',
  `upvote_list` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '点赞列表，以\",\"分割',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 123 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of evaluations
-- ----------------------------
INSERT INTO `evaluations` VALUES (4, NULL, 8, NULL, 'NEWS', 10, '我也遇到过这种问题', '1,7,8', '2024-05-23 11:33:01');
INSERT INTO `evaluations` VALUES (7, 4, 3, NULL, 'NEWS', 10, '大侠风范', '1,7,8,3', '2024-06-08 17:06:19');
INSERT INTO `evaluations` VALUES (8, 1, 3, 3, 'NEWS', 10, '一派胡言', '1,7,8', '2024-06-08 17:06:19');
INSERT INTO `evaluations` VALUES (13, 1, 3, 7, 'NEWS', 10, '这么神奇？', '1,8', '2024-06-08 17:06:19');
INSERT INTO `evaluations` VALUES (14, NULL, 8, 3, 'NEWS', 10, '大哥，我服了！', '1,8', '2024-06-08 17:06:19');
INSERT INTO `evaluations` VALUES (15, 4, 8, 8, 'NEWS', 10, '小子，你真没种！', '1,8', '2024-06-08 15:04:19');
INSERT INTO `evaluations` VALUES (23, NULL, 3, NULL, 'NEWS', 10, '我的评论', '3', '2024-05-24 16:24:14');
INSERT INTO `evaluations` VALUES (27, 23, 3, 3, 'NEWS', 10, '什么事情', '3', '2024-05-25 00:32:16');
INSERT INTO `evaluations` VALUES (29, 14, 3, NULL, 'NEWS', 10, '先生。', '3', '2024-05-25 15:08:06');
INSERT INTO `evaluations` VALUES (40, NULL, 3, NULL, 'NEWS', 19, '测试评论啊', '3', '2024-06-13 19:57:02');
INSERT INTO `evaluations` VALUES (44, 40, 3, NULL, 'NEWS', 19, '回复', NULL, '2024-06-13 20:17:20');
INSERT INTO `evaluations` VALUES (45, 40, 3, 3, 'NEWS', 19, '回复', '3', '2024-06-13 20:19:11');
INSERT INTO `evaluations` VALUES (47, 40, 3, 3, 'NEWS', 19, '测试UU', '3', '2024-06-13 20:19:51');
INSERT INTO `evaluations` VALUES (48, 11, 3, NULL, 'NEWS', 10, '哈哈哈哈', NULL, '2024-06-13 20:20:45');
INSERT INTO `evaluations` VALUES (49, NULL, 3, NULL, 'NEWS', 10, 'UUUIII', NULL, '2024-06-13 20:21:24');
INSERT INTO `evaluations` VALUES (50, 49, 3, NULL, 'NEWS', 10, '就是', NULL, '2024-06-13 20:21:30');
INSERT INTO `evaluations` VALUES (51, 4, 3, NULL, 'NEWS', 10, '就是', NULL, '2024-06-13 20:24:40');
INSERT INTO `evaluations` VALUES (52, 4, 3, 8, 'NEWS', 10, '哈哈哈', '3', '2024-06-13 20:24:51');
INSERT INTO `evaluations` VALUES (53, 49, 3, 3, 'NEWS', 10, '哈哈哈', NULL, '2024-06-13 20:32:37');
INSERT INTO `evaluations` VALUES (54, NULL, 3, NULL, 'NEWS', 18, '测试', NULL, '2024-06-13 20:37:26');
INSERT INTO `evaluations` VALUES (55, 54, 3, NULL, 'NEWS', 18, 'niaho ', NULL, '2024-06-13 20:37:36');
INSERT INTO `evaluations` VALUES (58, 57, 3, NULL, 'NEWS', 19, '4324234', NULL, '2024-06-13 20:39:02');
INSERT INTO `evaluations` VALUES (59, 57, 3, 3, 'NEWS', 19, '54354354', NULL, '2024-06-13 20:39:07');
INSERT INTO `evaluations` VALUES (61, NULL, 3, NULL, 'NEWS', 19, '432432423', NULL, '2024-06-13 20:39:29');
INSERT INTO `evaluations` VALUES (83, NULL, 3, NULL, 'NEWS', 13, '健康', NULL, '2024-06-15 02:12:55');
INSERT INTO `evaluations` VALUES (84, NULL, 3, NULL, 'NEWS', 13, '丝滑', NULL, '2024-06-15 02:13:08');
INSERT INTO `evaluations` VALUES (85, NULL, 3, NULL, 'NEWS', 14, '哈哈哈哈哈', NULL, '2024-06-15 02:13:39');
INSERT INTO `evaluations` VALUES (87, NULL, 3, NULL, 'NEWS', 16, '？？？', NULL, '2024-06-15 02:37:10');
INSERT INTO `evaluations` VALUES (88, NULL, 3, NULL, 'NEWS', 16, '好好说话', NULL, '2024-06-15 21:38:26');
INSERT INTO `evaluations` VALUES (89, NULL, 3, NULL, 'NEWS', 19, '12121', '3', '2024-06-17 00:05:02');
INSERT INTO `evaluations` VALUES (90, 89, 3, NULL, 'NEWS', 19, '同意同意', NULL, '2024-06-17 00:05:11');
INSERT INTO `evaluations` VALUES (91, 89, 3, 3, 'NEWS', 19, '323232', NULL, '2024-06-17 00:05:18');
INSERT INTO `evaluations` VALUES (92, NULL, 3, NULL, 'NEWS', 16, '323213', NULL, '2024-06-24 23:53:41');
INSERT INTO `evaluations` VALUES (93, NULL, 3, NULL, 'NEWS', 20, '3123213213', NULL, '2024-06-24 23:53:48');
INSERT INTO `evaluations` VALUES (94, 93, 3, NULL, 'NEWS', 20, '32132132', NULL, '2024-06-24 23:53:53');
INSERT INTO `evaluations` VALUES (98, 95, 3, NULL, 'NEWS', 15, '32132', NULL, '2024-06-24 23:54:38');
INSERT INTO `evaluations` VALUES (99, 95, 3, NULL, 'NEWS', 15, '43434', NULL, '2024-06-24 23:54:41');
INSERT INTO `evaluations` VALUES (100, 95, 3, 3, 'NEWS', 15, '432423433454354', NULL, '2024-06-24 23:54:45');
INSERT INTO `evaluations` VALUES (101, NULL, 3, NULL, 'NEWS', 15, '4324324', NULL, '2024-06-24 23:54:52');
INSERT INTO `evaluations` VALUES (102, NULL, 3, NULL, 'NEWS', 20, '2121', NULL, '2024-06-30 23:09:39');
INSERT INTO `evaluations` VALUES (103, NULL, 3, NULL, 'NEWS', 16, '43434', NULL, '2024-06-30 23:09:49');
INSERT INTO `evaluations` VALUES (104, NULL, 3, NULL, 'NEWS', 20, '4343', NULL, '2024-07-07 03:00:30');
INSERT INTO `evaluations` VALUES (105, NULL, 3, NULL, 'NEWS', 19, '2121', NULL, '2024-07-07 04:45:25');
INSERT INTO `evaluations` VALUES (106, NULL, 3, NULL, 'NEWS', 20, '2121', NULL, '2024-07-07 04:46:11');
INSERT INTO `evaluations` VALUES (107, NULL, 3, NULL, 'NEWS', 20, '测试', NULL, '2024-07-07 04:46:25');
INSERT INTO `evaluations` VALUES (110, 109, 17, NULL, 'NEWS', 10, 'dsdsds', NULL, '2024-07-08 14:09:37');
INSERT INTO `evaluations` VALUES (112, 111, 9, NULL, 'NEWS', 5, '4324322', NULL, '2024-07-14 15:17:44');
INSERT INTO `evaluations` VALUES (113, 111, 9, 9, 'NEWS', 5, '5435435', NULL, '2024-07-14 15:17:51');
INSERT INTO `evaluations` VALUES (115, NULL, 9, NULL, 'NEWS', 2, '221332131', NULL, '2024-07-14 15:21:54');
INSERT INTO `evaluations` VALUES (116, NULL, 9, NULL, 'NEWS', 3, '这是指标的评论！', NULL, '2024-07-14 15:22:13');
INSERT INTO `evaluations` VALUES (117, NULL, 9, NULL, 'NEWS', 5, '3232', NULL, '2024-07-14 19:20:56');
INSERT INTO `evaluations` VALUES (118, 117, 9, NULL, 'NEWS', 5, '4324324', NULL, '2024-07-14 19:21:01');
INSERT INTO `evaluations` VALUES (119, NULL, 9, NULL, 'NEWS', 12, '3342143', '9', '2024-07-16 16:27:51');
INSERT INTO `evaluations` VALUES (120, 119, 9, NULL, 'NEWS', 12, '43243243', '9', '2024-07-16 16:27:56');
INSERT INTO `evaluations` VALUES (121, 119, 9, 9, 'NEWS', 12, '432423432', '9', '2024-07-16 16:28:01');

SET FOREIGN_KEY_CHECKS = 1;
