package com.utopia.api.controllers;

import com.utopia.api.dao.FavouritesDAO;
import com.utopia.api.entities.Favourite;
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
public class FavouritesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TracesController.class);
    private final FavouritesDAO favouritesDAO;

    @Autowired
    public FavouritesController(JdbcTemplate jdbcTemplate) {
        this.favouritesDAO = new FavouritesDAO(jdbcTemplate);
    }

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
            LOGGER.error("Error during add favourite: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
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
            LOGGER.error("Error during update favourite: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
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
            LOGGER.error("Error during delete favourite: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }

    // Get all favourites of a user endpoint
    @GetMapping("/favourites/{userId}")
    public ResponseEntity<Object> getFavourites(@PathVariable("userId") int userId) {
        try {
            List<Favourite> favourites = this.favouritesDAO.getAll(userId);
            return ResponseEntity.status(HttpStatus.OK).body(favourites);
        } catch (Exception e) {
            LOGGER.error("Error during get all favourites of a user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
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
            LOGGER.error("Error during favourite count: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }
}
