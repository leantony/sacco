-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.16 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             8.3.0.4799
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for sacco
DROP DATABASE IF EXISTS `sacco`;
CREATE DATABASE IF NOT EXISTS `sacco` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sacco`;


-- Dumping structure for table sacco.contributions
DROP TABLE IF EXISTS `contributions`;
CREATE TABLE IF NOT EXISTS `contributions` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(5) unsigned NOT NULL,
  `Amount` int(7) unsigned NOT NULL,
  `paymentMethod` varchar(50) NOT NULL,
  `Approved` tinyint(1) NOT NULL DEFAULT '0',
  `DateOfContribution` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DateApproved` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_contributions_members` (`member_id`),
  CONSTRAINT `FK_contributions_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=latin1 COMMENT='all member contributions';

-- Dumping data for table sacco.contributions: ~3 rows (approximately)
/*!40000 ALTER TABLE `contributions` DISABLE KEYS */;
REPLACE INTO `contributions` (`id`, `member_id`, `Amount`, `paymentMethod`, `Approved`, `DateOfContribution`, `DateApproved`) VALUES
	(1000, 1000, 50000, 'Cash', 1, '2014-08-21 01:28:51', '2014-08-21 01:29:09'),
	(1001, 1000, 10000, 'Cash', 1, '2014-08-21 13:22:44', '2014-08-21 15:04:35'),
	(1002, 1001, 1000, 'Cash', 1, '2014-08-21 15:04:13', '2014-08-21 15:04:32');
/*!40000 ALTER TABLE `contributions` ENABLE KEYS */;


-- Dumping structure for table sacco.loanpayments
DROP TABLE IF EXISTS `loanpayments`;
CREATE TABLE IF NOT EXISTS `loanpayments` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(5) unsigned NOT NULL,
  `Amount` double unsigned NOT NULL,
  `DatePaid` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `loan_id` int(5) unsigned NOT NULL,
  `Approved` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_loanpayments_members` (`member_id`),
  KEY `FK_loanpayments_loans` (`loan_id`),
  CONSTRAINT `FK_loanpayments_loans` FOREIGN KEY (`loan_id`) REFERENCES `loans` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_loanpayments_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=latin1 COMMENT='all member loan payments';

-- Dumping data for table sacco.loanpayments: ~3 rows (approximately)
/*!40000 ALTER TABLE `loanpayments` DISABLE KEYS */;
REPLACE INTO `loanpayments` (`id`, `member_id`, `Amount`, `DatePaid`, `loan_id`, `Approved`) VALUES
	(1000, 1000, 7000, '2014-08-21 01:30:28', 1000, 1),
	(1001, 1000, 14000, '2014-08-21 15:41:36', 1000, 1),
	(1002, 1000, 14000, '2014-08-21 15:51:07', 1000, 1);
/*!40000 ALTER TABLE `loanpayments` ENABLE KEYS */;


-- Dumping structure for table sacco.loans
DROP TABLE IF EXISTS `loans`;
CREATE TABLE IF NOT EXISTS `loans` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(5) unsigned NOT NULL,
  `LoanType` varchar(1000) NOT NULL,
  `LoanAmount` double unsigned NOT NULL,
  `TotalAmount` double unsigned NOT NULL,
  `PaybackDate` double unsigned NOT NULL,
  `LoanPurpose` varchar(1000) NOT NULL,
  `paidAmount` double unsigned NOT NULL DEFAULT '0',
  `Approved` tinyint(1) NOT NULL DEFAULT '0',
  `DateApproved` timestamp NULL DEFAULT NULL,
  `cleared` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `DateLastPaid` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DateSubmitted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_loans_members` (`member_id`),
  CONSTRAINT `FK_loans_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=latin1 COMMENT='All member loans';

-- Dumping data for table sacco.loans: ~0 rows (approximately)
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
REPLACE INTO `loans` (`id`, `member_id`, `LoanType`, `LoanAmount`, `TotalAmount`, `PaybackDate`, `LoanPurpose`, `paidAmount`, `Approved`, `DateApproved`, `cleared`, `DateLastPaid`, `DateSubmitted`) VALUES
	(1000, 1000, 'secured', 70000, 77000, 0.4166666666666667, 'hdfhdfh', 21000, 1, NULL, 0, '2014-08-21 15:51:07', '2014-08-21 01:29:52');
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;


-- Dumping structure for table sacco.login_sessions
DROP TABLE IF EXISTS `login_sessions`;
CREATE TABLE IF NOT EXISTS `login_sessions` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(5) unsigned NOT NULL,
  `loginTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `logoutTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `FK_login_sessions_members` (`member_id`),
  CONSTRAINT `FK_login_sessions_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=latin1;

-- Dumping data for table sacco.login_sessions: ~4 rows (approximately)
/*!40000 ALTER TABLE `login_sessions` DISABLE KEYS */;
REPLACE INTO `login_sessions` (`id`, `member_id`, `loginTime`, `logoutTime`) VALUES
	(1000, 1000, '2014-08-21 17:31:40', '2014-08-21 17:31:46'),
	(1001, 1000, '2014-08-21 18:00:38', '2014-08-21 18:00:55'),
	(1002, 1000, '2014-08-21 18:02:41', '2014-08-21 18:03:05'),
	(1003, 1000, '2014-08-21 18:07:42', '2014-08-21 18:07:47');
/*!40000 ALTER TABLE `login_sessions` ENABLE KEYS */;


-- Dumping structure for table sacco.members
DROP TABLE IF EXISTS `members`;
CREATE TABLE IF NOT EXISTS `members` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `dob` date NOT NULL,
  `mobileno` bigint(20) unsigned NOT NULL,
  `address` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `DateModified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DateRegistered` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`email`),
  UNIQUE KEY `email` (`email`),
  FULLTEXT KEY `firstname` (`firstname`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=latin1 COMMENT='All members';

-- Dumping data for table sacco.members: ~4 rows (approximately)
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
REPLACE INTO `members` (`id`, `firstname`, `lastname`, `gender`, `dob`, `mobileno`, `address`, `email`, `password`, `active`, `DateModified`, `DateRegistered`) VALUES
	(1000, 'administrator', 'antony', 'Female', '1990-01-01', 25425005200, 'karen', 'a@b.com', '$2a$10$.xji1Vsui1sBJRwfFpZzcu0WqzcX/0hE.aD7N7Se057hrEC1CBroe', 1, '2014-08-21 01:40:52', '2014-08-21 00:32:46'),
	(1001, 'ksfashfk433', 'asfkfhkl', 'Female', '1970-01-19', 25479950555, 'asfasfsaf', 'a@birt.com', '$2a$10$mfq3XvVfPQ7uAYguS9a/8u4KHL4ClCZRvnJydvg52roFCQSvGA4IK', 1, '2014-08-21 00:33:28', '2014-08-21 00:33:28'),
	(1002, 'asdfert', '34dfsdf', 'Male', '1970-09-25', 25472557555, 'asfasfsaf', 'awer@birt.com', '$2a$10$mM2kFBPlC1iuL.Yg60YJPOlf7Va55WQTzGdJvAWlsu/QG/m1s79xO', 1, '2014-08-21 00:34:05', '2014-08-21 00:34:05');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;


-- Dumping structure for table sacco.members_positions
DROP TABLE IF EXISTS `members_positions`;
CREATE TABLE IF NOT EXISTS `members_positions` (
  `position_id` int(10) unsigned NOT NULL,
  `member_id` int(5) unsigned NOT NULL,
  `DateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DateModified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `member_id` (`member_id`),
  KEY `FK__positions` (`position_id`),
  CONSTRAINT `FK_members_pos_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK__positions` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='members in positions';

-- Dumping data for table sacco.members_positions: ~0 rows (approximately)
/*!40000 ALTER TABLE `members_positions` DISABLE KEYS */;
REPLACE INTO `members_positions` (`position_id`, `member_id`, `DateAdded`, `DateModified`) VALUES
	(1, 1000, '2014-08-21 01:28:18', '2014-08-21 01:28:18');
/*!40000 ALTER TABLE `members_positions` ENABLE KEYS */;


-- Dumping structure for table sacco.minutes
DROP TABLE IF EXISTS `minutes`;
CREATE TABLE IF NOT EXISTS `minutes` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(5) unsigned NOT NULL,
  `title` varchar(150) NOT NULL,
  `content` text NOT NULL,
  `DateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DateModified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_minutes_members_positions` (`member_id`),
  CONSTRAINT `FK_minutes_members_positions` FOREIGN KEY (`member_id`) REFERENCES `members_positions` (`member_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='the minutes recorded by the sec';

-- Dumping data for table sacco.minutes: ~0 rows (approximately)
/*!40000 ALTER TABLE `minutes` DISABLE KEYS */;
/*!40000 ALTER TABLE `minutes` ENABLE KEYS */;


-- Dumping structure for table sacco.positions
DROP TABLE IF EXISTS `positions`;
CREATE TABLE IF NOT EXISTS `positions` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COMMENT='All positions in the sacco';

-- Dumping data for table sacco.positions: ~3 rows (approximately)
/*!40000 ALTER TABLE `positions` DISABLE KEYS */;
REPLACE INTO `positions` (`id`, `name`) VALUES
	(1, 'admin'),
	(2, 'secretary'),
	(3, 'treasurer');
/*!40000 ALTER TABLE `positions` ENABLE KEYS */;


-- Dumping structure for table sacco.queries
DROP TABLE IF EXISTS `queries`;
CREATE TABLE IF NOT EXISTS `queries` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `query` varchar(1000) NOT NULL,
  `member_id` int(5) unsigned NOT NULL,
  `DateSubmitted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cleared` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_Queries_members` (`member_id`),
  CONSTRAINT `FK_Queries_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Member submitted queries';

-- Dumping data for table sacco.queries: ~0 rows (approximately)
/*!40000 ALTER TABLE `queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `queries` ENABLE KEYS */;


-- Dumping structure for table sacco.settings
DROP TABLE IF EXISTS `settings`;
CREATE TABLE IF NOT EXISTS `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `value` double DEFAULT NULL,
  `description` varchar(500) DEFAULT 'none available',
  `DateModified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `DateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='app settings e.g interest which apply to all members';

-- Dumping data for table sacco.settings: ~0 rows (approximately)
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
REPLACE INTO `settings` (`id`, `name`, `value`, `description`, `DateModified`, `DateAdded`) VALUES
	(1, 'interest', 2, 'none available', '2014-08-15 20:45:10', '2014-07-28 18:02:53');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
