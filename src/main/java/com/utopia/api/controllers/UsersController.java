package com.utopia.api.controllers;

import com.utopia.api.dao.UsersDAO;
import com.utopia.api.entities.User;
import com.utopia.api.utilities.JwtUtil;
import com.utopia.api.utilities.JwtChecked;
import com.utopia.api.utilities.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@CrossOrigin
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TracesController.class);
    private final UsersDAO usersDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UsersController(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil) {
        this.usersDAO = new UsersDAO(jdbcTemplate);
        this.jwtUtil = jwtUtil;
    }

    // User registration endpoint
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addUser(@RequestBody User req) {
        System.out.println("Users Request: " + req);

        if(!User.isValid(req))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You should send a valid user object");

        String hashedPassword = passwordEncoder.encode(req.getPassword());
        req.setPassword(hashedPassword);

        req.setBalance(BigDecimal.valueOf(10000));

        try {
            if (usersDAO.exists(req.getContact())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this contact already exists");
            }

            if(usersDAO.getUserCount() == 0) {
                req.setRole("owner");
            } else {
              req.setRole("user");
            }

            usersDAO.add(req);

            User newUser = usersDAO.getByContact(req.getContact());
            String jwtToken = jwtUtil.generateToken(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(jwtToken);
        } catch (Exception e) {
            LOGGER.error("Error during user registration: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        }
    }

    // Authentication endpoint
    @PostMapping("/auth")
    public ResponseEntity<Object> authenticate(@RequestBody User req) {
        try {
            if(!User.isValid(req)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong Credentials");
            }

            User authUser = usersDAO.getByContact(req.getContact());
            if(authUser == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid contact");
            }

            if(!passwordEncoder.matches(req.getPassword(), authUser.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password");
            }

            String jwtToken = jwtUtil.generateToken(authUser);

            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            LOGGER.error("Error during authentication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        }
    }

    @PutMapping("/users")
    public ResponseEntity<Object> updateUser(@RequestBody User req, @RequestHeader("x-auth-token") String token) {
        try {
            if (req.getContact() != null && !Validator.isValidContact(req.getContact())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid contact");
            }
            if(req.getPassword() != null && req.getPassword().length() < 5 ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password length should be greater than 5");
            }

            JwtChecked jwtChecked = jwtUtil.validate(token);
            if (!jwtChecked.isValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            long userId = jwtChecked.userId;

            User existingUser = usersDAO.getById(userId);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if(req.getName() != null) {
                existingUser.setName(req.getName());
            }

            //In the future you will need to do additional security to update the contact
            if(req.getContact() != null) {
                existingUser.setContact(req.getContact());
            }
            if(req.getAddress() != null) {
                existingUser.setAddress(req.getAddress());
            }

            //In the future you will need to do additional security to update the password
            if(req.getPassword() != null) {
                String hashedPassword = passwordEncoder.encode(req.getPassword());
                existingUser.setPassword(hashedPassword);
            }

            //In the future there might be image upload service
            if(req.getImage() != null) {
                existingUser.setImage(req.getImage());
            }

            //Currently, balance cannot be updated. In the future, you might want to update the balance here.
            //Currently, role can't be updated. In the future, you need to create a new endpoint to update it.
            usersDAO.update(existingUser);

            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            LOGGER.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        }
    }
}
