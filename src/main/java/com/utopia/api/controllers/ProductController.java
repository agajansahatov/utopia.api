package com.utopia.api.controllers;

import com.utopia.api.entities.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ProductController {
    private List<Product> products;
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public ProductController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        String query = "SELECT * FROM products";
        this.products = jdbcTemplate.query(query, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setImage(rs.getString("image"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getString("price"));
            product.setDescription(rs.getString("description"));
            product.setCategory(rs.getString("category"));
            product.setPopularity(rs.getString("popularity"));
            product.setDate(rs.getString("date"));
            return product;
        });
    }

    public Product addProduct(Product product) {
        String query = "INSERT INTO products (image, name, price, description, category, popularity, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            int rowsAffected = jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(1, product.getImage());
                            ps.setString(2, product.getName());
                            ps.setString(3, product.getPrice());
                            ps.setString(4, product.getDescription());
                            ps.setString(5, product.getCategory());
                            ps.setString(6, product.getPopularity());
                            ps.setString(7, product.getDate());
                            return ps;
                        }
                    },
                    keyHolder
            );

            if (rowsAffected > 0) {
                // Retrieve the generated key and set it to the product
                product.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

                // Update the 'products' list
                products.add(product);

                return product;
            }
        } catch (DataAccessException e) {
            // Handle the exception (e.g., log, rethrow, return null, etc.)
            e.printStackTrace();
        }

        return null;
    }


    public List<Product> getProducts() {
        List<Product> allProducts = new ArrayList<>();

        for (Product product : products) {
            // Create a new instance of Product with the modified description
            String description = product.getDescription();
            if (description.length() > 51) {
                description = description.substring(0, 51);
            }
            Product modifiedProduct = new Product(
                    product.getId(),
                    product.getImage(),
                    product.getName(),
                    product.getPrice(),
                    description,
                    product.getCategory(),
                    product.getPopularity(),
                    product.getDate()
            );

            // Add the modified product to the allProducts list
            allProducts.add(modifiedProduct);
        }

        return allProducts;
    }



    public Product getProduct(long id){
        for (Product product: products) {
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }

    public long getSize() {
        return products.size();
    }
}
