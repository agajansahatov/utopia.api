package com.utopia.api.controllers;

import com.utopia.api.dao.StatusesDAO;
import com.utopia.api.entities.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class StatusesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusesController.class);
    private final StatusesDAO statusesDAO;

    @Autowired
    public StatusesController(StatusesDAO statusesDAO) {
        this.statusesDAO = statusesDAO;
    }

    @GetMapping("/statuses")
    public ResponseEntity<Object> getStatuses() {
        try {
            List<Status> statusList = statusesDAO.getAll();
            return ResponseEntity.ok(statusList);
        } catch (Exception e) {
            LOGGER.error("Error getting statuses", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }
}
