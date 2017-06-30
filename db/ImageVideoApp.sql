-- MySQL dump 10.13  Distrib 5.5.55, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ImageVideoApp
-- ------------------------------------------------------
-- Server version	5.5.55-0ubuntu0.14.04.1

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
-- Table structure for table `application_properties`
--

DROP TABLE IF EXISTS `application_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_properties` (
  `property_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(2000) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(45) NOT NULL DEFAULT 'System',
  `modified_on` timestamp NULL DEFAULT NULL,
  `modified_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`property_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_properties`
--

LOCK TABLES `application_properties` WRITE;
/*!40000 ALTER TABLE `application_properties` DISABLE KEYS */;
INSERT INTO `application_properties` VALUES (1,'themecolor','#e8eaf6 indigo lighten-5','2017-05-30 18:45:31','System',NULL,NULL),(2,'imageFolder','/home/manishkm/personal_work/ImageVideoApp/src/main/webapp/static/UPLOAD_IMAGES/','2017-05-30 18:45:31','System',NULL,NULL),(3,'appPath','http://localhost:8080/ImageVideoApp/static/UPLOAD_IMAGES/','2017-05-30 18:45:31','System',NULL,NULL),(4,'uploadImageFolder','/uploadedImage/','2017-05-30 18:45:31','System',NULL,NULL),(5,'uploadVideoFolder','/uploadedVideo/','2017-05-30 18:45:31','System',NULL,NULL),(6,'appUrl','http://localhost:8080/ImageVideoApp/','2017-06-16 19:39:44','System',NULL,NULL);
/*!40000 ALTER TABLE `application_properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cities_m`
--

DROP TABLE IF EXISTS `cities_m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cities_m` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state_id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '1',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(20) DEFAULT 'System',
  `modified_on` timestamp NULL DEFAULT NULL,
  `modified_by` varchar(20) DEFAULT 'NULL',
  PRIMARY KEY (`id`),
  KEY `cities_fk0` (`state_id`),
  CONSTRAINT `cities_fk0` FOREIGN KEY (`state_id`) REFERENCES `states_m` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities_m`
--

LOCK TABLES `cities_m` WRITE;
/*!40000 ALTER TABLE `cities_m` DISABLE KEYS */;
/*!40000 ALTER TABLE `cities_m` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `countries_m`
--

DROP TABLE IF EXISTS `countries_m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `countries_m` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `short_name` varchar(5) DEFAULT NULL,
  `name` varchar(150) NOT NULL,
  `phone_country_code` varchar(10) NOT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '1',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(20) DEFAULT 'System',
  `modified_on` timestamp NULL DEFAULT NULL,
  `modified_by` varchar(20) DEFAULT 'NULL',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `sortname` (`short_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries_m`
--

LOCK TABLES `countries_m` WRITE;
/*!40000 ALTER TABLE `countries_m` DISABLE KEYS */;
/*!40000 ALTER TABLE `countries_m` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency_m`
--

DROP TABLE IF EXISTS `currency_m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency_m` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `currency_code` varchar(4) NOT NULL,
  `currency_name` varchar(20) NOT NULL,
  `country_name` varchar(30) NOT NULL,
  `status` tinyint(3) DEFAULT '1',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_on` timestamp NULL DEFAULT NULL,
  `dollar_converter_value` decimal(12,10) DEFAULT '1.0000000000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency_m`
--

LOCK TABLES `currency_m` WRITE;
/*!40000 ALTER TABLE `currency_m` DISABLE KEYS */;
/*!40000 ALTER TABLE `currency_m` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language_m`
--

DROP TABLE IF EXISTS `language_m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language_m` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `short_name` varchar(10) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language_m`
--

LOCK TABLES `language_m` WRITE;
/*!40000 ALTER TABLE `language_m` DISABLE KEYS */;
/*!40000 ALTER TABLE `language_m` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_m`
--

DROP TABLE IF EXISTS `role_m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_m` (
  `profile_id` bigint(3) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`profile_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_m`
--

LOCK TABLES `role_m` WRITE;
/*!40000 ALTER TABLE `role_m` DISABLE KEYS */;
INSERT INTO `role_m` VALUES (1,'SUPERADMIN'),(2,'ADMIN'),(3,'USER');
/*!40000 ALTER TABLE `role_m` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `states_m`
--

DROP TABLE IF EXISTS `states_m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `states_m` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '1',
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(20) DEFAULT 'System',
  `modified_on` timestamp NULL DEFAULT NULL,
  `modified_by` varchar(20) DEFAULT 'NULL',
  PRIMARY KEY (`id`),
  KEY `states_fk0` (`country_id`),
  CONSTRAINT `states_fk0` FOREIGN KEY (`country_id`) REFERENCES `countries_m` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `states_m`
--

LOCK TABLES `states_m` WRITE;
/*!40000 ALTER TABLE `states_m` DISABLE KEYS */;
/*!40000 ALTER TABLE `states_m` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uploaded_image`
--

DROP TABLE IF EXISTS `uploaded_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uploaded_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `imageUrl` varchar(100) DEFAULT NULL,
  `created_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(45) DEFAULT NULL,
  `image_link` varchar(1000) DEFAULT NULL,
  `image_description` varchar(1000) DEFAULT NULL,
  `link_type` tinyint(3) DEFAULT '1',
  `modified_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploaded_image`
--

LOCK TABLES `uploaded_image` WRITE;
/*!40000 ALTER TABLE `uploaded_image` DISABLE KEYS */;
INSERT INTO `uploaded_image` VALUES (135,3,'2017-06-12_01-39-2210-toilet.jpg','2017-06-11 20:09:22','showoff admin','http://www.ndtv.com/india-news/toilet-ek-prem-katha-trailer-akshay-kumar-bhumi-pednekars-anokhi-love-story-has-a-powerful-social-me-1710751?pfrom=home-topstories','',1,'2017-06-11 14:39:22'),(136,3,'2017-06-12_01-39-419-monsoon.jpg','2017-06-11 20:09:41','showoff admin','http://www.hindustantimes.com/india-news/monsoon-making-steady-advance-expect-a-good-week-ahead-weather-office/story-bHwg9MDcmOoiWq3wTKJFCK.html','',1,'2017-06-11 14:39:41'),(137,3,'2017-06-12_01-39-498-quote2.jpg','2017-06-11 20:09:49','showoff admin','','',1,'2017-06-11 14:39:49'),(138,3,'2017-06-12_01-40-037-para-athlete.jpg','2017-06-11 20:10:03','showoff admin','http://www.ndtv.com/india-news/forced-to-sleep-on-train-floor-para-athlete-suvarna-raj-rejects-railway-minister-suresh-prabhus-reme-1710750?pfrom=home-lateststories','',1,'2017-06-11 14:40:03'),(139,3,'2017-06-12_01-40-186-shikhar-dhawan.jpg','2017-06-11 20:10:18','showoff admin','https://sports.ndtv.com/icc-champions-trophy-2017/icc-champions-trophy-2017-shikhar-dhawan-fastest-to-score-1000-runs-in-odi-tournaments-1710770?pfrom=home-ct2017','',1,'2017-06-11 14:40:18'),(140,3,'2017-06-12_01-40-355-jee.jpg','2017-06-11 20:10:35','showoff admin','http://www.hindustantimes.com/education/jee-advanced-2017-haryana-boy-suraj-yadav-bags-air-5-has-best-rank-in-exam-from-kota/story-xy4blMSqG5VMKANYYaX06M.html','',1,'2017-06-11 14:40:35'),(141,3,'2017-06-12_01-40-494-gst-lowerrate.jpg','2017-06-11 20:10:49','showoff admin','http://profit.ndtv.com/news/tax/article-gst-council-lowers-rates-on-66-items-10-things-to-know-1710712','',1,'2017-06-11 14:40:49'),(142,3,'2017-06-12_01-41-003-vijay-mallya.jpg','2017-06-11 20:11:00','showoff admin','https://sports.ndtv.com/cricket/vijay-mallya-booed-with-chor-chor-chants-at-the-oval-1710766?pfrom=home-lateststories','',1,'2017-06-11 14:41:00'),(143,3,'2017-06-12_01-41-172-batman.jpg','2017-06-11 20:11:17','showoff admin','http://www.cbsnews.com/news/adam-west-batman-actor-dead-at-age-88/','',1,'2017-06-11 14:41:17'),(144,3,'2017-06-12_01-41-521-cricket3.jpg','2017-06-11 20:11:52','showoff admin','http://indianexpress.com/article/sports/cricket/india-vs-south-africa-ind-vs-sa-live-score-online-streaming-icc-champions-trophy-2017-rain-weather-abandoned-virat-kohli-ab-de-villiers-4698679/','',1,'2017-06-11 14:41:52'),(146,3,'2017-06-12_11-18-25apple.jpg','2017-06-12 05:48:25','showoff admin','http://www.gadgetsnow.com/slideshows/12-apps-that-received-one-of-the-biggest-awards-by-apple/old-mans-journey/photolist/59071517.cms','',1,'2017-06-12 00:18:25'),(147,3,'2017-06-12_11-18-47karnataka-bandh.jpg','2017-06-12 05:48:47','showoff admin','http://timesofindia.indiatimes.com/city/bengaluru/karnataka-bandh-today-metro-buses-cabs-autos-to-ply-as-usual/articleshow/59101704.cms','',1,'2017-06-12 00:18:47'),(152,3,'2017-06-13_12-16-1012-gamesofthrones.jpg','2017-06-12 18:46:10','showoff admin','https://www.polygon.com/2017/6/12/15783252/game-of-thrones-season-7-photos','',1,'2017-06-12 13:16:10'),(153,3,'2017-06-13_12-17-0811-teddy-bear.jpg','2017-06-12 18:47:08','showoff admin','http://timesofindia.indiatimes.com/world/uk/teddy-bear-reaches-space-after-tied-to-balloon-by-kids-in-uk/articleshow/59108510.cms','',1,'2017-06-12 13:17:08'),(154,3,'2017-06-13_12-17-4810-railway.jpg','2017-06-12 18:47:48','showoff admin','http://www.business-standard.com/article/current-affairs/railways-to-replace-conventional-coaches-with-lhb-ones-for-better-safety-117061200350_1.html','',1,'2017-06-12 13:17:48'),(155,3,'2017-06-13_12-18-249-jio2.jpg','2017-06-12 18:48:24','showoff admin','http://telecom.economictimes.indiatimes.com/news/lyf-handset-users-to-get-20-extra-data-from-reliance-jio/59103142','',1,'2017-06-12 13:18:24'),(156,3,'2017-06-13_12-18-388-clouds.jpg','2017-06-12 18:48:38','showoff admin','http://www.newsweek.com/nasa-watch-live-new-york-artificial-colorful-clouds-us-east-coast-space-624215','',1,'2017-06-12 13:18:38'),(157,3,'2017-06-13_12-18-537-tata.jpg','2017-06-12 18:48:53','showoff admin','http://www.business-standard.com/article/companies/tata-s-jlr-invests-in-uber-rival-lyft-for-driverless-cars-117061200290_1.html','',1,'2017-06-12 13:18:53'),(158,3,'2017-06-13_12-19-026-quote3.jpg','2017-06-12 18:49:02','showoff admin','','',1,'2017-06-12 13:19:02'),(159,3,'2017-06-13_12-21-075-dangal.jpg','2017-06-12 18:51:07','showoff admin','http://indianexpress.com/article/entertainment/bollywood/dangal-is-now-30th-biggest-hit-worldwide-but-this-is-all-aamir-khan-will-earn-from-it-4699729/','',1,'2017-06-12 13:21:07'),(160,3,'2017-06-13_12-21-205.1-daydream.jpg','2017-06-12 18:51:20','showoff admin','http://www.thehindu.com/sci-tech/technology/gadgets/google-brings-daydream-view-to-india-for-rs-6499/article18965426.ece?homepage=true','',1,'2017-06-12 13:21:20'),(161,3,'2017-06-13_12-21-443-electronics.jpg','2017-06-12 18:51:44','showoff admin','http://www.business-standard.com/article/technology/electronics-import-by-india-to-touch-300-bn-by-2020-study-117061200409_1.html','',1,'2017-06-12 13:21:45'),(162,3,'2017-06-13_12-21-592-indian-companies.jpg','2017-06-12 18:51:59','showoff admin','http://www.business-standard.com/article/economy-policy/15-080-profitable-indian-companies-paid-no-tax-in-2015-16-117061200101_1.html','',1,'2017-06-12 13:21:59'),(163,3,'2017-06-13_12-22-211-neet-result2.jpg','2017-06-12 18:52:21','showoff admin','http://www.thehindu.com/news/national/neet-examination-results-supreme-court-stays-madras-high-court-order-and-directed-for-release-of-neet-examination-results/article18964997.ece?homepage=true','',1,'2017-06-12 13:22:21'),(165,3,'2017-06-14_09-56-1613-virat2.jpg','2017-06-14 04:26:16','showoff admin','http://timesofindia.indiatimes.com/sports/cricket/news/kohli-reclaims-top-spot-in-icc-odi-rankings-for-batsmen/articleshow/59127761.cms','',1,'2017-06-13 22:56:16'),(166,3,'2017-06-14_09-58-2311-quote.jpg','2017-06-14 04:28:23','showoff admin','','',1,'2017-06-13 22:58:23'),(167,3,'2017-06-14_09-58-449-kapl.jpg','2017-06-14 04:28:44','showoff admin','http://timesofindia.indiatimes.com/tv/news/hindi/team-kapil-joins-rival-krushna-for-a-new-comedy-show/articleshow/59119882.cms','',1,'2017-06-13 22:58:44'),(168,3,'2017-06-14_09-58-558-instagram.jpg','2017-06-14 04:28:55','showoff admin','http://www.gadgetsnow.com/slideshows/9-new-instagram-features-you-need-to-know/rewind-and-hashtag/photolist/59126833.cms','',1,'2017-06-13 22:58:55'),(169,3,'2017-06-14_09-59-067-vijay-mallya.jpg','2017-06-14 04:29:06','showoff admin','http://timesofindia.indiatimes.com/india/have-enough-evidence-to-prove-innocence-vijay-mallya-ahead-of-extradition-hearing/articleshow/59127910.cms','',1,'2017-06-13 22:59:06'),(170,3,'2017-06-14_09-59-176-deepika.jpg','2017-06-14 04:29:17','showoff admin','http://www.misskyra.com/celebs/deepika-padukone-is-all-set-to-be-a-part-of-xxx-4/articleshow/59122303.cms','',1,'2017-06-13 22:59:17'),(171,3,'2017-06-14_09-59-295-US-job.jpg','2017-06-14 04:29:29','showoff admin','http://indiatoday.intoday.in/story/narendra-modis-new-india-vision-will-help-us-job-creation-sean-spicer/1/977445.html','',1,'2017-06-13 22:59:29'),(172,3,'2017-06-14_09-59-425.2-pari.jpg','2017-06-14 04:29:42','showoff admin','http://www.ndtv.com/india-news/pari-first-look-anushka-sharma-is-that-really-you-1711547','',1,'2017-06-13 22:59:42'),(173,3,'2017-06-14_09-59-595.1germany.jpg','2017-06-14 04:29:59','showoff admin','http://indianexpress.com/article/world/germany-shooting-munich-train-station-4701734/','',1,'2017-06-13 22:59:59'),(174,3,'2017-06-14_10-03-284-nokia.jpg','2017-06-14 04:33:28','showoff admin','http://www.news18.com/news/tech/nokia-6-5-nokia-3-launched-india-here-are-the-prices-specifications-and-features-1431007.html','',1,'2017-06-13 23:03:28'),(175,3,'2017-06-14_10-03-534.2-ronaldo.jpg','2017-06-14 04:33:54','showoff admin','http://timesofindia.indiatimes.com/sports/football/top-stories/spanish-prosecutor-files-tax-fraud-lawsuit-against-cristiano-ronaldo/articleshow/59126561.cms','',1,'2017-06-13 23:03:54'),(176,3,'2017-06-14_10-05-223-petrol.jpg','2017-06-14 04:35:22','showoff admin','http://timesofindia.indiatimes.com/business/india-business/petrol-diesel-prices-heres-how-to-check-daily-revisions-from-june-16/articleshow/59122965.cms','',1,'2017-06-13 23:05:22'),(177,3,'2017-06-14_10-05-312-500-note2.jpg','2017-06-14 04:35:31','showoff admin','http://economictimes.indiatimes.com/news/economy/policy/rbi-introduces-new-rs-500-currency-heres-whats-different-in-new-notes/articleshow/59123774.cms?from=mdr','',1,'2017-06-13 23:05:31'),(178,3,'2017-06-14_10-05-431-virat-kohli.jpg','2017-06-14 04:35:43','showoff admin','http://timesofindia.indiatimes.com/sports/cricket/champions-trophy-2017/top-stories/everyone-wants-to-see-an-india-england-final-virat-kohli/articleshow/59124700.cms','',1,'2017-06-13 23:05:43'),(193,1,'2017-06-19_10-59-00images.jpg','2017-06-19 05:29:00','manish','sdaasdasdads','asdasd',2,'2017-06-19 05:29:32');
/*!40000 ALTER TABLE `uploaded_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uploaded_video`
--

DROP TABLE IF EXISTS `uploaded_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uploaded_video` (
  `id` bigint(100) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(100) DEFAULT NULL,
  `video_url` varchar(100) DEFAULT NULL,
  `created_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploaded_video`
--

LOCK TABLES `uploaded_video` WRITE;
/*!40000 ALTER TABLE `uploaded_video` DISABLE KEYS */;
/*!40000 ALTER TABLE `uploaded_video` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `mobile_no` varchar(120) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `user_image` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `status` tinyint(3) DEFAULT '1',
  `pass_gen_token` varchar(500) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_on` timestamp NULL DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `email_2` (`email`),
  UNIQUE KEY `email_3` (`email`),
  UNIQUE KEY `email_4` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'manish263160@gmail.com','8744046090','password','','manish','',1,'MTQ5Nzg0OTk2Mzg5MSMjMQ==','2017-04-01 18:33:31','2017-06-19 05:26:03',''),(2,'manishkm@dewsolutions.in','9099099090','password','','manish','',1,NULL,'2017-04-05 18:27:51','2017-04-05 18:27:51',''),(3,'showofff.hello@gmail.com','8744046090','showofff7432',NULL,'showoff admin',NULL,1,NULL,'2017-05-31 18:49:09','2017-05-31 18:49:09',NULL),(4,'kumarmandal263160@gmail.com','8744046090','password',NULL,'manish',NULL,0,NULL,'2017-06-01 19:52:09','2017-06-01 19:54:35',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_relation`
--

DROP TABLE IF EXISTS `user_role_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role_relation` (
  `user_id` bigint(3) NOT NULL,
  `profile_id` bigint(3) NOT NULL,
  KEY `user_profile_relation_fk0` (`user_id`),
  KEY `user_profile_relation_fk1` (`profile_id`),
  CONSTRAINT `user_profile_relation_fk0` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_profile_relation_fk1` FOREIGN KEY (`profile_id`) REFERENCES `role_m` (`profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_relation`
--

LOCK TABLES `user_role_relation` WRITE;
/*!40000 ALTER TABLE `user_role_relation` DISABLE KEYS */;
INSERT INTO `user_role_relation` VALUES (1,3),(2,2),(3,3),(4,3);
/*!40000 ALTER TABLE `user_role_relation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-19 23:03:02
