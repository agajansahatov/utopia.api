package com.utopia.api.controllers;

import com.utopia.api.dao.CategoriesDAO;
import com.utopia.api.entities.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin
public class CategoriesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriesController.class);
    private final CategoriesDAO categoriesDAO;

    @Autowired
    public CategoriesController(JdbcTemplate jdbcTemplate) {
        this.categoriesDAO = new CategoriesDAO(jdbcTemplate);
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories() {
        try {
            List<Category> categoryList = categoriesDAO.getAll();
            return ResponseEntity.ok(categoryList);
        } catch (Exception e) {
            LOGGER.error("Error getting categories", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }
}
