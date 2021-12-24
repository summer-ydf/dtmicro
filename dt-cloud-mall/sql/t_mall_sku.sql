/*
 Navicat Premium Data Transfer

 Source Server         : dt
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : cms_mall_sku

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 24/12/2021 13:51:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_mall_sku
-- ----------------------------
DROP TABLE IF EXISTS `t_mall_sku`;
CREATE TABLE `t_mall_sku`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_mall_sku
-- ----------------------------
INSERT INTO `t_mall_sku` VALUES (1, 100);

SET FOREIGN_KEY_CHECKS = 1;
