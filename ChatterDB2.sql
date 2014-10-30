# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.20)
# Database: ChatterDB
# Generation Time: 2014-10-30 22:50:45 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Errors
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Errors`;

CREATE TABLE `Errors` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `error_code` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `exception` varchar(256) DEFAULT NULL,
  `server_name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Errors` WRITE;
/*!40000 ALTER TABLE `Errors` DISABLE KEYS */;

INSERT INTO `Errors` (`id`, `error_code`, `date`, `exception`, `server_name`)
VALUES
	(1,400,'2014-10-29 21:44:09','UserException',NULL),
	(2,400,'2014-10-29 21:44:25','TweetException',NULL),
	(3,400,'2014-10-30 15:19:57','ResolutionException',NULL),
	(4,400,'2014-10-30 15:26:04','TweetException',NULL),
	(5,400,'2014-10-30 16:37:27','TweetException',NULL),
	(6,400,'2014-10-30 16:47:18','TweetException','Andrews-MacBook-Pro.local');

/*!40000 ALTER TABLE `Errors` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Friends
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Friends`;

CREATE TABLE `Friends` (
  `user_id` bigint(20) unsigned NOT NULL,
  `friends_id` bigint(20) unsigned NOT NULL,
  `accepted` varchar(1) NOT NULL DEFAULT 'N',
  `friendship_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`friendship_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Friends` WRITE;
/*!40000 ALTER TABLE `Friends` DISABLE KEYS */;

INSERT INTO `Friends` (`user_id`, `friends_id`, `accepted`, `friendship_id`)
VALUES
	(15,16,'Y',16),
	(15,17,'N',17),
	(15,19,'N',19),
	(18,16,'Y',20),
	(18,17,'N',21),
	(18,19,'N',22),
	(16,17,'N',23),
	(16,19,'N',24),
	(20,18,'N',25),
	(20,15,'Y',27),
	(15,18,'N',28),
	(15,21,'N',29),
	(15,16,'N',33),
	(15,16,'N',34);

/*!40000 ALTER TABLE `Friends` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Queue
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Queue`;

CREATE TABLE `Queue` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `time` bigint(255) NOT NULL,
  `task` varchar(256) NOT NULL DEFAULT '',
  `date` datetime DEFAULT NULL,
  `server_name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Queue` WRITE;
/*!40000 ALTER TABLE `Queue` DISABLE KEYS */;

INSERT INTO `Queue` (`id`, `time`, `task`, `date`, `server_name`)
VALUES
	(1,206,'UsernameLogin','2014-10-30 16:46:32','Andrews-MacBook-Pro.local'),
	(2,185,'GetTweetList','2014-10-30 16:46:32','Andrews-MacBook-Pro.local'),
	(3,11,'GetIncomingFriends','2014-10-30 16:46:36','Andrews-MacBook-Pro.local'),
	(4,16,'GetFollowers','2014-10-30 16:46:36','Andrews-MacBook-Pro.local'),
	(5,11,'GetOutgoingFriends','2014-10-30 16:46:36','Andrews-MacBook-Pro.local'),
	(6,10,'GetFollowing','2014-10-30 16:46:36','Andrews-MacBook-Pro.local'),
	(7,9,'GetIncomingFriends','2014-10-30 16:46:37','Andrews-MacBook-Pro.local'),
	(8,11,'GetFollowing','2014-10-30 16:46:37','Andrews-MacBook-Pro.local'),
	(9,10,'GetOutgoingFriends','2014-10-30 16:46:37','Andrews-MacBook-Pro.local'),
	(10,11,'GetFollowers','2014-10-30 16:46:37','Andrews-MacBook-Pro.local'),
	(11,21,'GetFollowing','2014-10-30 16:46:39','Andrews-MacBook-Pro.local'),
	(12,9,'GetIncomingFriends','2014-10-30 16:46:39','Andrews-MacBook-Pro.local'),
	(13,9,'GetOutgoingFriends','2014-10-30 16:46:39','Andrews-MacBook-Pro.local'),
	(14,9,'GetFollowers','2014-10-30 16:46:39','Andrews-MacBook-Pro.local'),
	(15,40,'GetTweetList','2014-10-30 16:46:40','Andrews-MacBook-Pro.local'),
	(16,24,'GetTweetList','2014-10-30 16:46:40','Andrews-MacBook-Pro.local'),
	(17,32,'GetTweetList','2014-10-30 16:46:41','Andrews-MacBook-Pro.local'),
	(18,6,'PostTweet','2014-10-30 16:46:44','Andrews-MacBook-Pro.local'),
	(19,30,'GetTweetList','2014-10-30 16:46:45','Andrews-MacBook-Pro.local');

/*!40000 ALTER TABLE `Queue` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Tweet
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Tweet`;

CREATE TABLE `Tweet` (
  `tweet_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `message` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`tweet_id`),
  KEY `tweet_id` (`tweet_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Tweet` WRITE;
/*!40000 ALTER TABLE `Tweet` DISABLE KEYS */;

INSERT INTO `Tweet` (`tweet_id`, `user_id`, `message`)
VALUES
	(49,15,'af'),
	(61,15,'tweet'),
	(62,15,'tweet'),
	(63,15,'tweet'),
	(65,15,'hey'),
	(66,15,'TEST'),
	(67,15,'loooonnnngggg tweet'),
	(68,15,'hill?'),
	(69,15,'hank is cool'),
	(70,20,'hank'),
	(71,16,'tweet from hillary'),
	(72,15,'andrew?'),
	(73,15,'ng too long too long too '),
	(74,15,'ag'),
	(76,16,'asgd'),
	(77,16,'hill?'),
	(78,16,'tweet tweet'),
	(79,15,'tweet tweet'),
	(80,15,'a'),
	(81,21,'first tweet!! Woo I am a with user id of 21'),
	(82,15,'hey'),
	(83,15,'queue test!'),
	(84,15,'tester test test'),
	(85,15,'tweet'),
	(86,15,'queue test!'),
	(87,15,'Andrew'),
	(88,15,'andrew'),
	(89,15,'This is a local test'),
	(90,15,'time test'),
	(91,15,'time test 2'),
	(92,15,'andrew asdf'),
	(93,15,'ag ag ag 1234'),
	(94,15,'andrewasdf'),
	(95,15,'andrewas'),
	(96,15,'helloa'),
	(97,15,'wweeeerrr'),
	(98,15,'asdf'),
	(99,15,'asd'),
	(100,15,'alsdifhasg'),
	(101,15,'asgafgte'),
	(102,15,'2345qwert'),
	(103,15,'hello'),
	(104,15,'weeeee3245'),
	(105,15,'ansdfre'),
	(106,15,'adfgergq'),
	(107,15,'asdfg3'),
	(108,15,'gwergq3'),
	(109,15,'asfgd35'),
	(110,15,'agr235t3'),
	(111,15,'gqert'),
	(112,15,'gtr'),
	(113,15,'q53q'),
	(114,15,'geq'),
	(115,15,'date test'),
	(117,15,'tweet tweet using the date & time wooo'),
	(118,15,'tweet ya'),
	(119,15,'tweet ya'),
	(120,15,'tweeeter'),
	(121,15,'qwer'),
	(122,15,''),
	(123,15,'hey'),
	(124,15,'ip'),
	(125,15,'ip 2'),
	(126,15,'tweet');

/*!40000 ALTER TABLE `Tweet` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table User
# ------------------------------------------------------------

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_user_id` (`user_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;

INSERT INTO `User` (`name`, `email`, `user_id`)
VALUES
	('andrew','andrewgable@gmail.com',15),
	('hillary','hill@gmail.com',16),
	('birdy','birdy@gmail.com',17),
	('emma','emma@gmail.com',18),
	('evan','evan@gmail.com',19),
	('hank','hank@gmail.com',20),
	('a','a@a.co',21),
	('and','and@a.co',22),
	('ha','ha@gmail.com',23),
	('test','test@a.com',24);

/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
