/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : 127.0.0.1:3306
 Source Schema         : dtcms1.0

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 28/03/2022 19:11:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_config
-- ----------------------------
DROP TABLE IF EXISTS `base_config`;
CREATE TABLE `base_config`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数类型',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `k` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'key',
  `v` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'value',
  `enabled` tinyint(1) NULL DEFAULT 0 COMMENT '是否启用（1：是，0否）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of base_config
-- ----------------------------
INSERT INTO `base_config` VALUES ('1000000066909420', '系统主题配置', '主题七', '主题七', 'theme_seven.css', 1, '2021-11-19 23:57:38', '2021-11-20 00:35:57');
INSERT INTO `base_config` VALUES ('1000000256170896', '系统主题配置', '主题二', '主题二', 'theme_two.css', 0, '2021-10-31 18:27:53', '2021-11-19 23:57:49');
INSERT INTO `base_config` VALUES ('1000000332864445', '系统主题配置', '主题四', '主题四', 'theme_four.css', 0, '2021-10-31 18:27:53', '2021-11-20 00:26:02');
INSERT INTO `base_config` VALUES ('1000000470437159', '首页链接入口', '用户管理', 'usermannage', '/system/user/UserList', 0, '2021-11-06 12:12:59', '2021-11-06 12:13:41');
INSERT INTO `base_config` VALUES ('1000000682943458', '系统主题配置', '主题五', '主题五', 'theme_five.css', 0, '2021-10-31 18:27:53', '2021-11-20 00:26:12');
INSERT INTO `base_config` VALUES ('1000001361614097', '系统主题配置', '主题六', '主题六', 'theme_six.css', 0, '2021-10-31 18:27:53', '2021-11-20 00:35:57');
INSERT INTO `base_config` VALUES ('1000001698990134', '系统主题配置', '主题一', '主题一', 'theme_one.css', 0, '2021-10-31 18:27:53', '2021-11-20 00:26:25');
INSERT INTO `base_config` VALUES ('1000001739126234', '系统主题配置', '主题三', '主题三', 'theme_three.css', 0, '2021-10-31 18:27:53', '2021-11-19 18:51:56');

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int NULL DEFAULT NULL,
  `refresh_token_validity` int NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('cms', NULL, '$2a$10$bMNULS2JtrK.kEOIZw4MrOXgJ/B3XK3bnjkO58JHe2.t9kIBnScpe', 'web', 'password,refresh_token', NULL, NULL, 100, NULL, NULL, 'true');

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门id',
  `parent_id` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上级部门id',
  `label` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门名称',
  `sort` int NULL DEFAULT NULL COMMENT '序号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES ('1000000174227147', '1000001620535597', '市场部门', 1, '2021-06-17 21:33:41', '2021-06-17 21:34:03');
INSERT INTO `sys_department` VALUES ('1000000204663981', '1000001200689941', '前端部门', 3, '2021-06-17 21:27:59', '2021-06-17 21:28:22');
INSERT INTO `sys_department` VALUES ('1000000622591924', '1000001620535597', '设计部', 1, '2021-06-20 21:53:49', '2021-06-20 21:57:45');
INSERT INTO `sys_department` VALUES ('1000000952846438', '1000001251633881', '财务部门', 1, '2021-06-17 21:29:00', '2021-10-03 16:50:39');
INSERT INTO `sys_department` VALUES ('1000001186458564', '1000001637526739', '服务二部', 2, '2021-06-20 21:56:29', '2021-06-20 21:56:29');
INSERT INTO `sys_department` VALUES ('1000001200689941', '1000001776185099', '技术部', 1, '2021-06-13 19:15:04', '2021-06-17 21:25:31');
INSERT INTO `sys_department` VALUES ('1000001251633881', '1000001776185099', '财务部', 2, '2021-06-13 14:35:36', '2021-06-17 21:29:25');
INSERT INTO `sys_department` VALUES ('1000001258096779', '1000001200689941', 'UI设计部门', 4, '2021-06-17 21:28:10', '2021-06-17 21:31:41');
INSERT INTO `sys_department` VALUES ('1000001341234088', '1000001776185099', '行政部', 3, '2021-06-13 14:35:38', '2021-06-17 21:26:17');
INSERT INTO `sys_department` VALUES ('1000001620535597', '1000001776185099', '客服部', 4, '2021-06-13 14:35:40', '2021-06-17 21:29:39');
INSERT INTO `sys_department` VALUES ('1000001625392933', '1000001341234088', '法律部门', 1, '2021-06-17 21:33:05', '2021-06-17 21:33:05');
INSERT INTO `sys_department` VALUES ('1000001637526739', '1000001620535597', '服务部门', 2, '2021-06-17 21:33:55', '2021-06-17 21:33:55');
INSERT INTO `sys_department` VALUES ('1000001728835022', '1000001637526739', '服务一部', 1, '2021-06-20 21:56:19', '2021-06-20 21:56:19');
INSERT INTO `sys_department` VALUES ('1000001776185099', '0', 'DT编程科技有限公司', 1, '2021-06-13 14:35:42', '2021-11-14 11:22:51');
INSERT INTO `sys_department` VALUES ('1000001779686042', '1000001200689941', '后端部门', 1, '2021-06-17 21:27:48', '2021-06-17 21:27:48');
INSERT INTO `sys_department` VALUES ('1000001854756787', '1000000622591924', '设计组一', 1, '2021-06-20 21:54:18', '2021-06-20 21:54:18');
INSERT INTO `sys_department` VALUES ('1000001934748021', '1000000622591924', '设计组二', 2, '2021-06-20 21:54:27', '2021-06-20 21:54:27');
INSERT INTO `sys_department` VALUES ('1000001975876013', '1000000622591924', '设计组三', 3, '2021-06-20 21:54:48', '2021-06-20 21:54:48');

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login`  (
  `id` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志标题',
  `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `login_ip` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '登录IP',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录浏览器',
  `operating_system` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `status` int NULL DEFAULT NULL COMMENT '登录状态：1成功 2失败',
  `type` int NULL DEFAULT NULL COMMENT '类型：1登录系统 2退出系统',
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作消息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
INSERT INTO `sys_log_login` VALUES ('675106132562048', 'dt在：2022-03-25 19:38:51 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-25 19:38:51', '2022-03-25 19:38:51');
INSERT INTO `sys_log_login` VALUES ('675110987538560', 'admin在：2022-03-25 22:56:24 点击了登录', 'admin', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-25 22:56:24', '2022-03-25 22:56:24');
INSERT INTO `sys_log_login` VALUES ('675111007498368', 'dt在：2022-03-25 22:57:13 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-25 22:57:13', '2022-03-25 22:57:13');
INSERT INTO `sys_log_login` VALUES ('675129649299584', 'admin在：2022-03-26 11:35:45 点击了登录', 'admin', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 11:35:45', '2022-03-26 11:35:45');
INSERT INTO `sys_log_login` VALUES ('675129656197248', 'dt在：2022-03-26 11:36:02 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 11:36:02', '2022-03-26 11:36:02');
INSERT INTO `sys_log_login` VALUES ('675132655628416', 'admin在：2022-03-26 13:38:05 点击了登录', 'admin', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 13:38:05', '2022-03-26 13:38:05');
INSERT INTO `sys_log_login` VALUES ('675132661465216', 'dt在：2022-03-26 13:38:19 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 13:38:19', '2022-03-26 13:38:19');
INSERT INTO `sys_log_login` VALUES ('675133697495168', 'dt在：2022-03-26 14:20:28 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 14:20:29', '2022-03-26 14:20:29');
INSERT INTO `sys_log_login` VALUES ('675134085668992', 'admin在：2022-03-26 14:36:16 点击了登录', 'admin', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 14:36:16', '2022-03-26 14:36:16');
INSERT INTO `sys_log_login` VALUES ('675134849527936', 'dt在：2022-03-26 15:07:21 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 15:07:21', '2022-03-26 15:07:21');
INSERT INTO `sys_log_login` VALUES ('675134901076096', 'dt在：2022-03-26 15:09:27 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 15:09:27', '2022-03-26 15:09:27');
INSERT INTO `sys_log_login` VALUES ('675135131910272', 'dt在：2022-03-26 15:18:50 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 15:18:51', '2022-03-26 15:18:51');
INSERT INTO `sys_log_login` VALUES ('675140015325312', 'admin在：2022-03-26 18:37:32 点击了登录', 'admin', '127.0.0.1', 'Mobile Safari', 'iOS 10 (iPhone)', 1, 1, NULL, '2022-03-26 18:37:33', '2022-03-26 18:37:33');
INSERT INTO `sys_log_login` VALUES ('675141413855360', 'dt在：2022-03-26 19:34:27 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 19:34:27', '2022-03-26 19:34:27');
INSERT INTO `sys_log_login` VALUES ('675142699143296', 'admin在：2022-03-26 20:26:45 点击了登录', 'admin', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 20:26:45', '2022-03-26 20:26:45');
INSERT INTO `sys_log_login` VALUES ('675143169917056', 'dt在：2022-03-26 20:45:54 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 20:45:55', '2022-03-26 20:45:55');
INSERT INTO `sys_log_login` VALUES ('675143778627712', 'dt在：2022-03-26 21:10:40 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 21:10:41', '2022-03-26 21:10:41');
INSERT INTO `sys_log_login` VALUES ('675143909761152', 'dt在：2022-03-26 21:16:00 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 21:16:01', '2022-03-26 21:16:01');
INSERT INTO `sys_log_login` VALUES ('675143995998336', 'dt在：2022-03-26 21:19:31 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 21:19:31', '2022-03-26 21:19:31');
INSERT INTO `sys_log_login` VALUES ('675144421740672', 'dt在：2022-03-26 21:36:50 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 21:36:51', '2022-03-26 21:36:51');
INSERT INTO `sys_log_login` VALUES ('675144535732352', 'dt在：2022-03-26 21:41:29 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 21:41:29', '2022-03-26 21:41:29');
INSERT INTO `sys_log_login` VALUES ('675144652562560', 'dt在：2022-03-26 21:46:14 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 21:46:14', '2022-03-26 21:46:14');
INSERT INTO `sys_log_login` VALUES ('675144824578176', 'dt在：2022-03-26 21:53:14 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-26 21:53:14', '2022-03-26 21:53:14');
INSERT INTO `sys_log_login` VALUES ('675165194760320', 'dt在：2022-03-27 11:42:06 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-27 11:42:06', '2022-03-27 11:42:06');
INSERT INTO `sys_log_login` VALUES ('675179795128448', 'dt在：2022-03-27 21:36:11 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-27 21:36:12', '2022-03-27 21:36:12');
INSERT INTO `sys_log_login` VALUES ('675181628907648', 'dt在：2022-03-27 22:50:48 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-27 22:50:49', '2022-03-27 22:50:49');
INSERT INTO `sys_log_login` VALUES ('675181645410432', 'dt在：2022-03-27 22:51:28 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-27 22:51:29', '2022-03-27 22:51:29');
INSERT INTO `sys_log_login` VALUES ('675181745115264', 'dt在：2022-03-27 22:55:32 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-27 22:55:32', '2022-03-27 22:55:32');
INSERT INTO `sys_log_login` VALUES ('675181876363392', 'dt在：2022-03-27 23:00:52 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-27 23:00:53', '2022-03-27 23:00:53');
INSERT INTO `sys_log_login` VALUES ('675182039068800', 'dt在：2022-03-27 23:07:30 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-27 23:07:30', '2022-03-27 23:07:30');
INSERT INTO `sys_log_login` VALUES ('675182071902336', 'dt在：2022-03-27 23:08:50 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-27 23:08:50', '2022-03-27 23:08:50');
INSERT INTO `sys_log_login` VALUES ('675182095437952', 'dt在：2022-03-27 23:09:47 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-03-27 23:09:48', '2022-03-27 23:09:48');

-- ----------------------------
-- Table structure for sys_log_operator
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_operator`;
CREATE TABLE `sys_log_operator`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `business_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `request_method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `request_method_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法类型',
  `request_user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `request_url` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求URL',
  `request_ip` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求IP',
  `request_param` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `response_param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '响应参数',
  `status` int NULL DEFAULT NULL COMMENT '操作状态：1正常 2异常',
  `error_info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '错误信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log_operator
-- ----------------------------
INSERT INTO `sys_log_operator` VALUES ('675106595610752', '添加角色数据权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleDataScope()', 'POST', 'dt', '/role/saveRoleDataScope', '127.0.0.1', '{\"roleId\":674996182413440,\"deptIds\":[\"1000001200689941\",\"1000000204663981\"],\"dataScope\":2}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648209461003}', 1, NULL, '2022-03-25 19:57:42', '2022-03-25 19:57:42');
INSERT INTO `sys_log_operator` VALUES ('675132888031360', '添加角色数据权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleDataScope()', 'POST', 'dt', '/role/saveRoleDataScope', '127.0.0.1', '{\"roleId\":256693602365509,\"deptIds\":[],\"dataScope\":4}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648273651899}', 1, NULL, '2022-03-26 13:47:32', '2022-03-26 13:47:32');
INSERT INTO `sys_log_operator` VALUES ('675132895461504', '编辑系统角色日志记录', 'UPDATE', 'com.cms.manage.controller.SysRoleController.save()', 'POST', 'dt', '/role/save', '127.0.0.1', '{\"name\":\"WEB\",\"alias\":\"超级管理员\",\"remark\":\"超级管理员\",\"updateTime\":1648273670546,\"id\":674996182413440}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648273670548}', 1, NULL, '2022-03-26 13:47:51', '2022-03-26 13:47:51');
INSERT INTO `sys_log_operator` VALUES ('675132905480320', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"后端部门\",\"credentialsNonExpired\":true,\"deptId\":1000001779686042,\"admin\":false,\"updateTime\":1648273695004,\"avatar\":\"\",\"enabled\":true,\"password\":\"\",\"roleIds\":[674996182413440],\"scope\":\"web\",\"accountNonExpired\":true,\"id\":675070929846400,\"accountNonLocked\":true,\"username\":\"test\"}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648273695007}', 1, NULL, '2022-03-26 13:48:15', '2022-03-26 13:48:15');
INSERT INTO `sys_log_operator` VALUES ('675143824109696', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"117\",\"118\",\"119\",\"675073235689600\",\"675073256198272\",\"675073263906944\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648300351226}', 1, NULL, '2022-03-26 21:12:32', '2022-03-26 21:12:32');
INSERT INTO `sys_log_operator` VALUES ('675143829475456', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"后端部门\",\"credentialsNonExpired\":true,\"deptId\":1000001779686042,\"admin\":false,\"updateTime\":1648300364892,\"avatar\":\"\",\"enabled\":true,\"password\":\"\",\"roleIds\":[674996182413440],\"scope\":\"web\",\"accountNonExpired\":true,\"id\":675070929846400,\"accountNonLocked\":true,\"username\":\"test\"}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648300364894}', 1, NULL, '2022-03-26 21:12:45', '2022-03-26 21:12:45');
INSERT INTO `sys_log_operator` VALUES ('675143830556800', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"后端部门\",\"credentialsNonExpired\":true,\"deptId\":1000001779686042,\"admin\":false,\"updateTime\":1648300367559,\"avatar\":\"img/avatar.jpg\",\"enabled\":true,\"password\":\"\",\"roleIds\":[248204504629317,256693602365509],\"scope\":\"web\",\"accountNonExpired\":true,\"id\":248204704247877,\"accountNonLocked\":true,\"username\":\"admin\"}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648300367561}', 1, NULL, '2022-03-26 21:12:48', '2022-03-26 21:12:48');
INSERT INTO `sys_log_operator` VALUES ('675143831769216', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"前端部门\",\"credentialsNonExpired\":true,\"deptId\":1000000204663981,\"admin\":false,\"updateTime\":1648300370522,\"avatar\":\"img/avatar.jpg\",\"enabled\":true,\"password\":\"\",\"roleIds\":[248204504629317],\"scope\":\"app\",\"accountNonExpired\":true,\"id\":248204704247878,\"accountNonLocked\":true,\"username\":\"root\"}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648300370524}', 1, NULL, '2022-03-26 21:12:51', '2022-03-26 21:12:51');
INSERT INTO `sys_log_operator` VALUES ('675143838191744', '添加角色数据权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleDataScope()', 'POST', 'dt', '/role/saveRoleDataScope', '127.0.0.1', '{\"roleId\":256693602365509,\"deptIds\":[],\"dataScope\":4}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648300386199}', 1, NULL, '2022-03-26 21:13:06', '2022-03-26 21:13:06');
INSERT INTO `sys_log_operator` VALUES ('675143839314048', '添加角色数据权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleDataScope()', 'POST', 'dt', '/role/saveRoleDataScope', '127.0.0.1', '{\"roleId\":248204504629317,\"deptIds\":[],\"dataScope\":3}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648300388945}', 1, NULL, '2022-03-26 21:13:09', '2022-03-26 21:13:09');
INSERT INTO `sys_log_operator` VALUES ('675143841824896', '添加角色数据权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleDataScope()', 'POST', 'dt', '/role/saveRoleDataScope', '127.0.0.1', '{\"roleId\":674996182413440,\"deptIds\":[\"1000001200689941\",\"1000000204663981\"],\"dataScope\":2}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648300395078}', 1, NULL, '2022-03-26 21:13:15', '2022-03-26 21:13:15');
INSERT INTO `sys_log_operator` VALUES ('675144417947776', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"117\",\"118\",\"119\",\"675073235689600\",\"675073256198272\",\"675073263906944\",\"675144297996416\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648301801630}', 1, NULL, '2022-03-26 21:36:42', '2022-03-26 21:36:42');
INSERT INTO `sys_log_operator` VALUES ('675144530591872', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"117\",\"118\",\"119\",\"675073235689600\",\"675073256198272\",\"675073263906944\",\"675144297996416\",\"675144452886656\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648302076632}', 1, NULL, '2022-03-26 21:41:17', '2022-03-26 21:41:17');
INSERT INTO `sys_log_operator` VALUES ('675144649134208', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"117\",\"118\",\"119\",\"675073235689600\",\"675073256198272\",\"675073263906944\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648302366043}', 1, NULL, '2022-03-26 21:46:06', '2022-03-26 21:46:06');
INSERT INTO `sys_log_operator` VALUES ('675144848158848', '添加角色数据权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleDataScope()', 'POST', 'dt', '/role/saveRoleDataScope', '127.0.0.1', '{\"roleId\":674996182413440,\"deptIds\":[\"1000001200689941\",\"1000000204663981\"],\"dataScope\":2}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648302851939}', 1, NULL, '2022-03-26 21:54:12', '2022-03-26 21:54:12');
INSERT INTO `sys_log_operator` VALUES ('675181619179648', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"118\",\"119\",\"675073235689600\",\"675073256198272\",\"675073263906944\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648392624146}', 1, NULL, '2022-03-27 22:50:25', '2022-03-27 22:50:25');
INSERT INTO `sys_log_operator` VALUES ('675181641588864', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"118\",\"119\",\"675073235689600\",\"675073256198272\",\"675073263906944\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648392679649}', 1, NULL, '2022-03-27 22:51:20', '2022-03-27 22:51:20');
INSERT INTO `sys_log_operator` VALUES ('675181737009280', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"119\",\"675073235689600\",\"675073256198272\",\"675073263906944\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648392912616}', 1, NULL, '2022-03-27 22:55:13', '2022-03-27 22:55:13');
INSERT INTO `sys_log_operator` VALUES ('675181858431104', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675073263906944\",\"675181843529856\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648393209055}', 1, NULL, '2022-03-27 23:00:09', '2022-03-27 23:00:09');
INSERT INTO `sys_log_operator` VALUES ('675181871648896', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675073263906944\",\"675181843529856\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648393241322}', 1, NULL, '2022-03-27 23:00:41', '2022-03-27 23:00:41');
INSERT INTO `sys_log_operator` VALUES ('675181960319104', '添加角色数据权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleDataScope()', 'POST', 'dt', '/role/saveRoleDataScope', '127.0.0.1', '{\"roleId\":674996182413440,\"deptIds\":[\"1000001200689941\",\"1000000204663981\"],\"dataScope\":2}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648393457801}', 1, NULL, '2022-03-27 23:04:18', '2022-03-27 23:04:18');
INSERT INTO `sys_log_operator` VALUES ('675181963026560', '添加角色数据权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleDataScope()', 'POST', 'dt', '/role/saveRoleDataScope', '127.0.0.1', '{\"roleId\":674996182413440,\"deptIds\":[\"1000001200689941\",\"1000000204663981\"],\"dataScope\":2}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648393464413}', 1, NULL, '2022-03-27 23:04:24', '2022-03-27 23:04:24');
INSERT INTO `sys_log_operator` VALUES ('675182035165312', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648393640536}', 1, NULL, '2022-03-27 23:07:21', '2022-03-27 23:07:21');
INSERT INTO `sys_log_operator` VALUES ('675182059384960', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648393699665}', 1, NULL, '2022-03-27 23:08:20', '2022-03-27 23:08:20');
INSERT INTO `sys_log_operator` VALUES ('675182088450176', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648393770623}', 1, NULL, '2022-03-27 23:09:31', '2022-03-27 23:09:31');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_num` int NULL DEFAULT 1,
  `sort` int NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `redirect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hidden` tinyint(1) NULL DEFAULT 0,
  `hidden_breadcrumb` tinyint(1) NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('111', '0', '/setting', 'setting', NULL, '系统', 'el-icon-setting', 'menu', 1, 4, NULL, NULL, 0, 0, '2022-02-22 11:27:20', '2022-03-09 14:25:12');
INSERT INTO `sys_menu` VALUES ('112', '111', '/setting/user', 'user', 'setting/user', '用户管理', 'sc-icon-user', 'menu', 2, 5, 'user.list', NULL, 0, 0, '2022-02-22 11:27:19', '2022-03-09 14:25:03');
INSERT INTO `sys_menu` VALUES ('113', '112', NULL, NULL, NULL, '添加', NULL, 'button', 3, 6, 'user.add', NULL, 0, 0, '2022-02-22 11:27:18', '2022-02-22 11:27:18');
INSERT INTO `sys_menu` VALUES ('114', '0', '/home', 'home', NULL, '首页', 'el-icon-data-line', 'menu', 1, 1, NULL, NULL, 0, 0, '2022-02-22 11:27:17', '2022-02-22 11:27:17');
INSERT INTO `sys_menu` VALUES ('115', '114', '/dashboard', 'dashboard', 'home', '控制台', 'el-icon-monitor', 'menu', 2, 2, NULL, NULL, 0, 0, '2022-02-22 11:27:16', '2022-03-26 21:15:31');
INSERT INTO `sys_menu` VALUES ('116', '114', '/usercenter', 'usercenter', 'userCenter', '个人中心', 'el-icon-collection', 'menu', 3, 3, NULL, NULL, 0, 0, '2022-02-22 11:27:15', '2022-03-26 21:14:37');
INSERT INTO `sys_menu` VALUES ('117', '111', '/setting/menu', 'settingMenu', 'setting/menu', '菜单管理', 'sc-icon-menu', 'menu', 1, 7, NULL, '', 0, 0, '2022-02-22 13:58:56', '2022-03-09 14:24:56');
INSERT INTO `sys_menu` VALUES ('118', '111', '/setting/dept', 'settingDept', 'setting/dept', '部门管理', 'sc-icon-dept', 'menu', 1, 8, NULL, NULL, 0, 0, '2022-02-28 16:45:31', '2022-03-08 15:44:32');
INSERT INTO `sys_menu` VALUES ('119', '111', '/setting/role', 'role', 'setting/role', '角色管理', 'sc-icon-role', 'menu', 1, 9, NULL, NULL, 0, 0, '2022-03-09 14:26:01', '2022-03-09 14:26:01');
INSERT INTO `sys_menu` VALUES ('675073235689600', '0', '/log', 'log', '', '日志', 'sc-icon-log', 'menu', 1, 10, NULL, NULL, 0, 0, '2022-03-24 21:21:02', '2022-03-24 21:21:02');
INSERT INTO `sys_menu` VALUES ('675073256198272', '675073235689600', '/log/login', 'login', 'log/login', '登录日志', 'sc-icon-login-log', 'menu', 1, 11, NULL, NULL, 0, 0, '2022-03-24 21:21:19', '2022-03-24 21:26:32');
INSERT INTO `sys_menu` VALUES ('675073263906944', '675073235689600', '/log/operation', 'operation', 'log/operation', '操作日志', 'sc-icon-operation-log', 'menu', 1, 12, NULL, NULL, 0, 0, '2022-03-24 21:21:35', '2022-03-24 21:26:56');
INSERT INTO `sys_menu` VALUES ('675144297996416', '0', '/task', 'task', '', '任务', 'sc-icon-task', 'menu', 1, 13, NULL, NULL, 0, 0, '2022-03-26 21:32:17', '2022-03-26 21:36:34');
INSERT INTO `sys_menu` VALUES ('675144452886656', '675144297996416', '/task/manage', 'manage', 'task/manage', '任务管理', 'el-icon-clock', 'menu', 1, 14, NULL, NULL, 0, 0, '2022-03-26 21:40:23', '2022-03-26 21:40:24');
INSERT INTO `sys_menu` VALUES ('675144589611136', '0', '/config', 'config', '', '配置', 'sc-icon-config', 'menu', 1, 15, NULL, NULL, 0, 0, '2022-03-26 21:44:04', '2022-03-26 21:44:05');
INSERT INTO `sys_menu` VALUES ('675144638443648', '675144589611136', '/config/setting', '未命名1', 'config/setting', '系统配置', 'el-icon-more-filled', 'menu', 1, 16, NULL, NULL, 0, 0, '2022-03-26 21:46:00', '2022-03-26 21:46:00');
INSERT INTO `sys_menu` VALUES ('675181511221376', '112', '', '', '', '编辑', '', 'button', 1, 17, 'user.edit', NULL, 0, 0, '2022-03-27 22:46:46', '2022-03-27 22:46:47');
INSERT INTO `sys_menu` VALUES ('675181535461504', '112', '', '', '', '删除', NULL, 'button', 1, 18, 'user.delete', NULL, 0, 0, '2022-03-27 22:47:26', '2022-03-27 22:47:26');
INSERT INTO `sys_menu` VALUES ('675181546832000', '112', '', '', '', '批量删除', NULL, 'button', 1, 19, 'user.batch.delete', NULL, 0, 0, '2022-03-27 22:47:47', '2022-03-27 22:51:08');
INSERT INTO `sys_menu` VALUES ('675181714677888', '117', '', '', '', '添加', '', 'button', 1, 20, 'menu.add', NULL, 0, 0, '2022-03-27 22:54:38', '2022-03-27 22:54:38');
INSERT INTO `sys_menu` VALUES ('675181723545728', '117', '', '', '', '批量删除', NULL, 'button', 1, 21, 'menu.batch.delete', NULL, 0, 0, '2022-03-27 22:54:56', '2022-03-27 22:54:56');
INSERT INTO `sys_menu` VALUES ('675181755994240', '118', '', '', '', '添加', '', 'button', 1, 22, 'dept.add', NULL, 0, 0, '2022-03-27 22:56:14', '2022-03-27 22:56:14');
INSERT INTO `sys_menu` VALUES ('675181762871424', '118', '', '', '', '批量删除', NULL, 'button', 1, 23, 'dept.batch.delete', NULL, 0, 0, '2022-03-27 22:56:34', '2022-03-27 22:56:34');
INSERT INTO `sys_menu` VALUES ('675181771518080', '119', '', '', '', '添加', '', 'button', 1, 24, 'role.add', NULL, 0, 0, '2022-03-27 22:56:57', '2022-03-27 22:56:57');
INSERT INTO `sys_menu` VALUES ('675181780402304', '119', '', '', '', '编辑', NULL, 'button', 1, 25, 'role.edit', NULL, 0, 0, '2022-03-27 22:57:12', '2022-03-27 22:57:12');
INSERT INTO `sys_menu` VALUES ('675181786669184', '119', '', '', '', '删除', NULL, 'button', 1, 26, 'role.delete', NULL, 0, 0, '2022-03-27 22:57:32', '2022-03-27 22:57:32');
INSERT INTO `sys_menu` VALUES ('675181798477952', '119', '', '', '', '权限设置', NULL, 'button', 1, 27, 'role.auth', NULL, 0, 0, '2022-03-27 22:58:02', '2022-03-27 22:58:02');
INSERT INTO `sys_menu` VALUES ('675181806932096', '119', '', '', '', '数据权限', NULL, 'button', 1, 28, 'role.data.scope', NULL, 0, 0, '2022-03-27 22:58:21', '2022-03-27 22:58:21');
INSERT INTO `sys_menu` VALUES ('675181820149888', '119', '', '', '', '批量删除', NULL, 'button', 1, 29, 'role.batch.delete', NULL, 0, 0, '2022-03-27 22:58:58', '2022-03-27 22:58:58');
INSERT INTO `sys_menu` VALUES ('675181831987328', '675073256198272', '', '', '', '批量删除', '', 'button', 1, 30, 'log.login.batch.delete', NULL, 0, 0, '2022-03-27 22:59:30', '2022-03-27 22:59:30');
INSERT INTO `sys_menu` VALUES ('675181843529856', '675073263906944', '', '', '', '批量删除', NULL, 'button', 1, 31, 'log.oper.batch.delete', NULL, 0, 0, '2022-03-27 22:59:53', '2022-03-27 22:59:53');
INSERT INTO `sys_menu` VALUES ('675181998772352', '675073256198272', '', '', '', '删除', NULL, 'button', 1, 32, 'log.login.delete', NULL, 0, 0, '2022-03-27 23:06:08', '2022-03-27 23:06:08');
INSERT INTO `sys_menu` VALUES ('675182006534272', '675073263906944', '', '', '', '删除', NULL, 'button', 1, 33, 'log.oper.delete', NULL, 0, 0, '2022-03-27 23:06:26', '2022-03-27 23:09:22');

-- ----------------------------
-- Table structure for sys_operator
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator`;
CREATE TABLE `sys_operator`  (
  `id` bigint NOT NULL COMMENT '用户 ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统登录名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码，加密存储, admin/1234',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用范围（web PC端， app 手机端）',
  `dept_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门ID',
  `is_admin` int NOT NULL DEFAULT 0 COMMENT '是否超级管理员(1 是，0 否)',
  `is_account_non_expired` int NOT NULL DEFAULT 1 COMMENT '帐户是否过期(1 未过期，0 已过期)',
  `is_account_non_locked` int NOT NULL DEFAULT 1 COMMENT '帐户是否被锁定(1 未过期，0 已过期)',
  `is_credentials_non_expired` int NOT NULL DEFAULT 1 COMMENT '密码是否过期(1 未过期，0 已过期)',
  `is_enabled` int NOT NULL DEFAULT 1 COMMENT '帐户是否可用(1 可用，0 删除用户)',
  `avatar` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户图片地址',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_operator
-- ----------------------------
INSERT INTO `sys_operator` VALUES (248204704247877, 'admin', '', 'web', '1000001779686042', 0, 1, 1, 1, 1, 'img/avatar.jpg', '2022-01-21 10:47:34', '2022-03-26 21:12:48');
INSERT INTO `sys_operator` VALUES (248204704247878, 'root', '', 'app', '1000000204663981', 0, 1, 1, 1, 1, 'img/avatar.jpg', '2022-02-28 16:47:03', '2022-03-26 21:12:51');
INSERT INTO `sys_operator` VALUES (674996197965952, 'dt', '$2a$10$U6s5CKezRAhOcNMsfHhg8OsLxtA4bcZr3DLVVFPjq98C0a2xTLK0q', 'web', NULL, 1, 1, 1, 1, 1, '', '2022-03-22 17:05:36', '2022-03-22 17:05:36');
INSERT INTO `sys_operator` VALUES (675069938479232, 'admin1', '$2a$10$mm.FLgSonOC6aDvjwE.HuOgJeJQlmnGV.Fm2MWzUNN6ndOY4De736', 'web', '1000001779686042', 0, 1, 1, 1, 1, '', '2022-03-24 19:06:07', '2022-03-24 19:06:07');
INSERT INTO `sys_operator` VALUES (675070929846400, 'test', '', 'web', '1000001779686042', 0, 1, 1, 1, 1, '', '2022-03-24 19:46:27', '2022-03-26 21:12:45');

-- ----------------------------
-- Table structure for sys_operator_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator_role`;
CREATE TABLE `sys_operator_role`  (
  `id` bigint NOT NULL COMMENT '主键 ID',
  `user_id` bigint NOT NULL COMMENT '用户 ID',
  `role_id` bigint NOT NULL COMMENT '角色 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_operator_role
-- ----------------------------
INSERT INTO `sys_operator_role` VALUES (261749792542789, 261749792505925, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (261749792546885, 261749792505925, 256693602365509);
INSERT INTO `sys_operator_role` VALUES (263086138716229, 263086138699845, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (263086138724421, 263086138699845, 256693602365509);
INSERT INTO `sys_operator_role` VALUES (267011175546949, 1503614791426621441, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (269531279212613, 674996197965952, 674996182413440);
INSERT INTO `sys_operator_role` VALUES (270268684345413, 675069938479232, 256693602365509);
INSERT INTO `sys_operator_role` VALUES (271007591956549, 675070929846400, 674996182413440);
INSERT INTO `sys_operator_role` VALUES (271007602917445, 248204704247877, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (271007602921541, 248204704247877, 256693602365509);
INSERT INTO `sys_operator_role` VALUES (271007615053893, 248204704247878, 248204504629317);

-- ----------------------------
-- Table structure for sys_operator_setting
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator_setting`;
CREATE TABLE `sys_operator_setting`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `app` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_operator_setting
-- ----------------------------
INSERT INTO `sys_operator_setting` VALUES (675142950023296, 248204704247877, 'dashboard', '2022-03-26 20:36:58', '2022-03-26 20:45:03');
INSERT INTO `sys_operator_setting` VALUES (675143177953408, 674996197965952, 'usercenter,user,manage,settingDept,login', '2022-03-26 20:46:14', '2022-03-26 21:53:30');

-- ----------------------------
-- Table structure for sys_order
-- ----------------------------
DROP TABLE IF EXISTS `sys_order`;
CREATE TABLE `sys_order`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `a` int NULL DEFAULT NULL,
  `b` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_order
-- ----------------------------
INSERT INTO `sys_order` VALUES (31, 666, 666);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限 ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父权限 ID (0为顶级菜单)',
  `label` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '授权标识符',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '授权路径',
  `order_num` int NULL DEFAULT 0 COMMENT '序号',
  `type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '类型(0 目录 1菜单，2按钮)',
  `icon` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图标',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `parent_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '父级菜单名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 120 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (17, 0, '系统管理', 'sys:user:manage', '/system', NULL, NULL, 1, '0', 'el-icon-monitor', NULL, '2023-08-08 11:11:11', '2021-11-13 13:05:24', '顶级菜单');
INSERT INTO `sys_permission` VALUES (18, 17, '用户管理', 'sys:user', '/userList', 'userList', '/system/user/UserList', 3, '1', 'el-icon-user', NULL, '2023-08-08 11:11:11', '2021-10-31 12:36:43', '系统管理');
INSERT INTO `sys_permission` VALUES (20, 18, '新增', 'sys:user:add', NULL, NULL, '', 2, '2', '', '新增用户', '2023-08-08 11:11:11', '2021-06-20 20:07:50', '用户管理');
INSERT INTO `sys_permission` VALUES (21, 18, '修改', 'sys:user:update', NULL, NULL, '', 3, '2', '', '修改用户', '2023-08-08 11:11:11', '2021-06-20 20:08:17', '用户管理');
INSERT INTO `sys_permission` VALUES (22, 18, '删除', 'sys:user:del', NULL, NULL, '', 4, '2', '', '删除用户', '2023-08-08 11:11:11', '2021-06-20 20:08:08', '用户管理');
INSERT INTO `sys_permission` VALUES (23, 17, '角色管理', 'sys:role', '/roleList', 'roleList', '/system/role/RoleList', 4, '1', 'el-icon-lock', NULL, '2023-08-08 11:11:11', '2021-06-26 22:46:45', '系统管理');
INSERT INTO `sys_permission` VALUES (25, 23, '新增', 'sys:role:add', NULL, NULL, '', 2, '2', '', '新增角色', '2023-08-08 11:11:11', '2021-06-20 20:13:32', '角色管理');
INSERT INTO `sys_permission` VALUES (26, 23, '修改', 'sys:role:update', NULL, NULL, '', 3, '2', '', '修改角色', '2023-08-08 11:11:11', '2021-06-20 20:13:51', '角色管理');
INSERT INTO `sys_permission` VALUES (27, 23, '删除', 'sys:role:del', NULL, NULL, '', 4, '2', '', '删除角色', '2023-08-08 11:11:11', '2021-06-20 20:14:04', '角色管理');
INSERT INTO `sys_permission` VALUES (28, 17, '菜单管理', 'sys:menu', '/menuList', 'menuList', '/system/menu/MenuList', 5, '1', 'el-icon-s-operation', NULL, '2023-08-08 11:11:11', '2023-08-09 15:26:28', '系统管理');
INSERT INTO `sys_permission` VALUES (30, 28, '新增', 'sys:menu:add', NULL, NULL, '', 1, '2', NULL, '新增权限', '2023-08-08 11:11:11', '2021-06-20 21:04:12', '菜单管理');
INSERT INTO `sys_permission` VALUES (31, 28, '编辑', 'sys:menu:update', NULL, NULL, '', 2, '2', NULL, '修改权限', '2023-08-08 11:11:11', '2021-06-20 21:03:59', '菜单管理');
INSERT INTO `sys_permission` VALUES (32, 28, '删除', 'sys:menu:del', NULL, NULL, '', 3, '2', '', '删除权限', '2023-08-08 11:11:11', '2021-06-20 21:04:24', '菜单管理');
INSERT INTO `sys_permission` VALUES (33, 17, '机构管理', 'sys:dept', '/departmentList', 'departmentList', '/system/department/DepartmentList', 2, '1', 'el-icon-connection', '机构管理', '2020-04-12 22:58:29', '2021-06-27 00:23:02', '系统管理');
INSERT INTO `sys_permission` VALUES (42, 0, '参数配置', 'sys:config:manage', '/config', '', NULL, 5, '0', 'el-icon-setting', NULL, '2020-04-12 22:50:03', '2021-10-31 12:52:29', '顶级菜单');
INSERT INTO `sys_permission` VALUES (46, 33, '新增', 'sys:dept:add', '', '', NULL, 1, '2', '', NULL, '2020-04-12 19:58:48', '2021-06-20 21:02:05', '部门管理');
INSERT INTO `sys_permission` VALUES (76, 33, '编辑', 'sys:dept:update', '', '', NULL, 2, '2', '', NULL, '2020-04-12 20:42:20', '2021-10-06 15:08:21', '部门管理');
INSERT INTO `sys_permission` VALUES (77, 42, '参数配置', 'sys:config', '/configList', 'configList', '/config/ConfigList', 1, '1', 'el-icon-setting', NULL, '2020-04-13 11:31:45', '2021-10-27 21:16:23', '系统工具');
INSERT INTO `sys_permission` VALUES (78, 33, '删除', 'sys:dept:del', '', '', '', 3, '2', '', NULL, '2020-04-18 10:25:55', '2021-06-20 21:02:34', '机构管理');
INSERT INTO `sys_permission` VALUES (79, 23, '分配权限', 'sys:role:assign', '', '', '', 1, '2', '', NULL, '2020-04-18 10:31:05', '2021-06-20 20:13:38', '角色管理');
INSERT INTO `sys_permission` VALUES (80, 18, '分配角色', 'sys:user:assign', '', '', '', 1, '2', '', NULL, '2020-04-18 10:50:14', '2021-06-20 20:07:35', '用户管理');
INSERT INTO `sys_permission` VALUES (92, 0, '字典管理', 'sys:dic:manage', '/dic', '', '', 6, '0', 'el-icon-collection', NULL, '2021-06-27 00:36:23', '2021-07-07 21:19:59', '根节点');
INSERT INTO `sys_permission` VALUES (100, 0, '日志管理', 'sys:log:manage', '/log', '', '', 7, '0', 'el-icon-document-copy', NULL, '2021-07-07 21:26:02', '2021-11-20 19:43:23', '根节点');
INSERT INTO `sys_permission` VALUES (101, 100, '操作日志', 'sys:log:manage', '/operatorLogList', 'operatorLogList', '/log/OperatorLogList', 1, '1', 'el-icon-caret-right', NULL, '2021-07-07 21:26:59', '2021-11-20 19:50:07', '日志管理');
INSERT INTO `sys_permission` VALUES (103, 92, '一级菜单', 'a', 'a', 'a', 'a', 0, '1', 'el-icon-download', NULL, '2021-10-03 20:47:36', '2021-11-06 10:57:23', '字典管理');
INSERT INTO `sys_permission` VALUES (104, 103, '二级菜单', 'b', 'b', 'b', 'b', 0, '1', 'el-icon-video-camera', NULL, '2021-10-03 20:47:52', '2021-11-06 10:57:59', '一级菜单');
INSERT INTO `sys_permission` VALUES (105, 104, '区域列表', 'c', 'c', 'c', 'c', 0, '1', 'el-icon-s-home', NULL, '2021-10-03 20:48:06', '2021-11-06 11:01:14', '二级菜单');
INSERT INTO `sys_permission` VALUES (106, 105, '添加', 'd', '', '', '', 0, '2', '', NULL, '2021-10-03 20:48:22', '2021-11-06 11:01:22', '三级菜单');
INSERT INTO `sys_permission` VALUES (108, 28, '展开/折叠', 'sys:menu:expand', '', '', '', 4, '2', '', NULL, '2021-10-22 20:37:47', '2021-10-22 20:37:47', '菜单管理');
INSERT INTO `sys_permission` VALUES (109, 33, '展开/折叠', 'sys:dept:expand', '', '', '', 4, '2', '', NULL, '2021-10-23 15:45:18', '2021-10-23 15:45:18', '机构管理');
INSERT INTO `sys_permission` VALUES (110, 18, '导入', 'sys:user:import', '', '', '', 5, '2', '', NULL, '2021-10-23 19:45:38', '2021-10-23 19:45:46', '用户管理');
INSERT INTO `sys_permission` VALUES (111, 18, '导出', 'sys:user:export', '', '', '', 6, '2', '', NULL, '2021-10-23 19:46:18', '2021-10-23 19:46:18', '用户管理');
INSERT INTO `sys_permission` VALUES (114, 77, '添加', 'sys:config:add', '', '', '', 1, '2', '', NULL, '2021-11-06 11:48:24', '2021-11-06 11:48:24', '参数配置');
INSERT INTO `sys_permission` VALUES (115, 77, '编辑', 'sys:config:update', '', '', '', 2, '2', '', NULL, '2021-11-06 11:48:39', '2021-11-06 11:48:39', '参数配置');
INSERT INTO `sys_permission` VALUES (116, 77, '删除', 'sys:config:del', '', '', '', 0, '2', '', NULL, '2021-11-06 11:48:53', '2021-11-06 11:48:53', '参数配置');
INSERT INTO `sys_permission` VALUES (117, 100, '登录日志', 'sys:login:list', '/loginLogList', 'loginLogList', '/log/LoginLogList', 2, '1', 'el-icon-caret-right', NULL, '2021-11-20 19:50:58', '2021-11-20 19:51:10', '日志管理');
INSERT INTO `sys_permission` VALUES (118, 101, '删除', 'sys:operatorLog:del', '', '', '', 1, '2', '', NULL, '2021-11-20 20:17:20', '2021-11-20 20:17:20', '操作日志');
INSERT INTO `sys_permission` VALUES (119, 117, '删除', 'sys:loginLog:del', '', '', '', 1, '2', '', NULL, '2021-11-20 22:57:33', '2021-11-20 22:57:33', '登录日志');

-- ----------------------------
-- Table structure for sys_product
-- ----------------------------
DROP TABLE IF EXISTS `sys_product`;
CREATE TABLE `sys_product`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `c` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_product
-- ----------------------------
INSERT INTO `sys_product` VALUES (21, 666);
INSERT INTO `sys_product` VALUES (26, 666);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色 ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `data_scope` bigint NULL DEFAULT NULL COMMENT '数据权限',
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色别名',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色说明',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 674996182413441 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (248204504629317, 'ADMIN', 3, '管理员A', '超级管理员', '2022-01-21 10:46:45', '2022-03-26 21:13:09');
INSERT INTO `sys_role` VALUES (256693602365509, 'ROOT', 4, '管理员B', '超级管理员', '2022-02-14 10:28:59', '2022-03-26 21:13:06');
INSERT INTO `sys_role` VALUES (674996182413440, 'WEB', 2, '超级管理员', '超级管理员', '2022-03-22 17:04:58', '2022-03-27 23:04:24');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `id` bigint NOT NULL,
  `role_id` bigint NULL DEFAULT NULL,
  `dept_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (675181963018368, 674996182413440, 1000001200689941);
INSERT INTO `sys_role_dept` VALUES (675181963018369, 674996182413440, 1000000204663981);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('\r\n\r\n257838251663429', '248204504629317', '112');
INSERT INTO `sys_role_menu` VALUES ('257837477670981', '248204504629317', '111');
INSERT INTO `sys_role_menu` VALUES ('257838321094725', '248204504629317', '113');
INSERT INTO `sys_role_menu` VALUES ('258142502264901', '248204504629317', '114');
INSERT INTO `sys_role_menu` VALUES ('258142598918213', '248204504629317', '115');
INSERT INTO `sys_role_menu` VALUES ('258143150133317', '248204504629317', '116');
INSERT INTO `sys_role_menu` VALUES ('258143150133318', '248204504629317', '117');
INSERT INTO `sys_role_menu` VALUES ('675071403167872', '256693602365509', '114');
INSERT INTO `sys_role_menu` VALUES ('675071403171968', '256693602365509', '115');
INSERT INTO `sys_role_menu` VALUES ('675071403171969', '256693602365509', '116');
INSERT INTO `sys_role_menu` VALUES ('675071403171970', '256693602365509', '112');
INSERT INTO `sys_role_menu` VALUES ('675071403171971', '256693602365509', '113');
INSERT INTO `sys_role_menu` VALUES ('675071403171972', '256693602365509', '111');
INSERT INTO `sys_role_menu` VALUES ('675182088429696', '674996182413440', '114');
INSERT INTO `sys_role_menu` VALUES ('675182088429697', '674996182413440', '115');
INSERT INTO `sys_role_menu` VALUES ('675182088429698', '674996182413440', '116');
INSERT INTO `sys_role_menu` VALUES ('675182088429699', '674996182413440', '111');
INSERT INTO `sys_role_menu` VALUES ('675182088433792', '674996182413440', '112');
INSERT INTO `sys_role_menu` VALUES ('675182088433793', '674996182413440', '113');
INSERT INTO `sys_role_menu` VALUES ('675182088433794', '674996182413440', '675181511221376');
INSERT INTO `sys_role_menu` VALUES ('675182088433795', '674996182413440', '675181535461504');
INSERT INTO `sys_role_menu` VALUES ('675182088433796', '674996182413440', '675181546832000');
INSERT INTO `sys_role_menu` VALUES ('675182088433797', '674996182413440', '117');
INSERT INTO `sys_role_menu` VALUES ('675182088433798', '674996182413440', '675181714677888');
INSERT INTO `sys_role_menu` VALUES ('675182088433799', '674996182413440', '675181723545728');
INSERT INTO `sys_role_menu` VALUES ('675182088433800', '674996182413440', '118');
INSERT INTO `sys_role_menu` VALUES ('675182088437888', '674996182413440', '675181755994240');
INSERT INTO `sys_role_menu` VALUES ('675182088437889', '674996182413440', '675181762871424');
INSERT INTO `sys_role_menu` VALUES ('675182088437890', '674996182413440', '119');
INSERT INTO `sys_role_menu` VALUES ('675182088437891', '674996182413440', '675181771518080');
INSERT INTO `sys_role_menu` VALUES ('675182088437892', '674996182413440', '675181780402304');
INSERT INTO `sys_role_menu` VALUES ('675182088437893', '674996182413440', '675181786669184');
INSERT INTO `sys_role_menu` VALUES ('675182088437894', '674996182413440', '675181798477952');
INSERT INTO `sys_role_menu` VALUES ('675182088437895', '674996182413440', '675181806932096');
INSERT INTO `sys_role_menu` VALUES ('675182088437896', '674996182413440', '675181820149888');
INSERT INTO `sys_role_menu` VALUES ('675182088441984', '674996182413440', '675073235689600');
INSERT INTO `sys_role_menu` VALUES ('675182088441985', '674996182413440', '675073256198272');
INSERT INTO `sys_role_menu` VALUES ('675182088441986', '674996182413440', '675181831987328');
INSERT INTO `sys_role_menu` VALUES ('675182088441987', '674996182413440', '675181998772352');
INSERT INTO `sys_role_menu` VALUES ('675182088441988', '674996182413440', '675073263906944');
INSERT INTO `sys_role_menu` VALUES ('675182088441989', '674996182413440', '675181843529856');
INSERT INTO `sys_role_menu` VALUES ('675182088441990', '674996182413440', '675182006534272');
INSERT INTO `sys_role_menu` VALUES ('675182088441991', '674996182413440', '675144297996416');
INSERT INTO `sys_role_menu` VALUES ('675182088441992', '674996182413440', '675144452886656');
INSERT INTO `sys_role_menu` VALUES ('675182088446080', '674996182413440', '675144589611136');
INSERT INTO `sys_role_menu` VALUES ('675182088446081', '674996182413440', '675144638443648');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `role_id` bigint NOT NULL COMMENT '角色 ID',
  `permission_id` bigint NOT NULL COMMENT '权限 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1561 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1443, 33, 92);
INSERT INTO `sys_role_permission` VALUES (1444, 33, 103);
INSERT INTO `sys_role_permission` VALUES (1445, 33, 104);
INSERT INTO `sys_role_permission` VALUES (1446, 33, 105);
INSERT INTO `sys_role_permission` VALUES (1447, 33, 106);
INSERT INTO `sys_role_permission` VALUES (1448, 33, 100);
INSERT INTO `sys_role_permission` VALUES (1449, 33, 101);
INSERT INTO `sys_role_permission` VALUES (1523, 9, 17);
INSERT INTO `sys_role_permission` VALUES (1524, 9, 33);
INSERT INTO `sys_role_permission` VALUES (1525, 9, 46);
INSERT INTO `sys_role_permission` VALUES (1526, 9, 76);
INSERT INTO `sys_role_permission` VALUES (1527, 9, 78);
INSERT INTO `sys_role_permission` VALUES (1528, 9, 109);
INSERT INTO `sys_role_permission` VALUES (1529, 9, 18);
INSERT INTO `sys_role_permission` VALUES (1530, 9, 80);
INSERT INTO `sys_role_permission` VALUES (1531, 9, 20);
INSERT INTO `sys_role_permission` VALUES (1532, 9, 21);
INSERT INTO `sys_role_permission` VALUES (1533, 9, 22);
INSERT INTO `sys_role_permission` VALUES (1534, 9, 110);
INSERT INTO `sys_role_permission` VALUES (1535, 9, 111);
INSERT INTO `sys_role_permission` VALUES (1536, 9, 23);
INSERT INTO `sys_role_permission` VALUES (1537, 9, 79);
INSERT INTO `sys_role_permission` VALUES (1538, 9, 25);
INSERT INTO `sys_role_permission` VALUES (1539, 9, 26);
INSERT INTO `sys_role_permission` VALUES (1540, 9, 27);
INSERT INTO `sys_role_permission` VALUES (1541, 9, 28);
INSERT INTO `sys_role_permission` VALUES (1542, 9, 30);
INSERT INTO `sys_role_permission` VALUES (1543, 9, 31);
INSERT INTO `sys_role_permission` VALUES (1544, 9, 32);
INSERT INTO `sys_role_permission` VALUES (1545, 9, 108);
INSERT INTO `sys_role_permission` VALUES (1546, 9, 42);
INSERT INTO `sys_role_permission` VALUES (1547, 9, 77);
INSERT INTO `sys_role_permission` VALUES (1548, 9, 116);
INSERT INTO `sys_role_permission` VALUES (1549, 9, 114);
INSERT INTO `sys_role_permission` VALUES (1550, 9, 115);
INSERT INTO `sys_role_permission` VALUES (1551, 9, 92);
INSERT INTO `sys_role_permission` VALUES (1552, 9, 103);
INSERT INTO `sys_role_permission` VALUES (1553, 9, 104);
INSERT INTO `sys_role_permission` VALUES (1554, 9, 105);
INSERT INTO `sys_role_permission` VALUES (1555, 9, 106);
INSERT INTO `sys_role_permission` VALUES (1556, 9, 100);
INSERT INTO `sys_role_permission` VALUES (1557, 9, 101);
INSERT INTO `sys_role_permission` VALUES (1558, 9, 118);
INSERT INTO `sys_role_permission` VALUES (1559, 9, 117);
INSERT INTO `sys_role_permission` VALUES (1560, 9, 119);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
