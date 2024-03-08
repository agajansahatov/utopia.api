package com.utopia.api.controllers;

import com.utopia.api.dao.UsersDAO;
import com.utopia.api.dto.UserResponseDTO;
import com.utopia.api.entities.User;
import com.utopia.api.utilities.JwtChecked;
import com.utopia.api.utilities.JwtUtil;
import com.utopia.api.utilities.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin
public class AdminController {
    private final UsersDAO usersDAO;
    private final JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AdminController(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil) {
        this.usersDAO = new UsersDAO(jdbcTemplate);
        this.jwtUtil = jwtUtil;
    }

    // Endpoint to update a user by an admin or owner
    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") long userId,
                                             @RequestHeader("x-auth-token") String token,
                                             @RequestBody User req) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || (!jwtChecked.userRole.equals("owner") && !jwtChecked.userRole.equals("admin"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or role");
        }

        if(userId != req.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Route parameter(userId) and user object's id does not match");
        }

        try {
            User user = usersDAO.getById(userId);

            if(req.getImage() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Images should be updated by user themselves");
            }

            // Auth: owner
            if(req.getContact() != null) {
                if(!jwtChecked.userRole.equals("owner")) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Unauthorized: You cannot update the user role");
                }

                if(!Validator.isValidContact(req.getContact())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid contact");
                }
                if (usersDAO.exists(req.getContact())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this contact already exists");
                }
                user.setContact(req.getContact());
            }

            // Auth: owner
            boolean isPasswordUpdated = false;
            if(req.getPassword() != null) {
                if(!jwtChecked.userRole.equals("owner")) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Unauthorized: You cannot update the user role");
                }

                String hashedPassword = passwordEncoder.encode(req.getPassword());
                user.setPassword(hashedPassword);
                isPasswordUpdated = true;
            }

            // Auth: owner
            boolean isRoleUpdated = false;
            if(req.getRole() != null) {
                if(!jwtChecked.userRole.equals("owner")) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Unauthorized: You cannot update the user role");
                }
                if (!req.getRole().matches("admin|user")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role");
                }

                user.setRole(req.getRole());
                isRoleUpdated = true;
            }

            // Auth: owner, admin
            if(req.getBalance() != null) {
                if (req.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Balance should be greater than 0");
                }

                if(jwtChecked.userRole.equals("admin")){
                    if(jwtChecked.userId == userId) {
                        user.setBalance(req.getBalance());
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("Unauthorized: You can only update your own balance");
                    }
                }
                // If owner, then can update the balance
                user.setBalance(req.getBalance());
            }

            // Auth: owner, admin
            if(req.getName() != null) {
                user.setName(req.getName());
            }

            // Auth: owner, admin
            if(req.getAddress() != null) {
                user.setAddress(req.getAddress());
            }

            usersDAO.update(user);

            if(isPasswordUpdated || isRoleUpdated){
                //Renew the authentication
                Date now = new Date();
                Timestamp authTime = new Timestamp(now.getTime());
                CompletableFuture<Void> setAuthTimeFuture = CompletableFuture.runAsync(() -> {
                    usersDAO.setAuthTime(user.getId(), authTime);
                });
                setAuthTimeFuture.join();

                Timestamp authTimeFromDb = usersDAO.getAuthTime(user.getId());
                user.setAuthTime(authTimeFromDb);
            }

            return ResponseEntity.ok(new UserResponseDTO(user));
        } catch (Exception e) {
            System.err.println("Error updating user (" + e.getCause() + "): " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        }
    }
}
