package com.utopia.api.controllers;

import com.utopia.api.dao.ProductsDAO;
import com.utopia.api.entities.Product;
import com.utopia.api.utilities.JwtChecked;
import com.utopia.api.utilities.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class ProductsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);
    private final ProductsDAO productsDAO;
    private final JwtUtil jwtUtil;

    @Autowired
    public ProductsController(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil) {
        this.productsDAO = new ProductsDAO(jdbcTemplate);
        this.jwtUtil = jwtUtil;
    }

    // Get all products endpoint
    @GetMapping("/products")
    public ResponseEntity<Object> getProductsDAO() {
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
            LOGGER.error("Product not found with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This product is not found on our server");
        } catch (Exception e) {
            LOGGER.error("Error getting product with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }

    // Add a new product endpoint
    @PostMapping("/products")
    public ResponseEntity<Object> addProduct(@RequestHeader("x-auth-token") String token,
                                             @ModelAttribute Product product,
                                             @RequestParam("file") MultipartFile file) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || (!jwtChecked.userRole.equals("owner") && !jwtChecked.userRole.equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        try {
            if (file != null && !file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                assert originalFilename != null;
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

                if (fileExtension.isEmpty()) {
                    throw new IllegalArgumentException("Invalid file extension.");
                }

                String fileName = "p" + (productsDAO.getSize() + 1) + "." + fileExtension;
                product.setImageName(fileName);

                try {
                    String path = System.getProperty("user.dir") + "/public/images/products/";
                    Path directoryPath = Paths.get(path);

                    if (Files.notExists(directoryPath)) {
                        try {
                            Files.createDirectories(directoryPath);
                        } catch (IOException e) {
                            throw new IllegalStateException("Error while creating the directory.", e);
                        }
                    }

                    String filePath = path + fileName;
                    file.transferTo(new File(filePath));
                } catch (IOException e) {
                    throw new IllegalStateException("Error while transferring the file.", e);
                }
            } else {
                throw new IllegalArgumentException("No file uploaded.");
            }

            ProductsDAO.validateProduct(product);

            Product addedProduct = productsDAO.add(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during file transfer: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error during add product: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }
}
