package com.utopia.api.controllers;

import com.utopia.api.dao.ShippersDAO;
import com.utopia.api.entities.Shipper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin
public class ShippersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShippersController.class);
    private final ShippersDAO shippersDAO;

    @Autowired
    public ShippersController(ShippersDAO shippersDAO) {
        this.shippersDAO = shippersDAO;
    }

    @GetMapping("/shippers")
    public ResponseEntity<Object> getShippers() {
        try {
            List<Shipper> shipperList = shippersDAO.getAll();
            return ResponseEntity.ok(shipperList);
        } catch (Exception e) {
            LOGGER.error("Error getting shippers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }
}
