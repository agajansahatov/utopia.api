package com.utopia.api.controllers;

import com.utopia.api.dao.UsersDAO;
import com.utopia.api.dto.PasswordRequestDTO;
import com.utopia.api.dto.UserResponseDTO;
import com.utopia.api.entities.User;
import com.utopia.api.utilities.JwtChecked;
import com.utopia.api.utilities.JwtUtil;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
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

        if(!User.isValid(req))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid contact or password.");

        if (req.getFirstname() == null || req.getFirstname().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firstname is required.");
        }

        if (req.getCountry() == null || req.getCountry().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Country is required.");
        }

        if(req.getProvince() == null || req.getProvince().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Province is required.");
        }

        if(req.getCity() == null || req.getCity().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("City is required.");
        }

        if(req.getAddress() == null || req.getAddress().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Address is required.");
        }

        try {
            if (usersDAO.exists(req.getContact())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this contact already exists");
            }

            String hashedPassword = passwordEncoder.encode(req.getPassword());
            req.setPassword(hashedPassword);

            req.setBalance(BigDecimal.valueOf(100000));

            usersDAO.add(req);

            User newUser = usersDAO.getByContact(req.getContact());
            String jwtToken = jwtUtil.generateToken(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(jwtToken);
        } catch (Exception e) {
            LOGGER.error("Error during user registration: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during user registration.");
        }
    }

    // User Login (Authentication) endpoint
    @PostMapping("/auth")
    public ResponseEntity<Object> authenticate(@RequestBody User req) {
        try {
            if(!User.isValid(req)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");
            }

            User authUser = usersDAO.getByContact(req.getContact());
            if(authUser == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found!");
            }

            if(!passwordEncoder.matches(req.getPassword(), authUser.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is incorrect!");
            }

            String jwtToken = jwtUtil.generateToken(authUser);

            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            LOGGER.error("Error during authentication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        }
    }

    // Update user endpoint
    @PutMapping("/users/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") long userId,
                                             @RequestHeader("x-auth-token") String token,
                                             @RequestBody User req) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        if(req.getRole_id() != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: You cannot update the user role");
        }
        if(req.getBalance() != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: You cannot update the user balance");
        }
        if(req.getPassword() != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Passwords cannot be updated through this endpoint");
        }

        try {
            User existingUser = usersDAO.getById(userId);

            if(req.getContact() != null && !existingUser.getContact().equals(req.getContact())) {
                if(!Validator.isValidContact(req.getContact())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid contact");
                }
                if (usersDAO.exists(req.getContact())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("This contact is not valid");
                }
                existingUser.setContact(req.getContact());
            }

            if(req.getFirstname() != null) {
                existingUser.setFirstname(req.getFirstname());
            }

            if(req.getLastname() != null) {
                existingUser.setLastname(req.getLastname());
            }

            if(req.getCountry() != null) {
                existingUser.setCountry(req.getCountry());
            }

            if(req.getProvince() != null) {
                existingUser.setProvince(req.getProvince());
            }

            if(req.getCity() != null) {
                existingUser.setCity(req.getCity());
            }

            if(req.getAddress() != null) {
                existingUser.setAddress(req.getAddress());
            }

            usersDAO.update(existingUser);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            LOGGER.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        }
    }

    //Update password endpoint
    @PutMapping("/users/password/{userId}")
    public ResponseEntity<Object> updatePassword(@PathVariable("userId") long userId,
                                             @RequestHeader("x-auth-token") String token,
                                             @RequestBody PasswordRequestDTO req) {
        if(!Validator.isValidPassword(req.getOldPassword()) || !Validator.isValidPassword(req.getNewPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is not secure enough!");
        }

        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId!");
        }

        try {
            User existingUser = usersDAO.getById(userId);

            if(!passwordEncoder.matches(req.getOldPassword(), existingUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Old password is incorrect!");
            }

            String hashedPassword = passwordEncoder.encode(req.getNewPassword());
            existingUser.setPassword(hashedPassword);

            usersDAO.update(existingUser);

            String jwtToken = jwtUtil.generateToken(existingUser);
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            LOGGER.error("Error updating password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        }
    }

    //Get user endpoint
    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> getOrders(@RequestHeader("x-auth-token") String token,
                                            @PathVariable("userId") long userId) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            User user = usersDAO.getById(userId);
            UserResponseDTO res = new UserResponseDTO(user);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            LOGGER.error("Error during get user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    //Delete user endpoint
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> removeTrace(@RequestHeader("x-auth-token") String token,
                                              @PathVariable("userId") long userId) {
        try {
            User res = usersDAO.getById(userId);
            if(res == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not found");
            }

            JwtChecked jwtChecked = jwtUtil.validate(token);
            if (!jwtChecked.isValid || userId != jwtChecked.userId) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
            }

            if(userId == 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This user is not allowed to be deleted!");
            }

            usersDAO.delete(userId);

            return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDTO(res));
        } catch (Exception e) {
            LOGGER.error("Error during delete user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting the user!");
        }
    }
}
