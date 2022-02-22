/*
 Navicat Premium Data Transfer

 Source Server         : dt
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : dtcms1.0

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 22/02/2022 11:41:40
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
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
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
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('cms', NULL, '$2a$10$bMNULS2JtrK.kEOIZw4MrOXgJ/B3XK3bnjkO58JHe2.t9kIBnScpe', 'web', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true');

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门id',
  `pid` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上级部门id',
  `parent_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上级部门名称',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '序号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES ('1000000130649867', '1000000874136172', '后端开发', 'PHP开发部', 2, '2021-06-20 21:55:35', '2021-06-20 21:55:35');
INSERT INTO `sys_department` VALUES ('1000000174227147', '1000001620535597', '客服部', '市场部门', 1, '2021-06-17 21:33:41', '2021-06-17 21:34:03');
INSERT INTO `sys_department` VALUES ('1000000187669045', '1000000874136172', '后端开发', 'Python开发部', 0, '2021-06-20 21:58:41', '2021-06-20 21:58:41');
INSERT INTO `sys_department` VALUES ('1000000204663981', '1000001200689941', '技术部', '前端部门', 3, '2021-06-17 21:27:59', '2021-06-17 21:28:22');
INSERT INTO `sys_department` VALUES ('1000000354532983', '0', '根节点', 'DT设计科技有限公司', 2, '2021-06-20 21:53:26', '2021-06-20 21:53:26');
INSERT INTO `sys_department` VALUES ('1000000622591924', '1000001620535597', '客服部', '设计部', 1, '2021-06-20 21:53:49', '2021-06-20 21:57:45');
INSERT INTO `sys_department` VALUES ('1000000819493000', '1000000874136172', '后端开发', 'C语言开发部', 3, '2021-06-20 21:55:49', '2021-06-20 21:55:49');
INSERT INTO `sys_department` VALUES ('1000000874136172', '1000002112552988', '研发部', '后端开发', 1, '2021-06-20 21:55:09', '2021-06-20 21:55:09');
INSERT INTO `sys_department` VALUES ('1000000952846438', '1000001251633881', '财务部', '财务部门', 1, '2021-06-17 21:29:00', '2021-10-03 16:50:39');
INSERT INTO `sys_department` VALUES ('1000001186458564', '1000001637526739', '服务部门', '服务二部', 2, '2021-06-20 21:56:29', '2021-06-20 21:56:29');
INSERT INTO `sys_department` VALUES ('1000001200689941', '1000001776185099', 'DT编程科技有限公司', '技术部', 1, '2021-06-13 19:15:04', '2021-06-17 21:25:31');
INSERT INTO `sys_department` VALUES ('1000001218109551', '1000000874136172', '后端开发', 'JAVA开发部', 1, '2021-06-20 21:55:24', '2021-06-20 21:55:24');
INSERT INTO `sys_department` VALUES ('1000001251633881', '1000001776185099', 'DT编程科技有限公司', '财务部', 2, '2021-06-13 14:35:36', '2021-06-17 21:29:25');
INSERT INTO `sys_department` VALUES ('1000001258096779', '1000001200689941', '技术部', 'UI设计部门', 4, '2021-06-17 21:28:10', '2021-06-17 21:31:41');
INSERT INTO `sys_department` VALUES ('1000001341234088', '1000001776185099', 'DT编程科技有限公司', '行政部', 3, '2021-06-13 14:35:38', '2021-06-17 21:26:17');
INSERT INTO `sys_department` VALUES ('1000001620535597', '1000001776185099', 'DT编程科技有限公司', '客服部', 4, '2021-06-13 14:35:40', '2021-06-17 21:29:39');
INSERT INTO `sys_department` VALUES ('1000001625392933', '1000001341234088', '行政部', '法律部门', 1, '2021-06-17 21:33:05', '2021-06-17 21:33:05');
INSERT INTO `sys_department` VALUES ('1000001637526739', '1000001620535597', '客服部', '服务部门', 2, '2021-06-17 21:33:55', '2021-06-17 21:33:55');
INSERT INTO `sys_department` VALUES ('1000001728835022', '1000001637526739', '服务部门', '服务一部', 1, '2021-06-20 21:56:19', '2021-06-20 21:56:19');
INSERT INTO `sys_department` VALUES ('1000001776185099', '0', '顶级部门', 'DT编程科技有限公司', 1, '2021-06-13 14:35:42', '2021-11-14 11:22:51');
INSERT INTO `sys_department` VALUES ('1000001779686042', '1000001200689941', '技术部', '后端部门', 1, '2021-06-17 21:27:48', '2021-06-17 21:27:48');
INSERT INTO `sys_department` VALUES ('1000001854756787', '1000000622591924', '设计部', '设计组一', 1, '2021-06-20 21:54:18', '2021-06-20 21:54:18');
INSERT INTO `sys_department` VALUES ('1000001934748021', '1000000622591924', '设计部', '设计组二', 2, '2021-06-20 21:54:27', '2021-06-20 21:54:27');
INSERT INTO `sys_department` VALUES ('1000001975876013', '1000000622591924', '设计部', '设计组三', 3, '2021-06-20 21:54:48', '2021-06-20 21:54:48');
INSERT INTO `sys_department` VALUES ('1000002112552988', '1000000354532983', 'DT设计科技有限公司', '研发部', 1, '2021-06-20 21:54:57', '2021-06-20 21:54:57');

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志标题',
  `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `login_ip` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '登录IP',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录浏览器',
  `operating_system` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `status` int(11) NULL DEFAULT NULL COMMENT '登录状态：1成功 2失败',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型：1登录系统 2退出系统',
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作消息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES ('1000000378088885', '登录系统', 'admin', '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', 1, 1, '登录成功', '2021-11-20 23:04:17', '2021-11-20 23:04:17');
INSERT INTO `sys_login_log` VALUES ('1000000381958973', '登录系统', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '登录成功', '2021-12-29 18:01:29', '2021-12-29 18:01:29');
INSERT INTO `sys_login_log` VALUES ('1000000394247653', '登录系统', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '登录成功', '2021-12-30 10:11:12', '2021-12-30 10:11:12');
INSERT INTO `sys_login_log` VALUES ('1000000647413177', '登录系统', 'admin', '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', 1, 1, '登录成功', '2021-11-20 22:58:56', '2021-11-20 22:58:56');
INSERT INTO `sys_login_log` VALUES ('1000000698567554', '登录系统', 'admin', '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', 1, 1, '登录成功', '2021-11-20 23:05:04', '2021-11-20 23:05:04');
INSERT INTO `sys_login_log` VALUES ('1000000789815459', '账号被锁', 'root', '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', 2, 2, '登录失败，账号被锁定15分钟', '2021-11-20 23:01:17', '2021-11-20 23:01:17');
INSERT INTO `sys_login_log` VALUES ('1000000956147914', '账号被锁', 'test', '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', 2, 1, '账号被锁定15分钟', '2021-11-20 23:03:50', '2021-11-20 23:03:50');
INSERT INTO `sys_login_log` VALUES ('1000002019704519', '登录系统', 'admin', '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', 1, 1, '登录成功', '2021-11-20 22:58:26', '2021-11-20 22:58:26');
INSERT INTO `sys_login_log` VALUES ('1000002079599789', '登录系统', 'admin', '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', 1, 1, '登录成功', '2021-11-20 23:01:25', '2021-11-20 23:01:25');
INSERT INTO `sys_login_log` VALUES ('255277142253637', 'admin在：2022-02-10 10:25:22 点击了登录', 'admin', '127.0.0.1', '谷歌', 'windows10', 1, 1, NULL, '2022-02-10 10:25:23', '2022-02-10 10:25:23');
INSERT INTO `sys_login_log` VALUES ('255277633196101', 'admin在：2022-02-10 10:27:22 点击了登录', 'admin', '127.0.0.1', '谷歌', 'windows10', 1, 1, NULL, '2022-02-10 10:27:23', '2022-02-10 10:27:23');
INSERT INTO `sys_login_log` VALUES ('255278541254725', 'admin在：2022-02-10 10:31:04 点击了登录', 'admin', '127.0.0.1', '谷歌', 'windows10', 1, 1, NULL, '2022-02-10 10:31:05', '2022-02-10 10:31:05');
INSERT INTO `sys_login_log` VALUES ('255282542932037', 'admin在：2022-02-10 10:47:21 点击了登录', 'admin', '127.0.0.1', '谷歌', 'windows10', 1, 1, NULL, '2022-02-10 10:47:22', '2022-02-10 10:47:22');
INSERT INTO `sys_login_log` VALUES ('255353125032005', 'admin在：2022-02-10 15:34:33 点击了登录', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, NULL, '2022-02-10 15:34:34', '2022-02-10 15:34:34');
INSERT INTO `sys_login_log` VALUES ('255356480700485', 'admin在：2022-02-10 15:48:12 点击了登录', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, NULL, '2022-02-10 15:48:13', '2022-02-10 15:48:13');
INSERT INTO `sys_login_log` VALUES ('255358427013189', 'admin在：2022-02-10 15:56:07 点击了登录', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 2, NULL, '2022-02-10 15:56:08', '2022-02-10 15:56:08');
INSERT INTO `sys_login_log` VALUES ('255358632611909', 'admin在：2022-02-10 15:56:58 点击了登录', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 2, NULL, '2022-02-10 15:56:58', '2022-02-10 15:56:58');
INSERT INTO `sys_login_log` VALUES ('255720064213061', 'admin在：2022-02-11 16:27:38 点击了登录', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, NULL, '2022-02-11 16:27:38', '2022-02-11 16:27:38');
INSERT INTO `sys_login_log` VALUES ('255726480654405', 'admin在：2022-02-11 16:53:44 点击了登录', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, NULL, '2022-02-11 16:53:45', '2022-02-11 16:53:45');
INSERT INTO `sys_login_log` VALUES ('255732245528645', 'admin在：2022-02-11 17:17:12 点击了登录', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, NULL, '2022-02-11 17:17:12', '2022-02-11 17:17:12');
INSERT INTO `sys_login_log` VALUES ('255736898158661', 'admin在：2022-02-11 17:36:08 点击了登录', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, NULL, '2022-02-11 17:36:08', '2022-02-11 17:36:08');
INSERT INTO `sys_login_log` VALUES ('255741519315013', 'admin在：2022-02-11 17:54:56 点击了登录', 'admin', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, NULL, '2022-02-11 17:54:56', '2022-02-11 17:54:56');
INSERT INTO `sys_login_log` VALUES ('259205637263429', 'admin在：2022-02-21 12:50:28 点击了登录', 'admin', '192.168.200.248', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-02-21 12:50:28', '2022-02-21 12:50:28');
INSERT INTO `sys_login_log` VALUES ('259206696972357', 'admin在：2022-02-21 12:54:46 点击了登录', 'admin', '192.168.200.248', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-02-21 12:54:47', '2022-02-21 12:54:47');
INSERT INTO `sys_login_log` VALUES ('259207166926917', 'admin在：2022-02-21 12:56:41 点击了登录', 'admin', '192.168.200.248', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-02-21 12:56:42', '2022-02-21 12:56:42');
INSERT INTO `sys_login_log` VALUES ('259209907118149', 'admin在：2022-02-21 13:07:50 点击了登录', 'admin', '192.168.200.248', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-02-21 13:07:51', '2022-02-21 13:07:51');
INSERT INTO `sys_login_log` VALUES ('259210841428037', 'admin在：2022-02-21 13:11:38 点击了登录', 'admin', '192.168.200.248', 'Chrome 9', 'Windows 10', 1, 1, NULL, '2022-02-21 13:11:39', '2022-02-21 13:11:39');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_num` int(11) NULL DEFAULT 1,
  `sort` int(8) NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `redirect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hidden` tinyint(1) NULL DEFAULT 0,
  `hidden_breadcrumb` tinyint(1) NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('111', '0', '系统管理', '/setting', 'setting', NULL, '系统管理', 'el-icon-setting', 'menu', 1, 4, NULL, NULL, NULL, 0, 0, '2022-02-22 11:27:20', '2022-02-22 11:27:20');
INSERT INTO `sys_menu` VALUES ('112', '111', '系统管理', '/setting/user', 'user', 'setting/user', '用户管理', 'el-icon-user-filled', 'menu', 2, 5, 'user.list', NULL, NULL, 0, 0, '2022-02-22 11:27:19', '2022-02-22 11:27:19');
INSERT INTO `sys_menu` VALUES ('113', '112', '用户管理', NULL, NULL, NULL, '添加', NULL, 'button', 3, 6, 'user.add', NULL, NULL, 0, 0, '2022-02-22 11:27:18', '2022-02-22 11:27:18');
INSERT INTO `sys_menu` VALUES ('114', '0', '首页', '/home', 'home', NULL, '首页', 'el-icon-data-line', 'menu', 1, 1, NULL, NULL, NULL, 0, 0, '2022-02-22 11:27:17', '2022-02-22 11:27:17');
INSERT INTO `sys_menu` VALUES ('115', '114', '首页', '/dashboard', 'dashboard', 'home', '控制台', 'el-icon-menu', 'menu', 2, 2, NULL, NULL, NULL, 0, 0, '2022-02-22 11:27:16', '2022-02-22 11:27:16');
INSERT INTO `sys_menu` VALUES ('116', '114', '首页', '/usercenter', 'usercenter', 'userCenter', '个人信息', 'el-icon-user', 'menu', 3, 3, NULL, NULL, NULL, 0, 0, '2022-02-22 11:27:15', '2022-02-22 11:27:15');
INSERT INTO `sys_menu` VALUES ('117', '111', '系统管理', '/setting/menu', 'settingMenu', 'setting/menu', '菜单管理', 'el-icon-fold', 'menu', 1, 7, NULL, '#981515', 'https://www.baidu.com', 0, 0, '2022-02-22 11:34:18', '2022-02-22 11:34:18');

-- ----------------------------
-- Table structure for sys_operator
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator`;
CREATE TABLE `sys_operator`  (
  `id` bigint(20) NOT NULL COMMENT '用户 ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统登录名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码，加密存储, admin/1234',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用范围（web PC端， app 手机端）',
  `is_account_non_expired` int(11) NOT NULL DEFAULT 1 COMMENT '帐户是否过期(1 未过期，0已过期)',
  `is_account_non_locked` int(11) NOT NULL DEFAULT 1 COMMENT '帐户是否被锁定(1 未过期，0已过期)',
  `is_credentials_non_expired` int(11) NOT NULL DEFAULT 1 COMMENT '密码是否过期(1 未过期，0已过期)',
  `is_enabled` int(11) NOT NULL DEFAULT 1 COMMENT '帐户是否可用(1 可用，0 删除用户)',
  `avatar` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户图片地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_operator
-- ----------------------------
INSERT INTO `sys_operator` VALUES (248204704247877, 'admin', '$2a$10$Ty9JjBK2n/lGPjqsHYC8ve5Wd8esEhbK7aF0ryxFVa/5ymnDvmmYu', 'web', 1, 1, 1, 1, NULL, '2022-01-21 10:47:34', '2022-01-21 10:47:34');
INSERT INTO `sys_operator` VALUES (256689773064261, 'admin1', '$2a$10$v3a5.2KtUAQHKWZ5sGhH9.uqDmMFTTGVtoYxAMuicXkzSo6aoQZrm', NULL, 1, 1, 1, 1, NULL, '2022-02-14 10:13:24', '2022-02-14 10:13:24');
INSERT INTO `sys_operator` VALUES (256689985814597, 'admin2', '$2a$10$.PeZou3E0.ACogK9Dq0FDO0gsum0Xm9p/zpherySSbajJ.HsAf0LG', NULL, 1, 1, 1, 1, NULL, '2022-02-14 10:14:16', '2022-02-14 10:14:16');
INSERT INTO `sys_operator` VALUES (256689998258245, 'admin3', '$2a$10$tsqIqYYDl.6X/LxWoeKe6ebVoBrkR4J21VzSxvyy91Di3MBK1J/iK', NULL, 1, 1, 1, 1, NULL, '2022-02-14 10:14:19', '2022-02-14 10:14:19');
INSERT INTO `sys_operator` VALUES (256690010067013, 'admin4', '$2a$10$HdPGnx46BvoNt/tE50L24uOZDSYp1QjwJr3ZcNmPy4ZmvOCD9NvqW', NULL, 1, 1, 1, 1, NULL, '2022-02-14 10:14:21', '2022-02-14 10:14:21');
INSERT INTO `sys_operator` VALUES (256690022285381, 'admin5', '$2a$10$ko/1lDBwqFqVnjjr6OKFAeAoyrBxyGUvTZpFjjvUWon4QOJj6YTuq', NULL, 1, 1, 1, 1, NULL, '2022-02-14 10:14:24', '2022-02-14 10:14:24');
INSERT INTO `sys_operator` VALUES (256690034090053, 'admin6', '$2a$10$cURKOzekEAde1p9vSMgVBezeYBCECBdJK97mZMLzKmf6kTOERHuOK', NULL, 1, 1, 1, 1, NULL, '2022-02-14 10:14:27', '2022-02-14 10:14:27');
INSERT INTO `sys_operator` VALUES (256690045460549, 'admin7', '$2a$10$NUe/bK2Lhx0yqx4rTCUdZedmM2jofW6eGL1/.TIZB6lpZbRC8ydhS', NULL, 1, 1, 1, 1, NULL, '2022-02-14 10:14:30', '2022-02-14 10:14:30');
INSERT INTO `sys_operator` VALUES (256690056187973, 'admin8', '$2a$10$k749DHsKf5j.jBBIIM2NpOvp3Y50Unk121qH7yJpbj1Hz3NGZc1Z.', NULL, 1, 1, 1, 1, NULL, '2022-02-14 10:14:33', '2022-02-14 10:14:33');

-- ----------------------------
-- Table structure for sys_operator_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator_log`;
CREATE TABLE `sys_operator_log`  (
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
  `status` int(11) NULL DEFAULT NULL COMMENT '操作状态：1正常 2异常',
  `error_info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '错误信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_operator_log
-- ----------------------------
INSERT INTO `sys_operator_log` VALUES ('1000000247215363', '删除平台系统角色', 'delete', 'com.dt.app.controller.account.SysRoleController.delete()', 'DELETE', 'admin', '/api/v1/role/delete/40', '127.0.0.1', '40', '{\"code\":2000,\"msg\":\"操作成功!\"}', 1, NULL, '2021-11-20 20:51:10', '2021-11-20 20:51:10');
INSERT INTO `sys_operator_log` VALUES ('1000000321526495', '删除平台系统角色', 'delete', 'com.dt.app.controller.account.SysRoleController.delete()', 'DELETE', 'admin', '/api/v1/role/delete/41', '127.0.0.1', '41', '{\"code\":2000,\"msg\":\"操作成功!\"}', 1, NULL, '2021-11-20 20:51:12', '2021-11-20 20:51:12');
INSERT INTO `sys_operator_log` VALUES ('1000000420629317', '删除平台系统角色', 'delete', 'com.dt.app.controller.account.SysRoleController.delete()', 'DELETE', 'admin', '/api/v1/role/delete/38', '127.0.0.1', '38', '{\"code\":2000,\"msg\":\"操作成功!\"}', 1, NULL, '2021-11-20 20:48:30', '2021-11-20 20:48:30');
INSERT INTO `sys_operator_log` VALUES ('1000000597064637', '更新平台系统角色', 'update', 'com.dt.app.controller.account.SysRoleController.update()', 'PUT', 'admin', '/api/v1/role/update', '127.0.0.1', '{\"name\":\"FINANCE\",\"remark\":\"备注：财务部管理员描述\",\"id\":31}', '{\"code\":2000,\"msg\":\"操作成功!\"}', 1, NULL, '2021-11-20 20:48:49', '2021-11-20 20:48:49');
INSERT INTO `sys_operator_log` VALUES ('1000001358058078', '删除平台系统角色', 'delete', 'com.dt.app.controller.account.SysRoleController.delete()', 'DELETE', 'admin', '/api/v1/role/delete/37', '127.0.0.1', '37', '{\"code\":2000,\"msg\":\"操作成功!\"}', 1, NULL, '2021-11-20 20:48:27', '2021-11-20 20:48:27');
INSERT INTO `sys_operator_log` VALUES ('1000001404945205', '删除平台系统角色', 'delete', 'com.dt.app.controller.account.SysRoleController.delete()', 'DELETE', 'admin', '/api/v1/role/delete/39', '127.0.0.1', '39', '{\"code\":2000,\"msg\":\"操作成功!\"}', 1, NULL, '2021-11-20 20:48:33', '2021-11-20 20:48:33');
INSERT INTO `sys_operator_log` VALUES ('1000001874771042', '删除平台系统角色', 'delete', 'com.dt.app.controller.account.SysRoleController.delete()', 'DELETE', 'admin', '/api/v1/role/delete/43', '127.0.0.1', '43', '{\"code\":2000,\"msg\":\"操作成功!\"}', 1, NULL, '2021-11-20 20:45:36', '2021-11-20 20:45:36');
INSERT INTO `sys_operator_log` VALUES ('1000001962683932', '删除平台系统角色', 'delete', 'com.dt.app.controller.account.SysRoleController.delete()', 'DELETE', 'admin', '/api/v1/role/delete/42', '127.0.0.1', '42', '{\"code\":2000,\"msg\":\"操作成功!\"}', 1, NULL, '2021-11-20 20:51:14', '2021-11-20 20:51:14');
INSERT INTO `sys_operator_log` VALUES ('1000001966297287', '新增平台系统角色', 'insert', 'com.dt.app.controller.account.SysRoleController.save()', 'POST', 'admin', '/api/v1/role/save', '127.0.0.1', '{\"name\":\"aaaaaa\",\"remark\":\"bbbbb\"}', '{\"code\":2000,\"msg\":\"操作成功!\"}', 1, NULL, '2021-11-20 18:29:51', '2021-11-20 18:29:51');
INSERT INTO `sys_operator_log` VALUES ('255693587071045', '删除操作员日志记录', 'delete', 'com.cms.manage.controller.SysOperatorController.delete()', 'DELETE', NULL, '/operator/delete/1', NULL, '1', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1644561594050}', 1, NULL, '2022-02-11 14:39:54', '2022-02-11 14:39:54');
INSERT INTO `sys_operator_log` VALUES ('256691907207237', '删除操作员日志记录', 'DELETE', 'com.cms.manage.controller.SysOperatorController.delete()', 'DELETE', '未知用户', '/operator/delete/256690068275269', '0:0:0:0:0:0:0:1', '256690068275269', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1644805324184}', 1, NULL, '2022-02-14 10:22:05', '2022-02-14 10:22:05');

-- ----------------------------
-- Table structure for sys_operator_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator_role`;
CREATE TABLE `sys_operator_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键 ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_operator_role
-- ----------------------------
INSERT INTO `sys_operator_role` VALUES (248204704260165, 248204704247877, 248204504629317);

-- ----------------------------
-- Table structure for sys_order
-- ----------------------------
DROP TABLE IF EXISTS `sys_order`;
CREATE TABLE `sys_order`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `a` int(255) NULL DEFAULT NULL,
  `b` int(255) NULL DEFAULT NULL,
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限 ID',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父权限 ID (0为顶级菜单)',
  `label` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '授权标识符',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '授权路径',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '序号',
  `type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '类型(0 目录 1菜单，2按钮)',
  `icon` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图标',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `c` int(255) NULL DEFAULT NULL,
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色 ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色说明',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 256693602365510 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (248204504629317, 'ADMIN', '超级管理员', '2022-01-21 10:46:45', '2022-01-21 10:46:45');
INSERT INTO `sys_role` VALUES (256693602365509, 'ROOT', '超级管理员', '2022-02-14 10:28:59', '2022-02-14 10:28:59');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限 ID',
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
