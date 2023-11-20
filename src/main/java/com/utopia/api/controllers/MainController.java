package com.utopia.api.controllers;

import com.utopia.api.dao.*;
import com.utopia.api.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private final UsersDAO usersDAO;
    private final ProductsDAO productsDAO;
    private final TransactionsDAO transactionsDAO;
    private final FavouritesDAO favouritesDAO;
    private final TracesDAO tracesDAO;

    public MainController(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        this.usersDAO = new UsersDAO(jdbcTemplate);
        this.productsDAO = new ProductsDAO(jdbcTemplate);
        this.transactionsDAO = new TransactionsDAO(jdbcTemplate);
        this.favouritesDAO = new FavouritesDAO(jdbcTemplate);
        this.tracesDAO = new TracesDAO(jdbcTemplate);
    }

    // USERS CONTROLLER
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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            //LOGGER.error("Error during authentication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error in the server");
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
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve the added user data");
            }
        } catch (Exception e) {
            LOGGER.error("Error during user registration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error in the server");
        }
    }

    // Update user endpoint
    // Update password ishlanok, hem kone useri almaly, hem updated useri almaly.
    // Authentication ucin kone useri hem almaly
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            if (usersDAO.isAuthenticated(user.getContact(), user.getPassword())) {
                usersDAO.update(user);

                //get the updated user
                User authenticatedUser = usersDAO.get(user.getContact(), user.getPassword());;
                return ResponseEntity.ok(authenticatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            LOGGER.error("Error during user update", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // PRODUCTS CONTROLLER
    // Get all products endpoint
    @GetMapping("/products")
    public ResponseEntity<Object> getProductsDAO() {
        try {
            List<Product> productList = productsDAO.getProducts();
            Collections.shuffle(productList);
            return ResponseEntity.ok(productList);
        } catch (Exception e) {
            //LOGGER.error("Error getting products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get a specific product endpoint
    @GetMapping("/products/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable("productId") long id) {
        try {
            Product product = productsDAO.getProduct(id);
            return ResponseEntity.ok(product);
        } catch (EmptyResultDataAccessException e) {
            //LOGGER.error("Product not found with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            //LOGGER.error("Error getting product with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Add a new product endpoint
    @PostMapping("/products")
    public ResponseEntity<Object> addProduct(@ModelAttribute Product product,
                                                 @RequestParam("file") MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                assert originalFilename != null;
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

                if (fileExtension.isEmpty()) {
                    throw new IllegalArgumentException("Invalid file extension.");
                }

                String fileName = "p" + (productsDAO.getSize() + 1) + "." + fileExtension;
                product.setImageName(fileName);

                try {
                    String path = System.getProperty("user.dir") + "/public/images/products/";
                    Path directoryPath = Paths.get(path);

                    if (Files.notExists(directoryPath)) {
                        try {
                            Files.createDirectories(directoryPath);
                        } catch (IOException e) {
                            throw new IllegalStateException("Error while creating the directory.", e);
                        }
                    }

                    String filePath = path + fileName;
                    file.transferTo(new File(filePath));
                } catch (IOException e) {
                    throw new IllegalStateException("Error while transferring the file.", e);
                }
            } else {
                throw new IllegalArgumentException("No file uploaded.");
            }

            ProductsDAO.validateProduct(product);

            Product addedProduct = productsDAO.add(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // TRANSACTIONS CONTROLLER
    // Get all transactions of a user endpoint
    // returns all the order transacstion of a user
    @GetMapping("/transactions/{userId}")
    public ResponseEntity<Object> getTransactions(@PathVariable("userId") long userId) {
        try {
            List<Transaction> transactions = transactionsDAO.getTransactions(userId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Add all transactions of a user endpoint
    // this is used for purchasing items
    // Shuna tazeden seretmeli
    @PostMapping("/transactions")
    public ResponseEntity<Object> addTransactions(@RequestBody List<Transaction> transactions) {
        if(transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be empty!");
        }

        if(transactions.get(0) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be null!");
        }

        User user = usersDAO.getById(transactions.get(0).getUserId());

        try {
            //Check up;
            BigDecimal sum = BigDecimal.ZERO;
            for (Transaction transaction : transactions) {
                if(transaction == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be null!");
                }

                User u = usersDAO.getById(transaction.getUserId());
                if(!user.getId().equals(u.getId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("All transactions should belong to one person at the time!");
                }

                Product product = productsDAO.getProduct(transaction.getProductId());
                BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(transaction.getQuantity()));
                sum = sum.add(totalPrice);
                if (sum.compareTo(user.getBalance()) > 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Your Balance is not enough!");
                }
            }

            //If passes checkup, continue.
            //Add transactions to the database
            for (Transaction transaction : transactions) {
                this.transactionsDAO.add(transaction);
            }
            //Update the user balance
            BigDecimal balance = user.getBalance();
            balance = balance.subtract(sum);
            user.setBalance(balance);
            usersDAO.update(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("Transactions added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding transactions");
        }
    }




    // FAVOURITES CONTROLLER
    // Add a new favourite object endpoint
    // when a user likes a product, then it will be added to the favourites table
    @PostMapping("/favourites")
    public ResponseEntity<Object> addFavourite(@RequestBody Favourite f) {
        try {
            if (favouritesDAO.exists(f)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Data already exists");
            }
            favouritesDAO.add(f);
            Favourite addedFavourite = favouritesDAO.get(f.getUserId(), f.getProductId());

            if (addedFavourite != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(addedFavourite);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve the added data");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error in the server");
        }
    }

    // Update a favourite object endpoint
    // it only updates the date column
    @PutMapping("/favourites")
    public ResponseEntity<Object> updateFavourite(@RequestBody Favourite f) {
        try {
            if (!favouritesDAO.exists(f)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
            }

            favouritesDAO.update(f);
            Favourite updatedFavourite = favouritesDAO.get(f.getUserId(), f.getProductId());

            if (updatedFavourite != null) {
                return ResponseEntity.status(HttpStatus.OK).body(updatedFavourite);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve the updated data");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error in the server");
        }
    }

    // Delete a favourite object endpoint
    // this can be used when a user dislikes a product
    @DeleteMapping("/favourites")
    public ResponseEntity<Object> removeFavourite(@RequestBody Favourite f) {
        try {
            if (!favouritesDAO.exists(f)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
            }

            favouritesDAO.remove(f);

            // In a DELETE operation, you may choose not to retrieve the deleted object.
            // Here, I'm returning a generic success message.
            return ResponseEntity.status(HttpStatus.OK).body("Data removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error in the server");
        }
    }

    // Get all favourites of a user endpoint
    @GetMapping("/favourites/{userId}")
    public ResponseEntity<Object> getFavourites(@PathVariable("userId") int userId) {
        try {
            List<Favourite> favourites = this.favouritesDAO.getAll(userId);
            return ResponseEntity.status(HttpStatus.OK).body(favourites);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error in the server");
        }
    }

    // Get the count of likes of a product endpoint
    // how many people like this product
    @GetMapping("/favourites/count/{productId}")
    public ResponseEntity<Object> getCountOfaFavourite(@PathVariable("productId") long id) {
        try {
            long count = favouritesDAO.getCountOfProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error in the server");
        }
    }




    // TRACES CONTROLLER
    // Add new trace of a user endpoint
    @PostMapping("/traces")
    public ResponseEntity<Object> addTrace(@RequestBody Trace trace) {
        try {
            if (tracesDAO.exists(trace)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Data already exists");
            }

            tracesDAO.add(trace);
            Trace addedTrace = tracesDAO.get(trace.getUserId(), trace.getProductId());

            if (addedTrace != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(addedTrace);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve the added data");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error in the server");
        }
    }

    // Update the date of a trace of a user endpoint
    @PutMapping("/traces")
    public ResponseEntity<Object> updateTrace(@RequestBody Trace trace) {
        try {
            if (tracesDAO.exists(trace)) {
                tracesDAO.update(trace);
                Trace updatedTrace = tracesDAO.get(trace.getUserId(), trace.getProductId());

                if (updatedTrace != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(updatedTrace);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve the updated data");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error in the server");
        }
    }

    // Delete a trace of a user endpoint
    @DeleteMapping("/traces")
    public ResponseEntity<Object> removeTrace(@RequestBody Trace trace) {
        try {
            if (tracesDAO.exists(trace)) {
                tracesDAO.remove(trace);

                // In a DELETE operation, you may choose not to retrieve the deleted object.
                // Here, I'm returning a generic success message.
                return ResponseEntity.status(HttpStatus.OK).body("Data removed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error in the server");
        }
    }

    // Get all traces of a user endpoint
    @GetMapping("/traces/{userId}")
    public ResponseEntity<List<Trace>> getTraces(@PathVariable("userId") long userId) {
        try {
            List<Trace> tracesList = tracesDAO.getAll(userId);
            return ResponseEntity.ok(tracesList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get the count of all traces of a product
    // by how many people is this product is visited
    @GetMapping("/traces/count/{productId}")
    public ResponseEntity<Long> getCountOfaTrace(@PathVariable("productId") long id) {
        try {
            long count = tracesDAO.getCountOfProduct(id);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
