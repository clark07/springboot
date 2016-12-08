-- --------------------------------------------------------
-- 主机:                           10.0.3.19
-- 服务器版本:                        5.6.21-log - Source distribution
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.3.0.5016
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 test_cs.book 结构
CREATE TABLE IF NOT EXISTS `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `author` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `base_url` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `index_url` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `spider_open` int(11) NOT NULL DEFAULT '0' COMMENT '0:不开启 1:开启爬取',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0:未完结 100:已完结',
  `lastest_chapter_id` int(11) NOT NULL DEFAULT '0',
  `lastest_chapter_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lasted_spider_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO `book` (`name`, `author`, `description`, `base_url`, `index_url`, `spider_open`, `status`, `lastest_chapter_id`, `lastest_chapter_name`, `lasted_spider_time`, `create_time`, `update_time`) VALUES ('玄界之门', NULL, NULL, 'http://www.biquge.com.tw', 'http://www.biquge.com.tw/16_16273/', 1, 0, 0, '', null, now(), now());


-- 数据导出被取消选择。


-- 导出  表 test_cs.category 结构
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0' COMMENT '分类名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父id',
  `category_id_path` varchar(50) DEFAULT '0' COMMENT '分类全路径',
  `sort` int(11) DEFAULT '0',
  `depth` smallint(6) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `image_url` varchar(500) DEFAULT NULL,
  `image_url2` varchar(500) DEFAULT NULL,
  `discount_percent` int(11) DEFAULT NULL COMMENT '扣点百分比',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='类目表';

-- 数据导出被取消选择。


-- 导出  表 test_cs.chapter 结构
CREATE TABLE IF NOT EXISTS `chapter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `source_url` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `book_id` int(11) NOT NULL DEFAULT '0',
  `book_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` text COLLATE utf8mb4_unicode_ci,
  `retry_status` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_book_id` (`book_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 test_cs.city 结构
CREATE TABLE IF NOT EXISTS `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'name注释',
  `state` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'state注释',
  `country` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `map` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 test_cs.people 结构
CREATE TABLE IF NOT EXISTS `people` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `age` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
