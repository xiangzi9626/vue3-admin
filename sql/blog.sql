-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: blog
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `goods` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` varchar(500) DEFAULT NULL COMMENT '商品分类ID',
  `title` varchar(500) DEFAULT NULL,
  `content` text,
  `price` decimal(10,2) DEFAULT NULL,
  `img` varchar(500) DEFAULT NULL COMMENT '商品主图',
  `stock` int(10) unsigned DEFAULT '100' COMMENT '库存',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '1商品上架 0下架',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods_category`
--

DROP TABLE IF EXISTS `goods_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `goods_category` (
  `category_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods_category`
--

LOCK TABLES `goods_category` WRITE;
/*!40000 ALTER TABLE `goods_category` DISABLE KEYS */;
INSERT INTO `goods_category` VALUES (38,0,'手机');
/*!40000 ALTER TABLE `goods_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色主键ID',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `power_menus` varchar(500) DEFAULT NULL COMMENT '菜单权限',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (22,'超级管理员','31,32,33,37,46,34,38,39,44,45,35,42,43,49,50,47,48'),(24,'普通管理员','31');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_menu`
--

DROP TABLE IF EXISTS `system_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `system_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父ID',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
  `icon` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `path` varchar(100) NOT NULL DEFAULT '' COMMENT '链接',
  `sort` int(11) DEFAULT '0' COMMENT '菜单排序',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态(0:禁用,1:启用)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `component` varchar(500) DEFAULT NULL,
  `url` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `title` (`title`),
  KEY `href` (`path`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_menu`
--

LOCK TABLES `system_menu` WRITE;
/*!40000 ALTER TABLE `system_menu` DISABLE KEYS */;
INSERT INTO `system_menu` VALUES (31,0,'首页','fa fa-home','home',0,1,'2022-11-12 09:41:25','home/Home','/admin/home'),(32,0,'菜单管理','fa fa-th','menu',1,1,'2022-11-12 10:20:18','',''),(33,32,'菜单列表','fa fa-list','list',0,1,'2022-11-12 10:22:34','menu/MenuList','/admin/menu/list'),(34,0,'商品管理','fa fa-shopping-bag','goods',2,1,'2022-11-12 10:28:50','',''),(35,0,'用户管理','fa fa-users','user',3,1,'2022-11-12 10:34:01','',''),(37,32,'添加菜单','fa fa-plus-circle','add',0,0,'2022-11-12 11:07:48','menu/AddMenu','/admin/menu/add'),(38,34,'商品列表','fa fa-table','list',0,1,'2022-11-12 11:50:23','goods/GoodsList','/admin/goods/list'),(39,34,'商品分类','fa fa-bookmark','category/list',0,1,'2022-11-12 11:57:47','goods/GoodsCategory','/admin/goods/category/list'),(42,35,'管理员列表','fa fa-user','admin/list',0,1,'2022-11-12 12:24:08','user/AdminList','/admin/user/admin/list'),(43,35,'用户列表','fa fa-user','user/list',0,1,'2022-11-12 12:27:31','user/UserList','/admin/user/user/list'),(44,34,'添加商品','fa fa-plus','add',0,0,'2022-11-15 09:43:30','goods/AddGoods','/admin/goods/add'),(45,34,'编辑商品','fa fa-edit','edit',0,0,'2022-11-26 16:19:29','goods/EditGoods','/admin/goods/edit'),(46,32,'编辑菜单','fa fa-edit','edit',0,0,'2022-11-27 09:57:36','menu/EditMenu','/admin/menu/edit'),(47,0,'文件管理','fa fa-folder','file',5,1,'2022-12-01 15:57:03','',''),(48,47,'图片','fa fa-picture-o','img/list',0,1,'2022-12-01 16:04:22','file/ImgList','/admin/file/img/list'),(49,0,'权限角色','fa fa-user-secret','role',4,1,'2022-12-01 16:37:54','',''),(50,49,'角色列表','fa fa-user-circle','list',0,1,'2022-12-01 16:45:50','role/RoleList','/admin/role/list');
/*!40000 ALTER TABLE `system_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `r_id` varchar(500) DEFAULT NULL COMMENT '角色ID',
  `money` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '余额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `level` smallint(6) DEFAULT '3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','123456','12345678900','22',0.00,'2021-02-16 18:52:30',1),(5,'user02','123456','12345212130','0',0.00,'2021-05-26 14:50:49',3),(6,'user03','123456','12102124514',NULL,0.00,'2021-05-26 14:51:48',3),(13,'admin01','123456','12345678907','24',0.00,'2021-08-04 15:10:18',2),(14,'admin02','123456','12345678902','24',0.00,'2022-12-06 14:02:58',1),(15,'admin03','123456','12345678903','24',0.00,'2022-12-06 14:04:07',1),(16,'admin04','123456','12345678905','24',0.00,'2022-12-06 14:07:59',1),(17,'admin05','123456','12345678927','24',0.00,'2022-12-06 14:11:47',1),(18,'admin06','123456','12012451212','24',0.00,'2023-04-05 10:45:13',1),(19,'admin07','123456','12032562356','24',0.00,'2023-04-05 10:52:26',1),(20,'admin08','123456','12356457856','24',0.00,'2023-04-05 13:44:09',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-05 18:21:14
