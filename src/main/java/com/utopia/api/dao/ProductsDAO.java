package com.utopia.api.dao;

import com.utopia.api.entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class ProductsDAO {
    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public ProductsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static void validateProduct(Product product) throws IllegalArgumentException {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (isNullOrEmpty(product.getName())) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price must be a positive value.");
        }
        if (isNullOrEmpty(product.getCategory())) {
            throw new IllegalArgumentException("Product category cannot be null or empty.");
        }
        if (product.getDate() != null && product.getDate().after(new Date())) {
            throw new IllegalArgumentException("Product date cannot be in the future.");
        }
    }

    public long getSize() {
        String sql = "SELECT COUNT(*) FROM products";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return Optional.ofNullable(count).orElse(0L);
    }

    public Product add(Product product) {
        String sql = "INSERT INTO products (image_name, name, price, category, description) " +
                "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, product.getImageName());
                        ps.setString(2, product.getName());
                        ps.setBigDecimal(3, product.getPrice());
                        ps.setString(4, product.getCategory());
                        ps.setString(5, product.getDescription());
                        return ps;
                    }
                },
                keyHolder
        );

        Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        product.setId(generatedId);

        return product;
    }

    public List<Product> getProducts() {
        String sql = "SELECT * FROM products";
        RowMapper<Product> rowMapper = (rs, rowNum) -> mapProduct(rs);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Product getProduct(long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        RowMapper<Product> rowMapper = (rs, rowNum) -> mapProduct(rs);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setImageName(rs.getString("image_name"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setCategory(rs.getString("category"));
        product.setDescription(rs.getString("description"));
        product.setDate(rs.getTimestamp("date"));
        return product;
    }

}
