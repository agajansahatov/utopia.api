-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: utopia
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `favourites`
--

DROP TABLE IF EXISTS `favourites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favourites` (
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`product_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `favourites_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `favourites_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favourites`
--

LOCK TABLES `favourites` WRITE;
/*!40000 ALTER TABLE `favourites` DISABLE KEYS */;
INSERT INTO `favourites` VALUES (1,1,'2023-11-20 08:17:26'),(1,2,'2023-11-20 11:08:04'),(1,3,'2023-11-20 10:38:24'),(1,8,'2023-11-20 10:37:49'),(1,10,'2023-11-20 10:38:25'),(1,13,'2023-11-20 10:38:10'),(1,24,'2023-11-20 11:37:14'),(2,1,'2023-11-20 09:07:55');
/*!40000 ALTER TABLE `favourites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interactions`
--

DROP TABLE IF EXISTS `interactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `ip` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `device` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `source` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `page` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  CONSTRAINT `interactions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interactions`
--

LOCK TABLES `interactions` WRITE;
/*!40000 ALTER TABLE `interactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `interactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  `category` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'p1.jpg','A high quality turkmen carpet',3000.00,'furniture','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','2023-11-20 03:51:20'),(2,'p2.jpg','A coffee Mug',5.00,'furniture','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est ','2023-11-20 09:48:07'),(3,'p3.jpg','C++ Programming Language book',10.00,'books','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:19:53'),(4,'p4.jpg','Bed',99.00,'furniture','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:20:13'),(5,'p5.jpg','Beautiful Handmade Turkmen Carpet',5000.00,'furniture','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:21:02'),(6,'p6.jpg','Dining chair',15.00,'furniture','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:22:46'),(7,'p7.jpg','Table',25.00,'furniture','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:23:36'),(8,'p8.jpg','Gurgaon Sofa',50.00,'furniture','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:24:15'),(9,'p9.webp','A handmade high quality turkmen carpet',4000.00,'furniture','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:24:54'),(10,'p10.jfif','Headphone',10.00,'electronics','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:25:33'),(11,'p11.jpg','HP Laptop',700.00,'electronics','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:26:11'),(12,'p12.jpg','Indian Style full salon',250.00,'furniture','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:27:26'),(13,'p13.jfif','Iphone 15',999.00,'electronics','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:27:47'),(14,'p14.jpg','JavaScript book',8.00,'books','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:28:24'),(15,'p15.webp','Air conditioner',200.00,'furniture','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:28:54'),(16,'p16.jpg','Macbook',1000.00,'electronics','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:30:56'),(17,'p17.webp','Child clothes',11.00,'clothes','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:31:36'),(18,'p18.jpg','Apples',3.00,'fruits','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:32:06'),(19,'p19.webp','Adult clothes',50.00,'clothes','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:32:53'),(20,'p20.webp','Pro Java Programming book',9.00,'books','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:33:24'),(21,'p21.jfif','Sandisk Flashdisk',23.00,'electronics','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:34:00'),(22,'p22.jpg','A comfortable sofa',78.00,'furniture','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:34:21'),(23,'p23.jfif','Tablet',750.00,'electronics','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:35:22'),(24,'p24.jfif','Washing Machine',450.00,'furniture','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:35:57'),(25,'p25.jpg','Smart watch',600.00,'electronics','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','2023-11-20 10:36:30');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `traces`
--

DROP TABLE IF EXISTS `traces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `traces` (
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`product_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `traces_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `traces_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traces`
--

LOCK TABLES `traces` WRITE;
/*!40000 ALTER TABLE `traces` DISABLE KEYS */;
INSERT INTO `traces` VALUES (1,1,'2023-11-20 09:46:03'),(1,2,'2023-11-20 11:10:28'),(1,8,NULL),(1,13,'2023-11-20 10:50:01'),(1,24,NULL),(2,1,NULL);
/*!40000 ALTER TABLE `traces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `destination` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,1,1,'TYUT','Paid',3,'2023-11-20 11:36:54'),(2,1,3,'TYUT','Paid',1,'2023-11-20 11:36:54'),(3,1,23,'TYUT','Paid',1,'2023-11-20 11:36:54'),(4,1,2,'TYUT','Paid',5,'2023-11-20 11:36:54'),(5,1,14,'TYUT','Paid',1,'2023-11-20 11:36:54');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contact` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` char(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `balance` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Agajan Sahatov','agajansahatov@mail.com',NULL,'123456','TYUT',999990207.00),(2,NULL,'agajansahatov@email.com',NULL,'123456','TYUT',5000.00);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-20 11:50:23
