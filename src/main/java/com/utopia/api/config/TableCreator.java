package com.utopia.api.config;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TableCreator {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public TableCreator(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
////    Whenever you want to create a table, just uncomment the following line
//    @PostConstruct
//    public void createTable() {
//        String query = "";
////        users
//        query = "CREATE TABLE users (\n" +
//                "  id INT NOT NULL AUTO_INCREMENT,\n" +
//                "  name VARCHAR(255),\n" +
//                "  contact VARCHAR(255),\n" +
//                "  image VARCHAR(255),\n" +
//                "  password VARCHAR(255),\n" +
//                "  address VARCHAR(500),\n" +
//                "  balance VARCHAR(500),\n" +
//                "  PRIMARY KEY (id)\n" +
//                ")";
//        jdbcTemplate.execute(query);
//
//        //products
//        query = "CREATE TABLE products (\n" +
//                "  id INT NOT NULL AUTO_INCREMENT,\n" +
//                "  image VARCHAR(255),\n" +
//                "  name VARCHAR(255),\n" +
//                "  price VARCHAR(255),\n" +
//                "  description VARCHAR(1000),\n" +
//                "  category VARCHAR(250),\n" +
//                "  popularity VARCHAR(250),\n" +
//                "  date VARCHAR(250),\n" +
//                "  PRIMARY KEY (id)\n" +
//                ")";
//        jdbcTemplate.execute(query);
//
//        //purchased_products
//        query = "CREATE TABLE purchased_products (\n" +
//                "  id INT NOT NULL AUTO_INCREMENT,\n" +
//                "  user INT NOT NULL,\n" +
//                "  product INT NOT NULL,\n" +
//                "  destination VARCHAR(255),\n" +
//                "  status VARCHAR(255),\n" +
//                "  quantity INT NOT NULL,\n" +
//                "  date VARCHAR(250),\n" +
//                "  PRIMARY KEY (id)\n" +
//                ")";
//        jdbcTemplate.execute(query);
//
//        //favourites
//        query = "CREATE TABLE favourites (\n" +
//                "  user INT NOT NULL,\n" +
//                "  product INT NOT NULL,\n" +
//                "  PRIMARY KEY (user, product)\n" +
//                ")";
//        jdbcTemplate.execute(query);
//
//        //visited
//        query = "CREATE TABLE visited (\n" +
//                "  user INT NOT NULL,\n" +
//                "  product INT NOT NULL,\n" +
//                "  PRIMARY KEY (user, product)\n" +
//                ")";
//        jdbcTemplate.execute(query);
//
//    }
//}