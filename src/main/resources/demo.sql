/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.21-log : Database - demo
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`demo` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `demo`;

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) NOT NULL COMMENT '手机号码',
  `valid_code` varchar(4) NOT NULL DEFAULT '' COMMENT '验证码',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `expired_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`,`phone`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='短信记录表';

/*Data for the table `message` */

insert  into `message`(`id`,`phone`,`valid_code`,`send_time`,`expired_time`) values (1,'18827172824','1111','2020-03-16 14:31:06','2020-03-16 14:36:06'),(2,'15088889999','1111','2020-03-16 21:29:40','2020-03-16 21:34:40'),(3,'15045678965','1111','2020-03-16 22:22:26','2020-03-16 22:27:26'),(4,'18827162662','1111','2020-03-23 23:20:33','2020-03-23 23:25:33'),(5,'18827326331','1111','2020-04-10 13:49:43','2020-04-10 13:54:43');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `phone` varchar(11) NOT NULL COMMENT '手机号码',
  `status` int(11) NOT NULL COMMENT '状态：0->禁用；1->启用',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `icon` varchar(64) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`user_id`,`password`,`phone`,`status`,`create_time`,`icon`) values (1,'ddd',NULL,'3c3c139bd8467c1587a41081ad78045e','4443',1,'2020-03-05 00:56:09',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
