/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.73-1-log : Database - tyj_weixin_dingyuehao
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tyj_weixin_dingyuehao` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tyj_weixin_dingyuehao`;

/*Table structure for table `apply_install_infos` */

DROP TABLE IF EXISTS `apply_install_infos`;

CREATE TABLE `apply_install_infos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `apply_install_time` datetime DEFAULT NULL,
  `apply_install_place_id` int(11) DEFAULT NULL,
  `online_apply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `apply_install_infos` */

/*Table structure for table `apply_user_infos` */

DROP TABLE IF EXISTS `apply_user_infos`;

CREATE TABLE `apply_user_infos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wechat_openid` varchar(64) DEFAULT NULL,
  `real_name` varchar(10) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `identity_no` varchar(18) DEFAULT NULL,
  `car_type` varchar(30) DEFAULT NULL,
  `driver_license_type` varchar(2) DEFAULT NULL,
  `car_no` varchar(8) DEFAULT NULL,
  `drive_years` int(11) DEFAULT NULL,
  `home_addr_province` varchar(10) DEFAULT NULL,
  `home_addr_city` varchar(15) DEFAULT NULL,
  `home_addr_district` varchar(20) DEFAULT NULL,
  `home_add_street` varchar(100) DEFAULT NULL,
  `receive_addr_provice` varchar(10) DEFAULT NULL,
  `receive_addr_city` varchar(15) DEFAULT NULL,
  `receive_addr_district` varchar(20) DEFAULT NULL,
  `receive_addr_street` varchar(100) DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `apply_user_infos` */

/*Table structure for table `install_place_infos` */

DROP TABLE IF EXISTS `install_place_infos`;

CREATE TABLE `install_place_infos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `install_add_province` varchar(10) DEFAULT NULL,
  `install_add_city` varchar(15) DEFAULT NULL,
  `install_add_district` varchar(20) DEFAULT NULL,
  `install_add_street` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `install_place_infos` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
