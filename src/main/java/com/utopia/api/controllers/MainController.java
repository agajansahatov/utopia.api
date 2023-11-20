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

    //Users Controller

    // Authentication endpoint
    @PostMapping("/auth")
    public ResponseEntity<User> authenticate(@RequestBody User user) {
        try {
            boolean userExists = usersDAO.exists(user.getContact(), user.getPassword());
            if (userExists) {
                long id = usersDAO.getUserId(user.getContact(), user.getPassword());
                User authenticatedUser = usersDAO.get(id);
                return ResponseEntity.ok(authenticatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            LOGGER.error("Error during authentication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // User registration endpoint
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            usersDAO.add(user);

            //get the new added user
            long id = usersDAO.getUserId(user.getContact(), user.getPassword());
            User authenticatedUser = usersDAO.get(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(authenticatedUser);
        } catch (Exception e) {
            LOGGER.error("Error during user registration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update user endpoint
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            if (usersDAO.exists(user.getContact(), user.getPassword())) {
                usersDAO.update(user);

                //get the updated user
                long id = usersDAO.getUserId(user.getContact(), user.getPassword());
                User authenticatedUser = usersDAO.get(id);
                return ResponseEntity.ok(authenticatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            LOGGER.error("Error during user update", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //Products Controller

    // Get all products endpoint
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductsDAO() {
        try {
            List<Product> productList = productsDAO.getProducts();
            Collections.shuffle(productList);
            return ResponseEntity.ok(productList);
        } catch (Exception e) {
            LOGGER.error("Error getting products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get a specific product endpoint
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") long id) {
        try {
            Product product = productsDAO.getProduct(id);
            return ResponseEntity.ok(product);
        } catch (EmptyResultDataAccessException e) {
            // Handle the case when the product is not found
            LOGGER.error("Product not found with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Handle other exceptions with a 500 response
            LOGGER.error("Error getting product with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Add a new product endpoint
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@ModelAttribute Product product,
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


    //Transactions Controller

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("userId") long userId) {
        try {
            List<Transaction> transactions = transactionsDAO.getTransactions(userId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<String> addTransactions(@RequestBody List<Transaction> transactions) {
        if(transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be empty!");
        }

        if(transactions.get(0) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be null!");
        }


        User user = usersDAO.get(transactions.get(0).getUserId());

        try {
            //Check up;
            BigDecimal sum = BigDecimal.ZERO;
            for (Transaction transaction : transactions) {
                if(transaction == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be null!");
                }

                User u = usersDAO.get(transaction.getUserId());
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




    //Favourites Controller

    @PostMapping("/favourites")
    public ResponseEntity<String> addFavourite(@RequestBody Favourite f) {
        if (favouritesDAO.exists(f)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data already exists");
        }

        favouritesDAO.add(f);
        return ResponseEntity.status(HttpStatus.CREATED).body("Data added successfully");
    }

    @PutMapping("/favourites")
    public ResponseEntity<String> updateFavourite(@RequestBody Favourite f) {
        if (!favouritesDAO.exists(f)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }

        favouritesDAO.update(f);
        return ResponseEntity.status(HttpStatus.OK).body("Data updated successfully");
    }

    @DeleteMapping("/favourites")
    public ResponseEntity<String> removeFavourite(@RequestBody Favourite f) {
        if (!favouritesDAO.exists(f)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }

        favouritesDAO.remove(f);
        return ResponseEntity.status(HttpStatus.OK).body("Data removed successfully");
    }

    @GetMapping("/favourites/{userId}")
    public ResponseEntity<List<Favourite>> getFavourites(@PathVariable("userId") int userId) {
        List<Favourite> favourites = this.favouritesDAO.getAll(userId);

//        if (favourites.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
//        }

        return ResponseEntity.status(HttpStatus.OK).body(favourites);
    }

    @GetMapping("/favourites/count/{productId}")
    public ResponseEntity<Long> getCountOfaFavourite(@PathVariable("productId") long id) {
        long count = favouritesDAO.getCountOfProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }



    //Traces Controller

    @PostMapping("/traces")
    public ResponseEntity<String> add(@RequestBody Trace trace) {
        if (tracesDAO.exists(trace)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data already exists");
        }

        tracesDAO.add(trace);
        return ResponseEntity.status(HttpStatus.CREATED).body("Data added/updated successfully");
    }

    @PutMapping("/traces")
    public ResponseEntity<String> updateTrace(@RequestBody Trace trace) {
        if (tracesDAO.exists(trace)) {
            tracesDAO.update(trace);
            return ResponseEntity.status(HttpStatus.OK).body("Data updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

    @DeleteMapping("/traces")
    public ResponseEntity<String> removeTrace(@RequestBody Trace trace) {
        if (tracesDAO.exists(trace)) {
            tracesDAO.remove(trace);
            return ResponseEntity.status(HttpStatus.OK).body("Data removed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
        }
    }

    @GetMapping("/traces/{userId}")
    public ResponseEntity<List<Trace>> getTraces(@PathVariable("userId") long userId) {
        try {
            List<Trace> tracesList = tracesDAO.getAll(userId);
            return ResponseEntity.ok(tracesList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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
