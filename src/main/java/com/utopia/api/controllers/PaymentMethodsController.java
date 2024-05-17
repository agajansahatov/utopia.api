package com.utopia.api.controllers;

import com.utopia.api.dao.PaymentMethodsDAO;
import com.utopia.api.entities.PaymentMethod;
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
public class PaymentMethodsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentMethodsController.class);
    private final PaymentMethodsDAO paymentMethodsDAO;

    @Autowired
    public PaymentMethodsController(JdbcTemplate jdbcTemplate) {
        this.paymentMethodsDAO = new PaymentMethodsDAO(jdbcTemplate);
    }

    @GetMapping("/payment-methods")
    public ResponseEntity<Object> getPaymentMethods() {
        try {
            List<PaymentMethod> paymentMethodList = paymentMethodsDAO.getAll();
            return ResponseEntity.ok(paymentMethodList);
        }  catch (Exception e) {
            LOGGER.error("Error getting payment methods", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }
}
