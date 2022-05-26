/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 42.192.121.230:3306
 Source Schema         : dt_cloud_cms

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 26/05/2022 21:12:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Trigger作为Blob类型存储（用于Quartz用户用JDBC创建他们自己定制的Trigger类型，JobStore 并不知道如何存储实例的时候）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '以Blob类型存储Quartz的Calendar日历信息， quartz可配置一个日历来指定一个时间范围' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'triggers表trigger_name的外键',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储Cron Trigger，包括Cron表达式和时区信息。' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('scheduler', '675674866094208', 'DEFAULT', '0 0/10 * * * ? ', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储与已触发的Trigger相关的状态信息，以及相联Job的执行信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'JOB名称',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'JOB所属组',
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'job实现类的完全包名，quartz就是根据这个路径到classpath找到该job类',
  `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否持久化：0.否 1.是',
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_DATA` blob NULL COMMENT 'blob字段，存放持久化job对象',
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储每一个已配置的Job的详细信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('scheduler', '675674866094208', 'DEFAULT', '定时删除系统日志', 'com.cms.job.task.bean.CronLoginLogJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400067461736B496474000F3637353637343836363039343230387400127363686564756C655461736B4F626A4B657973720020636F6D2E636D732E6A6F622E656E746974792E51756172747A4A6F62496E666FB186A23DE4A9471F0200064C000E63726F6E45787072657373696F6E7400124C6A6176612F6C616E672F537472696E673B4C0007646174614D61707400174C6F72672F71756172747A2F4A6F62446174614D61703B4C00086A6F62436C6173737400114C6A6176612F6C616E672F436C6173733B4C000D7461736B47726F75704E616D6571007E000B4C00067461736B496471007E000B4C00087461736B4E616D6571007E000B787074000F3020302F3130202A202A202A203F207076720025636F6D2E636D732E6A6F622E7461736B2E6265616E2E43726F6E4C6F67696E4C6F674A6F620000000000000000000000787074000764656661756C7471007E0008740018E5AE9AE697B6E588A0E999A4E7B3BBE7BB9FE697A5E5BF977800);

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储程序的非观锁的信息(假如使用了悲观锁)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('scheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储已暂停的Trigger组的信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储少量的有关 Scheduler的状态信息，和别的 Scheduler 实例(假如是用于一个集群中)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储简单的 Trigger，包括重复次数，间隔，以及已触的次数' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int NULL DEFAULT NULL,
  `INT_PROP_2` int NULL DEFAULT NULL,
  `LONG_PROP_1` bigint NULL DEFAULT NULL,
  `LONG_PROP_2` bigint NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'trigger名称',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'trigger所属组',
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PRIORITY` int NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当前trigger状态，设置为ACQUIRED,如果设置为WAITING,则job不会触发',
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'trigger类型，CRON表达式',
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储已配置的 Trigger的信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('scheduler', '675674866094208', 'DEFAULT', '675674866094208', 'DEFAULT', NULL, 1653571200000, 1653570600000, 5, 'WAITING', 'CRON', 1649596841000, 0, NULL, 0, '');

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
INSERT INTO `oauth_client_details` VALUES ('cms-admin-web', NULL, '$2a$10$Ta0.HORUIlBZAAt9eFsZy.mrPmfpUSYusbK0gvc3nt8MgQz6XRRFe', 'all', 'captcha,refresh_token', NULL, NULL, 3600, 7200, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('cms-app', NULL, '$2a$10$qvBN7bD7xsxMqYAfr9mRUORGFHzF8yVUX/yiuBcI5Pe8r2pj85766', 'all', 'sms_code,refresh_token', NULL, NULL, 3600, 7200, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('cms-idcard', NULL, '$2a$10$Umv04ceouTDHrVQ4/FiPGux/7HPlr2sRv.dEp0YX.HxIXEoCXeWUa', 'all', 'id_card,refresh_token', NULL, NULL, 3600, 7200, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('cms-web', NULL, '$2a$10$bMNULS2JtrK.kEOIZw4MrOXgJ/B3XK3bnjkO58JHe2.t9kIBnScpe', 'all', 'authorization_code,refresh_token', 'http://www.baidu.com', NULL, 3600, 7200, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('cms-wechat', NULL, '$2a$10$z5HxhG09/KoDdjliO/PZe.XH0YccopieCDH4./hqFgd5fGQs2cIO.', 'all', 'wechat,refresh_token', NULL, NULL, 3600, 7200, NULL, 'true');

-- ----------------------------
-- Table structure for pms_device
-- ----------------------------
DROP TABLE IF EXISTS `pms_device`;
CREATE TABLE `pms_device`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_device
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL,
  `k` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `v` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (675106132562011, 'wx_appid', '4B5F44A293B32400537407BC25FD11C70610C93709AB5CC514BF7BA4C6008722', '2022-04-17 18:45:00', '2022-05-25 21:26:23');
INSERT INTO `sys_config` VALUES (675106132562012, 'wx_enabled', '1', '2022-04-19 15:28:44', '2022-05-25 21:26:23');
INSERT INTO `sys_config` VALUES (675106132562013, 'ti_cron', 'C9A7C5B844911287873803059C742688', '2022-04-19 15:58:14', '2022-05-25 21:26:24');
INSERT INTO `sys_config` VALUES (676062391390336, 'ex_ttc', '4536581B0B89D8C0ACB95B1859268784', '2022-04-21 20:09:08', '2022-05-25 21:26:24');
INSERT INTO `sys_config` VALUES (677127269904512, 'wx_secret', '6AAB2FD6A64FA09B255CA8190FB737D5713C8B68A63A944C38C3CDFE2CA326C281E966A7B4CEAEEE8EA37639435BBF39', '2022-05-21 22:19:09', '2022-05-25 21:26:23');
INSERT INTO `sys_config` VALUES (677127269957760, 'wx_templateid', '969899FDA44C8225B868A0A2647C948E1A212F9726245865AB4CD234F3EDFE9FE042ED7E94BBA3CED941D920C41CC6BC', '2022-05-21 22:19:09', '2022-05-25 21:26:23');
INSERT INTO `sys_config` VALUES (677129794703488, 'qy_corpid', '1F6F33765181A3087759AFD8D954419BDEF80177AF05049461A0BC0EFC7D34DE', '2022-05-22 00:01:53', '2022-05-25 21:26:23');
INSERT INTO `sys_config` VALUES (677129794756736, 'qy_agentid', '3F2A907F73A86F20738FDD6CC5EC7775', '2022-05-22 00:01:53', '2022-05-25 21:26:23');
INSERT INTO `sys_config` VALUES (677129794809984, 'qy_corpsecret', 'D73E338DA97EFE69C909E17A2328CAA0B80736C411EF2619416AC1A291F8F5DAC41215D03356BC8788FEC39F01702073', '2022-05-22 00:01:53', '2022-05-25 21:26:23');
INSERT INTO `sys_config` VALUES (677129794867328, 'qy_enabled', '1', '2022-05-22 00:01:53', '2022-05-25 21:26:24');

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
-- Table structure for sys_dic
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic`;
CREATE TABLE `sys_dic`  (
  `id` bigint NOT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enabled` tinyint(1) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dic
-- ----------------------------
INSERT INTO `sys_dic` VALUES (675672714387584, '101', '学年', 'baseType', 0, '2022-04-10 19:53:08', '2022-04-10 19:53:08');
INSERT INTO `sys_dic` VALUES (677110866047104, '102', '季度', 'dataType', 1, '2022-05-21 11:11:40', '2022-05-21 11:11:40');
INSERT INTO `sys_dic` VALUES (677110874067072, '103', '会员', 'dataType', 1, '2022-05-21 11:12:00', '2022-05-21 11:12:00');
INSERT INTO `sys_dic` VALUES (677110880575616, '104', '课程', 'messageType', 1, '2022-05-21 11:12:16', '2022-05-21 11:12:16');
INSERT INTO `sys_dic` VALUES (677110887645312, '105', '奖金', 'noticeType', 1, '2022-05-21 11:12:33', '2022-05-21 11:12:33');
INSERT INTO `sys_dic` VALUES (677110894116992, '106', '月卡', 'baseType', 1, '2022-05-21 11:12:49', '2022-05-21 11:12:49');
INSERT INTO `sys_dic` VALUES (677110899028096, '107', '年级', 'baseType', 1, '2022-05-21 11:13:01', '2022-05-21 11:13:01');
INSERT INTO `sys_dic` VALUES (677110908432512, '108', '消息', 'messageType', 0, '2022-05-21 11:13:24', '2022-05-25 22:16:02');
INSERT INTO `sys_dic` VALUES (677110922436736, '109', '对象', 'messageType', 1, '2022-05-21 11:13:58', '2022-05-21 11:13:58');
INSERT INTO `sys_dic` VALUES (677110934388864, '200', '学校', 'noticeType', 1, '2022-05-21 11:14:27', '2022-05-21 11:14:27');

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
  `message_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MQ消息ID',
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作消息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
INSERT INTO `sys_log_login` VALUES ('675106132562048', 'dt在：2022-03-25 19:38:51 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-25 19:38:51', '2022-03-25 19:38:51');
INSERT INTO `sys_log_login` VALUES ('675110987538560', 'admin在：2022-03-25 22:56:24 点击了登录', 'admin', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-25 22:56:24', '2022-03-25 22:56:24');
INSERT INTO `sys_log_login` VALUES ('675111007498368', 'dt在：2022-03-25 22:57:13 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-25 22:57:13', '2022-03-25 22:57:13');
INSERT INTO `sys_log_login` VALUES ('675129649299584', 'admin在：2022-03-26 11:35:45 点击了登录', 'admin', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 11:35:45', '2022-03-26 11:35:45');
INSERT INTO `sys_log_login` VALUES ('675129656197248', 'dt在：2022-03-26 11:36:02 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 11:36:02', '2022-03-26 11:36:02');
INSERT INTO `sys_log_login` VALUES ('675132655628416', 'admin在：2022-03-26 13:38:05 点击了登录', 'admin', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 13:38:05', '2022-03-26 13:38:05');
INSERT INTO `sys_log_login` VALUES ('675132661465216', 'dt在：2022-03-26 13:38:19 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 13:38:19', '2022-03-26 13:38:19');
INSERT INTO `sys_log_login` VALUES ('675133697495168', 'dt在：2022-03-26 14:20:28 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 14:20:29', '2022-03-26 14:20:29');
INSERT INTO `sys_log_login` VALUES ('675134085668992', 'admin在：2022-03-26 14:36:16 点击了登录', 'admin', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 14:36:16', '2022-03-26 14:36:16');
INSERT INTO `sys_log_login` VALUES ('675134849527936', 'dt在：2022-03-26 15:07:21 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 15:07:21', '2022-03-26 15:07:21');
INSERT INTO `sys_log_login` VALUES ('675134901076096', 'dt在：2022-03-26 15:09:27 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 15:09:27', '2022-03-26 15:09:27');
INSERT INTO `sys_log_login` VALUES ('675135131910272', 'dt在：2022-03-26 15:18:50 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 15:18:51', '2022-03-26 15:18:51');
INSERT INTO `sys_log_login` VALUES ('675140015325312', 'admin在：2022-03-26 18:37:32 点击了登录', 'admin', '192.168.31.158', 'Mobile Safari', 'iOS 10 (iPhone)', 1, 1, NULL, NULL, '2022-03-26 18:37:33', '2022-03-26 18:37:33');
INSERT INTO `sys_log_login` VALUES ('675141413855360', 'dt在：2022-03-26 19:34:27 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 19:34:27', '2022-03-26 19:34:27');
INSERT INTO `sys_log_login` VALUES ('675142699143296', 'admin在：2022-03-26 20:26:45 点击了登录', 'admin', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 20:26:45', '2022-03-26 20:26:45');
INSERT INTO `sys_log_login` VALUES ('675143169917056', 'dt在：2022-03-26 20:45:54 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 20:45:55', '2022-03-26 20:45:55');
INSERT INTO `sys_log_login` VALUES ('675143778627712', 'dt在：2022-03-26 21:10:40 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 21:10:41', '2022-03-26 21:10:41');
INSERT INTO `sys_log_login` VALUES ('675143909761152', 'dt在：2022-03-26 21:16:00 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 21:16:01', '2022-03-26 21:16:01');
INSERT INTO `sys_log_login` VALUES ('675143995998336', 'dt在：2022-03-26 21:19:31 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 21:19:31', '2022-03-26 21:19:31');
INSERT INTO `sys_log_login` VALUES ('675144421740672', 'dt在：2022-03-26 21:36:50 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 21:36:51', '2022-03-26 21:36:51');
INSERT INTO `sys_log_login` VALUES ('675144535732352', 'dt在：2022-03-26 21:41:29 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 21:41:29', '2022-03-26 21:41:29');
INSERT INTO `sys_log_login` VALUES ('675144652562560', 'dt在：2022-03-26 21:46:14 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 21:46:14', '2022-03-26 21:46:14');
INSERT INTO `sys_log_login` VALUES ('675144824578176', 'dt在：2022-03-26 21:53:14 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-26 21:53:14', '2022-03-26 21:53:14');
INSERT INTO `sys_log_login` VALUES ('675165194760320', 'dt在：2022-03-27 11:42:06 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-27 11:42:06', '2022-03-27 11:42:06');
INSERT INTO `sys_log_login` VALUES ('675179795128448', 'dt在：2022-03-27 21:36:11 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-27 21:36:12', '2022-03-27 21:36:12');
INSERT INTO `sys_log_login` VALUES ('675181628907648', 'dt在：2022-03-27 22:50:48 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-27 22:50:49', '2022-03-27 22:50:49');
INSERT INTO `sys_log_login` VALUES ('675181645410432', 'dt在：2022-03-27 22:51:28 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-27 22:51:29', '2022-03-27 22:51:29');
INSERT INTO `sys_log_login` VALUES ('675181745115264', 'dt在：2022-03-27 22:55:32 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-27 22:55:32', '2022-03-27 22:55:32');
INSERT INTO `sys_log_login` VALUES ('675181876363392', 'dt在：2022-03-27 23:00:52 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-27 23:00:53', '2022-03-27 23:00:53');
INSERT INTO `sys_log_login` VALUES ('675182039068800', 'dt在：2022-03-27 23:07:30 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-27 23:07:30', '2022-03-27 23:07:30');
INSERT INTO `sys_log_login` VALUES ('675182071902336', 'dt在：2022-03-27 23:08:50 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-27 23:08:50', '2022-03-27 23:08:50');
INSERT INTO `sys_log_login` VALUES ('675182095437952', 'dt在：2022-03-27 23:09:47 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-27 23:09:48', '2022-03-27 23:09:48');
INSERT INTO `sys_log_login` VALUES ('675214599049344', 'dt在：2022-03-28 21:12:21 点击了登录', 'dt', '192.168.31.158', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-28 21:12:22', '2022-03-28 21:12:22');
INSERT INTO `sys_log_login` VALUES ('675248769564800', 'dt在：2022-03-29 20:22:46 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-03-29 20:22:46', '2022-03-29 20:22:46');
INSERT INTO `sys_log_login` VALUES ('675355885867136', 'dt在：2022-04-01 21:01:20 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-01 21:01:21', '2022-04-01 21:01:21');
INSERT INTO `sys_log_login` VALUES ('675356197847168', 'dt在：2022-04-01 21:14:02 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-01 21:14:02', '2022-04-01 21:14:02');
INSERT INTO `sys_log_login` VALUES ('675356322574464', 'dt在：2022-04-01 21:19:06 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-01 21:19:07', '2022-04-01 21:19:07');
INSERT INTO `sys_log_login` VALUES ('675356342894720', 'dt在：2022-04-01 21:19:56 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-01 21:19:57', '2022-04-01 21:19:57');
INSERT INTO `sys_log_login` VALUES ('675450494468224', 'dt在：2022-04-04 13:10:58 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-04 13:10:59', '2022-04-04 13:10:59');
INSERT INTO `sys_log_login` VALUES ('675484600377472', 'dt在：2022-04-05 12:18:44 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-05 12:18:45', '2022-04-05 12:18:45');
INSERT INTO `sys_log_login` VALUES ('675530605326464', 'dt在：2022-04-06 19:30:41 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-06 19:30:42', '2022-04-06 19:30:42');
INSERT INTO `sys_log_login` VALUES ('675561982226560', 'dt在：2022-04-07 16:47:25 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-04-07 16:47:26', '2022-04-07 16:47:26');
INSERT INTO `sys_log_login` VALUES ('675562060284032', 'dt在：2022-04-07 16:50:36 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-04-07 16:50:36', '2022-04-07 16:50:36');
INSERT INTO `sys_log_login` VALUES ('675565560393856', 'dt在：2022-04-07 19:13:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-07 19:13:01', '2022-04-07 19:13:01');
INSERT INTO `sys_log_login` VALUES ('675589026467968', 'dt在：2022-04-08 11:07:51 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-04-08 11:07:52', '2022-04-08 11:07:52');
INSERT INTO `sys_log_login` VALUES ('675592690815104', 'dt在：2022-04-08 13:36:57 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-04-08 13:36:58', '2022-04-08 13:36:58');
INSERT INTO `sys_log_login` VALUES ('675595724968064', 'dt在：2022-04-08 15:40:25 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-04-08 15:40:25', '2022-04-08 15:40:25');
INSERT INTO `sys_log_login` VALUES ('675598781665408', 'dt在：2022-04-08 17:44:47 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, NULL, NULL, '2022-04-08 17:44:48', '2022-04-08 17:44:48');
INSERT INTO `sys_log_login` VALUES ('675601314594944', 'dt在：2022-04-08 19:27:51 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-08 19:27:52', '2022-04-08 19:27:52');
INSERT INTO `sys_log_login` VALUES ('675604787871872', 'dt在：2022-04-08 21:49:11 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-08 21:49:12', '2022-04-08 21:49:12');
INSERT INTO `sys_log_login` VALUES ('675607756210304', 'dt在：2022-04-08 23:49:58 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-08 23:49:59', '2022-04-08 23:49:59');
INSERT INTO `sys_log_login` VALUES ('675630570766464', 'dt在：2022-04-09 15:18:17 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-09 15:18:18', '2022-04-09 15:18:18');
INSERT INTO `sys_log_login` VALUES ('675634328014976', 'dt在：2022-04-09 17:51:11 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-09 17:51:11', '2022-04-09 17:51:11');
INSERT INTO `sys_log_login` VALUES ('675671572570240', 'dt在：2022-04-10 19:06:39 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-10 19:06:40', '2022-04-10 19:06:40');
INSERT INTO `sys_log_login` VALUES ('675672610259072', 'dt在：2022-04-10 19:48:53 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-10 19:48:54', '2022-04-10 19:48:54');
INSERT INTO `sys_log_login` VALUES ('675673865109632', 'dt在：2022-04-10 20:39:57 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-10 20:39:57', '2022-04-10 20:39:57');
INSERT INTO `sys_log_login` VALUES ('675674049470592', 'dt在：2022-04-10 20:47:27 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-10 20:47:27', '2022-04-10 20:47:27');
INSERT INTO `sys_log_login` VALUES ('675674109309056', 'dt在：2022-04-10 20:49:53 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-10 20:49:53', '2022-04-10 20:49:53');
INSERT INTO `sys_log_login` VALUES ('675674251071616', 'dt在：2022-04-10 20:55:39 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-10 20:55:40', '2022-04-10 20:55:40');
INSERT INTO `sys_log_login` VALUES ('675674541318272', 'dt在：2022-04-10 21:07:28 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-10 21:07:28', '2022-04-10 21:07:28');
INSERT INTO `sys_log_login` VALUES ('675674916302976', 'dt在：2022-04-10 21:22:43 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-10 21:22:44', '2022-04-10 21:22:44');
INSERT INTO `sys_log_login` VALUES ('675745800892544', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:27:02', '2022-04-12 21:27:02');
INSERT INTO `sys_log_login` VALUES ('675745800994944', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:27:02', '2022-04-12 21:27:02');
INSERT INTO `sys_log_login` VALUES ('675745801732224', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:27:04', '2022-04-12 21:27:04');
INSERT INTO `sys_log_login` VALUES ('675745802551424', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:27:06', '2022-04-12 21:27:06');
INSERT INTO `sys_log_login` VALUES ('675745894764672', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:30:51', '2022-04-12 21:30:51');
INSERT INTO `sys_log_login` VALUES ('675745895293056', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:30:52', '2022-04-12 21:30:52');
INSERT INTO `sys_log_login` VALUES ('675745896116352', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:30:54', '2022-04-12 21:30:54');
INSERT INTO `sys_log_login` VALUES ('675745946960000', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:32:58', '2022-04-12 21:32:58');
INSERT INTO `sys_log_login` VALUES ('675745947414656', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:32:59', '2022-04-12 21:32:59');
INSERT INTO `sys_log_login` VALUES ('675745948233856', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:33:01', '2022-04-12 21:33:01');
INSERT INTO `sys_log_login` VALUES ('675746004844672', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:35:20', '2022-04-12 21:35:20');
INSERT INTO `sys_log_login` VALUES ('675746005278848', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:35:21', '2022-04-12 21:35:21');
INSERT INTO `sys_log_login` VALUES ('675746006098048', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:35:23', '2022-04-12 21:35:23');
INSERT INTO `sys_log_login` VALUES ('675746052976768', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:37:17', '2022-04-12 21:37:17');
INSERT INTO `sys_log_login` VALUES ('675746080424064', 'dt在：2022-04-12 21:27:01 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:38:24', '2022-04-12 21:38:24');
INSERT INTO `sys_log_login` VALUES ('675746095775872', 'dt在：2022-04-12 21:39:00 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, NULL, NULL, '2022-04-12 21:39:02', '2022-04-12 21:39:02');
INSERT INTO `sys_log_login` VALUES ('675747112345728', 'dt在：2022-04-12 22:20:22 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '675747112046720', NULL, '2022-04-12 22:20:24', '2022-04-12 22:20:24');
INSERT INTO `sys_log_login` VALUES ('675747152842880', 'dt在：2022-04-12 22:22:02 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '675747152793728', NULL, '2022-04-12 22:22:02', '2022-04-12 22:22:02');
INSERT INTO `sys_log_login` VALUES ('675810522017920', 'dt在：2022-04-14 17:20:31 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, '675810521690240', NULL, '2022-04-14 17:20:32', '2022-04-14 17:20:32');
INSERT INTO `sys_log_login` VALUES ('675811216130176', 'dt在：2022-04-14 17:48:46 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, '675811216064640', NULL, '2022-04-14 17:48:47', '2022-04-14 17:48:47');
INSERT INTO `sys_log_login` VALUES ('675811375972480', 'dt在：2022-04-14 17:55:16 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, '675811375706240', NULL, '2022-04-14 17:55:17', '2022-04-14 17:55:17');
INSERT INTO `sys_log_login` VALUES ('675836104282240', 'dt在：2022-04-15 10:41:28 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, '675836104208512', NULL, '2022-04-15 10:41:29', '2022-04-15 10:41:29');
INSERT INTO `sys_log_login` VALUES ('675837772353664', 'dt在：2022-04-15 11:49:21 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, '675837772218496', NULL, '2022-04-15 11:49:21', '2022-04-15 11:49:21');
INSERT INTO `sys_log_login` VALUES ('675837863886976', 'dt在：2022-04-15 11:53:04 点击了登录', 'dt', '127.0.0.1', 'Chrome 9', 'Windows 10', 1, 1, '675837863792768', NULL, '2022-04-15 11:53:05', '2022-04-15 11:53:05');
INSERT INTO `sys_log_login` VALUES ('675850029224064', 'dt在：2022-04-15 20:08:05 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '675850029088896', NULL, '2022-04-15 20:08:05', '2022-04-15 20:08:05');
INSERT INTO `sys_log_login` VALUES ('675872720175232', 'dt在：2022-04-16 11:31:22 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '675872719745152', NULL, '2022-04-16 11:31:23', '2022-04-16 11:31:23');
INSERT INTO `sys_log_login` VALUES ('675878062141568', 'dt在：2022-04-16 15:08:44 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '675878061850752', NULL, '2022-04-16 15:08:45', '2022-04-16 15:08:45');
INSERT INTO `sys_log_login` VALUES ('676025888608384', 'dt在：2022-04-20 19:23:49 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '676025888497792', NULL, '2022-04-20 19:23:50', '2022-04-20 19:23:50');
INSERT INTO `sys_log_login` VALUES ('676058603720832', 'dt在：2022-04-21 17:35:00 点击了登录', 'dt', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '676058603626624', NULL, '2022-04-21 17:35:00', '2022-04-21 17:35:00');
INSERT INTO `sys_log_login` VALUES ('676059070435456', 'dt在：2022-04-21 17:53:59 点击了登录', 'dt', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '676059070390400', NULL, '2022-04-21 17:54:00', '2022-04-21 17:54:00');
INSERT INTO `sys_log_login` VALUES ('676059156390016', 'dt在：2022-04-21 17:57:29 点击了登录', 'dt', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '676059156140160', NULL, '2022-04-21 17:57:30', '2022-04-21 17:57:30');
INSERT INTO `sys_log_login` VALUES ('676060896325760', 'dt在：2022-04-21 19:08:17 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '676060896231552', NULL, '2022-04-21 19:08:18', '2022-04-21 19:08:18');
INSERT INTO `sys_log_login` VALUES ('676082039439488', 'dt在：2022-04-22 09:28:36 点击了登录', 'dt', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '676082039357568', NULL, '2022-04-22 09:28:37', '2022-04-22 09:28:37');
INSERT INTO `sys_log_login` VALUES ('676082324856960', 'dt在：2022-04-22 09:40:13 点击了登录', 'dt', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '676082324816000', NULL, '2022-04-22 09:40:13', '2022-04-22 09:40:13');
INSERT INTO `sys_log_login` VALUES ('676082377629824', 'dt在：2022-04-22 09:42:22 点击了登录', 'dt', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '676082377592960', NULL, '2022-04-22 09:42:22', '2022-04-22 09:42:22');
INSERT INTO `sys_log_login` VALUES ('676082454798464', 'dt在：2022-04-22 09:45:30 点击了登录', 'dt', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '676082454761600', NULL, '2022-04-22 09:45:31', '2022-04-22 09:45:31');
INSERT INTO `sys_log_login` VALUES ('676082520518784', 'dt在：2022-04-22 09:48:11 点击了登录', 'dt', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '676082520477824', NULL, '2022-04-22 09:48:11', '2022-04-22 09:48:11');
INSERT INTO `sys_log_login` VALUES ('676085056626816', 'dt在：2022-04-22 11:31:22 点击了登录', 'dt', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '676085056585856', NULL, '2022-04-22 11:31:23', '2022-04-22 11:31:23');
INSERT INTO `sys_log_login` VALUES ('676085127229568', 'dt在：2022-04-22 11:34:15 点击了登录', 'dt', '127.0.0.1', 'Unknown', 'Unknown', 1, 1, '676085127192704', NULL, '2022-04-22 11:34:15', '2022-04-22 11:34:15');
INSERT INTO `sys_log_login` VALUES ('677055160008832', 'dt在：2022-05-19 21:24:59 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677055159926912', NULL, '2022-05-19 21:24:59', '2022-05-19 21:24:59');
INSERT INTO `sys_log_login` VALUES ('677110276432000', 'dt在：2022-05-21 10:47:40 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677110276350080', NULL, '2022-05-21 10:47:41', '2022-05-21 10:47:41');
INSERT INTO `sys_log_login` VALUES ('677110323286144', 'dt在：2022-05-21 10:49:35 点击了登录', 'dt', '127.0.0.1', 'Mobile Safari', 'Mac OS X (iPhone)', 1, 1, '677110323253376', NULL, '2022-05-21 10:49:35', '2022-05-21 10:49:35');
INSERT INTO `sys_log_login` VALUES ('677110363005056', 'dt在：2022-05-21 10:51:12 点击了登录', 'dt', '127.0.0.1', 'Mobile Safari', 'Mac OS X (iPhone)', 1, 1, '677110362964096', NULL, '2022-05-21 10:51:12', '2022-05-21 10:51:12');
INSERT INTO `sys_log_login` VALUES ('677110393761920', 'dt在：2022-05-21 10:52:26 点击了登录', 'dt', '127.0.0.1', 'Mobile Safari', 'Mac OS X (iPhone)', 1, 1, '677110393618560', NULL, '2022-05-21 10:52:27', '2022-05-21 10:52:27');
INSERT INTO `sys_log_login` VALUES ('677110473474176', 'dt在：2022-05-21 10:55:41 点击了登录', 'dt', '127.0.0.1', 'Mobile Safari', 'Mac OS X (iPhone)', 1, 1, '677110473437312', NULL, '2022-05-21 10:55:42', '2022-05-21 10:55:42');
INSERT INTO `sys_log_login` VALUES ('677110599491712', 'dt在：2022-05-21 11:00:49 点击了登录', 'dt', '127.0.0.1', 'Mobile Safari', 'Mac OS X (iPhone)', 1, 1, '677110599446656', NULL, '2022-05-21 11:00:50', '2022-05-21 11:00:50');
INSERT INTO `sys_log_login` VALUES ('677110649458816', 'dt在：2022-05-21 11:02:51 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677110649311360', NULL, '2022-05-21 11:02:52', '2022-05-21 11:02:52');
INSERT INTO `sys_log_login` VALUES ('677112037019776', 'dt在：2022-05-21 11:59:19 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677112036982912', NULL, '2022-05-21 11:59:19', '2022-05-21 11:59:19');
INSERT INTO `sys_log_login` VALUES ('677112105730176', 'dt在：2022-05-21 12:02:06 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677112105697408', NULL, '2022-05-21 12:02:07', '2022-05-21 12:02:07');
INSERT INTO `sys_log_login` VALUES ('677112243806336', 'dt在：2022-05-21 12:07:43 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677112243773568', NULL, '2022-05-21 12:07:44', '2022-05-21 12:07:44');
INSERT INTO `sys_log_login` VALUES ('677121128091776', 'dt在：2022-05-21 18:09:13 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677121127694464', NULL, '2022-05-21 18:09:14', '2022-05-21 18:09:14');
INSERT INTO `sys_log_login` VALUES ('677126126342272', 'dt在：2022-05-21 21:32:36 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677126126010496', NULL, '2022-05-21 21:32:37', '2022-05-21 21:32:37');
INSERT INTO `sys_log_login` VALUES ('677126135132288', 'dt在：2022-05-21 21:32:57 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677126134972544', NULL, '2022-05-21 21:32:58', '2022-05-21 21:32:58');
INSERT INTO `sys_log_login` VALUES ('677129136832640', 'dt在：2022-05-21 23:35:06 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677129136754816', NULL, '2022-05-21 23:35:07', '2022-05-21 23:35:07');
INSERT INTO `sys_log_login` VALUES ('677129144070272', 'dt在：2022-05-21 23:35:23 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677129143906432', NULL, '2022-05-21 23:35:24', '2022-05-21 23:35:24');
INSERT INTO `sys_log_login` VALUES ('677129321160832', 'dt在：2022-05-21 23:42:36 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677129321111680', NULL, '2022-05-21 23:42:37', '2022-05-21 23:42:37');
INSERT INTO `sys_log_login` VALUES ('677129894498432', 'dt在：2022-05-22 00:05:56 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677129894457472', NULL, '2022-05-22 00:05:56', '2022-05-22 00:05:56');
INSERT INTO `sys_log_login` VALUES ('677157473300608', 'dt在：2022-05-22 18:48:07 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677157473181824', NULL, '2022-05-22 18:48:08', '2022-05-22 18:48:08');
INSERT INTO `sys_log_login` VALUES ('677161271140480', 'dt在：2022-05-22 21:22:38 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677161270734976', NULL, '2022-05-22 21:22:40', '2022-05-22 21:22:40');
INSERT INTO `sys_log_login` VALUES ('677162785067136', 'dt在：2022-05-22 22:24:15 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677162784919680', NULL, '2022-05-22 22:24:16', '2022-05-22 22:24:16');
INSERT INTO `sys_log_login` VALUES ('677162923118720', 'dt在：2022-05-22 22:29:52 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677162923085952', NULL, '2022-05-22 22:29:53', '2022-05-22 22:29:53');
INSERT INTO `sys_log_login` VALUES ('677162943230080', 'dt在：2022-05-22 22:30:41 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677162943197312', NULL, '2022-05-22 22:30:42', '2022-05-22 22:30:42');
INSERT INTO `sys_log_login` VALUES ('677163050520704', 'dt在：2022-05-22 22:35:03 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677163050487936', NULL, '2022-05-22 22:35:04', '2022-05-22 22:35:04');
INSERT INTO `sys_log_login` VALUES ('677163541688448', 'dt在：2022-05-22 22:55:02 点击了登录', 'dt', '127.0.0.1', 'Mobile Safari', 'Mac OS X (iPhone)', 1, 1, '677163541528704', NULL, '2022-05-22 22:55:03', '2022-05-22 22:55:03');
INSERT INTO `sys_log_login` VALUES ('677265476874368', 'dt在：2022-05-25 20:02:47 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677265476530304', NULL, '2022-05-25 20:02:48', '2022-05-25 20:02:48');
INSERT INTO `sys_log_login` VALUES ('677267568955520', 'dt在：2022-05-25 21:27:55 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677267568771200', NULL, '2022-05-25 21:27:56', '2022-05-25 21:27:56');
INSERT INTO `sys_log_login` VALUES ('677267594113152', 'dt在：2022-05-25 21:28:57 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677267594076288', NULL, '2022-05-25 21:28:57', '2022-05-25 21:28:57');
INSERT INTO `sys_log_login` VALUES ('677267623129216', 'dt在：2022-05-25 21:30:07 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677267622981760', NULL, '2022-05-25 21:30:08', '2022-05-25 21:30:08');
INSERT INTO `sys_log_login` VALUES ('677267642216576', 'dt在：2022-05-25 21:30:54 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677267642183808', NULL, '2022-05-25 21:30:55', '2022-05-25 21:30:55');
INSERT INTO `sys_log_login` VALUES ('677267709911168', 'dt在：2022-05-25 21:33:39 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677267709759616', NULL, '2022-05-25 21:33:40', '2022-05-25 21:33:40');
INSERT INTO `sys_log_login` VALUES ('677267811901568', 'dt在：2022-05-25 21:37:48 点击了登录', 'dt', '127.0.0.1', 'Mobile Safari', 'Mac OS X (iPhone)', 1, 1, '677267811864704', NULL, '2022-05-25 21:37:49', '2022-05-25 21:37:49');
INSERT INTO `sys_log_login` VALUES ('677267980034176', 'dt在：2022-05-25 21:44:39 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677267979997312', NULL, '2022-05-25 21:44:39', '2022-05-25 21:44:39');
INSERT INTO `sys_log_login` VALUES ('677268072013952', 'dt在：2022-05-25 21:48:23 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268071981184', NULL, '2022-05-25 21:48:24', '2022-05-25 21:48:24');
INSERT INTO `sys_log_login` VALUES ('677268122894464', 'dt在：2022-05-25 21:50:28 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268122857600', NULL, '2022-05-25 21:50:28', '2022-05-25 21:50:28');
INSERT INTO `sys_log_login` VALUES ('677268159037568', 'dt在：2022-05-25 21:51:56 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268159004800', NULL, '2022-05-25 21:51:56', '2022-05-25 21:51:56');
INSERT INTO `sys_log_login` VALUES ('677268192510080', 'dt在：2022-05-25 21:53:17 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268192370816', NULL, '2022-05-25 21:53:18', '2022-05-25 21:53:18');
INSERT INTO `sys_log_login` VALUES ('677268219719808', 'dt在：2022-05-25 21:54:24 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268219572352', NULL, '2022-05-25 21:54:25', '2022-05-25 21:54:25');
INSERT INTO `sys_log_login` VALUES ('677268290240640', 'dt在：2022-05-25 21:57:16 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268290060416', NULL, '2022-05-25 21:57:17', '2022-05-25 21:57:17');
INSERT INTO `sys_log_login` VALUES ('677268311138432', 'dt在：2022-05-25 21:58:07 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268311105664', NULL, '2022-05-25 21:58:08', '2022-05-25 21:58:08');
INSERT INTO `sys_log_login` VALUES ('677268326563968', 'dt在：2022-05-25 21:58:45 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268326531200', NULL, '2022-05-25 21:58:45', '2022-05-25 21:58:45');
INSERT INTO `sys_log_login` VALUES ('677268580954240', 'dt在：2022-05-25 22:09:06 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268580905088', NULL, '2022-05-25 22:09:06', '2022-05-25 22:09:06');
INSERT INTO `sys_log_login` VALUES ('677268606980224', 'dt在：2022-05-25 22:10:09 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268606828672', NULL, '2022-05-25 22:10:10', '2022-05-25 22:10:10');
INSERT INTO `sys_log_login` VALUES ('677268630892672', 'dt在：2022-05-25 22:11:08 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268630855808', NULL, '2022-05-25 22:11:08', '2022-05-25 22:11:08');
INSERT INTO `sys_log_login` VALUES ('677268671832192', 'dt在：2022-05-25 22:12:48 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268671799424', NULL, '2022-05-25 22:12:48', '2022-05-25 22:12:48');
INSERT INTO `sys_log_login` VALUES ('677268747784320', 'dt在：2022-05-25 22:15:53 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268747743360', NULL, '2022-05-25 22:15:54', '2022-05-25 22:15:54');
INSERT INTO `sys_log_login` VALUES ('677268878352512', 'dt在：2022-05-25 22:21:12 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677268878315648', NULL, '2022-05-25 22:21:13', '2022-05-25 22:21:13');
INSERT INTO `sys_log_login` VALUES ('677269030477952', 'test在：2022-05-25 22:27:23 点击了登录', 'test', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677269030289536', NULL, '2022-05-25 22:27:24', '2022-05-25 22:27:24');
INSERT INTO `sys_log_login` VALUES ('677302421745792', 'dt在：2022-05-26 21:06:05 点击了登录', 'dt', '127.0.0.1', 'Chrome 10', 'Windows 10', 1, 1, '677302421655680', NULL, '2022-05-26 21:06:06', '2022-05-26 21:06:06');

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
INSERT INTO `sys_log_operator` VALUES ('675356186480768', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648818814220}', 1, NULL, '2022-04-01 21:13:35', '2022-04-01 21:13:35');
INSERT INTO `sys_log_operator` VALUES ('675356306407552', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1648819107357}', 1, NULL, '2022-04-01 21:18:27', '2022-04-01 21:18:27');
INSERT INTO `sys_log_operator` VALUES ('675562054561920', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1649321421827}', 1, NULL, '2022-04-07 16:50:22', '2022-04-07 16:50:22');
INSERT INTO `sys_log_operator` VALUES ('675562056343680', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1649321426508}', 1, NULL, '2022-04-07 16:50:27', '2022-04-07 16:50:27');
INSERT INTO `sys_log_operator` VALUES ('675672600350848', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1649591308680}', 1, NULL, '2022-04-10 19:48:29', '2022-04-10 19:48:29');
INSERT INTO `sys_log_operator` VALUES ('675672714465408', '编辑字典日志记录', 'UPDATE', 'com.cms.manage.controller.SysDicController.save()', 'POST', 'dt', '/dic/save', '127.0.0.1', '{\"code\":\"101\",\"createTime\":1649591587869,\"name\":\"学年\",\"updateTime\":1649591587869,\"id\":675672714387584,\"category\":\"baseType\",\"enabled\":true}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1649591587949}', 1, NULL, '2022-04-10 19:53:08', '2022-04-10 19:53:08');
INSERT INTO `sys_log_operator` VALUES ('675673683320960', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1649593953311}', 1, NULL, '2022-04-10 20:32:33', '2022-04-10 20:32:33');
INSERT INTO `sys_log_operator` VALUES ('675673831063680', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1649594313989}', 1, NULL, '2022-04-10 20:38:34', '2022-04-10 20:38:34');
INSERT INTO `sys_log_operator` VALUES ('675673838583936', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1649594332367}', 1, NULL, '2022-04-10 20:38:52', '2022-04-10 20:38:52');
INSERT INTO `sys_log_operator` VALUES ('675674022064256', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1649594780328}', 1, NULL, '2022-04-10 20:46:20', '2022-04-10 20:46:20');
INSERT INTO `sys_log_operator` VALUES ('675674104144000', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1649594980730}', 1, NULL, '2022-04-10 20:49:41', '2022-04-10 20:49:41');
INSERT INTO `sys_log_operator` VALUES ('675674234843264', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1649595299812}', 1, NULL, '2022-04-10 20:55:00', '2022-04-10 20:55:00');
INSERT INTO `sys_log_operator` VALUES ('676309578068096', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', '未知用户', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"676309524000896\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651146430082}', 1, NULL, '2022-04-28 19:47:11', '2022-04-28 19:47:11');
INSERT INTO `sys_log_operator` VALUES ('676310575669376', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', '未知用户', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"676309524000896\",\"675355949138048\",\"675356032897152\",\"675356063563904\",\"675356128833664\",\"675356242559104\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651148865705}', 1, NULL, '2022-04-28 20:27:46', '2022-04-28 20:27:46');
INSERT INTO `sys_log_operator` VALUES ('676312044568704', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"password,refresh_token\",\"additionalInformation\":null,\"clientId\":\"web\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"scope\":\"all\",\"clientSecret\":\"$2a$10$bMNULS2JtrK.kEOIZw4MrOXgJ/B3XK3bnjkO58JHe2.t9kIBnScpe\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', 'null', 2, '\r\n### Error updating database.  Cause: java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allowed\r\n### The error may exist in com/cms/manage/mapper/SysOauthClientMapper.java (best guess)\r\n### The error may involve com.cms.manage.mapper.SysOauthClientMapper.updateById-Inline\r\n### The error occurred while setting parameters\r\n### SQL: UPDATE oauth_client_details  SET client_secret=?,  scope=?, authorized_grant_types=?,   access_token_validity=?, refresh_token_validity=?  WHERE client_id=?\r\n### Cause: java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allowed\n; Connection is read-only. Queries leading to data modification are not allowed; nested exception is java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allowed', '2022-04-28 21:27:33', '2022-04-28 21:27:33');
INSERT INTO `sys_log_operator` VALUES ('676312092082304', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"password,refresh_token\",\"additionalInformation\":null,\"clientId\":\"web\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"scope\":\"all\",\"clientSecret\":\"$2a$10$bMNULS2JtrK.kEOIZw4MrOXgJ/B3XK3bnjkO58JHe2.t9kIBnScpe\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651152568010}', 1, NULL, '2022-04-28 21:29:29', '2022-04-28 21:29:29');
INSERT INTO `sys_log_operator` VALUES ('676312164376704', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"password,refresh_token\",\"additionalInformation\":null,\"clientId\":\"cms_web\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"scope\":\"all\",\"clientSecret\":\"$2a$10$tXpeEFxFMARoWa6EbNgP4eoXqFwMhqbF2LKLP5PExAHyuVnTlrH5S\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651152744977}', 1, NULL, '2022-04-28 21:32:25', '2022-04-28 21:32:25');
INSERT INTO `sys_log_operator` VALUES ('676312221032576', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"captcha,refresh_token\",\"additionalInformation\":null,\"clientId\":\"cms-admin-web\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"scope\":\"all\",\"clientSecret\":\"$2a$10$DeYLxfFS2YW5Ai/AdTbkmuZRIAGt3/B6sO6d/cMFmEs6Z9TpJ5pZa\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651152883286}', 1, NULL, '2022-04-28 21:34:43', '2022-04-28 21:34:43');
INSERT INTO `sys_log_operator` VALUES ('676312283259008', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"password,refresh_token\",\"additionalInformation\":null,\"clientId\":\"1\",\"autoapprove\":null,\"refreshTokenValidity\":1,\"scope\":\"1\",\"clientSecret\":\"$2a$10$feIM.YOrr2Ak545Od7E93O9x6xsGO4pbe85ZgNCPuAdbrfGmdrDHq\",\"webServerRedirectUri\":null,\"accessTokenValidity\":1,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651153035200}', 1, NULL, '2022-04-28 21:37:15', '2022-04-28 21:37:15');
INSERT INTO `sys_log_operator` VALUES ('676312516694144', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":null,\"additionalInformation\":null,\"clientId\":\"cms-admin-web\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"authorizedGrantTypesArray\":[\"captcha\",\"refresh_token\"],\"scope\":\"all\",\"clientSecret\":\"dt$pwd123\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651153604413}', 1, NULL, '2022-04-28 21:46:45', '2022-04-28 21:46:45');
INSERT INTO `sys_log_operator` VALUES ('676312522702976', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"captcha,refresh_token,password\",\"additionalInformation\":null,\"clientId\":\"cms-admin-web\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"authorizedGrantTypesArray\":[\"captcha\",\"refresh_token\",\"password\"],\"scope\":\"all\",\"clientSecret\":\"$2a$10$Ta0.HORUIlBZAAt9eFsZy.mrPmfpUSYusbK0gvc3nt8MgQz6XRRFe\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651153619774}', 1, NULL, '2022-04-28 21:47:00', '2022-04-28 21:47:00');
INSERT INTO `sys_log_operator` VALUES ('676312525217920', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"captcha,refresh_token\",\"additionalInformation\":null,\"clientId\":\"cms-admin-web\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"authorizedGrantTypesArray\":[\"captcha\",\"refresh_token\"],\"scope\":\"all\",\"clientSecret\":\"$2a$10$Ta0.HORUIlBZAAt9eFsZy.mrPmfpUSYusbK0gvc3nt8MgQz6XRRFe\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651153625924}', 1, NULL, '2022-04-28 21:47:06', '2022-04-28 21:47:06');
INSERT INTO `sys_log_operator` VALUES ('676312559554688', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":null,\"additionalInformation\":null,\"clientId\":\"cms-app\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"authorizedGrantTypesArray\":[\"sms_code\",\"refresh_token\"],\"scope\":\"all\",\"clientSecret\":\"dt$pwd123\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651153709754}', 1, NULL, '2022-04-28 21:48:30', '2022-04-28 21:48:30');
INSERT INTO `sys_log_operator` VALUES ('676312591650944', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":null,\"additionalInformation\":null,\"clientId\":\"cms-wechat\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"authorizedGrantTypesArray\":[\"wechat\",\"captcha\"],\"scope\":\"all\",\"clientSecret\":\"dt$pwd123\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651153788115}', 1, NULL, '2022-04-28 21:49:48', '2022-04-28 21:49:48');
INSERT INTO `sys_log_operator` VALUES ('676312599965824', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":null,\"additionalInformation\":null,\"clientId\":\"cms-idcard\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"authorizedGrantTypesArray\":[\"id_card\",\"captcha\"],\"scope\":\"all\",\"clientSecret\":\"dt$pwd123\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651153808419}', 1, NULL, '2022-04-28 21:50:09', '2022-04-28 21:50:09');
INSERT INTO `sys_log_operator` VALUES ('676312605413504', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":null,\"additionalInformation\":null,\"clientId\":\"1\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"authorizedGrantTypesArray\":[\"password\"],\"scope\":\"all\",\"clientSecret\":\"1\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651153821702}', 1, NULL, '2022-04-28 21:50:22', '2022-04-28 21:50:22');
INSERT INTO `sys_log_operator` VALUES ('676312650223744', '删除客户端记录', 'DELETE', 'com.cms.manage.controller.SysOauthClientController.delete()', 'DELETE', '未知用户', '/client/delete/1', '127.0.0.1', '1', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651153931115}', 1, NULL, '2022-04-28 21:52:11', '2022-04-28 21:52:11');
INSERT INTO `sys_log_operator` VALUES ('676312848085120', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"password,refresh_token\",\"additionalInformation\":null,\"clientId\":\"cms-web\",\"autoapprove\":null,\"refreshTokenValidity\":120,\"authorizedGrantTypesArray\":[\"password\",\"refresh_token\"],\"scope\":\"all\",\"clientSecret\":\"$2a$10$bMNULS2JtrK.kEOIZw4MrOXgJ/B3XK3bnjkO58JHe2.t9kIBnScpe\",\"webServerRedirectUri\":null,\"accessTokenValidity\":50,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651154414174}', 1, NULL, '2022-04-28 22:00:14', '2022-04-28 22:00:14');
INSERT INTO `sys_log_operator` VALUES ('676381983031424', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"password,refresh_token\",\"additionalInformation\":null,\"clientId\":\"cms-web\",\"autoapprove\":null,\"refreshTokenValidity\":20,\"authorizedGrantTypesArray\":[\"password\",\"refresh_token\"],\"scope\":\"all\",\"clientSecret\":\"$2a$10$bMNULS2JtrK.kEOIZw4MrOXgJ/B3XK3bnjkO58JHe2.t9kIBnScpe\",\"webServerRedirectUri\":null,\"accessTokenValidity\":10,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651323199636}', 1, NULL, '2022-04-30 20:53:21', '2022-04-30 20:53:21');
INSERT INTO `sys_log_operator` VALUES ('676382369931392', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"password,refresh_token\",\"additionalInformation\":null,\"clientId\":\"cms-web\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"authorizedGrantTypesArray\":[\"password\",\"refresh_token\"],\"scope\":\"all\",\"clientSecret\":\"$2a$10$bMNULS2JtrK.kEOIZw4MrOXgJ/B3XK3bnjkO58JHe2.t9kIBnScpe\",\"webServerRedirectUri\":null,\"accessTokenValidity\":10,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651324145207}', 1, NULL, '2022-04-30 21:09:05', '2022-04-30 21:09:05');
INSERT INTO `sys_log_operator` VALUES ('676383302508672', '编辑客户端记录', 'UPDATE', 'com.cms.manage.controller.SysOauthClientController.save()', 'POST', '未知用户', '/client/save', '127.0.0.1', '{\"authorizedGrantTypes\":\"password,refresh_token\",\"additionalInformation\":null,\"clientId\":\"cms-web\",\"autoapprove\":null,\"refreshTokenValidity\":7200,\"authorizedGrantTypesArray\":[\"password\",\"refresh_token\"],\"scope\":\"all\",\"clientSecret\":\"$2a$10$bMNULS2JtrK.kEOIZw4MrOXgJ/B3XK3bnjkO58JHe2.t9kIBnScpe\",\"webServerRedirectUri\":null,\"accessTokenValidity\":3600,\"authorities\":null,\"resourceIds\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651326422029}', 1, NULL, '2022-04-30 21:47:02', '2022-04-30 21:47:02');
INSERT INTO `sys_log_operator` VALUES ('676383508295808', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', '未知用户', '/operator/save', '127.0.0.1', '{\"deptName\":\"后端部门\",\"openid\":\"\",\"credentialsNonExpired\":true,\"deptId\":1000001779686042,\"mobile\":\"1355001446\",\"admin\":false,\"updateTime\":1651326924349,\"avatar\":\"\",\"idno\":\"52020199602299316\",\"enabled\":true,\"password\":\"\",\"roleIds\":[674996182413440],\"createTime\":null,\"scope\":\"web\",\"name\":\"测试\",\"accountNonExpired\":true,\"id\":675070929846400,\"roleNames\":null,\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"test\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1651326924439}', 1, NULL, '2022-04-30 21:55:25', '2022-04-30 21:55:25');
INSERT INTO `sys_log_operator` VALUES ('677110768439424', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":null,\"openid\":\"oprIA5YF-0cn_Nb_zEK8bvJsExcv\",\"credentialsNonExpired\":true,\"deptId\":null,\"mobile\":\"1355000123\",\"admin\":false,\"updateTime\":1653102461365,\"avatar\":\"http://42.192.121.230:9000/default-bucket/20220406/af39ceaf3fe9464c8ead6d72da270ac1.jpg?Content-Disposition=attachment%3B%20filename%3D%2220220406%2Faf39ceaf3fe9464c8ead6d72da270ac1.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220406%2F%2Fs3%2Faws4_request&X-Amz-Date=20220406T120530Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=2aec1bc7308ff4394e5dfed2870a02af42da84dff2748cd703e4597287e0901e\",\"idno\":\"52020199\",\"enabled\":true,\"password\":null,\"roleIds\":[674996182413440],\"createTime\":1647939936000,\"name\":\"DT超管\",\"accountNonExpired\":true,\"id\":674996197965952,\"roleNames\":[\"WEB\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"dt\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102461451}', 1, NULL, '2022-05-21 11:07:42', '2022-05-21 11:07:42');
INSERT INTO `sys_log_operator` VALUES ('677110775357568', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"后端部门\",\"openid\":\"oprIA5YF-0cn_Nb_zEK8bvJsExcv\",\"credentialsNonExpired\":true,\"deptId\":1000001779686042,\"mobile\":\"1355000123\",\"admin\":false,\"updateTime\":1653102478709,\"avatar\":\"http://42.192.121.230:9000/default-bucket/20220406/af39ceaf3fe9464c8ead6d72da270ac1.jpg?Content-Disposition=attachment%3B%20filename%3D%2220220406%2Faf39ceaf3fe9464c8ead6d72da270ac1.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220406%2F%2Fs3%2Faws4_request&X-Amz-Date=20220406T120530Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=2aec1bc7308ff4394e5dfed2870a02af42da84dff2748cd703e4597287e0901e\",\"idno\":\"52020199\",\"enabled\":true,\"password\":null,\"roleIds\":[674996182413440],\"createTime\":1647939936000,\"name\":\"DT超管\",\"accountNonExpired\":true,\"id\":674996197965952,\"roleNames\":[\"WEB\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"dt\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102478795}', 1, NULL, '2022-05-21 11:07:59', '2022-05-21 11:07:59');
INSERT INTO `sys_log_operator` VALUES ('677110788804736', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"后端部门\",\"openid\":null,\"credentialsNonExpired\":true,\"deptId\":1000001779686042,\"mobile\":\"123456\",\"admin\":false,\"updateTime\":1653102511558,\"avatar\":\"\",\"idno\":\"12345\",\"enabled\":true,\"password\":null,\"roleIds\":[256693602365509],\"createTime\":1648119967000,\"name\":\"测试1\",\"accountNonExpired\":true,\"id\":675069938479232,\"roleNames\":[\"ROOT\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"admin1\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102511638}', 1, NULL, '2022-05-21 11:08:32', '2022-05-21 11:08:32');
INSERT INTO `sys_log_operator` VALUES ('677110793597056', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"前端部门\",\"openid\":null,\"credentialsNonExpired\":true,\"deptId\":1000000204663981,\"mobile\":\"123123\",\"admin\":false,\"updateTime\":1653102523243,\"avatar\":\"img/avatar.jpg\",\"idno\":\"123123\",\"enabled\":true,\"password\":null,\"roleIds\":[248204504629317],\"createTime\":1646038023000,\"name\":\"测试2\",\"accountNonExpired\":true,\"id\":248204704247878,\"roleNames\":[\"ADMIN\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"root\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102523327}', 1, NULL, '2022-05-21 11:08:43', '2022-05-21 11:08:43');
INSERT INTO `sys_log_operator` VALUES ('677110799749248', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"后端部门\",\"openid\":null,\"credentialsNonExpired\":true,\"deptId\":1000001779686042,\"mobile\":\"123456789\",\"admin\":false,\"updateTime\":1653102538267,\"avatar\":\"img/avatar.jpg\",\"idno\":\"123456789\",\"enabled\":true,\"password\":null,\"roleIds\":[248204504629317,256693602365509],\"createTime\":1642733254000,\"name\":\"admin\",\"accountNonExpired\":true,\"id\":248204704247877,\"roleNames\":[\"ADMIN\",\"ROOT\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"admin\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102538349}', 1, NULL, '2022-05-21 11:08:58', '2022-05-21 11:08:58');
INSERT INTO `sys_log_operator` VALUES ('677110866124928', '编辑字典日志记录', 'UPDATE', 'com.cms.manage.controller.SysDicController.save()', 'POST', 'dt', '/dic/save', '127.0.0.1', '{\"code\":\"102\",\"createTime\":1653102700315,\"name\":\"季度\",\"updateTime\":1653102700315,\"id\":677110866047104,\"category\":\"dataType\",\"enabled\":true}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102700398}', 1, NULL, '2022-05-21 11:11:41', '2022-05-21 11:11:41');
INSERT INTO `sys_log_operator` VALUES ('677110874136704', '编辑字典日志记录', 'UPDATE', 'com.cms.manage.controller.SysDicController.save()', 'POST', 'dt', '/dic/save', '127.0.0.1', '{\"code\":\"103\",\"createTime\":1653102719892,\"name\":\"会员\",\"updateTime\":1653102719892,\"id\":677110874067072,\"category\":\"dataType\",\"enabled\":true}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102719970}', 1, NULL, '2022-05-21 11:12:00', '2022-05-21 11:12:00');
INSERT INTO `sys_log_operator` VALUES ('677110880649344', '编辑字典日志记录', 'UPDATE', 'com.cms.manage.controller.SysDicController.save()', 'POST', 'dt', '/dic/save', '127.0.0.1', '{\"code\":\"104\",\"createTime\":1653102735787,\"name\":\"课程\",\"updateTime\":1653102735787,\"id\":677110880575616,\"category\":\"messageType\",\"enabled\":true}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102735868}', 1, NULL, '2022-05-21 11:12:16', '2022-05-21 11:12:16');
INSERT INTO `sys_log_operator` VALUES ('677110887719040', '编辑字典日志记录', 'UPDATE', 'com.cms.manage.controller.SysDicController.save()', 'POST', 'dt', '/dic/save', '127.0.0.1', '{\"code\":\"105\",\"createTime\":1653102753048,\"name\":\"奖金\",\"updateTime\":1653102753048,\"id\":677110887645312,\"category\":\"noticeType\",\"enabled\":true}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102753126}', 1, NULL, '2022-05-21 11:12:33', '2022-05-21 11:12:33');
INSERT INTO `sys_log_operator` VALUES ('677110894190720', '编辑字典日志记录', 'UPDATE', 'com.cms.manage.controller.SysDicController.save()', 'POST', 'dt', '/dic/save', '127.0.0.1', '{\"code\":\"106\",\"createTime\":1653102768844,\"name\":\"月卡\",\"updateTime\":1653102768844,\"id\":677110894116992,\"category\":\"baseType\",\"enabled\":true}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102768929}', 1, NULL, '2022-05-21 11:12:49', '2022-05-21 11:12:49');
INSERT INTO `sys_log_operator` VALUES ('677110899097728', '编辑字典日志记录', 'UPDATE', 'com.cms.manage.controller.SysDicController.save()', 'POST', 'dt', '/dic/save', '127.0.0.1', '{\"code\":\"107\",\"createTime\":1653102780839,\"name\":\"年级\",\"updateTime\":1653102780839,\"id\":677110899028096,\"category\":\"baseType\",\"enabled\":true}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102780915}', 1, NULL, '2022-05-21 11:13:01', '2022-05-21 11:13:01');
INSERT INTO `sys_log_operator` VALUES ('677110908506240', '编辑字典日志记录', 'UPDATE', 'com.cms.manage.controller.SysDicController.save()', 'POST', 'dt', '/dic/save', '127.0.0.1', '{\"code\":\"108\",\"createTime\":1653102803792,\"name\":\"消息\",\"updateTime\":1653102803792,\"id\":677110908432512,\"category\":\"messageType\",\"enabled\":false}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102803875}', 1, NULL, '2022-05-21 11:13:24', '2022-05-21 11:13:24');
INSERT INTO `sys_log_operator` VALUES ('677110922510464', '编辑字典日志记录', 'UPDATE', 'com.cms.manage.controller.SysDicController.save()', 'POST', 'dt', '/dic/save', '127.0.0.1', '{\"code\":\"109\",\"createTime\":1653102837982,\"name\":\"对象\",\"updateTime\":1653102837982,\"id\":677110922436736,\"category\":\"messageType\",\"enabled\":true}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102838071}', 1, NULL, '2022-05-21 11:13:58', '2022-05-21 11:13:58');
INSERT INTO `sys_log_operator` VALUES ('677110934462592', '编辑字典日志记录', 'UPDATE', 'com.cms.manage.controller.SysDicController.save()', 'POST', 'dt', '/dic/save', '127.0.0.1', '{\"code\":\"200\",\"createTime\":1653102867164,\"name\":\"学校\",\"updateTime\":1653102867164,\"id\":677110934388864,\"category\":\"noticeType\",\"enabled\":true}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653102867248}', 1, NULL, '2022-05-21 11:14:27', '2022-05-21 11:14:27');
INSERT INTO `sys_log_operator` VALUES ('677111028506752', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"法律部门\",\"openid\":\"\",\"credentialsNonExpired\":true,\"deptId\":1000001625392933,\"mobile\":\"123455\",\"admin\":false,\"updateTime\":1653103096777,\"avatar\":\"\",\"idno\":\"123455\",\"enabled\":true,\"password\":null,\"roleIds\":[248204504629317,674996182413440],\"createTime\":1653103084000,\"name\":\"测试6\",\"accountNonExpired\":true,\"id\":677111023026304,\"roleNames\":[\"ADMIN\",\"WEB\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"test6\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653103096856}', 1, NULL, '2022-05-21 11:18:17', '2022-05-21 11:18:17');
INSERT INTO `sys_log_operator` VALUES ('677111044440192', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"UI设计部门\",\"openid\":\"\",\"credentialsNonExpired\":true,\"deptId\":1000001258096779,\"mobile\":\"321321\",\"admin\":false,\"updateTime\":1653103135674,\"avatar\":\"\",\"idno\":\"321321\",\"enabled\":true,\"password\":null,\"roleIds\":[248204504629317,256693602365509],\"createTime\":1653103125000,\"name\":\"测试8\",\"accountNonExpired\":true,\"id\":677111039910016,\"roleNames\":[\"ADMIN\",\"ROOT\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"test7\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653103135757}', 1, NULL, '2022-05-21 11:18:56', '2022-05-21 11:18:56');
INSERT INTO `sys_log_operator` VALUES ('677111957139584', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"676309524000896\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653105364025}', 1, NULL, '2022-05-21 11:56:04', '2022-05-21 11:56:04');
INSERT INTO `sys_log_operator` VALUES ('677112027623552', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"676309524000896\",\"675355949138048\",\"675356032897152\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653105536094}', 1, NULL, '2022-05-21 11:58:56', '2022-05-21 11:58:56');
INSERT INTO `sys_log_operator` VALUES ('677112101134464', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"676309524000896\",\"675355949138048\",\"675356032897152\",\"677112042901632\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653105715534}', 1, NULL, '2022-05-21 12:01:56', '2022-05-21 12:01:56');
INSERT INTO `sys_log_operator` VALUES ('677128367235200', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"前端部门\",\"openid\":\"oiA2r6UaqbtBW3So900VYhki7Esw\",\"credentialsNonExpired\":true,\"deptId\":1000000204663981,\"mobile\":\"12333\",\"admin\":false,\"updateTime\":1653145427179,\"avatar\":\"\",\"idno\":\"12333\",\"enabled\":true,\"password\":null,\"roleIds\":[256693602365509,674996182413440,248204504629317],\"createTime\":1653103012000,\"name\":\"测试4\",\"accountNonExpired\":true,\"id\":677110993510528,\"roleNames\":[\"ROOT\",\"WEB\",\"ADMIN\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"test4\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653145427262}', 1, NULL, '2022-05-21 23:03:48', '2022-05-21 23:03:48');
INSERT INTO `sys_log_operator` VALUES ('677129158463616', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"前端部门\",\"openid\":\"oiA2r6T1npcDdXsiByNIlzkGPlWE\",\"credentialsNonExpired\":true,\"deptId\":1000000204663981,\"mobile\":\"123123\",\"admin\":false,\"updateTime\":1653147358859,\"avatar\":\"img/avatar.jpg\",\"idno\":\"123123\",\"enabled\":true,\"password\":null,\"roleIds\":[248204504629317],\"createTime\":1646038023000,\"name\":\"测试2\",\"accountNonExpired\":true,\"id\":248204704247878,\"roleNames\":[\"ADMIN\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"root\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653147358938}', 1, NULL, '2022-05-21 23:36:00', '2022-05-21 23:36:00');
INSERT INTO `sys_log_operator` VALUES ('677129315106944', '添加角色数据权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleDataScope()', 'POST', 'dt', '/role/saveRoleDataScope', '127.0.0.1', '{\"roleId\":674996182413440,\"deptIds\":[],\"dataScope\":1}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653147741368}', 1, NULL, '2022-05-21 23:42:22', '2022-05-21 23:42:22');
INSERT INTO `sys_log_operator` VALUES ('677129334259840', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"后端部门\",\"openid\":\"oiA2r6Q4Gw_HqOl_j9izx-hpu9Lw\",\"credentialsNonExpired\":true,\"deptId\":1000001779686042,\"mobile\":\"123456\",\"admin\":false,\"updateTime\":1653147788543,\"avatar\":\"\",\"idno\":\"12345\",\"enabled\":true,\"password\":null,\"roleIds\":[256693602365509],\"createTime\":1648119967000,\"name\":\"测试1\",\"accountNonExpired\":true,\"id\":675069938479232,\"roleNames\":[\"ROOT\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"admin1\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653147788624}', 1, NULL, '2022-05-21 23:43:09', '2022-05-21 23:43:09');
INSERT INTO `sys_log_operator` VALUES ('677129342361728', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"UI设计部门\",\"openid\":\"oiA2r6UaqbtBW3So900VYhki7Esw\",\"credentialsNonExpired\":true,\"deptId\":1000001258096779,\"mobile\":\"12311\",\"admin\":false,\"updateTime\":1653147808326,\"avatar\":\"\",\"idno\":\"12311\",\"enabled\":true,\"password\":null,\"roleIds\":[248204504629317],\"createTime\":1653102962000,\"name\":\"测试3\",\"accountNonExpired\":true,\"id\":677110973296768,\"roleNames\":[\"ADMIN\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"test1\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653147808407}', 1, NULL, '2022-05-21 23:43:29', '2022-05-21 23:43:29');
INSERT INTO `sys_log_operator` VALUES ('677129478602880', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"前端部门\",\"openid\":\"\",\"credentialsNonExpired\":true,\"deptId\":1000000204663981,\"mobile\":\"12333\",\"admin\":false,\"updateTime\":1653148140445,\"avatar\":\"\",\"idno\":\"12333\",\"enabled\":true,\"password\":null,\"roleIds\":[256693602365509,674996182413440,248204504629317],\"createTime\":1653103012000,\"name\":\"测试4\",\"accountNonExpired\":true,\"id\":677110993510528,\"roleNames\":[\"ROOT\",\"WEB\",\"ADMIN\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"test4\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653148140528}', 1, NULL, '2022-05-21 23:49:01', '2022-05-21 23:49:01');
INSERT INTO `sys_log_operator` VALUES ('677129881043072', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"676309524000896\",\"675356032897152\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\",\"675355949138048\"]}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653149122987}', 1, NULL, '2022-05-22 00:05:24', '2022-05-22 00:05:24');
INSERT INTO `sys_log_operator` VALUES ('677157958013056', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":null,\"openid\":\"oprIA5YF-0cn_Nb_zEK8bvJsExcv\",\"credentialsNonExpired\":true,\"deptId\":0,\"mobile\":\"1355000123\",\"admin\":false,\"updateTime\":1653217670003,\"avatar\":\"http://42.192.121.230:9000/default-bucket/20220406/af39ceaf3fe9464c8ead6d72da270ac1.jpg?Content-Disposition=attachment%3B%20filename%3D%2220220406%2Faf39ceaf3fe9464c8ead6d72da270ac1.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220406%2F%2Fs3%2Faws4_request&X-Amz-Date=20220406T120530Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=2aec1bc7308ff4394e5dfed2870a02af42da84dff2748cd703e4597287e0901e\",\"idno\":\"52020199\",\"enabled\":true,\"password\":null,\"roleIds\":[674996182413440],\"createTime\":1647939936000,\"name\":\"yandongfa\",\"accountNonExpired\":true,\"id\":674996197965952,\"roleNames\":[\"WEB\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"dt\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653217670084}', 1, NULL, '2022-05-22 19:07:51', '2022-05-22 19:07:51');
INSERT INTO `sys_log_operator` VALUES ('677163403014272', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":null,\"openid\":\"oprIA5YF-0cn_Nb_zEK8bvJsExcv\",\"credentialsNonExpired\":true,\"deptId\":0,\"mobile\":\"1355000123\",\"admin\":false,\"updateTime\":1653217670000,\"avatar\":\"http://42.192.121.230:9000/default-bucket/20220406/af39ceaf3fe9464c8ead6d72da270ac1.jpg?Content-Disposition=attachment%3B%20filename%3D%2220220406%2Faf39ceaf3fe9464c8ead6d72da270ac1.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220406%2F%2Fs3%2Faws4_request&X-Amz-Date=20220406T120530Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=2aec1bc7308ff4394e5dfed2870a02af42da84dff2748cd703e4597287e0901e\",\"idno\":\"52020199\",\"enabled\":true,\"password\":\"$2a$10$U6s5CKezRAhOcNMsfHhg8OsLxtA4bcZr3DLVVFPjq98C0a2xTLK0q\",\"roleIds\":null,\"createTime\":1647939936000,\"name\":\"yandongfa\",\"accountNonExpired\":true,\"id\":674996197965952,\"roleNames\":null,\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"dt\"}', 'null', 2, NULL, '2022-05-22 22:49:24', '2022-05-22 22:49:24');
INSERT INTO `sys_log_operator` VALUES ('677163522465920', '更新个人信息日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.updateMyInfo()', 'POST', 'dt', '/operator/updateMyInfo', '127.0.0.1', '{\"deptName\":null,\"openid\":\"oprIA5YF-0cn_Nb_zEK8bvJsExcv\",\"credentialsNonExpired\":true,\"deptId\":0,\"mobile\":\"1355000123\",\"admin\":false,\"updateTime\":1653231255375,\"avatar\":\"http://42.192.121.230:9000/default-bucket/20220406/af39ceaf3fe9464c8ead6d72da270ac1.jpg?Content-Disposition=attachment%3B%20filename%3D%2220220406%2Faf39ceaf3fe9464c8ead6d72da270ac1.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220406%2F%2Fs3%2Faws4_request&X-Amz-Date=20220406T120530Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=2aec1bc7308ff4394e5dfed2870a02af42da84dff2748cd703e4597287e0901e\",\"idno\":\"52020199\",\"enabled\":true,\"password\":\"$2a$10$U6s5CKezRAhOcNMsfHhg8OsLxtA4bcZr3DLVVFPjq98C0a2xTLK0q\",\"roleIds\":null,\"createTime\":1647939936000,\"name\":\"yandongfa\",\"accountNonExpired\":true,\"id\":674996197965952,\"roleNames\":null,\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"dt\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653231255473}', 1, NULL, '2022-05-22 22:54:16', '2022-05-22 22:54:16');
INSERT INTO `sys_log_operator` VALUES ('677163533799552', '更新个人信息日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.updateMyInfo()', 'POST', 'dt', '/operator/updateMyInfo', '127.0.0.1', '{\"deptName\":null,\"openid\":\"oprIA5YF-0cn_Nb_zEK8bvJsExcv\",\"credentialsNonExpired\":true,\"deptId\":0,\"mobile\":\"15186257311\",\"admin\":false,\"updateTime\":1653231283593,\"avatar\":\"http://42.192.121.230:9000/default-bucket/20220406/af39ceaf3fe9464c8ead6d72da270ac1.jpg?Content-Disposition=attachment%3B%20filename%3D%2220220406%2Faf39ceaf3fe9464c8ead6d72da270ac1.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220406%2F%2Fs3%2Faws4_request&X-Amz-Date=20220406T120530Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=2aec1bc7308ff4394e5dfed2870a02af42da84dff2748cd703e4597287e0901e\",\"idno\":\"52020199\",\"enabled\":true,\"password\":\"$2a$10$U6s5CKezRAhOcNMsfHhg8OsLxtA4bcZr3DLVVFPjq98C0a2xTLK0q\",\"roleIds\":null,\"createTime\":1647939936000,\"name\":\"yandongfa\",\"accountNonExpired\":true,\"id\":674996197965952,\"roleNames\":null,\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"dt\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653231283685}', 1, NULL, '2022-05-22 22:54:44', '2022-05-22 22:54:44');
INSERT INTO `sys_log_operator` VALUES ('677268057415808', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"676309524000896\",\"675355949138048\",\"675356032897152\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\",\"677268006871168\",\"677268028162176\"]}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653486467514}', 1, NULL, '2022-05-25 21:47:48', '2022-05-25 21:47:48');
INSERT INTO `sys_log_operator` VALUES ('677268575916160', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":674996182413440,\"menuIds\":[\"114\",\"115\",\"116\",\"111\",\"112\",\"113\",\"675181511221376\",\"675181535461504\",\"675181546832000\",\"117\",\"675181714677888\",\"675181723545728\",\"118\",\"675181755994240\",\"675181762871424\",\"119\",\"675181771518080\",\"675181780402304\",\"675181786669184\",\"675181798477952\",\"675181806932096\",\"675181820149888\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675144297996416\",\"675144452886656\",\"675144589611136\",\"675144638443648\",\"676309524000896\",\"675355949138048\",\"675356032897152\",\"675562021273728\",\"675562032300160\",\"675672524464256\",\"675672564641920\",\"677268006871168\",\"677268028162176\",\"677268523430016\"]}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653487733336}', 1, NULL, '2022-05-25 22:08:54', '2022-05-25 22:08:54');
INSERT INTO `sys_log_operator` VALUES ('677268889219200', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"后端部门\",\"openid\":\"\",\"credentialsNonExpired\":true,\"deptId\":1000001779686042,\"mobile\":\"1355001446\",\"admin\":false,\"updateTime\":1653488498723,\"avatar\":\"\",\"idno\":\"52020199602299316\",\"enabled\":true,\"password\":null,\"roleIds\":[674996182413440],\"createTime\":1648122387000,\"name\":\"测试号\",\"accountNonExpired\":true,\"id\":675070929846400,\"roleNames\":[\"WEB\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"test\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653488498909}', 1, NULL, '2022-05-25 22:21:39', '2022-05-25 22:21:39');
INSERT INTO `sys_log_operator` VALUES ('677268897640576', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":248204504629317,\"menuIds\":[\"114\",\"115\",\"116\",\"113\",\"117\",\"675181714677888\",\"675181723545728\",\"111\",\"112\"]}', 'null', 2, 'Error attempting to get column \'id\' from result set.  Cause: java.sql.SQLDataException: Cannot determine value type from string \'\r\n\r\n257838251663429\'\n; Cannot determine value type from string \'\r\n\r\n257838251663429\'; nested exception is java.sql.SQLDataException: Cannot determine value type from string \'\r\n\r\n257838251663429\'', '2022-05-25 22:22:00', '2022-05-25 22:22:00');
INSERT INTO `sys_log_operator` VALUES ('677268917395584', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":248204504629317,\"menuIds\":[\"114\",\"115\",\"116\",\"113\",\"675181511221376\",\"111\",\"112\"]}', 'null', 2, 'Error attempting to get column \'id\' from result set.  Cause: java.sql.SQLDataException: Cannot determine value type from string \'\r\n\r\n257838251663429\'\n; Cannot determine value type from string \'\r\n\r\n257838251663429\'; nested exception is java.sql.SQLDataException: Cannot determine value type from string \'\r\n\r\n257838251663429\'', '2022-05-25 22:22:48', '2022-05-25 22:22:48');
INSERT INTO `sys_log_operator` VALUES ('677268959199360', '编辑系统角色日志记录', 'UPDATE', 'com.cms.manage.controller.SysRoleController.save()', 'POST', 'dt', '/role/save', '127.0.0.1', '{\"children\":null,\"createTime\":1653488669729,\"name\":\"TEST\",\"alias\":\"测试角色\",\"remark\":\"测试\",\"startTime\":null,\"updateTime\":1653488669729,\"deptIds\":null,\"endTime\":null,\"id\":677268959117440,\"dataScope\":null}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653488669810}', 1, NULL, '2022-05-25 22:24:30', '2022-05-25 22:24:30');
INSERT INTO `sys_log_operator` VALUES ('677268970295424', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":677268959117440,\"menuIds\":[\"114\",\"115\",\"116\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675672524464256\",\"675672564641920\"]}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653488696884}', 1, NULL, '2022-05-25 22:24:57', '2022-05-25 22:24:57');
INSERT INTO `sys_log_operator` VALUES ('677268976763008', '添加角色菜单权限日志记录', 'INSERT', 'com.cms.manage.controller.SysRoleController.saveRoleMenu()', 'POST', 'dt', '/role/saveRoleMenu', '127.0.0.1', '{\"roleId\":677268959117440,\"menuIds\":[\"114\",\"115\",\"116\",\"675073235689600\",\"675073256198272\",\"675181831987328\",\"675181998772352\",\"675073263906944\",\"675181843529856\",\"675182006534272\",\"675672524464256\",\"675672564641920\",\"677268006871168\",\"677268028162176\",\"677268523430016\"]}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653488712701}', 1, NULL, '2022-05-25 22:25:13', '2022-05-25 22:25:13');
INSERT INTO `sys_log_operator` VALUES ('677268984385664', '编辑操作员日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.save()', 'POST', 'dt', '/operator/save', '127.0.0.1', '{\"deptName\":\"后端部门\",\"openid\":\"\",\"credentialsNonExpired\":true,\"deptId\":1000001779686042,\"mobile\":\"1355001446\",\"admin\":false,\"updateTime\":1653488731161,\"avatar\":\"\",\"idno\":\"52020199602299316\",\"enabled\":true,\"password\":null,\"roleIds\":[677268959117440],\"createTime\":1648122387000,\"name\":\"测试号\",\"accountNonExpired\":true,\"id\":675070929846400,\"roleNames\":[\"WEB\"],\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"test\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653488731251}', 1, NULL, '2022-05-25 22:25:31', '2022-05-25 22:25:31');
INSERT INTO `sys_log_operator` VALUES ('677302423834752', '更新个人信息日志记录', 'UPDATE', 'com.cms.manage.controller.SysOperatorController.updateMyInfo()', 'POST', 'dt', '/operator/updateMyInfo', '127.0.0.1', '{\"deptName\":null,\"openid\":\"oprIA5YF-0cn_Nb_zEK8bvJsExcv\",\"credentialsNonExpired\":true,\"deptId\":0,\"mobile\":\"15186257311\",\"admin\":false,\"updateTime\":1653570370162,\"avatar\":\"http://42.192.121.230:9000/default-bucket/20220406/af39ceaf3fe9464c8ead6d72da270ac1.jpg?Content-Disposition=attachment%3B%20filename%3D%2220220406%2Faf39ceaf3fe9464c8ead6d72da270ac1.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220406%2F%2Fs3%2Faws4_request&X-Amz-Date=20220406T120530Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=2aec1bc7308ff4394e5dfed2870a02af42da84dff2748cd703e4597287e0901e\",\"idno\":\"52020199\",\"enabled\":true,\"password\":\"$2a$10$U6s5CKezRAhOcNMsfHhg8OsLxtA4bcZr3DLVVFPjq98C0a2xTLK0q\",\"roleIds\":null,\"createTime\":1647939936000,\"name\":\"yandongfa\",\"accountNonExpired\":true,\"id\":674996197965952,\"roleNames\":null,\"strRoleIds\":null,\"accountNonLocked\":true,\"username\":\"dt\"}', '{\"code\":2000,\"data\":null,\"message\":\"操作成功!\",\"success\":true,\"timestamp\":1653570370256}', 1, NULL, '2022-05-26 21:06:11', '2022-05-26 21:06:11');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型（menu：菜单，iframe：嵌入，link：外链）',
  `order_num` int NULL DEFAULT 1 COMMENT '排序序号',
  `sort` int NULL DEFAULT NULL COMMENT '菜单排序',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权标识符',
  `hidden` tinyint(1) NULL DEFAULT 0 COMMENT '隐藏菜单',
  `hidden_breadcrumb` tinyint(1) NULL DEFAULT 0 COMMENT '隐藏面包屑',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('111', '0', '/setting', 'setting', NULL, '系统', 'el-icon-setting', 'menu', 1, 4, NULL, 0, 0, '2022-02-22 11:27:20', '2022-03-09 14:25:12');
INSERT INTO `sys_menu` VALUES ('112', '111', '/setting/user', 'user', 'setting/user', '用户管理', 'sc-icon-user', 'menu', 2, 5, 'user.list', 0, 0, '2022-02-22 11:27:19', '2022-03-09 14:25:03');
INSERT INTO `sys_menu` VALUES ('113', '112', NULL, NULL, NULL, '添加', NULL, 'button', 3, 6, 'user.add', 0, 0, '2022-02-22 11:27:18', '2022-02-22 11:27:18');
INSERT INTO `sys_menu` VALUES ('114', '0', '/home', 'home', NULL, '首页', 'el-icon-data-line', 'menu', 1, 1, NULL, 0, 0, '2022-02-22 11:27:17', '2022-02-22 11:27:17');
INSERT INTO `sys_menu` VALUES ('115', '114', '/dashboard', 'dashboard', 'home', '控制台', 'el-icon-monitor', 'menu', 2, 2, NULL, 0, 0, '2022-02-22 11:27:16', '2022-03-26 21:15:31');
INSERT INTO `sys_menu` VALUES ('116', '114', '/usercenter', 'usercenter', 'userCenter', '个人中心', 'el-icon-edit', 'menu', 3, 3, NULL, 0, 0, '2022-02-22 11:27:15', '2022-05-22 22:24:05');
INSERT INTO `sys_menu` VALUES ('117', '111', '/setting/menu', 'settingMenu', 'setting/menu', '菜单管理', 'sc-icon-menu', 'menu', 1, 7, NULL, 0, 0, '2022-02-22 13:58:56', '2022-03-09 14:24:56');
INSERT INTO `sys_menu` VALUES ('118', '111', '/setting/dept', 'settingDept', 'setting/dept', '部门管理', 'sc-icon-dept', 'menu', 1, 8, NULL, 0, 0, '2022-02-28 16:45:31', '2022-03-08 15:44:32');
INSERT INTO `sys_menu` VALUES ('119', '111', '/setting/role', 'role', 'setting/role', '角色管理', 'sc-icon-role', 'menu', 1, 9, NULL, 0, 0, '2022-03-09 14:26:01', '2022-03-09 14:26:01');
INSERT INTO `sys_menu` VALUES ('675073235689600', '0', '/log', 'log', '', '日志', 'sc-icon-log', 'menu', 1, 10, NULL, 0, 1, '2022-03-24 21:21:02', '2022-05-25 21:30:43');
INSERT INTO `sys_menu` VALUES ('675073256198272', '675073235689600', '/log/login', 'login', 'log/login', '登录日志', 'sc-icon-login-log', 'menu', 1, 11, NULL, 0, 0, '2022-03-24 21:21:19', '2022-03-24 21:26:32');
INSERT INTO `sys_menu` VALUES ('675073263906944', '675073235689600', '/log/operation', 'operation', 'log/operation', '操作日志', 'sc-icon-operation-log', 'menu', 1, 12, NULL, 0, 0, '2022-03-24 21:21:35', '2022-03-24 21:26:56');
INSERT INTO `sys_menu` VALUES ('675144297996416', '0', '/task', 'task', '', '任务', 'sc-icon-task', 'menu', 1, 13, NULL, 0, 0, '2022-03-26 21:32:17', '2022-04-10 20:53:08');
INSERT INTO `sys_menu` VALUES ('675144452886656', '675144297996416', '/task/dispatch', 'dispatch', 'task/dispatch', '任务管理', 'el-icon-clock', 'menu', 1, 14, NULL, 0, 0, '2022-04-10 20:41:52', '2022-04-10 20:53:23');
INSERT INTO `sys_menu` VALUES ('675144589611136', '0', '/config', 'config', '', '配置', 'sc-icon-config', 'menu', 1, 15, NULL, 0, 0, '2022-03-26 21:44:04', '2022-03-26 21:44:05');
INSERT INTO `sys_menu` VALUES ('675144638443648', '675144589611136', '/config/setting', 'setting', 'config/setting', '系统配置', 'el-icon-more-filled', 'menu', 1, 16, NULL, 0, 0, '2022-03-26 21:46:00', '2022-04-28 19:45:33');
INSERT INTO `sys_menu` VALUES ('675181511221376', '112', '', '', '', '编辑', '', 'button', 1, 17, 'user.edit', 0, 0, '2022-03-27 22:46:46', '2022-03-27 22:46:47');
INSERT INTO `sys_menu` VALUES ('675181535461504', '112', '', '', '', '删除', NULL, 'button', 1, 18, 'user.delete', 0, 0, '2022-03-27 22:47:26', '2022-03-27 22:47:26');
INSERT INTO `sys_menu` VALUES ('675181546832000', '112', '', '', '', '批量删除', NULL, 'button', 1, 19, 'user.batch.delete', 0, 0, '2022-03-27 22:47:47', '2022-03-27 22:51:08');
INSERT INTO `sys_menu` VALUES ('675181714677888', '117', '', '', '', '添加', '', 'button', 1, 20, 'menu.add', 0, 0, '2022-03-27 22:54:38', '2022-03-27 22:54:38');
INSERT INTO `sys_menu` VALUES ('675181723545728', '117', '', '', '', '批量删除', NULL, 'button', 1, 21, 'menu.batch.delete', 0, 0, '2022-03-27 22:54:56', '2022-03-27 22:54:56');
INSERT INTO `sys_menu` VALUES ('675181755994240', '118', '', '', '', '添加', '', 'button', 1, 22, 'dept.add', 0, 0, '2022-03-27 22:56:14', '2022-03-27 22:56:14');
INSERT INTO `sys_menu` VALUES ('675181762871424', '118', '', '', '', '批量删除', NULL, 'button', 1, 23, 'dept.batch.delete', 0, 0, '2022-03-27 22:56:34', '2022-03-27 22:56:34');
INSERT INTO `sys_menu` VALUES ('675181771518080', '119', '', '', '', '添加', '', 'button', 1, 24, 'role.add', 0, 0, '2022-03-27 22:56:57', '2022-03-27 22:56:57');
INSERT INTO `sys_menu` VALUES ('675181780402304', '119', '', '', '', '编辑', NULL, 'button', 1, 25, 'role.edit', 0, 0, '2022-03-27 22:57:12', '2022-03-27 22:57:12');
INSERT INTO `sys_menu` VALUES ('675181786669184', '119', '', '', '', '删除', NULL, 'button', 1, 26, 'role.delete', 0, 0, '2022-03-27 22:57:32', '2022-03-27 22:57:32');
INSERT INTO `sys_menu` VALUES ('675181798477952', '119', '', '', '', '权限设置', NULL, 'button', 1, 27, 'role.auth', 0, 0, '2022-03-27 22:58:02', '2022-03-27 22:58:02');
INSERT INTO `sys_menu` VALUES ('675181806932096', '119', '', '', '', '数据权限', NULL, 'button', 1, 28, 'role.data.scope', 0, 0, '2022-03-27 22:58:21', '2022-03-27 22:58:21');
INSERT INTO `sys_menu` VALUES ('675181820149888', '119', '', '', '', '批量删除', NULL, 'button', 1, 29, 'role.batch.delete', 0, 0, '2022-03-27 22:58:58', '2022-03-27 22:58:58');
INSERT INTO `sys_menu` VALUES ('675181831987328', '675073256198272', '', '', '', '批量删除', '', 'button', 1, 30, 'log.login.batch.delete', 0, 0, '2022-03-27 22:59:30', '2022-03-27 22:59:30');
INSERT INTO `sys_menu` VALUES ('675181843529856', '675073263906944', '', '', '', '批量删除', NULL, 'button', 1, 31, 'log.oper.batch.delete', 0, 0, '2022-03-27 22:59:53', '2022-03-27 22:59:53');
INSERT INTO `sys_menu` VALUES ('675181998772352', '675073256198272', '', '', '', '删除', NULL, 'button', 1, 32, 'log.login.delete', 0, 0, '2022-03-27 23:06:08', '2022-03-27 23:06:08');
INSERT INTO `sys_menu` VALUES ('675182006534272', '675073263906944', '', '', '', '删除', NULL, 'button', 1, 33, 'log.oper.delete', 0, 0, '2022-03-27 23:06:26', '2022-03-27 23:09:22');
INSERT INTO `sys_menu` VALUES ('675355949138048', '0', '/message', 'message', '', '消息', 'el-icon-chat-dot-round', 'menu', 1, 34, NULL, 0, 0, '2022-04-01 21:04:18', '2022-05-21 11:56:42');
INSERT INTO `sys_menu` VALUES ('675356032897152', '675355949138048', '/message/wechart', 'wechart', 'message/wechart', '微信消息', 'el-icon-chat-line-square', 'menu', 1, 35, NULL, 0, 0, '2022-04-01 21:09:44', '2022-05-22 00:05:40');
INSERT INTO `sys_menu` VALUES ('675562021273728', '0', '/doc', 'doc', '', '文件', 'el-icon-folder-opened', 'menu', 1, 39, NULL, 0, 0, '2022-04-07 16:49:20', '2022-04-10 20:53:38');
INSERT INTO `sys_menu` VALUES ('675562032300160', '675562021273728', '/doc/file', 'file', 'doc/file', '文件管理', 'el-icon-apple', 'menu', 1, 40, NULL, 0, 0, '2022-04-07 16:50:04', '2022-04-10 20:53:47');
INSERT INTO `sys_menu` VALUES ('675672524464256', '0', '/dic', 'dic', '', '字典', 'el-icon-collection', 'menu', 1, 41, NULL, 0, 0, '2022-04-10 19:47:00', '2022-05-25 21:29:24');
INSERT INTO `sys_menu` VALUES ('675672564641920', '675672524464256', '/dic/manage', 'manage', 'dic/manage', '系统字典', 'el-icon-message-box', 'menu', 1, 42, NULL, 0, 0, '2022-05-25 21:45:33', '2022-05-25 22:13:07');
INSERT INTO `sys_menu` VALUES ('676309524000896', '675144589611136', '/config/client', 'client', 'config/client', '客户端配置', 'el-icon-more-filled', 'menu', 1, 43, NULL, 0, 0, '2022-04-28 19:46:47', '2022-04-28 20:28:44');
INSERT INTO `sys_menu` VALUES ('677268006871168', '0', '/othertwo', 'othertwo', '', '其它', 'el-icon-connection', 'menu', 1, 44, NULL, 0, 0, '2022-05-25 21:46:33', '2022-05-25 21:53:44');
INSERT INTO `sys_menu` VALUES ('677268028162176', '677268006871168', 'https://www.baidu.com', 'link', '', '百度外链', 'el-icon-connection', 'link', 1, 45, NULL, 0, 0, '2022-05-25 21:47:59', '2022-05-25 22:08:19');
INSERT INTO `sys_menu` VALUES ('677268523430016', '677268006871168', 'https://v3.cn.vuejs.org', 'iframe', '', 'Vue官网', 'sc-icon-vue', 'iframe', 1, 46, NULL, 0, 1, '2022-05-25 22:08:34', '2022-05-25 22:09:50');

-- ----------------------------
-- Table structure for sys_operator
-- ----------------------------
DROP TABLE IF EXISTS `sys_operator`;
CREATE TABLE `sys_operator`  (
  `id` bigint NOT NULL COMMENT '用户 ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统登录名',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `idno` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信ID',
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
INSERT INTO `sys_operator` VALUES (248204704247877, 'admin', 'admin', '123456789', '123456789', NULL, '', 'web', '1000001779686042', 0, 1, 1, 1, 1, 'img/avatar.jpg', '2022-01-21 10:47:34', '2022-05-21 11:08:58');
INSERT INTO `sys_operator` VALUES (248204704247878, 'root', '测试2', '123123', '123123', 'oiA2r6T1npcDdXsiByNIlzkGPlWE', '', 'app', '1000000204663981', 0, 1, 1, 1, 1, 'img/avatar.jpg', '2022-02-28 16:47:03', '2022-05-21 23:35:59');
INSERT INTO `sys_operator` VALUES (674996197965952, 'dt', 'yandongfa', '15186257311', '52020199', 'oprIA5YF-0cn_Nb_zEK8bvJsExcv', '$2a$10$U6s5CKezRAhOcNMsfHhg8OsLxtA4bcZr3DLVVFPjq98C0a2xTLK0q', 'web', '0', 0, 1, 1, 1, 1, 'http://42.192.121.230:9000/default-bucket/20220406/af39ceaf3fe9464c8ead6d72da270ac1.jpg?Content-Disposition=attachment%3B%20filename%3D%2220220406%2Faf39ceaf3fe9464c8ead6d72da270ac1.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220406%2F%2Fs3%2Faws4_request&X-Amz-Date=20220406T120530Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=2aec1bc7308ff4394e5dfed2870a02af42da84dff2748cd703e4597287e0901e', '2022-03-22 17:05:36', '2022-05-26 21:06:10');
INSERT INTO `sys_operator` VALUES (675069938479232, 'admin1', '测试1', '123456', '12345', 'oiA2r6Q4Gw_HqOl_j9izx-hpu9Lw', '$2a$10$mm.FLgSonOC6aDvjwE.HuOgJeJQlmnGV.Fm2MWzUNN6ndOY4De736', 'web', '1000001779686042', 0, 1, 1, 1, 1, '', '2022-03-24 19:06:07', '2022-05-21 23:43:09');
INSERT INTO `sys_operator` VALUES (675070929846400, 'test', '测试号', '1355001446', '52020199602299316', '', '$2a$10$U6s5CKezRAhOcNMsfHhg8OsLxtA4bcZr3DLVVFPjq98C0a2xTLK0q', 'web', '1000001779686042', 0, 1, 1, 1, 1, '', '2022-03-24 19:46:27', '2022-05-25 22:25:31');
INSERT INTO `sys_operator` VALUES (677110973296768, 'test1', '测试3', '12311', '12311', 'oiA2r6UaqbtBW3So900VYhki7Esw', '$2a$10$K52iqtV/P3EXuBusihYyRuhaYafcIVpmnCjYc1Em24hJcHZN5v6Fm', NULL, '1000001258096779', 0, 1, 1, 1, 1, '', '2022-05-21 11:16:02', '2022-05-21 23:43:28');
INSERT INTO `sys_operator` VALUES (677110993510528, 'test4', '测试4', '12333', '12333', '', '$2a$10$lGQP5QtJltM0ePRX8ilxiOBtQJHjShsrNH7xTEswizNickiOGqw/y', NULL, '1000000204663981', 0, 1, 1, 1, 1, '', '2022-05-21 11:16:52', '2022-05-21 23:49:00');
INSERT INTO `sys_operator` VALUES (677111008456832, 'test5', '测试5', '12344', '12344', '', '$2a$10$cqj4h1cgJhCWdYKY.gR/NeI6OqFQd7qDXvTLU.X4MKTPG8XzMssSe', NULL, '1000000952846438', 0, 1, 1, 1, 1, '', '2022-05-21 11:17:28', '2022-05-21 11:17:28');
INSERT INTO `sys_operator` VALUES (677111023026304, 'test6', '测试6', '123455', '123455', '', '$2a$10$KaRRrB3PfW6YpE/.HBxahetEl2/oWfVe91kxTmtsKVV9wSPBqcXJy', NULL, '1000001625392933', 0, 1, 1, 1, 1, '', '2022-05-21 11:18:04', '2022-05-21 11:18:17');
INSERT INTO `sys_operator` VALUES (677111039910016, 'test7', '测试8', '321321', '321321', '', '$2a$10$aXB0lFJUoF5faj9pqxXD3ON0P7GEVyp3DbAKBctTca/soqiBSckDW', NULL, '1000001258096779', 0, 1, 1, 1, 1, '', '2022-05-21 11:18:45', '2022-05-25 22:19:09');

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
INSERT INTO `sys_operator_role` VALUES (290677293453381, 248204704247877, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (290677293797445, 248204704247877, 256693602365509);
INSERT INTO `sys_operator_role` VALUES (290679382298693, 677111008456832, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (290679382605893, 677111008456832, 256693602365509);
INSERT INTO `sys_operator_role` VALUES (290679581167685, 677111023026304, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (290679581487173, 677111023026304, 674996182413440);
INSERT INTO `sys_operator_role` VALUES (290679740432453, 677111039910016, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (290679740776517, 677111039910016, 256693602365509);
INSERT INTO `sys_operator_role` VALUES (290860878938181, 248204704247878, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (290862638932037, 675069938479232, 256693602365509);
INSERT INTO `sys_operator_role` VALUES (290862719963205, 677110973296768, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (290864079663173, 677110993510528, 256693602365509);
INSERT INTO `sys_operator_role` VALUES (290864079990853, 677110993510528, 674996182413440);
INSERT INTO `sys_operator_role` VALUES (290864080318533, 677110993510528, 248204504629317);
INSERT INTO `sys_operator_role` VALUES (291148873314373, 674996197965952, 674996182413440);
INSERT INTO `sys_operator_role` VALUES (292259139383365, 675070929846400, 677268959117440);

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_operator_setting
-- ----------------------------
INSERT INTO `sys_operator_setting` VALUES (675142950023296, 248204704247877, 'dashboard', '2022-03-26 20:36:58', '2022-03-26 20:45:03');
INSERT INTO `sys_operator_setting` VALUES (675143177953408, 674996197965952, 'user,settingDept,dispatch,setting,file', '2022-03-26 20:46:14', '2022-04-30 21:43:18');

-- ----------------------------
-- Table structure for sys_order
-- ----------------------------
DROP TABLE IF EXISTS `sys_order`;
CREATE TABLE `sys_order`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `a` int NULL DEFAULT NULL,
  `b` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 119 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `sys_role` VALUES (674996182413440, 'WEB', 1, '超级管理员', '超级管理员', '2022-03-22 17:04:58', '2022-05-21 23:42:21');
INSERT INTO `sys_role` VALUES (677268959117440, 'TEST', NULL, '测试角色', '测试', '2022-05-25 22:24:30', '2022-05-25 22:24:30');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `id` bigint NOT NULL,
  `role_id` bigint NULL DEFAULT NULL,
  `dept_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

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
INSERT INTO `sys_role_menu` VALUES ('677268573704320', '674996182413440', '114');
INSERT INTO `sys_role_menu` VALUES ('677268573737088', '674996182413440', '115');
INSERT INTO `sys_role_menu` VALUES ('677268573765760', '674996182413440', '116');
INSERT INTO `sys_role_menu` VALUES ('677268573798528', '674996182413440', '111');
INSERT INTO `sys_role_menu` VALUES ('677268573831296', '674996182413440', '112');
INSERT INTO `sys_role_menu` VALUES ('677268573864064', '674996182413440', '113');
INSERT INTO `sys_role_menu` VALUES ('677268573917312', '674996182413440', '675181511221376');
INSERT INTO `sys_role_menu` VALUES ('677268573945984', '674996182413440', '675181535461504');
INSERT INTO `sys_role_menu` VALUES ('677268574003328', '674996182413440', '675181546832000');
INSERT INTO `sys_role_menu` VALUES ('677268574036096', '674996182413440', '117');
INSERT INTO `sys_role_menu` VALUES ('677268574068864', '674996182413440', '675181714677888');
INSERT INTO `sys_role_menu` VALUES ('677268574130304', '674996182413440', '675181723545728');
INSERT INTO `sys_role_menu` VALUES ('677268574163072', '674996182413440', '118');
INSERT INTO `sys_role_menu` VALUES ('677268574195840', '674996182413440', '675181755994240');
INSERT INTO `sys_role_menu` VALUES ('677268574228608', '674996182413440', '675181762871424');
INSERT INTO `sys_role_menu` VALUES ('677268574257280', '674996182413440', '119');
INSERT INTO `sys_role_menu` VALUES ('677268574318720', '674996182413440', '675181771518080');
INSERT INTO `sys_role_menu` VALUES ('677268574380160', '674996182413440', '675181780402304');
INSERT INTO `sys_role_menu` VALUES ('677268574412928', '674996182413440', '675181786669184');
INSERT INTO `sys_role_menu` VALUES ('677268574486656', '674996182413440', '675181798477952');
INSERT INTO `sys_role_menu` VALUES ('677268574548096', '674996182413440', '675181806932096');
INSERT INTO `sys_role_menu` VALUES ('677268574580864', '674996182413440', '675181820149888');
INSERT INTO `sys_role_menu` VALUES ('677268574613632', '674996182413440', '675073235689600');
INSERT INTO `sys_role_menu` VALUES ('677268574695552', '674996182413440', '675073256198272');
INSERT INTO `sys_role_menu` VALUES ('677268574756992', '674996182413440', '675181831987328');
INSERT INTO `sys_role_menu` VALUES ('677268574822528', '674996182413440', '675181998772352');
INSERT INTO `sys_role_menu` VALUES ('677268574855296', '674996182413440', '675073263906944');
INSERT INTO `sys_role_menu` VALUES ('677268574908544', '674996182413440', '675181843529856');
INSERT INTO `sys_role_menu` VALUES ('677268574990464', '674996182413440', '675182006534272');
INSERT INTO `sys_role_menu` VALUES ('677268575027328', '674996182413440', '675144297996416');
INSERT INTO `sys_role_menu` VALUES ('677268575060096', '674996182413440', '675144452886656');
INSERT INTO `sys_role_menu` VALUES ('677268575092864', '674996182413440', '675144589611136');
INSERT INTO `sys_role_menu` VALUES ('677268575150208', '674996182413440', '675144638443648');
INSERT INTO `sys_role_menu` VALUES ('677268575182976', '674996182413440', '676309524000896');
INSERT INTO `sys_role_menu` VALUES ('677268575211648', '674996182413440', '675355949138048');
INSERT INTO `sys_role_menu` VALUES ('677268575244416', '674996182413440', '675356032897152');
INSERT INTO `sys_role_menu` VALUES ('677268575277184', '674996182413440', '675562021273728');
INSERT INTO `sys_role_menu` VALUES ('677268575309952', '674996182413440', '675562032300160');
INSERT INTO `sys_role_menu` VALUES ('677268575338624', '674996182413440', '675672524464256');
INSERT INTO `sys_role_menu` VALUES ('677268575404160', '674996182413440', '675672564641920');
INSERT INTO `sys_role_menu` VALUES ('677268575436928', '674996182413440', '677268006871168');
INSERT INTO `sys_role_menu` VALUES ('677268575510656', '674996182413440', '677268028162176');
INSERT INTO `sys_role_menu` VALUES ('677268575539328', '674996182413440', '677268523430016');
INSERT INTO `sys_role_menu` VALUES ('677268975874176', '677268959117440', '114');
INSERT INTO `sys_role_menu` VALUES ('677268975911040', '677268959117440', '115');
INSERT INTO `sys_role_menu` VALUES ('677268975952000', '677268959117440', '116');
INSERT INTO `sys_role_menu` VALUES ('677268975988864', '677268959117440', '675073235689600');
INSERT INTO `sys_role_menu` VALUES ('677268976058496', '677268959117440', '675073256198272');
INSERT INTO `sys_role_menu` VALUES ('677268976107648', '677268959117440', '675181831987328');
INSERT INTO `sys_role_menu` VALUES ('677268976140416', '677268959117440', '675181998772352');
INSERT INTO `sys_role_menu` VALUES ('677268976210048', '677268959117440', '675073263906944');
INSERT INTO `sys_role_menu` VALUES ('677268976246912', '677268959117440', '675181843529856');
INSERT INTO `sys_role_menu` VALUES ('677268976283776', '677268959117440', '675182006534272');
INSERT INTO `sys_role_menu` VALUES ('677268976332928', '677268959117440', '675672524464256');
INSERT INTO `sys_role_menu` VALUES ('677268976398464', '677268959117440', '675672564641920');
INSERT INTO `sys_role_menu` VALUES ('677268976484480', '677268959117440', '677268006871168');
INSERT INTO `sys_role_menu` VALUES ('677268976566400', '677268959117440', '677268028162176');
INSERT INTO `sys_role_menu` VALUES ('677268976648320', '677268959117440', '677268523430016');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `role_id` bigint NOT NULL COMMENT '角色 ID',
  `permission_id` bigint NOT NULL COMMENT '权限 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1560 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = DYNAMIC;

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
-- Table structure for t_file_info
-- ----------------------------
DROP TABLE IF EXISTS `t_file_info`;
CREATE TABLE `t_file_info`  (
  `id` bigint NOT NULL,
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_url` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` bigint NULL DEFAULT NULL,
  `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `object_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_file_info
-- ----------------------------
INSERT INTO `t_file_info` VALUES (1511969115361787906, '728da9773912b31b686ade638d18367adab4e172.jpg', 'http://42.192.121.230:9000/default/20220407/a68378a4765945b5865a879f331306b2.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220407%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220407T072822Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=45c9e48fa897a09df3868fa1358e7f01b849689f273448205bf72e940995e0ad', 'jpg', 35739, 'default', '20220407/a68378a4765945b5865a879f331306b2.jpg', '2022-04-07 15:28:23', '2022-04-07 15:28:23');
INSERT INTO `t_file_info` VALUES (1512037468051468289, 'src=http___img.zcool.cn_community_01ccb65c540cdaa801203d224def33.jpg@2o.jpg&refer=http___img.zcool.jpg', 'http://42.192.121.230:9000/default/20220407/986434ae18004998a181d7eb74ac9806.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220407%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220407T115959Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=7144be59ac6281ef5f0f8e942ca45a7732d036ae05cfaf019ad58f037a656b67', 'jpg', 192806, 'default', '20220407/986434ae18004998a181d7eb74ac9806.jpg', '2022-04-07 19:59:59', '2022-04-07 19:59:59');
INSERT INTO `t_file_info` VALUES (1512042294919426050, 'dtcms.sql', 'http://42.192.121.230:9000/default/20220407/61d63e8a91724f85a82d5c25e48893c9.sql?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220407%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220407T121910Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=46b48c6ddaab010cce0efa7a808c9eafc5e78ca35ae5f4a3fc40ddae3bea86bc', 'sql', 37567, 'default', '20220407/61d63e8a91724f85a82d5c25e48893c9.sql', '2022-04-07 20:19:10', '2022-04-07 20:19:10');
INSERT INTO `t_file_info` VALUES (1512042623937409026, 't_cb_class_photo.sql', 'http://42.192.121.230:9000/default/20220407/4ec22a8fd8044548aa6cbe7483fef49b.sql?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220407%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220407T122028Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=adb8d22b5fbc3ed51517daf40e0bd2b287c4083d2b94d713a6d6e39872898d38', 'sql', 3303, 'default', '20220407/4ec22a8fd8044548aa6cbe7483fef49b.sql', '2022-04-07 20:20:29', '2022-04-07 20:20:29');
INSERT INTO `t_file_info` VALUES (1512042993099075585, '202203262157.mp4', 'http://42.192.121.230:9000/default/20220407/3c0de9810ab84900ae371fd2d82767ee.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220407%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220407T122156Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=fc26f86edb43884d9c1d95a42674506ecf08bc9efe5e5ce55104d514f799f88b', 'mp4', 223976953, 'default', '20220407/3c0de9810ab84900ae371fd2d82767ee.mp4', '2022-04-07 20:21:57', '2022-04-07 20:21:57');
INSERT INTO `t_file_info` VALUES (1512266110915846146, '728da9773912b31b686ade638d18367adab4e172.jpg', 'http://42.192.121.230:9000/default/20220408/16400d8b5fd4462185da29f7d1b00ce7.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T030831Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=7e5e2628a5dc25c40f355f959cf26dc7f4fab5c3b6ee9ea4f5e8fd18532769a4', 'jpg', 35739, 'default', '20220408/16400d8b5fd4462185da29f7d1b00ce7.jpg', '2022-04-08 11:08:32', '2022-04-08 11:08:32');
INSERT INTO `t_file_info` VALUES (1512266152187797506, '728da9773912b31b686ade638d18367adab4e172.jpg', 'http://42.192.121.230:9000/default/20220408/320813eac11c4328a6beb614caa3acac.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T030841Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=b888487a034321b7a2758cd12de68ee04d8938f1663dc6e132e9d520fcf58591', 'jpg', 35739, 'default', '20220408/320813eac11c4328a6beb614caa3acac.jpg', '2022-04-08 11:08:42', '2022-04-08 11:08:42');
INSERT INTO `t_file_info` VALUES (1512266181023637506, '728da9773912b31b686ade638d18367adab4e172.jpg', 'http://42.192.121.230:9000/default/20220408/4c562f2294f743ea97e0695dfbaa1b70.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T030848Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=6ff2baba53fea5cd8061a8af8151df3699e0195df71cd77063b3bf6d908fc2f8', 'jpg', 35739, 'default', '20220408/4c562f2294f743ea97e0695dfbaa1b70.jpg', '2022-04-08 11:08:49', '2022-04-08 11:08:49');
INSERT INTO `t_file_info` VALUES (1512266207917514754, '728da9773912b31b686ade638d18367adab4e172.jpg', 'http://42.192.121.230:9000/default/20220408/d900b89e8e0e43fc95eb6404b856bbc2.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T030855Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=7b8c84657b82f47435da01c2239fd0ffcf0136eab0cbd4723abce67dc2a22e2a', 'jpg', 35739, 'default', '20220408/d900b89e8e0e43fc95eb6404b856bbc2.jpg', '2022-04-08 11:08:55', '2022-04-08 11:08:55');
INSERT INTO `t_file_info` VALUES (1512266250535837698, '728da9773912b31b686ade638d18367adab4e172.jpg', 'http://42.192.121.230:9000/default/20220408/f818c727452f466d853afd39ebd116e0.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T030905Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=292667565b72b986aaddc6f4fa316d11211cfd31947ce575af8dd7686adce3e5', 'jpg', 35739, 'default', '20220408/f818c727452f466d853afd39ebd116e0.jpg', '2022-04-08 11:09:05', '2022-04-08 11:09:05');
INSERT INTO `t_file_info` VALUES (1512303890073251841, '728da9773912b31b686ade638d18367adab4e172.jpg', 'http://42.192.121.230:9000/default/20220408/391853064ac64d7fbcfc8932479b4901.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T053839Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=f66276fc96dfdab41430fbda27dcfea4be0ef34b9d9536ee23e29f0d99d883d5', 'jpg', 35739, 'default', '20220408/391853064ac64d7fbcfc8932479b4901.jpg', '2022-04-08 13:38:39', '2022-04-08 13:38:39');
INSERT INTO `t_file_info` VALUES (1512326692142592002, '728da9773912b31b686ade638d18367adab4e172.jpg', 'http://42.192.121.230:9000/default/20220408/607f8b653aff43cea4fbfbde9e7e4ab6.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T070915Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=316ba7fdcbfb5838073c223605cbf831960a12f7b3eb56abc2a6e29180efc4b3', 'jpg', 35739, 'default', '20220408/607f8b653aff43cea4fbfbde9e7e4ab6.jpg', '2022-04-08 15:09:16', '2022-04-08 15:09:16');
INSERT INTO `t_file_info` VALUES (1512332404977573890, 'src=http___b-ssl.duitang.com_uploads_item_201512_13_20151213112543_sitRU.jpeg&refer=http___b-ssl.duitang.webp', 'http://42.192.121.230:9000/default/20220408/c933922fea964647a0a5d460ff5cecbe.webp?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T073157Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=1fd1f2c73ee94873a914c48c5775cab96bc5473346a2004403991675b5372290', 'webp', 25928, 'default', '20220408/c933922fea964647a0a5d460ff5cecbe.webp', '2022-04-08 15:31:58', '2022-04-08 15:31:58');
INSERT INTO `t_file_info` VALUES (1512333081493643265, '测试.jpg', 'http://42.192.121.230:9000/default/20220408/0d8b13dadf4e42b8bcf7fbdf19f3cdbc.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T073438Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=167b1990565cc4b7e76a73ad8092379cd891aee64f97dbb4e39886785135c137', 'jpg', 65903, 'default', '20220408/0d8b13dadf4e42b8bcf7fbdf19f3cdbc.jpg', '2022-04-08 15:34:39', '2022-04-08 15:34:39');
INSERT INTO `t_file_info` VALUES (1512392415695622146, '是七叔呢 - 客子光阴.flac', 'http://42.192.121.230:9000/default/20220408/f6d0424485e6466cae833949747fbcbd.flac?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T113024Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=b410d02653d9f28e59db9e04b78f8e5f40032daf12f06c09cea50bee750d2278', 'flac', 18844084, 'default', '20220408/f6d0424485e6466cae833949747fbcbd.flac', '2022-04-08 19:30:25', '2022-04-08 19:30:25');
INSERT INTO `t_file_info` VALUES (1512451202569834497, 'src=http___img.zcool.cn_community_01ccb65c540cdaa801203d224def33.jpg@2o.jpg&refer=http___img.zcool.jpg', 'http://42.192.121.230:9000/default/20220408/abf305b16cc64f89bace5bbbdd3cf9a4.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220408%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220408T152400Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=93a81c048b3c92d9cd26ebedae3340743e40a954293a2fe1c8b49d45bd70dbaf', 'jpg', 192806, 'default', '20220408/abf305b16cc64f89bace5bbbdd3cf9a4.jpg', '2022-04-08 23:24:01', '2022-04-08 23:24:01');
INSERT INTO `t_file_info` VALUES (1512695020254765058, 'src=http___img.zcool.cn_community_01ccb65c540cdaa801203d224def33.jpg@2o.jpg&refer=http___img.zcool.jpg', 'http://42.192.121.230:9000/default/20220409/6681dceacbac4671ba7089df3eb8d3f1.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220409%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220409T073251Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=d6a61c266433613b36618c237665102e61aada285611905c7ae6a8b3cb98a54b', 'jpg', 192806, 'default', '20220409/6681dceacbac4671ba7089df3eb8d3f1.jpg', '2022-04-09 15:32:52', '2022-04-09 15:32:52');
INSERT INTO `t_file_info` VALUES (1513111347398189057, 'community-epidemic.sql', 'http://42.192.121.230:9000/default/20220410/505a25ad88b444f5869c07558607ee53.sql?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220410%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220410T110712Z&X-Amz-Expires=7200&X-Amz-SignedHeaders=host&X-Amz-Signature=db932f511f260feaa53d3bf8abf28a3f0477d17f7976b6b77be3a784e942ccbc', 'sql', 23905, 'default', '20220410/505a25ad88b444f5869c07558607ee53.sql', '2022-04-10 19:07:12', '2022-04-10 19:07:12');
INSERT INTO `t_file_info` VALUES (1513111583147433986, '白小白 - 我爱你不问归期.flac', 'http://42.192.121.230:9000/default/20220410/018a9007ca4c46a08eaec4a6fbd85402.flac?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220410%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220410T110808Z&X-Amz-Expires=7200&X-Amz-SignedHeaders=host&X-Amz-Signature=c43ba92ff4f6d86738a29b51dece31392bb4bd54a83ffb3b9add653b7f7e0873', 'flac', 32547590, 'default', '20220410/018a9007ca4c46a08eaec4a6fbd85402.flac', '2022-04-10 19:08:08', '2022-04-10 19:08:08');
INSERT INTO `t_file_info` VALUES (1518434257725333505, '728da9773912b31b686ade638d18367adab4e172.jpg', 'http://42.192.121.230:9000/default/20220425/8a9cf0ac7ac84eebbe74ee143edb258a.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220425%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220425T033832Z&X-Amz-Expires=7200&X-Amz-SignedHeaders=host&X-Amz-Signature=156bcf378c62a3379bc4f711e8ee51399ca51b96dbe2da114670412910e06e5b', 'jpg', 35739, NULL, '20220425/8a9cf0ac7ac84eebbe74ee143edb258a.jpg', '2022-04-25 11:38:33', '2022-04-25 11:38:33');
INSERT INTO `t_file_info` VALUES (1518434452030660609, '测试.jpg', 'http://42.192.121.230:9000/default/20220425/7d6ec5668b6f4a7ebc919bf2d8d051e8.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220425%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220425T033919Z&X-Amz-Expires=7200&X-Amz-SignedHeaders=host&X-Amz-Signature=1bb6332235f0da8cccfe484de416814fbbce8e0df4547bb4bb19f07432830c2a', 'jpg', 65903, NULL, '20220425/7d6ec5668b6f4a7ebc919bf2d8d051e8.jpg', '2022-04-25 11:39:19', '2022-04-25 11:39:19');
INSERT INTO `t_file_info` VALUES (1529811480206733313, '是七叔呢 - 客子光阴.flac', 'http://42.192.121.230:9000/default/20220526/40d6131707db4e97b7fd1c90ad0c3f0a.flac?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220526%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220526T130734Z&X-Amz-Expires=7200&X-Amz-SignedHeaders=host&X-Amz-Signature=08cf2ef53ff47da107e0fb2f79c22a4c9c4d79aa722e8dab7ef1ebe586e54494', 'flac', 18844084, 'default', '20220526/40d6131707db4e97b7fd1c90ad0c3f0a.flac', '2022-05-26 21:07:34', '2022-05-26 21:07:34');

-- ----------------------------
-- Table structure for t_job_information
-- ----------------------------
DROP TABLE IF EXISTS `t_job_information`;
CREATE TABLE `t_job_information`  (
  `id` bigint NOT NULL,
  `task_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `task_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `task_group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT 1,
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_job_information
-- ----------------------------
INSERT INTO `t_job_information` VALUES (675674866266240, '675674866094208', '定时删除系统日志', 'default', 'com.cms.job.task.bean.CronLoginLogJob', '0 0/10 * * * ? ', 1, '', '2022-04-10 21:20:41', '2022-04-10 21:20:41');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for wx_member_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_member_user`;
CREATE TABLE `wx_member_user`  (
  `id` bigint NOT NULL,
  `gender` int NULL DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar_url` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `session_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `country` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `language` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wx_member_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
