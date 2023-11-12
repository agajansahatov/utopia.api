CREATE DATABASE  IF NOT EXISTS `lz2001` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `lz2001`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: lz2001
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
  `user` int NOT NULL,
  `product` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favourites`
--

LOCK TABLES `favourites` WRITE;
/*!40000 ALTER TABLE `favourites` DISABLE KEYS */;
INSERT INTO `favourites` VALUES (1,10),(1,5),(1,2),(1,3),(2,1),(1,1),(1,17),(3,2),(3,3),(3,4),(3,5),(3,6),(3,7),(3,8),(4,1),(4,2),(3,9),(3,10),(4,8),(3,25),(3,24),(3,23),(3,22),(3,21),(3,20),(3,19),(3,18),(3,17),(3,16),(3,15),(3,14),(3,13),(3,12),(3,11),(3,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'p1.jpg','High Quality Turkmen Carpet','3333','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(2,'p2.jpg','A Coffee Mug','5','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(3,'p3.jpg','C++ Programming Language book','10','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','books','',''),(4,'p4.jpg','Bed','99','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(5,'p5.jpg','Beautiful Handmade Turkmen Carpet','4999','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(6,'p6.jpg','Dining Chair','15','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(7,'p7.jpeg','Dining table','25','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(8,'p8.jpg','Gurgaon Sofa','50','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(9,'p9.webp','Handmade High Quality Turkmen Carpet','3999','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(10,'p10.jfif','Headphone','10',' Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','electronics','',''),(11,'p11.jpg','HP Laptop','499','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','electronics','',''),(12,'p12.jpg','Indian Style','150','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(13,'p13.jfif','Iphone 15','999','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','electronics','',''),(14,'p14.jpg','JavaScript book','8','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','books','',''),(15,'p15.webp','Air Conditioner','200','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(16,'p16.jfif','Macbook','999','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','electronics','',''),(17,'p17.webp','Child clothes','11','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','clothes','',''),(18,'p18.jpg','Apples','3','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','fruits','',''),(19,'p19.webp','Clothes','50','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','clothes','',''),(20,'p20.webp','Pro Java Programming','9','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','books','',''),(21,'p21.jfif','Sandisk Flashdisk','23','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','electronics','',''),(22,'p22.jpg','A comfortable sofa','78','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','furniture','',''),(23,'p23.jfif','Tablet','999','Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolorum ad, libero id a perferendis ab quaerat animi porro nostrum adipisci numquam molestiae neque impedit voluptatem. Quo quaerat, dolorem iste odio quidem natus voluptate accusamus et reprehenderit quis velit sequi maxime dolores aspernatur repellat eaque ab necessitatibus voluptatum in nemo unde provident. Iure sit, accusamus assumenda vitae esse consequatur ipsum quibusdam repellendus dolores explicabo optio earum eveniet quasi repudiandae odit harum doloribus, voluptatum quia quis alias! Pariatur eveniet quidem animi, minus voluptatum dolorem similique. Commodi quas aspernatur quidem accusantium officiis deserunt! Eaque ratione minus nihil quia corrupti eligendi unde eius, totam incidunt architecto illo quas culpa voluptatum ipsam rem provident velit?','electronics','',''),(24,'p24.jfif','Washing Machine','500','Lorem ipsum dolor sit amet consectetur, adipisicing elit. Blanditiis dolorem quae aliquam, deserunt eum dolore magni quod, quia eos obcaecati esse at repudiandae sit temporibus autem neque quas, sed ratione soluta cum. Rerum quis, inventore aliquid quasi nobis blanditiis ipsa accusantium ut accusamus perferendis sint fuga temporibus, delectus non recusandae veritatis expedita quae quod ducimus pariatur obcaecati voluptates. Voluptatibus eveniet facilis aliquid culpa reprehenderit consequuntur commodi praesentium maxime similique molestiae placeat, voluptas perferendis cumque est doloremque labore? Fugiat, repellat deserunt.','furniture','',''),(25,'p25.jpg','Smart Watch','599','Lorem ipsum dolor sit amet consectetur, adipisicing elit. Blanditiis dolorem quae aliquam, deserunt eum dolore magni quod, quia eos obcaecati esse at repudiandae sit temporibus autem neque quas, sed ratione soluta cum. Rerum quis, inventore aliquid quasi nobis blanditiis ipsa accusantium ut accusamus perferendis sint fuga temporibus, delectus non recusandae veritatis expedita quae quod ducimus pariatur obcaecati voluptates. Voluptatibus eveniet facilis aliquid culpa reprehenderit consequuntur commodi praesentium maxime similique molestiae placeat, voluptas perferendis cumque est doloremque labore? Fugiat, repellat deserunt.','electronics','','');
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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchased_products`
--

LOCK TABLES `purchased_products` WRITE;
/*!40000 ALTER TABLE `purchased_products` DISABLE KEYS */;
INSERT INTO `purchased_products` VALUES (1,1,10,'Taiyuan University Of Technology','Paid',1,'7.11.2023'),(2,1,1,'Taiyuan University Of Technology','Paid',1,'7.11.2023'),(3,1,23,'Taiyuan University Of Technology','Paid',1,'7.11.2023'),(4,1,19,'Taiyuan University Of Technology','Paid',1,'7.11.2023'),(5,1,16,'Taiyuan University Of Technology','Paid',1,'7.11.2023'),(6,1,13,'Taiyuan University Of Technology','Paid',1,'7.11.2023'),(7,1,7,'Taiyuan University Of Technology','Paid',1,'7.11.2023'),(8,1,20,'Taiyuan University Of Technology','Paid',23,'10.11.2023'),(9,1,3,'Taiyuan University Of Technology','Paid',4,'10.11.2023'),(10,1,1,'Taiyuan University Of Technology','Paid',1,'10.11.2023'),(11,1,3,'Taiyuan University Of Technology','Paid',4,'10.11.2023'),(12,2,2,'Taiyuan University of Technology','Paid',2,'10.11.2023'),(13,2,3,'Taiyuan University of Technology','Paid',6,'10.11.2023'),(14,2,6,'Taiyuan University of Technology','Paid',2,'10.11.2023'),(15,2,3,'Taiyuan University of Technology','Paid',1,'10.11.2023'),(16,2,4,'Taiyuan University of Technology','Paid',1,'10.11.2023'),(17,2,7,'Taiyuan University of Technology','Paid',1,'10.11.2023'),(18,4,2,'Mary city','Paid',1,'12.11.2023'),(19,3,4,'China Jinzhonv','Paid',1,'12.11.2023'),(20,3,6,'China Jinzhonv','Paid',1,'12.11.2023'),(21,3,7,'China Jinzhonv','Paid',1,'12.11.2023'),(22,3,8,'China Jinzhonv','Paid',1,'12.11.2023'),(23,3,10,'China Jinzhonv','Paid',2,'12.11.2023'),(24,3,15,'China Jinzhonv','Paid',1,'12.11.2023'),(25,3,18,'China Jinzhonv','Paid',1,'12.11.2023'),(26,3,19,'China Jinzhonv','Paid',1,'12.11.2023'),(27,3,21,'China Jinzhonv','Paid',1,'12.11.2023'),(28,3,22,'China Jinzhonv','Paid',1,'12.11.2023'),(29,2,2,'Taiyuan University of Technology','Paid',1,'12.11.2023'),(30,2,2,'Taiyuan University of Technology','Paid',1,'13.11.2023'),(31,1,1,'Taiyuan University Of Technology','Paid',1,'13.11.2023'),(32,1,5,'Taiyuan University Of Technology','Paid',1,'13.11.2023'),(33,1,6,'Taiyuan University Of Technology','Paid',5,'13.11.2023');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Agajan Sahatov','agajansahatov@mail.com',NULL,'123456','Taiyuan University Of Technology','981568'),(2,NULL,'agajan.st@gmail.com',NULL,'123456','Taiyuan University of Technology','755'),(3,NULL,'17703403272',NULL,'serdar2001','China Jinzhonv','436'),(4,'','Meylisgeldi@gmail.com',NULL,'12345678me','Mary city','994');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visited`
--

DROP TABLE IF EXISTS `visited`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visited` (
  `user` int NOT NULL,
  `product` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visited`
--

LOCK TABLES `visited` WRITE;
/*!40000 ALTER TABLE `visited` DISABLE KEYS */;
INSERT INTO `visited` VALUES (1,5),(1,6),(1,8),(2,4),(2,12),(2,10),(2,13),(2,23),(2,3),(2,2),(1,4),(1,17),(3,8),(4,2),(3,11),(4,8),(3,24),(3,1),(3,4),(3,25),(4,7),(2,1),(1,1);
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

-- Dump completed on 2023-11-13  4:15:06
