package api.utopia.controllers;

import api.utopia.entities.FavouriteProduct;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class FavouritesController {
    List<FavouriteProduct> favouriteProducts;
    private final JdbcTemplate jdbcTemplate;
    public FavouritesController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        String query = "SELECT * FROM favourites";
        this.favouriteProducts =  jdbcTemplate.query(query, (rs, rowNum) -> {
            FavouriteProduct favouriteProduct = new FavouriteProduct();
            favouriteProduct.setUser(rs.getLong("user"));
            favouriteProduct.setProduct(rs.getLong("product"));
            return favouriteProduct;
        });
    }

    public List<FavouriteProduct> getAll(long userId) {
        String query = "SELECT * FROM favourites WHERE user=" + userId;
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            FavouriteProduct favouriteProduct = new FavouriteProduct();
            favouriteProduct.setUser(rs.getLong("user"));
            favouriteProduct.setProduct(rs.getLong("product"));
            return favouriteProduct;
        });
    }

    public void add(FavouriteProduct favouriteProduct) {
        // Check if the row already exists in the table
        String checkIfExistsSql = "SELECT COUNT(*) FROM favourites WHERE user = ? AND product = ?";
        int count = jdbcTemplate.queryForObject(checkIfExistsSql, Integer.class, favouriteProduct.getUser(), favouriteProduct.getProduct());

        if (count == 0) {
            // Row does not exist, so add it
            String addToFavouritesSql = "INSERT INTO favourites (user, product) VALUES (?, ?)";
            jdbcTemplate.update(addToFavouritesSql, favouriteProduct.getUser(), favouriteProduct.getProduct());
        }
    }

    public void remove(FavouriteProduct favouriteProduct) {
        String checkIfExistsSql = "SELECT COUNT(*) FROM favourites WHERE user = ? AND product = ?";
        int count = jdbcTemplate.queryForObject(checkIfExistsSql, Integer.class, favouriteProduct.getUser(), favouriteProduct.getProduct());

        if(count != 0){
            String removeFromFavouritesSql = "DELETE FROM favourites WHERE user = ? AND product = ?";
            jdbcTemplate.update(removeFromFavouritesSql, favouriteProduct.getUser(), favouriteProduct.getProduct());
        }
    }
}
