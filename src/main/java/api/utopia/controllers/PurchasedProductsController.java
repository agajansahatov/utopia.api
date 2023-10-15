package api.utopia.controllers;

import api.utopia.entities.PurchasedProduct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class PurchasedProductsController {
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public PurchasedProductsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PurchasedProduct> getProducts(long userId) {
        String sql = "SELECT * FROM purchased_products WHERE user = ?";
        List<PurchasedProduct> products = jdbcTemplate.query(sql, new Object[]{userId}, rs -> {
            List<PurchasedProduct> productList = new ArrayList<>();
            while (rs.next()) {
                PurchasedProduct product = new PurchasedProduct();
                product.setId(rs.getLong("id"));
                product.setUser(rs.getLong("user"));
                product.setProduct(rs.getLong("product"));
                product.setDestination(rs.getString("destination"));
                product.setQuantity(rs.getLong("quantity"));
                product.setStatus(rs.getString("status"));
                product.setDate(rs.getString("date"));
                productList.add(product);
            }
            return productList;
        });
        return products;
    }

    public void addNewProduct(PurchasedProduct purchasedProduct) {
        String sql = "INSERT INTO purchased_products (user, product, destination, quantity, status, date) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                purchasedProduct.getUser(),
                purchasedProduct.getProduct(),
                purchasedProduct.getDestination(),
                purchasedProduct.getQuantity(),
                purchasedProduct.getStatus(),
                purchasedProduct.getDate()
        );
    }
}
