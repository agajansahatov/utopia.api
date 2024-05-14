package com.utopia.api.controllers;

import com.utopia.api.dao.ProductsDAO;
import com.utopia.api.dao.UsersDAO;
import com.utopia.api.dto.UserResponseDTO;
import com.utopia.api.entities.Product;
import com.utopia.api.entities.User;
import com.utopia.api.utilities.ImageNameGenerator;
import com.utopia.api.utilities.JwtChecked;
import com.utopia.api.utilities.JwtUtil;
import com.utopia.api.utilities.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin
public class AdminController {
    private final JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UsersDAO usersDAO;
    private final ProductsDAO productsDAO;

    @Autowired
    public AdminController(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.usersDAO = new UsersDAO(jdbcTemplate);
        this.productsDAO = new ProductsDAO(jdbcTemplate);
    }

    // Endpoint to update a user by an admin or owner
    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") long userId,
                                             @RequestHeader("x-auth-token") String token,
                                             @RequestBody User req) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || (jwtChecked.userRole != 1 && jwtChecked.userRole != 2)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or role");
        }

        if(userId != req.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Route parameter(userId) and user object's id does not match");
        }

        try {
            User user = usersDAO.getById(userId);

            // Auth: owner (Only owners can update a user's contact)
            if(req.getContact() != null) {
                if(jwtChecked.userRole != 1) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Unauthorized: You cannot update the user contact");
                }
                if(!Validator.isValidContact(req.getContact())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid contact");
                }
                if (usersDAO.exists(req.getContact())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this contact already exists");
                }
                user.setContact(req.getContact());
            }

            // Auth: owner (Only owners can update a user's password)
            boolean isPasswordUpdated = false;
            if(req.getPassword() != null) {
                if(jwtChecked.userRole != 1) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Unauthorized: You cannot update the user password");
                }

                String hashedPassword = passwordEncoder.encode(req.getPassword());
                user.setPassword(hashedPassword);
                isPasswordUpdated = true;
            }

            // Auth: owner (Only owners can update a user's role)
            boolean isRoleUpdated = false;
            if(req.getRole() != null) {
                if(jwtChecked.userRole != 1) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Unauthorized: You cannot update the user role");
                }

                user.setRole(req.getRole());
                isRoleUpdated = true;
            }

            // Auth: owner, admin
            if(req.getBalance() != null) {
                if (req.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Balance should be greater than 0");
                }

                if(jwtChecked.userRole == 2){
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
            if(req.getFirstname() != null) {
                user.setFirstname(req.getFirstname());
            }

            // Auth: owner, admin
            if(req.getLastname() != null) {
                user.setLastname(req.getLastname());
            }

            // Auth: owner, admin
            if(req.getCountry() != null) {
                user.setCountry(req.getCountry());
            }

            // Auth: owner, admin
            if(req.getProvince() != null) {
                user.setProvince(req.getProvince());
            }

            // Auth: owner, admin
            if(req.getCity() != null) {
                user.setCity(req.getCity());
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

    // Endpoint to get the list of users
    @GetMapping("/admin/users")
    public ResponseEntity<Object> getProductsDAO(@RequestHeader("x-auth-token") String token) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || (jwtChecked.userRole != 1 && jwtChecked.userRole != 2)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or role");
        }

        try {
            List<User> userList = usersDAO.getUsers();

            List<UserResponseDTO> resList = new ArrayList<>();
            for (User user : userList) {
                resList.add(new UserResponseDTO(user));
            }

            return ResponseEntity.ok(resList);
        } catch (Exception e) {
            System.err.println("Error getting products from db: (" + e.getCause() + ") " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting users!");
        }
    }

    // Add a new product endpoint
    @PostMapping("/admin/products")
    public ResponseEntity<Object> addProduct(@RequestHeader("x-auth-token") String token,
                                             @ModelAttribute Product product,
                                             @RequestParam("file") MultipartFile file) {
        //Token validation
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || (jwtChecked.userRole != 1 && jwtChecked.userRole != 2)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        //File validation
        String[] allowedExtensions = {"jpg", "jpeg", "png", "gif", "webp", "bmp", "tiff", "tif", "svg", "svgz", "heif", "heic", "ico"};
        Validator fileValidator = Validator.validateFile(file, "image", allowedExtensions);
        if(!fileValidator.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fileValidator.getMessage());
        }

        //Product Validation
        Validator productValidator = ProductsDAO.validateProduct(product);
        if (!productValidator.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productValidator.getMessage());
        }

        //Set ImageName and Directory
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String fileName = "p" + ImageNameGenerator.generateUniqueName(jwtChecked.userId) + "." + fileExtension;
        String path = System.getProperty("user.dir") + "/public/images/products/";
        Path directoryPath = Paths.get(path);
        if (Files.notExists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating the directory");
            }
        }
        String filePath = path + fileName;

        //Transfer the image to the server directory
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            System.err.println("Error while transferring the file ("+ e.getCause() + "): " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while transferring the file");
        }

        //Save product to the database
        product.setImageName(fileName);
        try {
            Product addedProduct = productsDAO.add(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
        } catch (Exception e) {
            System.err.println("Error during add product ("+ e.getCause() + "): " + e.getMessage());
            try {
                Files.deleteIfExists(Paths.get(filePath));
            } catch (IOException ex) {
                System.err.println("Product has not added to the db and the transferred image is cannot be deleted ("+ e.getCause() + "): " + e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product to database");
        }
    }

    // Endpoint to update a product
    // Endpoint to delete a product
    // Endpoint to delete a user
    // Endpoint to get orders of all users
    // Endpoint to get orders of a user
    // Endpoint to delete an order of a user (Maybe automatically) (This one also need to build for user itself)
}
