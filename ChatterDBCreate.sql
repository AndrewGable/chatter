#
# SQL Export
# Created: September 22, 2014 at 11:38:13 PM MDT
# Encoding: Unicode (UTF-8)
#

CREATE TABLE `Friends` (
  `user_id` bigint(20) unsigned NOT NULL,
  `friends_id` bigint(20) unsigned NOT NULL,
  `accepted` varchar(1) NOT NULL DEFAULT 'N',
  `friendship_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`friendship_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;


CREATE TABLE `Tweet` (
  `tweet_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `message` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`tweet_id`),
  KEY `tweet_id` (`tweet_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=latin1;


CREATE TABLE `User` (
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_user_id` (`user_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;




