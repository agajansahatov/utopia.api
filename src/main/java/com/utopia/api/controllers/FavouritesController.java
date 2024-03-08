package com.utopia.api.controllers;

import com.utopia.api.dao.FavouritesDAO;
import com.utopia.api.dao.ProductsDAO;
import com.utopia.api.entities.Favourite;
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
public class FavouritesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TracesController.class);
    private final FavouritesDAO favouritesDAO;
    private final ProductsDAO productsDAO;
    private final JwtUtil jwtUtil;

    @Autowired
    public FavouritesController(JdbcTemplate jdbcTemplate, JwtUtil jwtUtil) {
        this.favouritesDAO = new FavouritesDAO(jdbcTemplate);
        this.productsDAO = new ProductsDAO(jdbcTemplate);
        this.jwtUtil = jwtUtil;
    }

    // Add favourite endpoint
    // when a user likes a product, then it will be added to the favourites table
    @PostMapping("/favourites/{userId}")
    public ResponseEntity<Object> addFavourite(@RequestHeader("x-auth-token") String token,
                                               @PathVariable("userId") long userId,
                                               @RequestBody Favourite f) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId || userId != f.getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            if(!productsDAO.exists(f.getProductId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product " + f.getProductId() + " not found");
            }

            if (favouritesDAO.exists(f)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Favourite already exists");
            }

            // Only userId and productId is included in "f", the date is null
            // The date will be set by database
            favouritesDAO.add(f);
            Favourite addedFavourite = favouritesDAO.get(f.getUserId(), f.getProductId());

            if (addedFavourite != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(addedFavourite);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve the added favourite");
            }
        }catch (Exception e){
            LOGGER.error("Error during add favourite: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding favourite!");
        }
    }

    // Update favourite endpoint
    @PutMapping("/favourites/{userId}")
    public ResponseEntity<Object> updateFavourite(@RequestHeader("x-auth-token") String token,
                                                  @PathVariable("userId") long userId,
                                                  @RequestBody Favourite f) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId || userId != f.getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            if(!productsDAO.exists(f.getProductId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product " + f.getProductId() + " not found");
            }

            if (!favouritesDAO.exists(f)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This favourite object is not found");
            }

            // Only userId and productId is included in "f", the date is null
            // The date will be updated by database
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

    // Delete favourite endpoint
    // This can be used when a user dislikes a product
    @DeleteMapping("/favourites/{userId}")
    public ResponseEntity<Object> removeFavourite(@RequestHeader("x-auth-token") String token,
                                                  @PathVariable("userId") long userId,
                                                  @RequestBody Favourite f) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId || userId != f.getUserId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            if (!favouritesDAO.exists(f)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This favourite object is not found");
            }

            favouritesDAO.remove(f);

            // In a DELETE operation, you may choose not to retrieve the deleted object.
            // Here, I'm returning a generic success message.
            return ResponseEntity.status(HttpStatus.OK).body("This favourite object is removed successfully");
        } catch (Exception e) {
            LOGGER.error("Error during delete favourite: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when interacting with db!");
        }
    }

    // Get all favourites of a user endpoint
    @GetMapping("/favourites/{userId}")
    public ResponseEntity<Object> getFavourites(@RequestHeader("x-auth-token") String token,
                                                @PathVariable("userId") long userId) {
        JwtChecked jwtChecked = jwtUtil.validate(token);
        if (!jwtChecked.isValid || userId != jwtChecked.userId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid token or userId");
        }

        try {
            List<Favourite> favourites = this.favouritesDAO.getAll(userId);
            return ResponseEntity.status(HttpStatus.OK).body(favourites);
        } catch (Exception e) {
            LOGGER.error("Error during get all favourites of a user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting all favourites");
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting count of likes of this product!");
        }
    }
}
