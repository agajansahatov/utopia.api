package com.utopia.api.controllers;

import com.utopia.api.dao.ProductsDAO;
import com.utopia.api.dao.OrdersDAO;
import com.utopia.api.dao.UsersDAO;
import com.utopia.api.entities.Product;
import com.utopia.api.entities.Order;
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
public class OrdersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);
    private final OrdersDAO ordersDAO;
    private final JwtUtil jwtUtil;
    private final UsersDAO usersDAO;
    private final ProductsDAO productsDAO;

    @Autowired
    public OrdersController(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil) {
        this.ordersDAO = new OrdersDAO(jdbcTemplate);
        this.usersDAO = new UsersDAO(jdbcTemplate);
        this.productsDAO = new ProductsDAO(jdbcTemplate);
        this.jwtUtil = jwtUtil;
    }

    // Get all orders of a user endpoint
    @GetMapping("/orders/{userId}")
    public ResponseEntity<Object> getOrders(@RequestHeader("x-auth-token") String token,
                                            @PathVariable("userId") long userId) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            List<Order> orders = ordersDAO.getOrders(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            LOGGER.error("Error during get orders: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }


    // Add all orders of a user endpoint
    @PostMapping("/orders/{userId}")
    public ResponseEntity<String> addOrders(@RequestHeader("x-auth-token") String token,
                                            @PathVariable("userId") long userId,
                                            @RequestBody List<Order> orders) {
        //Check the user authorization
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            //Check if the user can add orders
            if(orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Orders cannot be empty!");
            }

            User user = usersDAO.getById(userId);
            BigDecimal sum = BigDecimal.ZERO;

            for (Order order : orders) {
                if(order == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An order cannot be null!");
                }

                if(order.getQuantity() < 1) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantity must be greater than 0!");
                }

                if(!user.getId().equals(order.getUserId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All orders must belong to the current user");
                }

                Product product = productsDAO.getProduct(order.getProductId());
                if(product == null){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Product with the id - " + order.getProductId() + " is not found!");
                }

                BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
                sum = sum.add(totalPrice);
                if (sum.compareTo(user.getBalance()) > 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Your Balance is not enough!");
                }
            }

            //If passes checkup, continue adding and updating user balance
            for (Order order : orders) {
                this.ordersDAO.add(order);
            }

            BigDecimal balance = user.getBalance();
            balance = balance.subtract(sum);
            user.setBalance(balance);
            usersDAO.update(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("Orders added successfully");
        } catch (Exception e) {
            LOGGER.error("Error during add orders: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding orders");
        }
    }
}
