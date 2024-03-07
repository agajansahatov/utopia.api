package com.utopia.api.controllers;

import com.utopia.api.dao.ProductsDAO;
import com.utopia.api.dao.TransactionsDAO;
import com.utopia.api.dao.UsersDAO;
import com.utopia.api.entities.Product;
import com.utopia.api.entities.Transaction;
import com.utopia.api.entities.User;
import com.utopia.api.utilities.JwtChecked;
import com.utopia.api.utilities.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
public class TransactionsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TracesController.class);
    private final TransactionsDAO transactionsDAO;
    private final JwtUtil jwtUtil;
    private final UsersDAO usersDAO;
    private final ProductsDAO productsDAO;

    @Autowired
    public TransactionsController(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil) {
        this.transactionsDAO = new TransactionsDAO(jdbcTemplate);
        this.jwtUtil = jwtUtil;
        this.usersDAO = new UsersDAO(jdbcTemplate);
        this.productsDAO = new ProductsDAO(jdbcTemplate);
    }

    // Get all transactions of a user endpoint
    // returns all the order transactions of a user
    @GetMapping("/transactions")
    public ResponseEntity<Object> getTransactions(@RequestHeader("x-auth-token") String token) {
        try {
            JwtChecked jwtChecked = jwtUtil.validate(token);
            if (!jwtChecked.isValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
            }

            long userId = jwtChecked.userId;
            List<Transaction> transactions = transactionsDAO.getTransactions(userId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            LOGGER.error("Error during get transactions: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }


//     Add all transactions of a user endpoint
//     this is used for purchasing items
    @PostMapping("/transactions")
    public ResponseEntity<String> addTransactions(@RequestHeader("x-auth-token") String token, @RequestBody List<Transaction> transactions) {
        //Check the user authorization
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        try {
            //Check if the user can do this transaction
            if(transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be empty!");
            }

            long userId = jwtChecked.userId;
            User user = usersDAO.getById(userId);
            BigDecimal sum = BigDecimal.ZERO;

            for (Transaction transaction : transactions) {
                if(transaction == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be null!");
                }

                if(transaction.getQuantity() < 1) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity must be greater than 0!");
                }

                if(!user.getId().equals(transaction.getUserId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All transactions must belong to the same user");
                }

                Product product = productsDAO.getProduct(transaction.getProductId());
                if(product == null){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Product with the id - " + transaction.getProductId() + " is not found!");
                }

                BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(transaction.getQuantity()));
                sum = sum.add(totalPrice);
                if (sum.compareTo(user.getBalance()) > 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Your Balance is not enough!");
                }
            }

            //If passes checkup, continue...
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
            LOGGER.error("Error during add transactions: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding transactions");
        }
    }
}
