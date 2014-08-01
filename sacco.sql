-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.16 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             8.3.0.4694
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COMMENT='all member contributions';

-- Dumping data for table sacco.contributions: ~2 rows (approximately)
/*!40000 ALTER TABLE `contributions` DISABLE KEYS */;
REPLACE INTO `contributions` (`id`, `member_id`, `Amount`, `paymentMethod`, `Approved`, `DateOfContribution`, `DateApproved`) VALUES
	(1, 16, 5000, 'Cash', 1, '2014-07-31 00:43:04', '2014-07-31 00:43:33'),
	(2, 16, 5000, 'EXCESS', 0, '2014-07-31 01:05:53', '0000-00-00 00:00:00'),
	(3, 16, 5000, 'EXCESS', 1, '2014-07-31 01:06:38', '0000-00-00 00:00:00'),
	(4, 16, 300, 'EXCESS', 1, '2014-07-31 01:27:26', '0000-00-00 00:00:00'),
	(5, 16, 300, 'EXCESS', 1, '2014-07-31 01:27:51', '2014-07-31 01:27:51');
/*!40000 ALTER TABLE `contributions` ENABLE KEYS */;


-- Dumping structure for table sacco.loanpayments
DROP TABLE IF EXISTS `loanpayments`;
CREATE TABLE IF NOT EXISTS `loanpayments` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(5) unsigned NOT NULL,
  `Amount` double unsigned NOT NULL,
  `DatePaid` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `loan_id` int(5) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loanpayments_members` (`member_id`),
  KEY `FK_loanpayments_loans` (`loan_id`),
  CONSTRAINT `FK_loanpayments_loans` FOREIGN KEY (`loan_id`) REFERENCES `loans` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_loanpayments_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1 COMMENT='all member loan payments';

-- Dumping data for table sacco.loanpayments: ~7 rows (approximately)
/*!40000 ALTER TABLE `loanpayments` DISABLE KEYS */;
REPLACE INTO `loanpayments` (`id`, `member_id`, `Amount`, `DatePaid`, `loan_id`) VALUES
	(38, 16, 12700, '2014-07-31 01:25:10', 21),
	(39, 16, 12700, '2014-07-31 01:25:15', 21),
	(40, 16, 12700, '2014-07-31 01:25:26', 21),
	(41, 16, 12000, '2014-07-31 01:26:56', 22),
	(42, 16, 12000, '2014-07-31 01:27:00', 22),
	(43, 16, 12000, '2014-07-31 01:27:15', 22),
	(44, 16, 12000, '2014-07-31 01:27:23', 22),
	(45, 16, 50000, '2014-07-31 18:25:00', 23),
	(46, 16, 50000, '2014-08-01 19:01:58', 23);
/*!40000 ALTER TABLE `loanpayments` ENABLE KEYS */;


-- Dumping structure for table sacco.loans
DROP TABLE IF EXISTS `loans`;
CREATE TABLE IF NOT EXISTS `loans` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(5) unsigned NOT NULL,
  `LoanType` varchar(1000) NOT NULL,
  `LoanAmount` double unsigned NOT NULL,
  `TotalAmount` double unsigned NOT NULL,
  `PaybackDate` tinyint(3) unsigned NOT NULL,
  `LoanPurpose` varchar(1000) NOT NULL,
  `paidAmount` double unsigned NOT NULL DEFAULT '0',
  `cleared` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `DateLastPaid` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DateSubmitted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_loans_members` (`member_id`),
  CONSTRAINT `FK_loans_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1 COMMENT='All member loans';

-- Dumping data for table sacco.loans: ~2 rows (approximately)
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
REPLACE INTO `loans` (`id`, `member_id`, `LoanType`, `LoanAmount`, `TotalAmount`, `PaybackDate`, `LoanPurpose`, `paidAmount`, `cleared`, `DateLastPaid`, `DateSubmitted`) VALUES
	(21, 16, 'secured', 12700, 12964.583333333334, 1, '5dfghdfh', 13050, 1, '2014-07-31 01:25:30', '2014-07-31 01:24:56'),
	(22, 16, 'secured', 12000, 12250, 1, '4tdfgdg', 12550, 1, '2014-07-31 01:27:26', '2014-07-31 01:26:42'),
	(23, 16, 'secured', 50000, 53125, 3, 'nothing', 12000, 0, '2014-08-01 19:01:58', '2014-07-31 18:24:09');
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 COMMENT='All members';

-- Dumping data for table sacco.members: ~7 rows (approximately)
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
REPLACE INTO `members` (`id`, `firstname`, `lastname`, `gender`, `dob`, `mobileno`, `address`, `email`, `password`, `active`, `DateModified`, `DateRegistered`) VALUES
	(16, 'Administrator', 'anto', 'Male', '1833-01-05', 454545, 'ngong', 'admin@localhost.org', '$2a$10$uNdaavQ1hELAhxeB1RpU/e/CM9VIPK1U7XgEmZImzLzhLpaEW/Rda', 1, '2014-07-31 17:16:07', '2014-07-25 23:06:22'),
	(17, 'ffgdfg5', 'dfgdfg', 'Female', '2000-02-05', 758585, '6575', 'a@b.c', '$2a$10$kXi7hAY/jpZeDofJ1Tq3..dZFzCEltEsT03GhslB/lJhIRkTjzBgS', 1, '2014-07-30 01:14:35', '2014-07-28 22:00:06'),
	(18, 'rtedr', 'rgtr', 'Male', '1223-10-22', 3434534, '345345', '34535', '$2a$10$IyC4bGjuNNg89dE7ntaUI.owmlroLKXJLkL09aAiUjjydNLAQw8mi', 0, '2014-07-31 00:13:07', '2014-07-30 23:55:31'),
	(19, 'rderyrhy', 'rrhyreyh', 'Male', '1304-03-05', 54454, '456785', '786786', '$2a$10$L3Qgxxase72XLjLzjf5r/eM.J5.aMLBbrAufEfEdqcgCwY.dfyE3W', 1, '2014-07-30 23:57:06', '2014-07-30 23:57:06'),
	(20, '123456', '123456', 'Male', '1233-01-05', 454545, '67676', '67676', '$2a$10$nBRRdq26khuGVvBf.mxraO1NYT0XaMFPyJtGq6FCSYWpsEusi7WIq', 1, '2014-07-30 23:58:16', '2014-07-30 23:58:16'),
	(21, '123456', '123456', 'Male', '1233-01-05', 454545, '67676', '67676', '$2a$10$VC9VLyQtOWpFb0XFTPMYgeItTM0Gzkbgi5u.dCq4KQBnrUlCE6nKa', 1, '2014-07-31 16:49:39', '2014-07-30 23:58:25'),
	(22, '123456', '123456', 'Male', '1233-01-05', 454545, '67676', '67676', '$2a$10$6M.z07tPKnqrMhe9jbbRg.H3FkJLsKuYl8BR1UuXwzZzK3qS2kUqe', 1, '2014-08-01 18:59:59', '2014-07-30 23:58:44');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;


-- Dumping structure for table sacco.members_positions
DROP TABLE IF EXISTS `members_positions`;
CREATE TABLE IF NOT EXISTS `members_positions` (
  `position_id` int(10) unsigned NOT NULL,
  `DateModified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `member_id` int(5) unsigned NOT NULL,
  `DateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `member_id` (`member_id`),
  KEY `FK__positions` (`position_id`),
  CONSTRAINT `FK_members_pos_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK__positions` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='members in positions';

-- Dumping data for table sacco.members_positions: ~4 rows (approximately)
/*!40000 ALTER TABLE `members_positions` DISABLE KEYS */;
REPLACE INTO `members_positions` (`position_id`, `DateModified`, `member_id`, `DateAdded`) VALUES
	(1, '0000-00-00 00:00:00', 16, '2014-07-29 01:58:36'),
	(1, '2014-07-30 23:58:16', 20, '2014-07-30 23:58:16'),
	(2, '2014-07-30 23:58:25', 21, '2014-07-30 23:58:25'),
	(3, '2014-07-30 23:58:44', 22, '2014-07-30 23:58:44');
/*!40000 ALTER TABLE `members_positions` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='Member submitted queries';

-- Dumping data for table sacco.queries: ~0 rows (approximately)
/*!40000 ALTER TABLE `queries` DISABLE KEYS */;
REPLACE INTO `queries` (`id`, `query`, `member_id`, `DateSubmitted`, `cleared`) VALUES
	(2, 'system is good', 16, '2014-07-29 01:06:27', 0);
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
	(1, 'interest', 25, 'none available', '2014-07-30 01:18:33', '2014-07-28 18:02:53');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
