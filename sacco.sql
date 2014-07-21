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


-- Dumping structure for table sacco.admins
DROP TABLE IF EXISTS `admins`;
CREATE TABLE IF NOT EXISTS `admins` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(5) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_id` (`member_id`),
  CONSTRAINT `FK_admins_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table sacco.admins: ~2 rows (approximately)
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT IGNORE INTO `admins` (`id`, `member_id`) VALUES
	(2, 6),
	(1, 7);
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;


-- Dumping structure for table sacco.loans
DROP TABLE IF EXISTS `loans`;
CREATE TABLE IF NOT EXISTS `loans` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `member_id` int(5) unsigned NOT NULL,
  `LoanType` varchar(1000) NOT NULL,
  `LoanAmount` int(7) unsigned NOT NULL,
  `TotalAmount` int(10) unsigned NOT NULL,
  `PaybackDate` tinyint(4) unsigned NOT NULL,
  `LoanPurpose` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_id` (`member_id`),
  CONSTRAINT `FK_loans_members` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- Dumping data for table sacco.loans: ~2 rows (approximately)
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
INSERT IGNORE INTO `loans` (`id`, `member_id`, `LoanType`, `LoanAmount`, `TotalAmount`, `PaybackDate`, `LoanPurpose`) VALUES
	(15, 6, 'secured', 5000, 5092, 4, 'ryryryy');
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
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table sacco.members: ~3 rows (approximately)
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT IGNORE INTO `members` (`id`, `firstname`, `lastname`, `gender`, `dob`, `mobileno`, `address`, `email`, `password`) VALUES
	(6, '555', '555', 'Male', '2014-07-20', 555, '555', '555', '555'),
	(7, 'asdf', 'this', 'Female', '2014-07-20', 890, '890', '890', '1234');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
