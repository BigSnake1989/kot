# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.12)
# Database: kot
# Generation Time: 2016-07-31 09:54:45 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table comment
# ------------------------------------------------------------

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `topic_id` int(11) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table topic
# ------------------------------------------------------------

DROP TABLE IF EXISTS `topic`;

CREATE TABLE `topic` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `topic_name` varchar(100) DEFAULT NULL COMMENT 'topic name',
  `category` varchar(100) DEFAULT NULL,
  `content` text COMMENT 'content',
  `user_id` int(11) DEFAULT NULL COMMENT 'creator',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(11) NOT NULL DEFAULT '',
  `name` varchar(33) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `sign` varchar(100) DEFAULT NULL,
  `register_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
