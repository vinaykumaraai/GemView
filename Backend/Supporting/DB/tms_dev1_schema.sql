-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: tms_dev1
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `a_data`
--

DROP TABLE IF EXISTS `a_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `a_data` (
  `data` bigint(20) NOT NULL COMMENT 'Account''s number.',
  `client` bigint(20) DEFAULT NULL COMMENT 'Client identification.',
  PRIMARY KEY (`data`),
  KEY `FKA_Data804023` (`client`),
  KEY `FKA_Data956026` (`data`),
  CONSTRAINT `FKA_Data804023` FOREIGN KEY (`client`) REFERENCES `client` (`id`),
  CONSTRAINT `FKA_Data956026` FOREIGN KEY (`data`) REFERENCES `data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores accounts information. Accounts can be of type BankAccount, CheckAccount or CardAccount.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `action`
--

DROP TABLE IF EXISTS `action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(128) NOT NULL DEFAULT 'New Action',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `state` bigint(20) NOT NULL COMMENT 'Address'' state.',
  `country` bigint(20) NOT NULL COMMENT 'Address''s country.',
  `address1` varchar(100) NOT NULL COMMENT 'Address''s first part information.',
  `address2` varchar(100) DEFAULT NULL COMMENT 'Address''s second part information.',
  `city` varchar(50) NOT NULL COMMENT 'Address''s city.',
  `zip` varchar(9) NOT NULL COMMENT 'Address''s zip.',
  `type` int(2) NOT NULL COMMENT 'Address''s type. Ex: Billing, Domicile.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Address'' creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Address'' last update date.',
  PRIMARY KEY (`id`),
  KEY `FKAddress927795` (`state`),
  KEY `FKAddress687498` (`country`),
  CONSTRAINT `FKAddress687498` FOREIGN KEY (`country`) REFERENCES `country` (`id`),
  CONSTRAINT `FKAddress927795` FOREIGN KEY (`state`) REFERENCES `state` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='Store addresses.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app`
--

DROP TABLE IF EXISTS `app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(128) NOT NULL DEFAULT 'New App',
  `Version` char(20) DEFAULT '0.0.0.0',
  `Entity` bigint(20) NOT NULL,
  `StartDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `EndDate` datetime NOT NULL DEFAULT '2030-12-31 23:59:59',
  `Active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `App_fk0` (`Entity`),
  CONSTRAINT `App_fk0` FOREIGN KEY (`Entity`) REFERENCES `entity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `appfile`
--

DROP TABLE IF EXISTS `appfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appfile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` char(128) NOT NULL DEFAULT 'New App File',
  `App` bigint(20) NOT NULL,
  `Description` varchar(128) DEFAULT NULL,
  `AppFileFormat` bigint(20) NOT NULL,
  `DefaultValue` varchar(256) NOT NULL DEFAULT 'filename',
  `Modifiable` tinyint(4) NOT NULL DEFAULT '1',
  `ForceUpdate` tinyint(4) NOT NULL DEFAULT '0',
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `AppFile_fk0` (`App`),
  KEY `AppFile_fk1` (`AppFileFormat`),
  CONSTRAINT `AppFile_fk0` FOREIGN KEY (`App`) REFERENCES `app` (`id`),
  CONSTRAINT `AppFile_fk1` FOREIGN KEY (`AppFileFormat`) REFERENCES `appfileformat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `appfileformat`
--

DROP TABLE IF EXISTS `appfileformat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appfileformat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(128) NOT NULL DEFAULT 'New App File Format',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `applianceapp`
--

DROP TABLE IF EXISTS `applianceapp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applianceapp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `AppProfile` bigint(20) NOT NULL,
  `Terminal` bigint(20) NOT NULL,
  `Device` bigint(20) NOT NULL,
  `CreatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ApplianceApp_fk0` (`AppProfile`),
  KEY `ApplianceApp_fk1` (`Terminal`),
  KEY `ApplianceApp_fk2` (`Device`),
  CONSTRAINT `ApplianceApp_fk0` FOREIGN KEY (`AppProfile`) REFERENCES `appprofile` (`id`),
  CONSTRAINT `ApplianceApp_fk1` FOREIGN KEY (`Terminal`) REFERENCES `terminal` (`id`),
  CONSTRAINT `ApplianceApp_fk2` FOREIGN KEY (`Device`) REFERENCES `device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `applianceappfilevalue`
--

DROP TABLE IF EXISTS `applianceappfilevalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applianceappfilevalue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ApplianceApp` bigint(20) NOT NULL,
  `AppProfileFileValue` bigint(20) NOT NULL,
  `Value` varchar(256) NOT NULL DEFAULT 'filename',
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ApplianceAppFileValue_fk0` (`ApplianceApp`),
  KEY `ApplianceAppFileValue_fk1` (`AppProfileFileValue`),
  CONSTRAINT `ApplianceAppFileValue_fk0` FOREIGN KEY (`ApplianceApp`) REFERENCES `applianceapp` (`id`),
  CONSTRAINT `ApplianceAppFileValue_fk1` FOREIGN KEY (`AppProfileFileValue`) REFERENCES `appprofilefilevalue` (`AppProfile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `applianceappparamvalue`
--

DROP TABLE IF EXISTS `applianceappparamvalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applianceappparamvalue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ApplianceApp` bigint(20) NOT NULL,
  `AppProfileParamValue` bigint(20) NOT NULL,
  `Value` varchar(512) DEFAULT NULL,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ApplianceAppParamValue_fk0` (`ApplianceApp`),
  KEY `ApplianceAppParamValue_fk1` (`AppProfileParamValue`),
  CONSTRAINT `ApplianceAppParamValue_fk0` FOREIGN KEY (`ApplianceApp`) REFERENCES `applianceapp` (`id`),
  CONSTRAINT `ApplianceAppParamValue_fk1` FOREIGN KEY (`AppProfileParamValue`) REFERENCES `appprofileparamvalue` (`AppProfile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `software` bigint(20) DEFAULT NULL COMMENT 'Application''s software.',
  `type` int(2) NOT NULL COMMENT 'Application''s type.',
  `name` varchar(100) NOT NULL COMMENT 'Application''s name.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Application''s description.',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Flag which says if the application is active or not.',
  `version` int(11) NOT NULL COMMENT 'Application''s version.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Application''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FKApplicatio464023` (`software`),
  CONSTRAINT `FKApplicatio464023` FOREIGN KEY (`software`) REFERENCES `software` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Stores applications.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_package`
--

DROP TABLE IF EXISTS `application_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_package` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `terminal` bigint(20) NOT NULL COMMENT 'Terminal.',
  `application` bigint(20) NOT NULL COMMENT 'Application.',
  `payment_profile` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `terminal_application_unique` (`terminal`,`application`),
  KEY `FKApplicatio968269` (`payment_profile`),
  KEY `FKApplicatio793888` (`application`),
  KEY `FKApplicatio470178` (`terminal`),
  CONSTRAINT `FKApplicatio470178` FOREIGN KEY (`terminal`) REFERENCES `terminal` (`id`),
  CONSTRAINT `FKApplicatio793888` FOREIGN KEY (`application`) REFERENCES `application` (`id`),
  CONSTRAINT `FKApplicatio968269` FOREIGN KEY (`payment_profile`) REFERENCES `payment_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores relation between terminals and applications.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `appparam`
--

DROP TABLE IF EXISTS `appparam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appparam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` char(128) NOT NULL DEFAULT 'New App Param',
  `App` bigint(20) NOT NULL,
  `Description` varchar(128) DEFAULT NULL,
  `EntityLevel` bigint(20) NOT NULL,
  `AppParamFormat` bigint(20) NOT NULL,
  `DefaultValue` varchar(512) DEFAULT NULL,
  `Modifiable` tinyint(4) NOT NULL DEFAULT '1',
  `Action` bigint(20) NOT NULL DEFAULT '1',
  `Min` int(11) NOT NULL DEFAULT '0',
  `Max` int(11) NOT NULL DEFAULT '0',
  `Restrict` varchar(256) DEFAULT NULL,
  `Regex` varchar(256) DEFAULT NULL,
  `ForceUpdate` tinyint(4) NOT NULL DEFAULT '0',
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `AppParam_fk0` (`App`),
  KEY `AppParam_fk1` (`EntityLevel`),
  KEY `AppParam_fk2` (`AppParamFormat`),
  KEY `AppParam_fk3` (`Action`),
  CONSTRAINT `AppParam_fk0` FOREIGN KEY (`App`) REFERENCES `app` (`id`),
  CONSTRAINT `AppParam_fk1` FOREIGN KEY (`EntityLevel`) REFERENCES `entitylevel` (`id`),
  CONSTRAINT `AppParam_fk2` FOREIGN KEY (`AppParamFormat`) REFERENCES `appparamformat` (`id`),
  CONSTRAINT `AppParam_fk3` FOREIGN KEY (`Action`) REFERENCES `action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `appparamformat`
--

DROP TABLE IF EXISTS `appparamformat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appparamformat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(128) NOT NULL DEFAULT 'New App Param Format',
  `Value` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `appprofile`
--

DROP TABLE IF EXISTS `appprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appprofile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(128) NOT NULL DEFAULT 'New App Profile',
  `App` bigint(20) NOT NULL,
  `Active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `AppProfile_fk0` (`App`),
  CONSTRAINT `AppProfile_fk0` FOREIGN KEY (`App`) REFERENCES `app` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `appprofilefilevalue`
--

DROP TABLE IF EXISTS `appprofilefilevalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appprofilefilevalue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `AppProfile` bigint(20) NOT NULL,
  `AppFile` bigint(20) NOT NULL,
  `DefaultValue` varchar(256) NOT NULL DEFAULT 'filename',
  `ForceUpdate` tinyint(4) NOT NULL DEFAULT '0',
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `AppProfileFileValue_fk0` (`AppProfile`),
  KEY `AppProfileFileValue_fk1` (`AppFile`),
  CONSTRAINT `AppProfileFileValue_fk0` FOREIGN KEY (`AppProfile`) REFERENCES `appprofile` (`id`),
  CONSTRAINT `AppProfileFileValue_fk1` FOREIGN KEY (`AppFile`) REFERENCES `appfile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `appprofileparamvalue`
--

DROP TABLE IF EXISTS `appprofileparamvalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appprofileparamvalue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `AppProfile` bigint(20) NOT NULL,
  `AppParam` bigint(20) NOT NULL,
  `DefaultValue` varchar(512) DEFAULT NULL,
  `ForceUpdate` tinyint(4) NOT NULL DEFAULT '0',
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `AppProfileParamValue_fk0` (`AppProfile`),
  KEY `AppProfileParamValue_fk1` (`AppParam`),
  CONSTRAINT `AppProfileParamValue_fk0` FOREIGN KEY (`AppProfile`) REFERENCES `appprofile` (`id`),
  CONSTRAINT `AppProfileParamValue_fk1` FOREIGN KEY (`AppParam`) REFERENCES `appparam` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audit_log`
--

DROP TABLE IF EXISTS `audit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `terminal` bigint(20) DEFAULT NULL COMMENT 'Terminal where the action occurred.',
  `user` bigint(20) DEFAULT NULL COMMENT 'User who performed the action.',
  `action` int(2) NOT NULL COMMENT 'Action''s category.',
  `details` text NOT NULL COMMENT 'Log details.',
  `occurred_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Action''s occurred date.',
  PRIMARY KEY (`id`),
  KEY `FKAudit_Log646650` (`user`),
  KEY `FKAudit_Log776337` (`terminal`),
  CONSTRAINT `FKAudit_Log646650` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `FKAudit_Log776337` FOREIGN KEY (`terminal`) REFERENCES `terminal` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores system logs.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ba_data`
--

DROP TABLE IF EXISTS `ba_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ba_data` (
  `data` bigint(20) NOT NULL COMMENT 'Account''s number.',
  `bank` bigint(20) NOT NULL COMMENT 'Account''s bank.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Bank Account''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Bank Account''s last update date.',
  PRIMARY KEY (`data`),
  KEY `FKBA_Data655274` (`data`),
  KEY `FKBA_Data535758` (`bank`),
  CONSTRAINT `FKBA_Data535758` FOREIGN KEY (`bank`) REFERENCES `bank` (`id`),
  CONSTRAINT `FKBA_Data655274` FOREIGN KEY (`data`) REFERENCES `data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores bank account information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bank`
--

DROP TABLE IF EXISTS `bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `routing_number` int(9) NOT NULL COMMENT 'Bank''s routing number.',
  `name` varchar(100) NOT NULL COMMENT 'Bank''s name.',
  `address` bigint(20) NOT NULL COMMENT 'Bank''s address.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Bank''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Bank''s last update date.',
  PRIMARY KEY (`id`),
  KEY `FKBank665141` (`address`),
  CONSTRAINT `FKBank665141` FOREIGN KEY (`address`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores bank information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ca_data`
--

DROP TABLE IF EXISTS `ca_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ca_data` (
  `data` bigint(20) NOT NULL COMMENT 'Card number.',
  `cardholder_name` varchar(100) DEFAULT NULL COMMENT 'Card holder name.',
  `exp_date` varchar(4) NOT NULL COMMENT 'Card''s expiration date.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Card Account''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Card Account''s last update date.',
  PRIMARY KEY (`data`),
  KEY `FKCA_Data159843` (`data`),
  CONSTRAINT `FKCA_Data159843` FOREIGN KEY (`data`) REFERENCES `data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores credit card information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cakey`
--

DROP TABLE IF EXISTS `cakey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cakey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `payment_system` bigint(20) NOT NULL COMMENT 'Data elements that describe a Payment Application.  ',
  `index` varchar(2) NOT NULL COMMENT 'Represents the CA Key index as defined by EMV.',
  `hash_algorithm` varchar(2) NOT NULL COMMENT 'Represents the Public Key Algorithm value associate to a specific CA Key',
  `public_key_algorithm` varchar(2) NOT NULL COMMENT 'Represents the Public Key Algorithm value associate to a specific CA Key. ',
  `key_check_sum` varchar(40) NOT NULL COMMENT 'Represents the Key Check Sum calculated over the CA key.',
  `modulus` varchar(496) NOT NULL COMMENT 'Represents the CA Key modulus as defined by EMV. ',
  `exponent` varchar(6) NOT NULL COMMENT 'Represents the CA Key exponent as defined by EMV.',
  `version` int(11) NOT NULL COMMENT 'Certification Authority Key version.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update day.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `payment_system_index_unique` (`payment_system`,`index`),
  KEY `index` (`index`),
  KEY `FKCAKey361478` (`payment_system`),
  CONSTRAINT `FKCAKey361478` FOREIGN KEY (`payment_system`) REFERENCES `payment_system` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='Stores Certification Authority Keys.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cakey_profile`
--

DROP TABLE IF EXISTS `cakey_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cakey_profile` (
  `cakey` bigint(20) NOT NULL,
  `payment_system_profile` bigint(20) NOT NULL,
  PRIMARY KEY (`cakey`,`payment_system_profile`),
  KEY `FKCAKey_Prof592029` (`payment_system_profile`),
  KEY `FKCAKey_Prof27687` (`cakey`),
  CONSTRAINT `FKCAKey_Prof27687` FOREIGN KEY (`cakey`) REFERENCES `cakey` (`id`),
  CONSTRAINT `FKCAKey_Prof592029` FOREIGN KEY (`payment_system_profile`) REFERENCES `payment_system_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cka_data`
--

DROP TABLE IF EXISTS `cka_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cka_data` (
  `data` bigint(20) NOT NULL COMMENT 'Check''s number.',
  `bank` bigint(20) NOT NULL COMMENT 'Check''s bank.',
  `number` int(4) NOT NULL COMMENT 'Check''s number.',
  `date` varchar(8) NOT NULL COMMENT 'Check''s date.',
  `memo` varchar(255) DEFAULT NULL COMMENT 'Check''s memo.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Check Account''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Check Account''s last update date.',
  PRIMARY KEY (`data`),
  KEY `FKCKA_Data429466` (`data`),
  KEY `FKCKA_Data309950` (`bank`),
  CONSTRAINT `FKCKA_Data309950` FOREIGN KEY (`bank`) REFERENCES `bank` (`id`),
  CONSTRAINT `FKCKA_Data429466` FOREIGN KEY (`data`) REFERENCES `data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores check information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `first_name` varchar(100) NOT NULL DEFAULT '' COMMENT 'Client''s first name.',
  `last_name` varchar(100) NOT NULL DEFAULT '' COMMENT 'Client''s last name.',
  `middle_initial` varchar(2) DEFAULT '' COMMENT 'Client''s middle name.',
  `email` varchar(50) DEFAULT NULL COMMENT 'Client''s email.',
  `company` varchar(100) DEFAULT '' COMMENT 'Client''s company.',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Flag which says if the client is active or not.',
  `dl_data` bigint(20) DEFAULT NULL COMMENT 'Client''s driver license.',
  `ss_data` bigint(20) DEFAULT NULL COMMENT 'Client''s social security.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Client''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Client''s last update date.',
  `entity` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKClient427225` (`dl_data`),
  KEY `FKClient428341` (`ss_data`),
  CONSTRAINT `FKClient427225` FOREIGN KEY (`dl_data`) REFERENCES `dl_data` (`data`),
  CONSTRAINT `FKClient428341` FOREIGN KEY (`ss_data`) REFERENCES `ss_data` (`data`)
) ENGINE=InnoDB AUTO_INCREMENT=499 DEFAULT CHARSET=utf8 COMMENT='Stores clients information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `client_address`
--

DROP TABLE IF EXISTS `client_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_address` (
  `client` bigint(20) NOT NULL COMMENT 'Client''s identification.',
  `address` bigint(20) NOT NULL COMMENT 'Client''s address.',
  PRIMARY KEY (`client`,`address`),
  KEY `FKClient_Add369996` (`address`),
  KEY `FKClient_Add459902` (`client`),
  CONSTRAINT `FKClient_Add369996` FOREIGN KEY (`address`) REFERENCES `address` (`id`),
  CONSTRAINT `FKClient_Add459902` FOREIGN KEY (`client`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores relation between client and address.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `client_telephone`
--

DROP TABLE IF EXISTS `client_telephone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_telephone` (
  `telephone` bigint(20) NOT NULL,
  `client` bigint(20) NOT NULL,
  PRIMARY KEY (`telephone`,`client`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `name` varchar(100) NOT NULL COMMENT 'Country''s name.',
  `abbreviation` varchar(3) NOT NULL COMMENT 'Country''s abbreviation.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8 COMMENT='Stores countries.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data`
--

DROP TABLE IF EXISTS `data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `data` varchar(64) NOT NULL COMMENT 'Data.',
  `key_serial_number` varchar(200) DEFAULT NULL COMMENT 'Key''s serial number.',
  `key_id` int(2) DEFAULT NULL COMMENT 'Key which is used to encrypt the data.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=508689 DEFAULT CHARSET=utf8 COMMENT='Stores sensitive information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `serial_number` char(64) NOT NULL DEFAULT '',
  `last_contact` datetime NOT NULL DEFAULT '1980-01-01 00:00:00',
  `active` tinyint(4) NOT NULL DEFAULT '1',
  `last_download` datetime NOT NULL DEFAULT '1980-01-01 00:00:00',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `SerialNum_UNIQUE` (`serial_number`)
) ENGINE=InnoDB AUTO_INCREMENT=2487 DEFAULT CHARSET=utf8 COMMENT='Stores Device information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dl_data`
--

DROP TABLE IF EXISTS `dl_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_data` (
  `data` bigint(20) NOT NULL COMMENT 'Driver license''s number.',
  `exp_date` varchar(8) NOT NULL COMMENT 'Driver license''s expiration date.',
  `state` bigint(20) NOT NULL COMMENT 'Driver license''s state.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Driver License''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Driver License''s last update date.',
  PRIMARY KEY (`data`),
  KEY `FKDL_Data622228` (`state`),
  KEY `FKDL_Data386203` (`data`),
  CONSTRAINT `FKDL_Data386203` FOREIGN KEY (`data`) REFERENCES `data` (`id`),
  CONSTRAINT `FKDL_Data622228` FOREIGN KEY (`state`) REFERENCES `state` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores driver licenses.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entity`
--

DROP TABLE IF EXISTS `entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `entity_id` varchar(13) DEFAULT NULL COMMENT 'Entity identification.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Organization''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Organization''s last update date.',
  `parent` bigint(20) DEFAULT NULL,
  `path` text,
  `entitytype` tinyint(2) DEFAULT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FKEntity270942` (`parent`),
  CONSTRAINT `FKEntity270942` FOREIGN KEY (`parent`) REFERENCES `entity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2491 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entity_copy`
--

DROP TABLE IF EXISTS `entity_copy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity_copy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `entity_id` varchar(13) DEFAULT NULL COMMENT 'Entity identification.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Organization''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Organization''s last update date.',
  `parent` bigint(20) DEFAULT NULL,
  `path` text,
  `entitytype` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKEntity270942` (`parent`),
  CONSTRAINT `entity_copy_ibfk_1` FOREIGN KEY (`parent`) REFERENCES `entity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2441 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entitylevel`
--

DROP TABLE IF EXISTS `entitylevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entitylevel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(128) NOT NULL DEFAULT 'New Entity Level',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `application` bigint(20) NOT NULL COMMENT 'Application''s file.',
  `filename` varchar(100) NOT NULL COMMENT 'File''s name.',
  `file` mediumblob COMMENT 'File''s data.',
  `size` bigint(20) NOT NULL DEFAULT '0' COMMENT 'File''s size.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  PRIMARY KEY (`id`),
  KEY `FKFile601178` (`application`),
  CONSTRAINT `FKFile601178` FOREIGN KEY (`application`) REFERENCES `application` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores application''s files.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `forgotpassword`
--

DROP TABLE IF EXISTS `forgotpassword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forgotpassword` (
  `id` int(11) NOT NULL,
  `email_id` varchar(45) NOT NULL,
  `token_password` int(11) NOT NULL,
  `sent_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `name` varchar(100) NOT NULL COMMENT 'Group''s name.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Group''s description.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Stores parameter''s group.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `host`
--

DROP TABLE IF EXISTS `host`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `host` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `enum_key` varchar(100) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT '',
  `in_queue` varchar(255) NOT NULL DEFAULT '',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`enum_key`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `host_mode_operation`
--

DROP TABLE IF EXISTS `host_mode_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `host_mode_operation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `host` bigint(11) NOT NULL COMMENT 'Host''s identification.',
  `mode` int(2) NOT NULL COMMENT 'Mode''s identification.',
  `operation` int(2) NOT NULL COMMENT 'Operation''s identification.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Host Mode Operation ''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Host Mode Operation''s last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mode` (`mode`,`host`,`operation`),
  KEY `host` (`host`),
  CONSTRAINT `host_mode_operation_ibfk_1` FOREIGN KEY (`host`) REFERENCES `host` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='Stores relation between Host, Mode and Operation.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `host_setting_value`
--

DROP TABLE IF EXISTS `host_setting_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `host_setting_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `host` bigint(11) NOT NULL COMMENT 'Host identification.',
  `name` varchar(255) NOT NULL DEFAULT '',
  `enum_key` varchar(100) NOT NULL DEFAULT '',
  `value` text NOT NULL COMMENT 'Host setting value.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Host Setting value''''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Host Setting value''''s last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `host` (`host`,`enum_key`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='Stores host setting''s value.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `key_block`
--

DROP TABLE IF EXISTS `key_block`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `key_block` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `encrypted_key` varchar(512) NOT NULL COMMENT 'Encripted key',
  `type` varchar(2) NOT NULL COMMENT 'Key block''s type',
  `length` varchar(2) NOT NULL COMMENT 'Key block''s lenght',
  `kcv` varchar(6) NOT NULL COMMENT 'Key block''s kcv',
  `ksn` varchar(20) DEFAULT NULL COMMENT 'Key block''s ksn',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  `version` int(11) NOT NULL COMMENT 'Key block''s version.',
  `signature` varchar(512) NOT NULL COMMENT 'Key block signature',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores Key Block.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mcc`
--

DROP TABLE IF EXISTS `mcc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mcc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `mcc` varchar(4) NOT NULL COMMENT 'MCC''s name.',
  `edited_description` varchar(255) NOT NULL COMMENT 'MCC''s description.',
  `combined_description` varchar(255) NOT NULL,
  `usda_description` varchar(255) NOT NULL,
  `irs_description` varchar(255) NOT NULL,
  `irs_reportable` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=982 DEFAULT CHARSET=utf8 COMMENT='Stores MCC which represents the Merchant Category Code to be supported by the terminals.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant`
--

DROP TABLE IF EXISTS `merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant` (
  `id` bigint(20) NOT NULL COMMENT 'Database identification.',
  `dba` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKMerchant915144` (`id`),
  CONSTRAINT `FKMerchant915144` FOREIGN KEY (`id`) REFERENCES `entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores Merchant information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_address`
--

DROP TABLE IF EXISTS `merchant_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_address` (
  `merchant` bigint(20) NOT NULL,
  `address` bigint(20) NOT NULL,
  PRIMARY KEY (`address`,`merchant`),
  KEY `merchant` (`merchant`),
  CONSTRAINT `merchant_address_ibfk_1` FOREIGN KEY (`merchant`) REFERENCES `merchant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `merchant_address_ibfk_2` FOREIGN KEY (`address`) REFERENCES `address` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_host`
--

DROP TABLE IF EXISTS `merchant_host`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_host` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `host` bigint(11) NOT NULL COMMENT 'Host identification.',
  `merchant` bigint(20) NOT NULL COMMENT 'Merchant identification.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Merchant Host''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Merchant host''s last update date.',
  `setting_merchant_number` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Merchant_Host_Unique` (`merchant`,`host`),
  KEY `host` (`host`),
  CONSTRAINT `merchant_host_ibfk_1` FOREIGN KEY (`host`) REFERENCES `host` (`id`),
  CONSTRAINT `merchant_host_ibfk_2` FOREIGN KEY (`merchant`) REFERENCES `merchant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='Stores the merchant''s settings.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_host_mode_operation`
--

DROP TABLE IF EXISTS `merchant_host_mode_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_host_mode_operation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `merchant` bigint(20) NOT NULL COMMENT 'Merchant identification.',
  `host_mode_operation` bigint(20) NOT NULL COMMENT 'Host_Mode_Operation ''S identification.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Merchant host mode operation''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Merchant host mode operation''s last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `merchant_hmo_unique` (`merchant`,`host_mode_operation`),
  KEY `FKMerchant_H775539` (`host_mode_operation`),
  KEY `FKMerchant_H610816` (`merchant`),
  CONSTRAINT `FKMerchant_H610816` FOREIGN KEY (`merchant`) REFERENCES `merchant` (`id`),
  CONSTRAINT `FKMerchant_H775539` FOREIGN KEY (`host_mode_operation`) REFERENCES `host_mode_operation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8 COMMENT='Stores the mode and operation that a merchant support by host.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_host_setting`
--

DROP TABLE IF EXISTS `merchant_host_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_host_setting` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `enum_key` varchar(100) NOT NULL DEFAULT '',
  `host` bigint(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `enum_key` (`enum_key`,`host`),
  KEY `host` (`host`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_host_setting_value`
--

DROP TABLE IF EXISTS `merchant_host_setting_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_host_setting_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `value` text COMMENT 'Merchant_Host_Setting''''s value.',
  `merchant_host` bigint(20) NOT NULL COMMENT 'Merchant Host''s identification.',
  `merchant_host_setting` bigint(11) NOT NULL COMMENT 'Merchant_Host_Setting identification.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Merchant Host Setting value ''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Merchant Host Setting value''s last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Merchant_Host_Setting_Unique` (`merchant_host`,`merchant_host_setting`),
  KEY `FKMerchant_H658318` (`merchant_host`),
  KEY `merchant_host_setting_value_ibfk_3` (`merchant_host_setting`),
  CONSTRAINT `merchant_host_setting_value_ibfk_2` FOREIGN KEY (`merchant_host`) REFERENCES `merchant_host` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `merchant_host_setting_value_ibfk_3` FOREIGN KEY (`merchant_host_setting`) REFERENCES `merchant_host_setting` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=556 DEFAULT CHARSET=utf8 COMMENT='Stores merchant_host setting''s value.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_setting_value`
--

DROP TABLE IF EXISTS `merchant_setting_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_setting_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `value` varchar(200) NOT NULL COMMENT 'Setting''s value.',
  `merchant_setting` int(2) NOT NULL COMMENT 'Setting identification.',
  `merchant` bigint(20) NOT NULL COMMENT 'Merchant identification.',
  `default_value` varchar(200) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Merchant setting''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Merchant setting''s last update date.',
  `group` int(2) NOT NULL DEFAULT '0' COMMENT 'Merchant setting'' group.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Merchant_MerchantSetting_Unique` (`merchant`,`merchant_setting`),
  KEY `FKMerchant_S782124` (`merchant`),
  CONSTRAINT `FKMerchant_S782124` FOREIGN KEY (`merchant`) REFERENCES `merchant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores merchant setting''s value.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `merchant_telephone`
--

DROP TABLE IF EXISTS `merchant_telephone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant_telephone` (
  `merchant` bigint(20) NOT NULL,
  `telephone` bigint(20) NOT NULL,
  PRIMARY KEY (`merchant`,`telephone`),
  KEY `FKMerchant_T356121` (`merchant`),
  CONSTRAINT `FKMerchant_T356121` FOREIGN KEY (`merchant`) REFERENCES `merchant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `name` varchar(100) NOT NULL COMMENT 'Model''s name.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Model''s description.',
  `multi_app` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Flag which say if the model supports multiple applications.',
  `max_apps` int(10) DEFAULT NULL COMMENT 'Maximum number of applications that the model supports.',
  `max_keys` int(10) DEFAULT NULL COMMENT 'Maximum number of keys that the model supports.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Stores terminal''s model.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `model_application`
--

DROP TABLE IF EXISTS `model_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model_application` (
  `model` bigint(20) NOT NULL COMMENT 'Model.',
  `application` bigint(20) NOT NULL COMMENT 'Application.',
  PRIMARY KEY (`model`,`application`),
  KEY `FKModel_Appl808718` (`application`),
  KEY `FKModel_Appl629451` (`model`),
  CONSTRAINT `FKModel_Appl629451` FOREIGN KEY (`model`) REFERENCES `model` (`id`),
  CONSTRAINT `FKModel_Appl808718` FOREIGN KEY (`application`) REFERENCES `application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores the relation between models and applications.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `model_terminal_profile`
--

DROP TABLE IF EXISTS `model_terminal_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model_terminal_profile` (
  `model` bigint(20) NOT NULL COMMENT 'Model.',
  `terminal_profile` bigint(20) NOT NULL COMMENT 'Terminal profile.',
  PRIMARY KEY (`model`,`terminal_profile`),
  KEY `FKModel_Term410793` (`terminal_profile`),
  KEY `FKModel_Term904222` (`model`),
  CONSTRAINT `FKModel_Term410793` FOREIGN KEY (`terminal_profile`) REFERENCES `terminal_profile` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKModel_Term904222` FOREIGN KEY (`model`) REFERENCES `model` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores the relation between model and its terminal profiles.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization` (
  `id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `parameter`
--

DROP TABLE IF EXISTS `parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parameter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `application` bigint(20) NOT NULL COMMENT 'Parameter''s application.',
  `group` bigint(20) NOT NULL COMMENT 'Parameter''s group.',
  `format` int(2) NOT NULL COMMENT 'Parameter''s format.',
  `name` varchar(100) NOT NULL COMMENT 'Parameter''s name.',
  `default_value` varchar(255) NOT NULL COMMENT 'Parameter''s default value.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Parameter''s description.',
  `application_wide` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Flag which says if the parameter is red_only.',
  `convert_to_hex` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Flag which say if the parameter should be converted to hex.',
  `min_length` int(10) DEFAULT NULL COMMENT 'Parameter''s minimun length.',
  `max_length` int(10) DEFAULT NULL COMMENT 'Parameter''s maximum length.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  `additional_data` text NOT NULL COMMENT 'Parameter''s additional data.',
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_application_unique` (`name`,`application`),
  KEY `FKParameter829734` (`group`),
  KEY `FKParameter872372` (`application`),
  CONSTRAINT `FKParameter829734` FOREIGN KEY (`group`) REFERENCES `group` (`id`),
  CONSTRAINT `FKParameter872372` FOREIGN KEY (`application`) REFERENCES `application` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='Stores application''s parameters.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_product`
--

DROP TABLE IF EXISTS `payment_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `payment_system` bigint(20) NOT NULL COMMENT 'Product''s payment system.',
  `aid` varchar(32) NOT NULL COMMENT 'Represents the Application Identifier as defined by EMV and assigned by ISO to identify EMV card applications.',
  `name` varchar(100) NOT NULL COMMENT 'Product''s name.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Product''s description.',
  `app_version` varchar(4) NOT NULL COMMENT 'Represents the Card Application Version as supported by the terminal for the given Payment Application. ',
  `floor_limit` varchar(8) NOT NULL COMMENT 'Represents Terminal Floor Limit as defined by EMV and used in terminal risk management. ',
  `tprs` varchar(2) NOT NULL COMMENT 'Represents the Target Percentage used for Random Selection, EMV attribute. This value is used by the terminal during the risk management assessment',
  `tvbrs` varchar(8) NOT NULL COMMENT 'Represents the Threshold Value for Based Random Selection, EMV attribute. This value is used by the terminal during the risk management assessment. ',
  `mtpbrs` varchar(2) NOT NULL COMMENT 'Represents the Maximum Target Percentage for Based Random Selection, EMV attribute. This value is used by the terminal during the risk management assessment. ',
  `partial_selection` varchar(2) NOT NULL COMMENT 'Represents the Partial Selection Indicator. This attributes determines if the Partial Selection as defined by EMV is supported for the associated Payment Application. This is a 1 byte binary encoded field. Possible values:  \n00 – Not supported  \n01 – Supported    ',
  `terminal_app_label` varchar(16) NOT NULL COMMENT 'Represents the terminal default Application Label as defined by EMV. ',
  `fallback_control` varchar(2) NOT NULL COMMENT ' Represents the application fallback control. Allows enabling or disabling fallback for specific applications.    ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `aid` (`aid`),
  KEY `FKPayment_Pr919216` (`payment_system`),
  CONSTRAINT `FKPayment_Pr919216` FOREIGN KEY (`payment_system`) REFERENCES `payment_system` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='Stores Products (Payment Applications). Ex Maestro, Electron, etc';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_product_profile`
--

DROP TABLE IF EXISTS `payment_product_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_product_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `payment_product` bigint(20) NOT NULL,
  `payment_system_profile` bigint(20) NOT NULL,
  `app_version` varchar(4) NOT NULL COMMENT 'Represents the Card Application Version as supported by the terminal for the given Payment Application. ',
  `floor_limit` varchar(8) NOT NULL COMMENT 'Represents Terminal Floor Limit as defined by EMV and used in terminal risk management. ',
  `tprs` varchar(2) NOT NULL COMMENT 'Represents the Target Percentage used for Random Selection, EMV attribute. This value is used by the terminal during the risk management assessment',
  `tvbrs` varchar(8) NOT NULL COMMENT 'Represents the Threshold Value for Based Random Selection, EMV attribute. This value is used by the terminal during the risk management assessment. ',
  `mtpbrs` varchar(2) NOT NULL COMMENT 'Represents the Maximum Target Percentage for Based Random Selection, EMV attribute. This value is used by the terminal during the risk management assessment. ',
  `partial_selection` varchar(2) NOT NULL COMMENT 'Represents the Partial Selection Indicator. This attributes determines if the Partial Selection as defined by EMV is supported for the associated Payment Application. This is a 1 byte binary encoded field. Possible values:  \n00 – Not supported  \n01 – Supported    ',
  `terminal_app_label` varchar(16) NOT NULL COMMENT 'Represents the terminal default Application Label as defined by EMV. ',
  `fallback_control` varchar(2) NOT NULL COMMENT ' Represents the application fallback control. Allows enabling or disabling fallback for specific applications.    ',
  `version` int(11) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  PRIMARY KEY (`id`),
  KEY `FKPayment_Pr382324` (`payment_system_profile`),
  KEY `FKPayment_Pr525489` (`payment_product`),
  CONSTRAINT `FKPayment_Pr382324` FOREIGN KEY (`payment_system_profile`) REFERENCES `payment_system_profile` (`id`),
  CONSTRAINT `FKPayment_Pr525489` FOREIGN KEY (`payment_product`) REFERENCES `payment_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_profile`
--

DROP TABLE IF EXISTS `payment_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `MCC` bigint(20) NOT NULL COMMENT 'Payment profile''s MCC.',
  `name` varchar(100) NOT NULL COMMENT 'Payment profile''s name.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Payment profile''s description.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FKPayment_Pr782559` (`MCC`),
  CONSTRAINT `FKPayment_Pr782559` FOREIGN KEY (`MCC`) REFERENCES `mcc` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Stores payment''s profiles.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_system`
--

DROP TABLE IF EXISTS `payment_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_system` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `rid` varchar(10) NOT NULL COMMENT 'Represents the Registered Identifier unique per Payment System and assigned by ISO.',
  `tac_default` varchar(255) NOT NULL COMMENT 'Represents the TAC Default to be supported by the terminals. ',
  `name` varchar(100) NOT NULL COMMENT 'Payment System''s name.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Payment System''s description.',
  `tac_denial` varchar(10) NOT NULL COMMENT 'Represents the TAC Denial to be supported by the terminals. ',
  `tac_online` varchar(10) NOT NULL COMMENT 'Represents the TAC Online to be supported by the terminals. ',
  `tcc` varchar(2) DEFAULT NULL COMMENT 'Represents the Transaction Category Code to be supported by the terminals. ',
  `default_tdol` varchar(255) NOT NULL COMMENT 'Represents the Default TDOL as defined by EMV. ',
  `default_ddol` varchar(255) NOT NULL COMMENT 'Represents the Default DDOL as defined by EMV. ',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `rid` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT=' Stores a Payment System (for example AMEX or MasterCard).   ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_system_profile`
--

DROP TABLE IF EXISTS `payment_system_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_system_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `payment_system` bigint(20) NOT NULL,
  `payment_profile` bigint(20) NOT NULL,
  `tac_default` varchar(255) NOT NULL COMMENT 'Represents the TAC Default to be supported by the terminals. ',
  `tac_denial` varchar(10) NOT NULL COMMENT 'Represents the TAC Denial to be supported by the terminals. ',
  `tac_online` varchar(10) NOT NULL COMMENT 'Represents the TAC Online to be supported by the terminals. ',
  `tcc` varchar(2) DEFAULT NULL COMMENT 'Represents the Transaction Category Code to be supported by the terminals. ',
  `default_tdol` varchar(255) NOT NULL COMMENT 'Represents the Default TDOL as defined by EMV. ',
  `default_ddol` varchar(255) NOT NULL COMMENT 'Represents the Default DDOL as defined by EMV. ',
  `version` int(11) NOT NULL COMMENT 'Payment System''s version.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update.',
  PRIMARY KEY (`id`),
  KEY `FKPayment_Sy296683` (`payment_system`),
  KEY `FKPayment_Sy527788` (`payment_profile`),
  CONSTRAINT `FKPayment_Sy296683` FOREIGN KEY (`payment_system`) REFERENCES `payment_system` (`id`),
  CONSTRAINT `FKPayment_Sy527788` FOREIGN KEY (`payment_profile`) REFERENCES `payment_profile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `region` (
  `id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `name` varchar(100) NOT NULL COMMENT 'Role''s name.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Role''s description.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='Stores roles.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `role` bigint(20) NOT NULL COMMENT 'Role.',
  `permission` int(2) DEFAULT NULL COMMENT 'Role''s permissions.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_permission_unique` (`role`,`permission`),
  KEY `FKRole_Permi314838` (`role`),
  CONSTRAINT `FKRole_Permi314838` FOREIGN KEY (`role`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=472 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rule`
--

DROP TABLE IF EXISTS `rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `time_interval` bigint(20) NOT NULL COMMENT 'Schedule''s time interval.',
  `schedule_group` bigint(20) NOT NULL COMMENT 'Schedule''s group.',
  `day` int(2) NOT NULL DEFAULT '7' COMMENT 'Schedule''s day.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `day_time_schedule_unique` (`day`,`time_interval`,`schedule_group`),
  KEY `FKRule777376` (`time_interval`),
  KEY `FKRule843781` (`schedule_group`),
  CONSTRAINT `FKRule777376` FOREIGN KEY (`time_interval`) REFERENCES `time_interval` (`id`),
  CONSTRAINT `FKRule843781` FOREIGN KEY (`schedule_group`) REFERENCES `schedule_group` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='Stores schedule rules.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schedule_group`
--

DROP TABLE IF EXISTS `schedule_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `name` varchar(100) NOT NULL COMMENT 'Schedule group''s name.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Schedule group''s description.',
  `days` int(11) NOT NULL DEFAULT '127',
  `window_start` datetime NOT NULL DEFAULT '1980-01-01 00:00:00',
  `window_finish` datetime NOT NULL DEFAULT '1980-01-01 23:59:59',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='Stores schedule groups.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schedulegroup`
--

DROP TABLE IF EXISTS `schedulegroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedulegroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(128) NOT NULL DEFAULT 'New Schedule Group',
  `Days` int(11) NOT NULL DEFAULT '127',
  `WindowStart` datetime NOT NULL DEFAULT '1980-01-01 00:00:00',
  `WindowFinish` datetime NOT NULL DEFAULT '1980-01-01 23:59:59',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `user` bigint(20) NOT NULL COMMENT 'User who has the session.',
  `token` varchar(50) NOT NULL COMMENT 'Session''s security token.',
  `session_info` int(11) DEFAULT NULL COMMENT 'Session information.',
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Session''s last updated.',
  PRIMARY KEY (`id`),
  KEY `FKSession78200` (`user`),
  CONSTRAINT `FKSession78200` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores session information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `setting` int(2) NOT NULL COMMENT 'Settings'' name.',
  `value` varchar(200) NOT NULL COMMENT 'Settings'' value.',
  `group` int(2) NOT NULL DEFAULT '0' COMMENT 'Settings'' group.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `setting_unique` (`setting`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `signature`
--

DROP TABLE IF EXISTS `signature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `signature` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `image` mediumblob NOT NULL COMMENT 'Signature''s image.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Stores signatures.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `software`
--

DROP TABLE IF EXISTS `software`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `software` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `filename` varchar(100) NOT NULL COMMENT 'Software''s file name.',
  `file` mediumblob NOT NULL COMMENT 'Software data.',
  `size` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Software size.',
  `version` varchar(25) NOT NULL COMMENT 'Software''s version.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores application''s software.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ss_data`
--

DROP TABLE IF EXISTS `ss_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ss_data` (
  `data` bigint(20) NOT NULL COMMENT 'SS number.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'SS''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'SS''s last update date.',
  PRIMARY KEY (`data`),
  KEY `FKSS_Data328191` (`data`),
  CONSTRAINT `FKSS_Data328191` FOREIGN KEY (`data`) REFERENCES `data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores ss information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `country` bigint(20) NOT NULL COMMENT 'State''s country.',
  `name` varchar(100) NOT NULL COMMENT 'State''s name.',
  `abbreviation` varchar(2) NOT NULL COMMENT 'State abbreviation.',
  PRIMARY KEY (`id`),
  KEY `FKState444258` (`country`),
  CONSTRAINT `FKState444258` FOREIGN KEY (`country`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='Stores states.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `telephone`
--

DROP TABLE IF EXISTS `telephone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `telephone` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `number` varchar(30) NOT NULL COMMENT 'Telephone''s number.',
  `type` int(2) NOT NULL COMMENT 'Telephone''s type. Ex: cellular, home.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='Stores all telephones.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `terminal`
--

DROP TABLE IF EXISTS `terminal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `serial_number` varchar(255) NOT NULL DEFAULT '' COMMENT 'Terminal''s serial number.',
  `last_contact` datetime NOT NULL DEFAULT '1980-01-01 00:00:00',
  `last_download` datetime NOT NULL DEFAULT '1980-01-01 00:00:00',
  `schedule_group` bigint(20) DEFAULT NULL,
  `model` bigint(20) DEFAULT NULL COMMENT 'Terminal''s model.',
  `description` varchar(128) NOT NULL DEFAULT 'new terminal',
  `active` tinyint(4) NOT NULL DEFAULT '1',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `serial_number` (`serial_number`),
  KEY `Terminal_fk0` (`schedule_group`),
  KEY `Terminal_fk1_idx` (`schedule_group`,`id`),
  KEY `Terminal_fk13_idx` (`model`),
  CONSTRAINT `Terminal_fk12` FOREIGN KEY (`schedule_group`) REFERENCES `schedule_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Terminal_fk13` FOREIGN KEY (`model`) REFERENCES `model` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2488 DEFAULT CHARSET=utf8 COMMENT='Stores terminals.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `terminal_application_parameter`
--

DROP TABLE IF EXISTS `terminal_application_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal_application_parameter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `parameter` bigint(20) NOT NULL COMMENT 'Parameter.',
  `application_package` bigint(20) NOT NULL COMMENT 'Terminal application.',
  `value` varchar(255) NOT NULL COMMENT 'Parameter''s value.',
  `version` int(11) NOT NULL COMMENT 'Parameter''s version.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `terminal_application_parameter_unique` (`parameter`,`application_package`),
  KEY `FKTerminal_A858381` (`parameter`),
  KEY `FKTerminal_A997671` (`application_package`),
  CONSTRAINT `FKTerminal_A858381` FOREIGN KEY (`parameter`) REFERENCES `parameter` (`id`),
  CONSTRAINT `FKTerminal_A997671` FOREIGN KEY (`application_package`) REFERENCES `application_package` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores parameters''s value for an application in a terminal.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `terminal_host`
--

DROP TABLE IF EXISTS `terminal_host`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal_host` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `host` bigint(11) NOT NULL COMMENT 'Host identification.',
  `terminal` bigint(20) NOT NULL COMMENT 'Terminal identification.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Terminal host''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Terminal host''s last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Terminal_Host_Unique` (`terminal`,`host`),
  KEY `FKTerminal_H294843` (`terminal`),
  KEY `host` (`host`),
  CONSTRAINT `FKTerminal_H294843` FOREIGN KEY (`terminal`) REFERENCES `terminal` (`id`),
  CONSTRAINT `terminal_host_ibfk_1` FOREIGN KEY (`host`) REFERENCES `host` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='Stores terminal host relation.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `terminal_host_setting`
--

DROP TABLE IF EXISTS `terminal_host_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal_host_setting` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `enum_key` varchar(100) NOT NULL DEFAULT '',
  `host` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `enum_key` (`enum_key`,`host`),
  KEY `host` (`host`),
  CONSTRAINT `terminal_host_setting_ibfk_1` FOREIGN KEY (`host`) REFERENCES `host` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `terminal_host_setting_value`
--

DROP TABLE IF EXISTS `terminal_host_setting_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal_host_setting_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `terminal_host` bigint(20) NOT NULL COMMENT 'Terminal host identification.',
  `terminal_host_setting` bigint(11) NOT NULL COMMENT 'Terminal host settings identification.',
  `value` text COMMENT 'Terminal host setting''s value.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Terminal host setting value''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Terminal host setting value''s last update date.',
  PRIMARY KEY (`id`),
  KEY `FKTerminal_H422155` (`terminal_host`),
  KEY `terminal_host_setting` (`terminal_host_setting`),
  CONSTRAINT `terminal_host_setting_value_ibfk_2` FOREIGN KEY (`terminal_host_setting`) REFERENCES `terminal_host_setting` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=538 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `terminal_profile`
--

DROP TABLE IF EXISTS `terminal_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `name` varchar(100) NOT NULL COMMENT 'Terminal profile''s name.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Terminal profile''s description.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores terminal''s profiles.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `terminal_profile_application`
--

DROP TABLE IF EXISTS `terminal_profile_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal_profile_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `terminal_profile` bigint(20) NOT NULL COMMENT 'Terminal profile.',
  `application` bigint(20) NOT NULL COMMENT 'Application.',
  `payment_profile` bigint(20) DEFAULT NULL COMMENT 'Payment profile.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `terminal_profile_application_unique` (`terminal_profile`,`application`),
  KEY `FKTerminal_P720687` (`terminal_profile`),
  KEY `FKTerminal_P802906` (`payment_profile`),
  KEY `FKTerminal_P406525` (`application`),
  CONSTRAINT `FKTerminal_P406525` FOREIGN KEY (`application`) REFERENCES `application` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKTerminal_P720687` FOREIGN KEY (`terminal_profile`) REFERENCES `terminal_profile` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKTerminal_P802906` FOREIGN KEY (`payment_profile`) REFERENCES `payment_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores the relation between terminal profile and its applications.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `terminal_setting_value`
--

DROP TABLE IF EXISTS `terminal_setting_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal_setting_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(200) NOT NULL COMMENT 'Terminal setting''s value.',
  `terminal_setting` int(2) NOT NULL,
  `terminal` bigint(20) NOT NULL COMMENT 'Terminal identification.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Terminal Setting value''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Terminal setting value''s last update date.',
  `default_value` varchar(200) NOT NULL,
  `group` int(2) NOT NULL DEFAULT '0' COMMENT 'Terminal Setting'' group.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Terminal_TerminalSetting_Unique` (`terminal`,`terminal_setting`),
  KEY `FKTerminal_S370816` (`terminal`),
  CONSTRAINT `FKTerminal_S370816` FOREIGN KEY (`terminal`) REFERENCES `terminal` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `time_interval`
--

DROP TABLE IF EXISTS `time_interval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `time_interval` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `start` int(2) DEFAULT NULL COMMENT 'Start time.',
  `end` int(2) DEFAULT NULL COMMENT 'End time.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update date.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='Stores time intervals.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `time_performance`
--

DROP TABLE IF EXISTS `time_performance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `time_performance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rqst_in_front` int(10) NOT NULL,
  `rqst_in_core_queue` int(10) NOT NULL,
  `rqst_in_core` int(10) NOT NULL,
  `rqst_in_host_queue` int(10) NOT NULL,
  `rqst_in_host` int(10) NOT NULL,
  `rqst_in_processor` int(10) NOT NULL,
  `rsps_in_host` int(10) NOT NULL,
  `rsps_in_host_queue` int(10) NOT NULL,
  `rsps_in_core` int(10) NOT NULL,
  `rsps_in_core_queue` int(10) NOT NULL,
  `rsps_in_front` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57233 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `transaction_id` binary(16) NOT NULL COMMENT 'Transaction identification.',
  `host` int(2) NOT NULL,
  `mode` int(2) NOT NULL,
  `operation` int(2) NOT NULL,
  `amount` decimal(19,4) NOT NULL DEFAULT '0.0000' COMMENT 'Transaction''s amount.',
  `tip_amount` decimal(19,4) NOT NULL DEFAULT '0.0000' COMMENT 'Transaction''s tip amount.',
  `cash_back_amount` decimal(19,4) NOT NULL DEFAULT '0.0000' COMMENT 'Transaction''s cash back amount.',
  `total_amount` decimal(19,4) NOT NULL DEFAULT '0.0000' COMMENT 'Transaction''s total amount.',
  `authorized_amount` decimal(19,4) DEFAULT NULL COMMENT 'Amount tha was approved by the host.',
  `sequence_number` int(10) NOT NULL COMMENT 'Transaction''s sequence number.',
  `entry_method` int(2) DEFAULT NULL COMMENT 'Transaction''s entry method.',
  `currency` int(3) NOT NULL COMMENT 'Transaction''s currency.',
  `disposition` varchar(255) NOT NULL COMMENT 'Transaction''s disposition.',
  `approval_code` varchar(10) NOT NULL COMMENT 'Transaction''s approval code.',
  `approval_number` varchar(25) NOT NULL COMMENT 'Transaction''s approval number.',
  `retrieval_data` varchar(255) DEFAULT NULL COMMENT 'Transaction''s retrieval data.',
  `cvv_result` int(2) DEFAULT NULL COMMENT 'Transaction''s cvv result.',
  `avs_result` int(2) DEFAULT NULL COMMENT 'Transaction''s avs result.',
  `status` int(2) NOT NULL COMMENT 'Transaction''s status. Ex: approved, denied.',
  `finalized` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Transaction''s finalized status.',
  `voided` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Flag which says if the transaction was voided.',
  `original_transaction` bigint(20) DEFAULT NULL COMMENT 'Original transaction identification if this is a void or refund.',
  `account_suffix` varchar(4) NOT NULL COMMENT 'Transaction''s card suffix. First 4 digits and/or Last 4 digits.',
  `batch_number` int(9) DEFAULT NULL COMMENT 'Transaction''s batch number.',
  `invoice_number` varchar(20) DEFAULT NULL COMMENT 'Transaction''s invoice number.',
  `geo_location` varchar(60) DEFAULT NULL COMMENT 'Location where the transaction occurred at.',
  `card_brand` int(2) DEFAULT NULL COMMENT 'Transaction''s card brand',
  `terminal` bigint(20) NOT NULL COMMENT 'Terminal identification where the transaction occurred at.',
  `payment_product` bigint(20) DEFAULT NULL COMMENT 'Payment product identification.',
  `client` bigint(20) DEFAULT NULL COMMENT 'Transaction''s client identification.',
  `signature` bigint(20) DEFAULT NULL COMMENT 'Transaction''s signature.',
  `device` bigint(20) DEFAULT NULL COMMENT 'Device identification where the transaction occurred at.',
  `a_data` bigint(20) DEFAULT NULL COMMENT 'Account identification.',
  `time_performance` bigint(20) DEFAULT NULL COMMENT 'Transaction''s time performance.',
  `host_request` blob,
  `host_response` blob,
  `host_additional_info` blob,
  `event_data` varchar(255) DEFAULT NULL COMMENT 'The transaction event data.',
  `moto_mode` int(11) DEFAULT NULL COMMENT 'The transaction MOTO mode.',
  `cvm` int(11) DEFAULT NULL COMMENT 'The transaction CVM.',
  `capture_mode` int(11) DEFAULT NULL COMMENT 'The transaction capture mode.',
  `account_type` int(11) DEFAULT NULL COMMENT 'The transaction account type.',
  `emv_data` text COMMENT 'The transaction EMV data.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Transaction''s creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Transaction''s last update date.',
  `local_creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Transaction''s creation time.',
  `transmission_time` timestamp NULL DEFAULT NULL COMMENT 'Transaction''s transmission time.',
  `merchant_transaction_id` varchar(50) DEFAULT NULL COMMENT 'Merchant Transaction Identifier',
  `billing_city` varchar(255) DEFAULT NULL,
  `billing_country` varchar(20) DEFAULT NULL,
  `billing_email` varchar(255) DEFAULT NULL,
  `billing_phone_number` varchar(20) DEFAULT NULL,
  `billing_tate` varchar(20) DEFAULT NULL,
  `billing_street1` varchar(255) DEFAULT NULL,
  `billing_street2` varchar(255) DEFAULT NULL,
  `billing_zip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `transaction_id` (`transaction_id`),
  KEY `FKTransactio661879` (`time_performance`),
  KEY `FKTransactio138396` (`original_transaction`),
  KEY `FKTransactio300302` (`payment_product`),
  KEY `FKTransactio680460` (`signature`),
  KEY `FKTransactio560640` (`client`),
  KEY `FKTransactio846432` (`a_data`),
  KEY `FKTransactio671235` (`device`),
  KEY `FKTransactio266008` (`terminal`),
  CONSTRAINT `FKTransactio138396` FOREIGN KEY (`original_transaction`) REFERENCES `transaction` (`id`),
  CONSTRAINT `FKTransactio266008` FOREIGN KEY (`terminal`) REFERENCES `terminal` (`id`),
  CONSTRAINT `FKTransactio300302` FOREIGN KEY (`payment_product`) REFERENCES `payment_product` (`id`),
  CONSTRAINT `FKTransactio560640` FOREIGN KEY (`client`) REFERENCES `client` (`id`),
  CONSTRAINT `FKTransactio661879` FOREIGN KEY (`time_performance`) REFERENCES `time_performance` (`id`),
  CONSTRAINT `FKTransactio671235` FOREIGN KEY (`device`) REFERENCES `device` (`id`),
  CONSTRAINT `FKTransactio680460` FOREIGN KEY (`signature`) REFERENCES `signature` (`id`),
  CONSTRAINT `FKTransactio846432` FOREIGN KEY (`a_data`) REFERENCES `a_data` (`data`)
) ENGINE=InnoDB AUTO_INCREMENT=115700 DEFAULT CHARSET=utf8 COMMENT='Stores transaction information.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transaction_tax`
--

DROP TABLE IF EXISTS `transaction_tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction_tax` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tax` int(2) NOT NULL,
  `transaction` bigint(20) NOT NULL,
  `amount` decimal(19,4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tax_transaction_unique` (`tax`,`transaction`),
  KEY `FKTransactio696962` (`transaction`),
  CONSTRAINT `FKTransactio696962` FOREIGN KEY (`transaction`) REFERENCES `transaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Database identification.',
  `username` varchar(100) NOT NULL COMMENT 'User''s username.',
  `password` varchar(100) NOT NULL COMMENT 'User''s password.',
  `first_name` varchar(100) NOT NULL COMMENT 'User''s first name.',
  `last_name` varchar(100) DEFAULT NULL COMMENT 'User''s last name.',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Flag which says if the user is active.',
  `email` varchar(50) DEFAULT NULL COMMENT 'User''s email.',
  `logout_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'User''s logout time.',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation date.',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update.',
  `role` bigint(20) NOT NULL COMMENT 'User''s role.',
  `entity` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FKUser933884` (`entity`),
  KEY `FKUser708634` (`role`),
  CONSTRAINT `FKUser708634` FOREIGN KEY (`role`) REFERENCES `role` (`id`),
  CONSTRAINT `FKUser933884` FOREIGN KEY (`entity`) REFERENCES `entity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Stores all the system users';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'tms_dev1'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-17 23:30:22
