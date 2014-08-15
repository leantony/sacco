-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.16-log - MySQL Community Server (GPL)
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
  `Approved` tinyint(1) NOT NULL DEFAULT '1',
  `DateOfContribution` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DateApproved` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_contributions_members` (`member_id`),
  CONSTRAINT `FK_contributions_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COMMENT='all member contributions';

-- Dumping data for table sacco.contributions: ~9 rows (approximately)
/*!40000 ALTER TABLE `contributions` DISABLE KEYS */;
REPLACE INTO `contributions` (`id`, `member_id`, `Amount`, `paymentMethod`, `Approved`, `DateOfContribution`, `DateApproved`) VALUES
	(1, 1, 10000, 'Cash', 1, '2014-08-13 23:11:49', '2014-08-14 16:58:37'),
	(4, 1, 4454, 'EXCESS', 1, '2014-08-14 18:44:15', '2014-08-14 18:44:15'),
	(5, 1, 4454, 'EXCESS', 1, '2014-08-14 18:47:44', '2014-08-14 18:47:44'),
	(6, 1, 0, 'EXCESS', 1, '2014-08-14 19:21:01', '2014-08-14 19:21:01'),
	(7, 2, 4500, 'Cash', 1, '2014-08-14 19:29:17', '2014-08-14 19:29:43'),
	(8, 1, 12000, 'Cheque', 1, '2014-08-14 23:31:44', '2014-08-14 23:31:44'),
	(9, 1, 19291, 'EXCESS', 1, '2014-08-14 23:44:16', '2014-08-14 23:44:16'),
	(10, 1, 2036, 'EXCESS', 1, '2014-08-14 23:52:05', '2014-08-14 23:52:05'),
	(11, 1, 5359, 'EXCESS', 1, '2014-08-14 23:58:12', '2014-08-14 23:58:12');
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1 COMMENT='all member loan payments';

-- Dumping data for table sacco.loanpayments: ~21 rows (approximately)
/*!40000 ALTER TABLE `loanpayments` DISABLE KEYS */;
REPLACE INTO `loanpayments` (`id`, `member_id`, `Amount`, `DatePaid`, `loan_id`, `Approved`) VALUES
	(1, 1, 2000, '2014-08-14 17:40:26', 1, 1),
	(2, 1, 2000, '2014-08-14 17:46:50', 1, 1),
	(3, 1, 7000, '2014-08-14 17:49:18', 1, 1),
	(4, 1, 3000, '2014-08-14 17:58:40', 1, 1),
	(5, 1, 23500, '2014-08-14 18:43:31', 1, 1),
	(6, 2, 5000, '2014-08-14 19:31:33', 3, 1),
	(7, 1, 2500, '2014-08-14 23:38:17', 9, 1),
	(8, 1, 6000, '2014-08-14 23:38:33', 9, 1),
	(9, 1, 7000, '2014-08-14 23:38:46', 9, 1),
	(10, 1, 7000, '2014-08-14 23:38:50', 9, 1),
	(11, 1, 7000, '2014-08-14 23:38:58', 9, 1),
	(12, 1, 25000, '2014-08-14 23:45:41', 10, 1),
	(13, 1, 5000, '2014-08-14 23:46:15', 10, 1),
	(14, 1, 1000, '2014-08-14 23:46:28', 10, 1),
	(15, 1, 1000, '2014-08-14 23:46:39', 10, 1),
	(16, 1, 1000, '2014-08-14 23:46:44', 10, 1),
	(17, 1, 1000, '2014-08-14 23:47:18', 10, 1),
	(18, 1, 5000, '2014-08-14 23:53:14', 11, 1),
	(19, 1, 10000, '2014-08-14 23:53:22', 11, 1),
	(20, 1, 3000, '2014-08-14 23:53:50', 11, 1),
	(21, 1, 3000, '2014-08-14 23:54:08', 11, 1);
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COMMENT='All member loans';

-- Dumping data for table sacco.loans: ~5 rows (approximately)
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
REPLACE INTO `loans` (`id`, `member_id`, `LoanType`, `LoanAmount`, `TotalAmount`, `PaybackDate`, `LoanPurpose`, `paidAmount`, `Approved`, `DateApproved`, `cleared`, `DateLastPaid`, `DateSubmitted`) VALUES
	(1, 1, 'secured', 14500, 33046.40126817059, 0.5, 'nothing', 37500, 0, NULL, 1, '2014-08-14 18:47:44', '2014-08-14 17:08:34'),
	(3, 2, 'secured', 10000, 22276.55106742179, 0.4166666666666667, 'nothing', 5000, 0, NULL, 0, '2014-08-14 19:31:33', '2014-08-14 19:30:52'),
	(9, 1, 'secured', 5000, 10209.370894973094, 0.08333333333333333, 'none', 29500, 0, NULL, 1, '2014-08-14 23:44:16', '2014-08-14 23:37:38'),
	(10, 1, 'secured', 15000, 31964.3445259683, 0.25, 'nhjhn', 34000, 0, NULL, 1, '2014-08-14 23:52:05', '2014-08-14 23:45:21'),
	(11, 1, 'secured', 7500, 15641.26353641783, 0.16666666666666666, 'sfsf', 21000, 0, NULL, 1, '2014-08-14 23:58:11', '2014-08-14 23:52:32');
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;


-- Dumping structure for table sacco.members
DROP TABLE IF EXISTS `members`;
CREATE TABLE IF NOT EXISTS `members` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `dob` date NOT NULL,
  `mobileno` int(11) unsigned NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `DateModified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DateRegistered` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  FULLTEXT KEY `firstname` (`firstname`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COMMENT='All members';

-- Dumping data for table sacco.members: ~3 rows (approximately)
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
REPLACE INTO `members` (`id`, `firstname`, `lastname`, `gender`, `dob`, `mobileno`, `address`, `email`, `password`, `active`, `DateModified`, `DateRegistered`) VALUES
	(1, 'Administrator', 'sacco', 'Female', '1999-08-14', 712568952, '78, karen', 'admin@localhost.com', 'e10adc3949ba59abbe56e057f20f883e', 1, '2014-08-14 23:07:55', '2014-08-13 22:26:44'),
	(2, 'auhda', 'hilda', 'Female', '1892-03-10', 123456, '123456', 'x@y.z', '670b14728ad9902aecba32e22fa4f6bd', 1, '2014-08-14 01:15:28', '2014-08-14 01:15:28'),
	(3, 'aliuhda', 'asdfg', 'Female', '1892-03-10', 123456, '123456', 'xa@ye.coza', '670b14728ad9902aecba32e22fa4f6bd', 1, '2014-08-14 01:16:01', '2014-08-14 01:16:01');
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

-- Dumping data for table sacco.members_positions: ~3 rows (approximately)
/*!40000 ALTER TABLE `members_positions` DISABLE KEYS */;
REPLACE INTO `members_positions` (`position_id`, `member_id`, `DateAdded`, `DateModified`) VALUES
	(1, 1, '2014-08-13 22:27:38', '2014-08-13 22:27:33'),
	(2, 2, '2014-08-14 01:15:28', '2014-08-14 01:15:28'),
	(3, 3, '2014-08-14 01:16:01', '2014-08-14 01:16:01');
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
	(1, 'interest', 5.3, 'none available', '2014-08-14 16:05:39', '2014-07-28 18:02:53');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
