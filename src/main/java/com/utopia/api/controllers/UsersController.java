package com.utopia.api.controllers;

import com.utopia.api.dao.UsersDAO;
import com.utopia.api.dto.UpdateUserRequestDTO;
import com.utopia.api.entities.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

@RestController
@CrossOrigin
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TracesController.class);
    private final UsersDAO usersDAO;

    @Autowired
    public UsersController(JdbcTemplate jdbcTemplate) {
        this.usersDAO = new UsersDAO(jdbcTemplate);
    }

    // Authentication endpoint
    @PostMapping("/auth")
    public ResponseEntity<Object> authenticate(@RequestBody User user) {
        try {
            if (usersDAO.isAuthenticated(user.getContact(), user.getPassword())) {
                User authenticatedUser = usersDAO.get(user.getContact(), user.getPassword());

                if (authenticatedUser != null) {
                    return ResponseEntity.ok(authenticatedUser);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve authenticated user data");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong Credentials!");
            }
        } catch (Exception e) {
            LOGGER.error("Error during authentication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }

    // User registration endpoint
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            if (usersDAO.exists(user.getContact())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this contact already exists");
            }

            usersDAO.add(user);
            User addedUser = usersDAO.get(user.getContact(), user.getPassword());

            if (addedUser != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
            } else {
                throw new Exception("Failed to retrieve the added user data!");
            }
        } catch (Exception e) {
            LOGGER.error("Error during user registration: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }

    // Update user endpoint
    @PutMapping("/users")
    public ResponseEntity<Object> updateUser(@RequestBody UpdateUserRequestDTO request) {
        User oldUser = request.getOldUser();
        User updatedUser = request.getUpdatedUser();

        try {
            if (usersDAO.isAuthenticated(oldUser.getContact(), oldUser.getPassword())) {
                usersDAO.update(updatedUser);

                User newUpdatedUser = usersDAO.get(updatedUser.getContact(), updatedUser.getPassword());
                return ResponseEntity.ok(newUpdatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed!");
            }
        } catch (Exception e) {
            LOGGER.error("Error during user update", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with the database: " + e.getMessage());
        }
    }

}
