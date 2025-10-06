/*
 Navicat Premium Dump SQL

 Source Server         : navicat-mysql
 Source Server Type    : MySQL
 Source Server Version : 50727 (5.7.27)
 Source Host           : localhost:3306
 Source Schema         : big_personal_health

 Target Server Type    : MySQL
 Target Server Version : 50727 (5.7.27)
 File Encoding         : 65001

 Date: 07/09/2025 13:45:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for evalutions_upvote
-- ----------------------------
DROP TABLE IF EXISTS `evalutions_upvote`;
CREATE TABLE `evalutions_upvote`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `evaluations_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
