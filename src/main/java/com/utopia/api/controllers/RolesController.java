package com.utopia.api.controllers;

import com.utopia.api.dao.RolesDAO;
import com.utopia.api.entities.Role;
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
public class RolesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RolesController.class);
    private final RolesDAO rolesDAO;

    @Autowired
    public RolesController(RolesDAO rolesDAO) {
        this.rolesDAO = rolesDAO;
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> getRoles() {
        try {
            List<Role> roleList = rolesDAO.getAll();
            return ResponseEntity.ok(roleList);
        } catch (Exception e) {
            LOGGER.error("Error getting roles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }
}
