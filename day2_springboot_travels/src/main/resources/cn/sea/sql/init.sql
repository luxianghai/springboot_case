/*
SQLyog Community
MySQL - 5.5.15 : Database - springboot_vue_travels
*********************************************************************
*/

CREATE DATABASE /*!32312 IF NOT EXISTS*/`springboot_vue_travels` /*!40100 DEFAULT CHARACTER SET gbk */;

/*Table structure for table `t_place` */

CREATE TABLE `t_place` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `picpath` mediumtext COMMENT '景点图片',
  `hottime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '旺季时间',
  `hotticket` double(7,2) DEFAULT NULL COMMENT '旺季门票价钱',
  `dimticket` double(7,2) DEFAULT NULL COMMENT '淡季门票价钱',
  `placedesc` varchar(300) DEFAULT NULL COMMENT '景点描述',
  `provinceid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=gbk;

/*Table structure for table `t_province` */

CREATE TABLE `t_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `tags` varchar(60) DEFAULT NULL COMMENT '标签',
  `placecounts` int(4) DEFAULT NULL COMMENT '景点个数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=gbk;

/*Table structure for table `t_user` */

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
