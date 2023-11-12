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
        String query = "SELECT * FROM visited WHERE user=?";
        List<VisitedProduct> visitedProducts = jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) -> {
            VisitedProduct visitedProduct = new VisitedProduct();
            visitedProduct.setUser(rs.getLong("user"));
            visitedProduct.setProduct(rs.getLong("product"));
            return visitedProduct;
        });

        return visitedProducts;
    }


    public void add(VisitedProduct visitedProduct) {
        if (!isExists(visitedProduct)) {
            String addToVisitedSql = "INSERT INTO visited (user, product) VALUES (?, ?)";
            jdbcTemplate.update(addToVisitedSql, visitedProduct.getUser(), visitedProduct.getProduct());
        }
    }

    public void remove(VisitedProduct visitedProduct) {
        if(isExists(visitedProduct)){
            String removeFromVisitedSql = "DELETE FROM visited WHERE user = ? AND product = ?";
            jdbcTemplate.update(removeFromVisitedSql, visitedProduct.getUser(), visitedProduct.getProduct());
        }
    }

    public boolean isExists(VisitedProduct visitedProduct) {
        String checkIfExistsSql = "SELECT COUNT(*) FROM visited WHERE user = ? AND product = ?";
        int count = jdbcTemplate.queryForObject(checkIfExistsSql, Integer.class, visitedProduct.getUser(), visitedProduct.getProduct());

        return count != 0;
    }

    public long getCountOfProduct(long productId) {
        String checkIfExistsSql = "SELECT COUNT(*) FROM visited WHERE product = ?";
        int count = jdbcTemplate.queryForObject(checkIfExistsSql, Integer.class, productId);
        return count;
    }
}
