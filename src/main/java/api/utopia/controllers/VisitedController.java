package api.utopia.controllers;

import api.utopia.entities.VisitedProduct;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class VisitedController {
    private final JdbcTemplate jdbcTemplate;
    public VisitedController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<VisitedProduct> getAll(long userId) {
        String query = "SELECT * FROM visited WHERE user=" + userId;
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            VisitedProduct visitedProduct = new VisitedProduct();
            visitedProduct.setUser(rs.getLong("user"));
            visitedProduct.setProduct(rs.getLong("product"));
            return visitedProduct;
        });
    }

    public void add(VisitedProduct visitedProduct) {
        // Check if the row already exists in the table
        String checkIfExistsSql = "SELECT COUNT(*) FROM visited WHERE user = ? AND product = ?";
        int count = jdbcTemplate.queryForObject(checkIfExistsSql, Integer.class, visitedProduct.getUser(), visitedProduct.getProduct());

        if (count == 0) {
            // Row does not exist, so add it
            String addToVisitedSql = "INSERT INTO visited (user, product) VALUES (?, ?)";
            jdbcTemplate.update(addToVisitedSql, visitedProduct.getUser(), visitedProduct.getProduct());
        }
    }

    public void remove(VisitedProduct visitedProduct) {
        String checkIfExistsSql = "SELECT COUNT(*) FROM visited WHERE user = ? AND product = ?";
        int count = jdbcTemplate.queryForObject(checkIfExistsSql, Integer.class, visitedProduct.getUser(), visitedProduct.getProduct());

        if(count != 0){
            String removeFromVisitedSql = "DELETE FROM visited WHERE user = ? AND product = ?";
            jdbcTemplate.update(removeFromVisitedSql, visitedProduct.getUser(), visitedProduct.getProduct());
        }
    }
}
