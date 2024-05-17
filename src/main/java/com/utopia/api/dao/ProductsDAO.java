package com.utopia.api.dao;

import com.utopia.api.entities.CategorizedProduct;
import com.utopia.api.entities.Product;
import com.utopia.api.entities.ProductInfo;
import com.utopia.api.utilities.Validator;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigDecimal;
import java.sql.*;
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

    public static Validator validateProduct(Product product) throws IllegalArgumentException {
        if (product == null) {
            return new Validator(false, "Product cannot be null.");
        }
        if (isNullOrEmpty(product.getTitle())) {
            return new Validator(false, "Product title cannot be null or empty.");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return new Validator(false, "Product price must be a positive value.");
        }

        return new Validator(true, "Valid Product");
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setTitle(rs.getString("title"));
        product.setPrice(rs.getBigDecimal("sales_price"));
        product.setDescription(rs.getString("description"));
        product.setDate(rs.getTimestamp("date"));
        product.setMedia(rs.getString("media"));
        return product;
    }

    private ProductInfo mapProductInfo(ResultSet rs) throws SQLException {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(rs.getLong("id"));
        productInfo.setTitle(rs.getString("title"));
        productInfo.setPrice(rs.getBigDecimal("sales_price"));
        productInfo.setDescription(rs.getString("description"));
        productInfo.setDate(rs.getTimestamp("date"));
        productInfo.setProperties(rs.getString("properties"));
        productInfo.setMedias(rs.getString("medias"));
        productInfo.setCategories(rs.getString("categories"));
        productInfo.setLikesCount(rs.getLong("likesCount"));
        productInfo.setVisitsCount(rs.getLong("visitsCount"));
        productInfo.setOrdersCount(rs.getLong("ordersCount"));
        productInfo.setCommentsCount(rs.getLong("commentsCount"));
        return productInfo;
    }

    public boolean exists(long id) throws DataAccessException {
        String sql = "SELECT COUNT(*) FROM products WHERE id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, id);
        return count != null && count > 0;
    }

    public List<Product> getProducts(int page, int amount, int category_id) {
        try {
            String sql = "CALL get_products(?, ?, ?);";
            RowMapper<Product> rowMapper = (rs, rowNum) -> mapProduct(rs);
            return jdbcTemplate.query(sql, rowMapper, page, amount, category_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public ProductInfo getProductInfo(long id) throws DataAccessException {
        try {
            String sql = "CALL get_product(?);";
            RowMapper<ProductInfo> rowMapper = (rs, rowNum) -> mapProductInfo(rs);
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public List<CategorizedProduct> getCategorizedProducts() throws DataAccessException {
        String sql = "SELECT * from categorized_products";

        RowMapper<CategorizedProduct> rowMapper = (rs, rowNum) -> {
            CategorizedProduct categorizedProduct = new CategorizedProduct();
            categorizedProduct.setProductId(rs.getLong("product_id"));
            categorizedProduct.setCategoryId(rs.getShort("category_id"));
            return categorizedProduct;
        };

        return jdbcTemplate.query(sql, rowMapper);
    }

//    public Product add(Product product) {
//        String sql = "INSERT INTO products (image_name, title, price, category, description) " +
//                "VALUES (?, ?, ?, ?, ?)";
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(
//                new PreparedStatementCreator() {
//                    @Override
//                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//                        ps.setString(1, product.getMedia());
//                        ps.setString(2, product.getTitle());
//                        ps.setBigDecimal(3, product.getPrice());
//                        ps.setString(4, product.getCategory());
//                        ps.setString(5, product.getDescription());
//                        return ps;
//                    }
//                },
//                keyHolder
//        );
//
//        Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
//        product.setId(generatedId);
//
//        return product;
//    }
}
