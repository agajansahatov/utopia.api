package api.utopia.controllers;

import org.springframework.jdbc.core.JdbcTemplate;

public class VisitedController {
    private final JdbcTemplate jdbcTemplate;
    public VisitedController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

//    public List<VisitedProduct> getFavourites(long userId) {
//        String query = "SELECT * FROM visited WHERE user=" + userId;
//        return jdbcTemplate.query(query, (rs, rowNum) -> {
//            VisitedProduct visitedProduct = new VisitedProduct();
//            visitedProduct.setId(rs.getLong("id"));
//            visitedProduct.setUser(rs.getLong("user"));
//            visitedProduct.setProduct(rs.getLong("product"));
//            return visitedProduct;
//        });
//    }
}
