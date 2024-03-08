package com.utopia.api.controllers;

import com.utopia.api.dao.ProductsDAO;
import com.utopia.api.dao.TracesDAO;
import com.utopia.api.entities.Trace;
import com.utopia.api.utilities.JwtChecked;
import com.utopia.api.utilities.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
public class TracesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TracesController.class);
    private final TracesDAO tracesDAO;
    private final ProductsDAO productsDAO;
    private final JwtUtil jwtUtil;

    @Autowired
    public TracesController(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil) {
        this.tracesDAO = new TracesDAO(jdbcTemplate);
        this.productsDAO = new ProductsDAO(jdbcTemplate);
        this.jwtUtil = jwtUtil;
    }

    // Add trace endpoint
    @PostMapping("/traces/{userId}")
    public ResponseEntity<Object> addTrace(@RequestHeader("x-auth-token") String token,
                                           @PathVariable("userId") long userId,
                                           @RequestBody Trace trace) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId || userId != trace.getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            if(!productsDAO.exists(trace.getProductId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product " + trace.getProductId() + " not found");
            }

            if (tracesDAO.exists(trace)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Trace already exists");
            }

            tracesDAO.add(trace);
            Trace addedTrace = tracesDAO.get(trace.getUserId(), trace.getProductId());

            if (addedTrace != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(addedTrace);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve the added trace");
            }
        } catch (Exception e) {
            LOGGER.error("Error during add trace: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding trace!");
        }
    }

    // Update the date of a trace endpoint
    @PutMapping("/traces/{userId}")
    public ResponseEntity<Object> updateTrace(@RequestHeader("x-auth-token") String token,
                                              @PathVariable("userId") long userId,
                                              @RequestBody Trace trace) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId || userId != trace.getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            if(!productsDAO.exists(trace.getProductId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product " + trace.getProductId() + " not found");
            }

            if (!tracesDAO.exists(trace)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trace is not found");
            }

            tracesDAO.update(trace);
            Trace updatedTrace = tracesDAO.get(trace.getUserId(), trace.getProductId());

            if (updatedTrace != null) {
                return ResponseEntity.status(HttpStatus.OK).body(updatedTrace);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve the updated trace");
            }
        } catch (Exception e) {
            LOGGER.error("Error during update trace", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating trace!");
        }
    }

    // Delete a trace of a user endpoint
    @DeleteMapping("/traces/{userId}")
    public ResponseEntity<Object> removeTrace(@RequestHeader("x-auth-token") String token,
                                              @PathVariable("userId") long userId,
                                              @RequestBody Trace trace) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId || userId != trace.getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            if (!tracesDAO.exists(trace)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trace is not found");
            }

            tracesDAO.remove(trace);

            // In a DELETE operation, you may choose not to retrieve the deleted object.
            // Here, I'm returning a generic success message.
            return ResponseEntity.status(HttpStatus.OK).body("Data removed successfully");
        } catch (Exception e) {
            LOGGER.error("Error during delete trace: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting the trace!");
        }
    }

    // Get all traces of a user endpoint
    @GetMapping("/traces/{userId}")
    public ResponseEntity<Object> getTraces(@RequestHeader("x-auth-token") String token,
                                            @PathVariable("userId") long userId) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            List<Trace> tracesList = tracesDAO.getAll(userId);
            return ResponseEntity.ok(tracesList);
        } catch (Exception e) {
            LOGGER.error("Error during get all traces of a user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting traces!");
        }
    }

    // Get the count of all traces of a product
    // by how many people is this product is visited
    @GetMapping("/traces/count/{productId}")
    public ResponseEntity<Object> getCountOfaTrace(@PathVariable("productId") long id) {
        try {
            if(!productsDAO.exists(id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product " + id + " not found");
            }
            long count = tracesDAO.getCountOfProduct(id);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            LOGGER.error("Error during trace count: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting count of traces of this product!");
        }
    }
}
