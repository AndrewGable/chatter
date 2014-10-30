# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.20)
# Database: ChatterDB
# Generation Time: 2014-10-30 21:37:21 +0000
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Errors` WRITE;
/*!40000 ALTER TABLE `Errors` DISABLE KEYS */;

INSERT INTO `Errors` (`id`, `error_code`, `date`, `exception`)
VALUES
	(1,400,'2014-10-29 21:44:09','UserException'),
	(2,400,'2014-10-29 21:44:25','TweetException'),
	(3,400,'2014-10-30 15:19:57','ResolutionException'),
	(4,400,'2014-10-30 15:26:04','TweetException');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Queue` WRITE;
/*!40000 ALTER TABLE `Queue` DISABLE KEYS */;

INSERT INTO `Queue` (`id`, `time`, `task`, `date`)
VALUES
	(2,17,'PostTweet','2014-10-29 20:03:58'),
	(3,3,'PostTweet','2014-10-29 20:09:17'),
	(4,182,'GetTweetList','2014-10-29 20:15:06'),
	(5,7,'PostTweet','2014-10-29 20:15:12'),
	(6,36,'GetTweetList','2014-10-29 20:15:14'),
	(7,39,'DestroyTweet','2014-10-29 20:15:18'),
	(8,30,'GetTweetList','2014-10-29 20:15:19'),
	(9,4,'RetweetTweet','2014-10-29 20:15:27'),
	(10,30,'GetTweetList','2014-10-29 20:15:28'),
	(11,196,'GetTweetList','2014-10-29 20:18:14'),
	(12,7,'PostTweet','2014-10-29 20:18:19'),
	(13,38,'GetTweetList','2014-10-29 20:18:21'),
	(14,28,'GetTweetList','2014-10-29 20:18:30'),
	(15,39,'GetTweetList','2014-10-29 20:18:31'),
	(16,30,'GetTweetList','2014-10-29 20:18:32'),
	(17,30,'GetTweetList','2014-10-29 20:18:32'),
	(18,24,'GetTweetList','2014-10-29 20:18:33'),
	(19,24,'GetTweetList','2014-10-29 20:18:37'),
	(20,23,'GetTweetList','2014-10-29 20:18:38'),
	(21,20,'GetTweetList','2014-10-29 20:18:39'),
	(22,12,'UsernameLogin','2014-10-29 20:33:12'),
	(23,194,'GetTweetList','2014-10-29 20:33:12'),
	(24,11,'GetIncomingFriends','2014-10-29 20:33:15'),
	(25,17,'GetFollowers','2014-10-29 20:33:15'),
	(26,18,'GetOutgoingFriends','2014-10-29 20:33:15'),
	(27,11,'GetFollowing','2014-10-29 20:33:15'),
	(28,14,'CreateFriendship','2014-10-29 20:33:18'),
	(29,12,'GetFollowing','2014-10-29 20:33:18'),
	(30,12,'GetOutgoingFriends','2014-10-29 20:33:18'),
	(31,19,'GetFollowers','2014-10-29 20:33:18'),
	(32,7,'GetIncomingFriends','2014-10-29 20:33:18'),
	(33,9,'DestroyFriendship','2014-10-29 20:33:40'),
	(34,10,'GetFollowers','2014-10-29 20:33:40'),
	(35,12,'GetOutgoingFriends','2014-10-29 20:33:40'),
	(36,8,'GetIncomingFriends','2014-10-29 20:33:40'),
	(37,8,'GetFollowing','2014-10-29 20:33:40'),
	(38,9,'GetOutgoingFriends','2014-10-29 20:33:44'),
	(39,8,'GetFollowing','2014-10-29 20:33:44'),
	(40,11,'GetFollowers','2014-10-29 20:33:44'),
	(41,33,'GetIncomingFriends','2014-10-29 20:33:44'),
	(42,43,'GetTweetList','2014-10-29 20:33:47'),
	(43,27,'GetTweetList','2014-10-29 20:33:48'),
	(44,22,'GetTweetList','2014-10-29 20:33:48'),
	(45,3,'PostTweet','2014-10-29 20:33:51'),
	(46,25,'GetTweetList','2014-10-29 20:33:52'),
	(47,7,'GetFollowing','2014-10-29 20:34:22'),
	(48,8,'GetFollowers','2014-10-29 20:34:22'),
	(49,6,'GetOutgoingFriends','2014-10-29 20:34:22'),
	(50,6,'GetIncomingFriends','2014-10-29 20:34:22'),
	(51,45,'UsernameLogin','2014-10-29 20:51:24'),
	(52,360,'GetTweetList','2014-10-29 20:51:25'),
	(53,78,'UsernameLogin','2014-10-29 21:00:46'),
	(54,200,'GetTweetList','2014-10-29 21:00:47'),
	(55,9,'UsernameLogin','2014-10-29 21:44:19'),
	(56,410,'GetTweetList','2014-10-29 21:44:19'),
	(57,208,'UsernameLogin','2014-10-29 21:55:58'),
	(58,195,'GetTweetList','2014-10-29 21:55:59'),
	(59,330,'GetTweetList','2014-10-29 22:29:40'),
	(60,15,'GetTweetList','2014-10-29 22:29:42'),
	(61,61,'UsernameLogin','2014-10-30 15:06:12'),
	(62,345,'GetTweetList','2014-10-30 15:06:12'),
	(63,35,'GetTweetList','2014-10-30 15:06:13'),
	(64,29,'GetTweetList','2014-10-30 15:06:14'),
	(65,34,'GetTweetList','2014-10-30 15:06:15'),
	(66,27,'GetTweetList','2014-10-30 15:06:15'),
	(67,34,'GetTweetList','2014-10-30 15:06:16'),
	(68,28,'GetTweetList','2014-10-30 15:06:16'),
	(69,37,'GetTweetList','2014-10-30 15:06:16'),
	(70,26,'GetTweetList','2014-10-30 15:06:17'),
	(71,32,'GetTweetList','2014-10-30 15:06:17'),
	(72,31,'GetTweetList','2014-10-30 15:06:17'),
	(73,29,'GetTweetList','2014-10-30 15:06:17'),
	(74,26,'GetTweetList','2014-10-30 15:06:18'),
	(75,24,'GetTweetList','2014-10-30 15:06:18'),
	(76,20,'GetTweetList','2014-10-30 15:06:18'),
	(77,23,'GetTweetList','2014-10-30 15:06:19'),
	(78,31,'GetTweetList','2014-10-30 15:06:20'),
	(79,27,'UsernameLogin','2014-10-30 15:20:30'),
	(80,197,'GetTweetList','2014-10-30 15:20:31'),
	(81,247,'GetTweetList','2014-10-30 15:23:28'),
	(82,17,'GetTweetList','2014-10-30 15:23:28'),
	(83,12,'GetTweetList','2014-10-30 15:23:29'),
	(84,13,'GetTweetList','2014-10-30 15:23:30'),
	(85,23,'GetOutgoingFriends','2014-10-30 15:23:36'),
	(86,23,'GetFollowers','2014-10-30 15:23:36'),
	(87,10,'GetIncomingFriends','2014-10-30 15:23:36'),
	(88,9,'GetFollowing','2014-10-30 15:23:36'),
	(89,6,'GetOutgoingFriends','2014-10-30 15:23:39'),
	(90,10,'GetIncomingFriends','2014-10-30 15:23:39'),
	(91,10,'GetFollowing','2014-10-30 15:23:39'),
	(92,9,'GetFollowers','2014-10-30 15:23:39'),
	(93,14,'GetFollowers','2014-10-30 15:24:16'),
	(94,8,'GetIncomingFriends','2014-10-30 15:24:16'),
	(95,9,'GetFollowing','2014-10-30 15:24:16'),
	(96,7,'GetOutgoingFriends','2014-10-30 15:24:16'),
	(97,4,'UsernameLogin','2014-10-30 15:25:16'),
	(98,35,'GetTweetList','2014-10-30 15:25:16'),
	(99,37,'PostTweet','2014-10-30 15:25:55'),
	(100,31,'GetTweetList','2014-10-30 15:25:56'),
	(101,25,'GetTweetList','2014-10-30 15:28:40'),
	(102,10,'GetOutgoingFriends','2014-10-30 15:28:42'),
	(103,73,'GetFollowers','2014-10-30 15:28:43'),
	(104,9,'GetFollowing','2014-10-30 15:28:43'),
	(105,9,'GetIncomingFriends','2014-10-30 15:28:43'),
	(106,10,'GetFollowers','2014-10-30 15:28:44'),
	(107,11,'GetOutgoingFriends','2014-10-30 15:28:44'),
	(108,16,'GetFollowing','2014-10-30 15:28:44'),
	(109,15,'GetIncomingFriends','2014-10-30 15:28:44'),
	(110,9,'GetFollowers','2014-10-30 15:28:45'),
	(111,8,'GetIncomingFriends','2014-10-30 15:28:45'),
	(112,7,'GetFollowing','2014-10-30 15:28:45'),
	(113,9,'GetOutgoingFriends','2014-10-30 15:28:45'),
	(114,12,'GetTweetList','2014-10-30 15:28:46'),
	(115,10,'GetTweetList','2014-10-30 15:28:47'),
	(116,7,'GetIncomingFriends','2014-10-30 15:28:47'),
	(117,8,'GetOutgoingFriends','2014-10-30 15:28:47'),
	(118,8,'GetFollowing','2014-10-30 15:28:47'),
	(119,7,'GetFollowers','2014-10-30 15:28:47'),
	(120,7,'GetFollowers','2014-10-30 15:28:48'),
	(121,6,'GetOutgoingFriends','2014-10-30 15:28:48'),
	(122,14,'GetFollowing','2014-10-30 15:28:48'),
	(123,5,'GetIncomingFriends','2014-10-30 15:28:48'),
	(124,9,'GetTweetList','2014-10-30 15:28:48'),
	(125,16,'GetTweetList','2014-10-30 15:28:49'),
	(126,443,'GetFollowers','2014-10-30 15:30:32'),
	(127,451,'GetFollowing','2014-10-30 15:30:32'),
	(128,461,'GetIncomingFriends','2014-10-30 15:30:32'),
	(129,488,'GetTweetList','2014-10-30 15:30:32'),
	(130,458,'GetOutgoingFriends','2014-10-30 15:30:32'),
	(131,7,'GetIncomingFriends','2014-10-30 15:30:51'),
	(132,53,'GetOutgoingFriends','2014-10-30 15:30:51'),
	(133,10,'GetFollowers','2014-10-30 15:30:51'),
	(134,9,'GetFollowing','2014-10-30 15:30:51'),
	(135,12,'GetTweetList','2014-10-30 15:30:52');

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
	(122,15,'');

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
