CREATE DATABASE  IF NOT EXISTS `timebank` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `timebank`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: timebank
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `authentication_type` varchar(20) NOT NULL,
  `birthday` date NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `gender` varchar(10) NOT NULL,
  `last_login_at` datetime(6) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'2023-09-07 18:20:47.276724','2023-09-07 18:20:47.276730','SOCIAL','1962-01-19',NULL,'FEMALE','2023-09-07 09:20:47.393573','박명임 ','01037639400',2),(3,'2023-09-07 18:24:17.126551','2023-09-07 18:24:17.126555','SOCIAL','1954-01-15',NULL,'MALE','2023-09-07 09:31:45.900363','이상언','01063080072',3),(4,'2023-09-07 18:25:55.874055','2023-09-07 18:25:55.874057','SOCIAL','1974-06-11',NULL,'MALE','2023-09-07 09:25:55.931965','하현삿','01040648410',4),(5,'2023-09-07 18:27:04.625860','2023-09-07 18:27:04.625862','SOCIAL','1995-04-17',NULL,'FEMALE','2023-09-07 09:35:38.465675','이지원','01027050417',5),(6,'2023-09-07 18:27:40.740715','2023-09-07 18:27:40.740717','SOCIAL','1952-08-16',NULL,'FEMALE','2023-09-07 09:27:40.834799','이용식','01099274621',6),(7,'2023-09-07 18:29:10.804811','2023-09-07 18:29:10.804813','SOCIAL','1953-03-05',NULL,'MALE','2023-09-07 09:29:10.977695','김란기','01031679349',7),(8,'2023-09-07 18:33:17.555158','2023-09-07 18:33:17.555160','SOCIAL','1964-07-07',NULL,'MALE','2023-09-07 09:33:17.647910','정찬경','01092886177',8),(9,'2023-09-09 15:11:10.827090','2023-09-09 15:11:10.827091','SOCIAL','1974-12-02',NULL,'FEMALE','2023-09-09 06:11:10.900937','백성화 ','01031112932',9),(10,'2023-10-18 13:13:56.244061','2023-10-18 13:13:56.244062','SOCIAL','1965-03-14',NULL,'FEMALE','2023-10-18 04:13:56.344851','김준용','01075565794',10),(11,'2023-10-21 11:40:01.432532','2023-10-21 11:40:01.432533','SOCIAL','1950-11-22',NULL,'FEMALE','2023-10-21 02:40:01.562856','박영순','01021760140',11),(12,'2023-10-28 16:43:11.224359','2023-10-28 16:43:11.224361','SOCIAL','1980-02-26',NULL,'FEMALE','2023-10-28 07:43:55.870168','김백선','01076601717',12),(13,'2023-10-28 17:05:35.843118','2023-10-28 17:05:35.843119','SOCIAL','1964-02-20',NULL,'FEMALE','2023-10-28 08:05:36.001960','이말숙 ','01040939056',13),(14,'2023-10-28 17:13:06.008531','2023-10-28 17:13:06.008533','SOCIAL','1955-08-26',NULL,'FEMALE','2023-10-28 09:31:16.094904','박윤숙','01092360836',14),(15,'2023-10-31 13:12:26.039832','2023-10-31 13:12:26.039834','SOCIAL','1968-09-20',NULL,'FEMALE','2023-10-31 04:12:26.242809','김미숙','01076816489',15),(16,'2023-10-31 13:38:10.923592','2023-10-31 13:38:10.923593','SOCIAL','2003-04-01',NULL,'MALE','2023-10-31 04:38:11.027719','최선우','01052980568',16),(17,'2023-11-16 12:47:42.421040','2023-11-16 12:47:42.421041','SOCIAL','1999-11-24',NULL,'MALE','2023-11-16 03:47:42.517113','정승우','01051677567',17),(18,'2023-11-16 12:47:42.421040','2023-11-16 12:47:42.421041','PASSWORD','2023-12-03',NULL,'MALE','2023-12-04 10:07:42.765963','KMU','00000000000',18);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-04 12:32:17