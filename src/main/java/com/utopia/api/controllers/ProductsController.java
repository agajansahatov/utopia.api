package com.utopia.api.controllers;

import com.utopia.api.dao.ProductsDAO;
import com.utopia.api.entities.CategorizedProduct;
import com.utopia.api.entities.Product;
import com.utopia.api.utilities.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class ProductsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);
    private final ProductsDAO productsDAO;

    @Autowired
    public ProductsController(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil) {
        this.productsDAO = new ProductsDAO(jdbcTemplate);
    }

    // Get all products endpoint
    @GetMapping("/products")
    public ResponseEntity<Object> getProducts() {
        try {
            List<Product> productList = productsDAO.getProducts();
            Collections.shuffle(productList);
            return ResponseEntity.ok(productList);
        } catch (Exception e) {
            LOGGER.error("Error getting products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }

    // Get a specific product endpoint
    @GetMapping("/products/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable("productId") long id) {
        try {
            Product product = productsDAO.getProduct(id);
            return ResponseEntity.ok(product);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Product not found with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This product is not found on our server");
        } catch (Exception e) {
            LOGGER.error("Error getting product with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }

    @GetMapping("/categorized-products")
    public ResponseEntity<Object> getCategorizedProducts() {
        try {
            List<CategorizedProduct> categorizedProducts = productsDAO.getCategorizedProducts();
            return ResponseEntity.ok(categorizedProducts);
        } catch (Exception e) {
            LOGGER.error("Error getting products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }
}
