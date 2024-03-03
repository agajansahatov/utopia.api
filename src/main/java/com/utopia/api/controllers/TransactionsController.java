package com.utopia.api.controllers;

import com.utopia.api.dao.TransactionsDAO;
import com.utopia.api.entities.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin
public class TransactionsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TracesController.class);
    private final TransactionsDAO transactionsDAO;

    @Autowired
    public TransactionsController(JdbcTemplate jdbcTemplate) {
        this.transactionsDAO = new TransactionsDAO(jdbcTemplate);
    }

    // Get all transactions of a user endpoint
    // returns all the order transactions of a user
    @GetMapping("/transactions/{userId}")
    public ResponseEntity<Object> getTransactions(@PathVariable("userId") long userId) {
        try {
            List<Transaction> transactions = transactionsDAO.getTransactions(userId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            LOGGER.error("Error during get transactions: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in retrieving data from db!");
        }
    }

    // Add all transactions of a user endpoint
    // this is used for purchasing items
//    @PostMapping("/transactions")
//    public ResponseEntity<String> addTransactions(@RequestBody List<Transaction> transactions) {
//        if(transactions.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be empty!");
//        }
//
//        if(transactions.get(0) == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be null!");
//        }
//
//        User user = usersDAO.getById(transactions.get(0).getUserId());
//
//        try {
//            //Check up;
//            BigDecimal sum = BigDecimal.ZERO;
//            for (Transaction transaction : transactions) {
//                if(transaction == null) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transactions cannot be null!");
//                }
//
//                User u = usersDAO.getById(transaction.getUserId());
//                if(!user.getId().equals(u.getId())) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                            .body("All transactions should belong to one person at the time!");
//                }
//
//                Product product = productsDAO.getProduct(transaction.getProductId());
//                BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(transaction.getQuantity()));
//                sum = sum.add(totalPrice);
//                if (sum.compareTo(user.getBalance()) > 0) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                            .body("Your Balance is not enough!");
//                }
//            }
//
//            //If passes checkup, continue.
//            //Add transactions to the database
//            for (Transaction transaction : transactions) {
//                this.transactionsDAO.add(transaction);
//            }
//            //Update the user balance
//            BigDecimal balance = user.getBalance();
//            balance = balance.subtract(sum);
//            user.setBalance(balance);
//            usersDAO.update(user);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body("Transactions added successfully");
//        } catch (Exception e) {
//            LOGGER.error("Error during add transactions: ", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding transactions");
//        }
//    }
}
