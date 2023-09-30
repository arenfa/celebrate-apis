-- MySQL dump 10.13  Distrib 5.6.37, for macos10.12 (x86_64)
--
-- Host: localhost    Database: celebrate
-- ------------------------------------------------------
-- Server version	5.6.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `COMMENT`
--

DROP TABLE IF EXISTS `COMMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMMENT` (
  `COMMENTID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CTEXT` varchar(100) DEFAULT NULL,
  `CLIKE` bigint(20) NOT NULL,
  `CREATED_AT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `QUESTIONID` bigint(20) NOT NULL,
  PRIMARY KEY (`COMMENTID`),
  UNIQUE KEY `COMMENTID_UNIQUE` (`COMMENTID`),
  KEY `FK_QUESTIONID_idx` (`QUESTIONID`),
  CONSTRAINT `FK_QUESTIONID` FOREIGN KEY (`QUESTIONID`) REFERENCES `QUESTION` (`QUESTIONID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `QUESTION`
--

DROP TABLE IF EXISTS `QUESTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUESTION` (
  `QUESTIONID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(100) NOT NULL,
  `FREQUENCY` varchar(40) DEFAULT NULL,
  `RESPONSE` varchar(40) DEFAULT NULL,
  `CREATED_AT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`QUESTIONID`),
  UNIQUE KEY `QUESTIONID_UNIQUE` (`QUESTIONID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `QUESTION_AVAIL_RESPONSE`
--

DROP TABLE IF EXISTS `QUESTION_AVAIL_RESPONSE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUESTION_AVAIL_RESPONSE` (
  `QAR_ID` int(11) NOT NULL AUTO_INCREMENT,
  `QUESTIONID` bigint(20) NOT NULL,
  `RESPONSEID` bigint(20) NOT NULL,
  PRIMARY KEY (`QAR_ID`),
  UNIQUE KEY `QAR_ID_UNIQUE` (`QAR_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `QUESTION_RESPONSE`
--

DROP TABLE IF EXISTS `QUESTION_RESPONSE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUESTION_RESPONSE` (
  `QR_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USERID` bigint(20) NOT NULL,
  `QUESTIONID` bigint(20) NOT NULL,
  `RESPONSE` bigint(20) DEFAULT NULL,
  `CREATED_AT` datetime NOT NULL,
  PRIMARY KEY (`QR_ID`),
  UNIQUE KEY `QR_ID_UNIQUE` (`QR_ID`),
  KEY `FK_QR_USERID_idx` (`USERID`),
  KEY `FK_QR_QUESTIONID_idx` (`QUESTIONID`),
  CONSTRAINT `FK_QR_QUESTIONID` FOREIGN KEY (`QUESTIONID`) REFERENCES `QUESTION` (`QUESTIONID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_QR_USERID` FOREIGN KEY (`USERID`) REFERENCES `USER` (`USERID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RESPONSE`
--

DROP TABLE IF EXISTS `RESPONSE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESPONSE` (
  `RESPONSEID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RESPONSE` varchar(100) NOT NULL,
  PRIMARY KEY (`RESPONSEID`),
  UNIQUE KEY `RESPONSEID_UNIQUE` (`RESPONSEID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `USERID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(60) DEFAULT NULL,
  `CREATED_AT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`USERID`),
  UNIQUE KEY `Id_UNIQUE` (`USERID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER_QUESTION`
--

DROP TABLE IF EXISTS `USER_QUESTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_QUESTION` (
  `UQ_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USERID` bigint(20) NOT NULL,
  `QUESTIONID` bigint(20) NOT NULL,
  PRIMARY KEY (`UQ_ID`),
  UNIQUE KEY `UQ_ID_UNIQUE` (`UQ_ID`),
  KEY `FK_UQ_USERID_idx` (`USERID`),
  KEY `FK_UQ_QUESTIONID_idx` (`QUESTIONID`),
  CONSTRAINT `FK_UQ_QUESTIONID` FOREIGN KEY (`QUESTIONID`) REFERENCES `QUESTION` (`QUESTIONID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_UQ_USERID` FOREIGN KEY (`USERID`) REFERENCES `USER` (`USERID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER_TOKEN`
--

DROP TABLE IF EXISTS `USER_TOKEN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_TOKEN` (
  `TOKENID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TOKEN_VALUE` longtext NOT NULL,
  `EXPIRATION_TIME` bigint(20) NOT NULL,
  `USERID` bigint(20) NOT NULL,
  PRIMARY KEY (`TOKENID`),
  UNIQUE KEY `TOKENID_UNIQUE` (`TOKENID`),
  KEY `FK_USERID_idx` (`USERID`),
  CONSTRAINT `FK_USERID` FOREIGN KEY (`USERID`) REFERENCES `USER` (`USERID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-24 12:04:57