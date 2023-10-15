-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: lz2001
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
  `id` int NOT NULL AUTO_INCREMENT,
  `user` int NOT NULL,
  `product` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favourites`
--

LOCK TABLES `favourites` WRITE;
/*!40000 ALTER TABLE `favourites` DISABLE KEYS */;
/*!40000 ALTER TABLE `favourites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `category` varchar(250) DEFAULT NULL,
  `popularity` varchar(250) DEFAULT NULL,
  `date` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'assets/products/ProJavaProgramming.webp','Pro Java Programming','50','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','books','',''),(2,'assets/products/JavaScriptBook.jpg','JavaScript Book','99','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','books','',''),(3,'assets/products/CppBook.jpg','C++ For Beginners','123','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','books','',''),(4,'assets/products/coffee.jpg','Jacobs Coffee','11','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','drinks','',''),(5,'assets/products/product1.webp','Baby Clothes','15','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','clothes','',''),(6,'assets/products/product3.webp','Clothes','20','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','clothes','',''),(7,'assets/products/product2.jpg','Apples','10','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','fruits','',''),(8,'assets/products/bed.jpg','Comfortable Bed','150','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(9,'assets/products/diningchair.jpg','Dining Chair','99','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(10,'assets/products/diningtable.jpeg','Dining Table','125','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(11,'assets/products/gurgaonsofa.jpg','Gurgaon Sofa','175','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(12,'assets/products/headphone.jfif','HeadPhone','25','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','electronics','',''),(13,'assets/products/indianstyle.jpg','Indian Style','185','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(14,'assets/products/Iphone.jfif','Iphone','35','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','electronics','',''),(15,'assets/products/sandisk.jfif','Sandisk','18','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','electronics','',''),(16,'assets/products/sofa.jpg','Sofa','35','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(17,'assets/products/tablet.jfif','Tablet','125','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','electronics','',''),(18,'assets/products/turkmen_carpet.jpg','High Quality Carpet','999','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(19,'assets/products/turkmen_carpet2.jpg','Turkmen Carpet','999','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(20,'assets/products/turkmencarpet.webp','High Quality Turkmen Carpet','999','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(21,'assets/products/washing_machine.jfif','Washing Machine','333','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(22,'assets/products/wearable_smart_watch.jpg','Wearable Smart Watch','541','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','electronics','',''),(23,'assets/products/lg.webp','LG Air Conditioner','444','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','furniture','',''),(24,'assets/products/macbook.jfif','Macbook','811','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','electronics','',''),(25,'assets/products/hp.jpg','HP Laptop','666','Lorem ipsum dolor sit, amet consectetur adipisicing elit. Atque molestiae corrupti veritatis ea dignissimos aut...','electronics','',''),(27,'assets/products/product1.webp','Bananas','12','Lorem ipsum dolor sit amet consectetur adipisicing elit. Similique, culpa qui dignissimos totam repudiandae voluptatem eos fugit impedit mollitia reprehenderit deserunt cum provident minus sint?','fruits','',''),(28,'assets/products/product1.webp','Bananas','12','Lorem ipsum dolor sit amet consectetur adipisicing elit. Similique, culpa qui dignissimos totam repudiandae voluptatem eos fugit impedit mollitia reprehenderit deserunt cum provident minus sint?','fruits','','');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchased_products`
--

DROP TABLE IF EXISTS `purchased_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchased_products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` int NOT NULL,
  `product` int NOT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `date` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchased_products`
--

LOCK TABLES `purchased_products` WRITE;
/*!40000 ALTER TABLE `purchased_products` DISABLE KEYS */;
INSERT INTO `purchased_products` VALUES (1,1,1,'Taiyuan University of Technology','paid',1,'11.7.2023'),(2,1,5,'Taiyuan University of Technology','received',2,'11.7.2023'),(3,1,1,'Taiyuan University of Technology','paid',1,'11.7.2023'),(4,1,5,'Taiyuan University of Technology','received',2,'11.7.2023'),(5,1,10,'Taiyuan University of Technology','paid',2,'11.7.2023'),(6,1,11,'Taiyuan University of Technology','paid',2,'11.7.2023'),(7,1,2,'Taiyuan University of Technology','received',1,'11.7.2023'),(8,1,1,'Taiyuan University of Technology','paid',1,'11.7.2023'),(9,1,1,'Taiyuan University of Technology','paid',1,'11.7.2023'),(10,1,1,'Taiyuan University of Technology','paid',1,'11.7.2023'),(11,1,1,'Taiyuan University of Technology','paid',1,'11.7.2023'),(12,1,1,'Taiyuan University of Technology','paid',1,'11.7.2023'),(13,1,1,'Taiyuan University of Technology','paid',1,'11.7.2023'),(14,1,2,'Taiyuan University of Technology','paid',1,'11.7.2023'),(15,1,2,'Taiyuan University of Technology','paid',1,'11.7.2023'),(16,1,3,'Taiyuan University of Technology','paid',1,'11.7.2023'),(17,2,2,'Taiyuan University Of Technology','paid',5,'11.7.2023'),(18,2,3,'Taiyuan University Of Technology','paid',1,'11.7.2023'),(19,1,4,'Taiyuan University of Technology','paid',3,'13.7.2023');
/*!40000 ALTER TABLE `purchased_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `balance` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Agajan','agajansahatov@mail.com',NULL,'123456','Taiyuan University of Technology','307'),(2,NULL,'agajansahatov@email.com',NULL,'123456','Taiyuan University Of Technology','372');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visited`
--

DROP TABLE IF EXISTS `visited`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visited` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` int NOT NULL,
  `product` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visited`
--

LOCK TABLES `visited` WRITE;
/*!40000 ALTER TABLE `visited` DISABLE KEYS */;
/*!40000 ALTER TABLE `visited` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-13  5:23:47
