# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.22)
# Database: walletserver
# Generation Time: 2018-12-02 15:14:57 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table account_book
# ------------------------------------------------------------

use walletserver;
DROP TABLE IF EXISTS `account_book`;

CREATE TABLE `account_book` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL COMMENT '账本名称',
  `msg` varchar(200) DEFAULT NULL COMMENT '账本说明',
  `creatime` int(20) DEFAULT NULL COMMENT '时间戳',
  `owner_id` int(20) DEFAULT NULL COMMENT '账本管理人',
  `disable` int(1) DEFAULT '0' COMMENT '是否废弃',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `account_book` WRITE;
/*!40000 ALTER TABLE `account_book` DISABLE KEYS */;

INSERT INTO `account_book` (`id`, `name`, `msg`, `creatime`, `owner_id`, `disable`)
VALUES
	(1,'个人记账本',NULL,0,1,0),
	(2,'非常重要的记账本','这是一个非常重要的记账本',1543743723,1,-1),
	(3,'个人记账本',NULL,0,2,0);

/*!40000 ALTER TABLE `account_book` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table account_book_user_mapping
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account_book_user_mapping`;

CREATE TABLE `account_book_user_mapping` (
  `account_book_id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `createtime` int(20) DEFAULT NULL,
  `disable` int(1) DEFAULT '0' COMMENT '是否废弃',
  PRIMARY KEY (`account_book_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `account_book_user_mapping` WRITE;
/*!40000 ALTER TABLE `account_book_user_mapping` DISABLE KEYS */;

INSERT INTO `account_book_user_mapping` (`account_book_id`, `user_id`, `createtime`, `disable`)
VALUES
	(1,1,1543743548,0),
	(2,1,1543743723,0),
	(2,2,1543744339,-1),
	(3,2,1543743750,0);

/*!40000 ALTER TABLE `account_book_user_mapping` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table account_book_user_mapping_aduit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account_book_user_mapping_aduit`;

CREATE TABLE `account_book_user_mapping_aduit` (
  `account_book_id` int(20) NOT NULL,
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL,
  `createtime` int(20) DEFAULT NULL,
  `disable` int(1) DEFAULT '0' COMMENT '是否废弃',
  `type` int(1) DEFAULT '0' COMMENT '审批类型：0待办，1通过，-1拒绝',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `account_book_user_mapping_aduit` WRITE;
/*!40000 ALTER TABLE `account_book_user_mapping_aduit` DISABLE KEYS */;

INSERT INTO `account_book_user_mapping_aduit` (`account_book_id`, `id`, `user_id`, `createtime`, `disable`, `type`)
VALUES
	(2,1,2,1543743787,-1,1);

/*!40000 ALTER TABLE `account_book_user_mapping_aduit` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table account_money
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account_money`;

CREATE TABLE `account_money` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL COMMENT '记账人id',
  `type` int(1) NOT NULL COMMENT '记账类型：1:支出 2:收入',
  `catecory_id` int(3) DEFAULT NULL COMMENT '记账类别',
  `book_id` int(20) DEFAULT NULL COMMENT '属于哪个记账本',
  `money` double(20,0) DEFAULT '0' COMMENT '记账金额',
  `createtime` int(20) DEFAULT NULL,
  `updatetime` int(20) DEFAULT NULL COMMENT '修改时间',
  `comment` varchar(200) DEFAULT NULL COMMENT '记账说明',
  `year` int(5) DEFAULT NULL,
  `month` int(2) DEFAULT NULL,
  `day` int(2) DEFAULT NULL,
  `disable` int(1) DEFAULT '0' COMMENT '是否删除，1代表删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `account_money` WRITE;
/*!40000 ALTER TABLE `account_money` DISABLE KEYS */;

INSERT INTO `account_money` (`id`, `user_id`, `type`, `catecory_id`, `book_id`, `money`, `createtime`, `updatetime`, `comment`, `year`, `month`, `day`, `disable`)
VALUES
	(1,1,0,1001,1,30,1543763252,1543763457,'牛肉饭 ，鸡肉饭',2018,12,2,0);

/*!40000 ALTER TABLE `account_money` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table award
# ------------------------------------------------------------

DROP TABLE IF EXISTS `award`;

CREATE TABLE `award` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `wxid` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `passwd` varchar(50) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `recommend_user_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `name`, `wxid`, `phone`, `area`, `passwd`, `email`, `recommend_user_id`)
VALUES
	(1,NULL,NULL,'13800138001','1011','123abc',NULL,-1),
	(2,NULL,NULL,'13800138008','1011','123abc',NULL,1);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_award_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_award_log`;

CREATE TABLE `user_award_log` (
  `user_id` int(20) NOT NULL,
  `award_id` int(20) NOT NULL,
  `createtime` int(20) NOT NULL,
  `is_avail` int(1) DEFAULT '1' COMMENT '奖励是否正常',
  PRIMARY KEY (`user_id`,`award_id`,`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user_consume_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_consume_log`;

CREATE TABLE `user_consume_log` (
  `user_id` int(20) NOT NULL,
  `goods_id` int(20) NOT NULL,
  `money` double(20,0) DEFAULT NULL,
  `is_cancel` int(1) DEFAULT NULL,
  `createtime` int(20) NOT NULL,
  PRIMARY KEY (`user_id`,`goods_id`,`createtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user_money
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_money`;

CREATE TABLE `user_money` (
  `user_id` int(20) NOT NULL,
  `money` double(20,0) DEFAULT NULL,
  `updatetime` int(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
