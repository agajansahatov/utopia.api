package com.utopia.api.controllers;

import com.utopia.api.dao.TracesDAO;
import com.utopia.api.entities.Trace;
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

    @Autowired
    public TracesController(JdbcTemplate jdbcTemplate) {
        this.tracesDAO = new TracesDAO(jdbcTemplate);
    }

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
            LOGGER.error("Error during add trace: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
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
            LOGGER.error("Error during update trace", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
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
            LOGGER.error("Error during delete trace: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }

    // Get all traces of a user endpoint
    @GetMapping("/traces/{userId}")
    public ResponseEntity<Object> getTraces(@PathVariable("userId") long userId) {
        try {
            List<Trace> tracesList = tracesDAO.getAll(userId);
            return ResponseEntity.ok(tracesList);
        } catch (Exception e) {
            LOGGER.error("Error during get all traces of a user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving data from db!");
        }
    }

    // Get the count of all traces of a product
    // by how many people is this product is visited
    @GetMapping("/traces/count/{productId}")
    public ResponseEntity<Object> getCountOfaTrace(@PathVariable("productId") long id) {
        try {
            long count = tracesDAO.getCountOfProduct(id);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            LOGGER.error("Error during trace count: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving data from db!");
        }
    }
}
