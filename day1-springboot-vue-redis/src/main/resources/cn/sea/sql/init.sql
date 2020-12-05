/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.5.15 : Database - springboot_vue_case1
*********************************************************************
*/

CREATE DATABASE `springboot_vue_case1` ;

USE `springboot_vue_case1`;


DROP TABLE IF EXISTS `t_emp`;

CREATE TABLE `t_emp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `imgPath` varchar(100) DEFAULT NULL COMMENT '头像地址',
  `salary` double(10,2) DEFAULT NULL COMMENT '工资',
  `age` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `realname` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `status` varchar(4) DEFAULT NULL COMMENT '用户状态，是否激活',
  `registerTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

---------------------------------------------------------------------------

各种语句

INSERT INTO `t_user`(`id`,`username`,`realname`,`password`,`sex`,`status`,`registerTime`)
VALUES(#{id}, #{username}, #{realname}, #{password}, #{sex}, #{status}, #{registerTime});

SELECT `id`,`username`,`realname`,`password`,`sex`,`status`,`registerTime` FROM t_user WHERE `username`=#{username};

TRUNCATE TABLE t_user;

SELECT `id`,`name`,`imgPath`,`salary`,`age`
FROM `t_emp`;

SELECT `id`,`name`,`imgPath`,`salary`,`age`
FROM `t_emp` WHERE `id` = #{id};

INSERT INTO `t_emp`(`id`,`name`,`imgPath`,`salary`,`age`)
VALUES(#{id},#{name},#{imgPath},#{salary},#{age});

INSERT INTO `t_emp`(`id`,`name`,`imgPath`,`salary`,`age`)
VALUES(NULL,'魏无羡',NULL,8000,18);
INSERT INTO `t_emp`(`id`,`name`,`imgPath`,`salary`,`age`)
VALUES(NULL,'张无忌',NULL,7800,21);

TRUNCATE TABLE `t_emp`;

DELETE FROM `t_emp` WHERE `id` = #{id}

UPDATE `t_emp`
SET `name` = #{name},
`imgPath` = #{imgPath},
`salary` = #{salary},
`age` = #{age}
WHERE `id` = #{id};