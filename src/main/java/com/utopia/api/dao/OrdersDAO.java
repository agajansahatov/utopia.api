package com.utopia.api.dao;

import com.utopia.api.entities.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class OrdersDAO {
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public OrdersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Order> getOrders(long userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";

        RowMapper<Order> rowMapper = (rs, rowNum) -> {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setUserId(rs.getLong("user_id")); // Corrected column name
            order.setProductId(rs.getLong("product_id"));
            order.setPayment_method(rs.getString("destination"));
            order.setQuantity(rs.getInt("quantity"));
            order.setStatus(rs.getString("status"));
            order.setOrder_date(rs.getTimestamp("date"));
            return order;
        };

        return jdbcTemplate.query(sql, rowMapper, userId);
    }


    public void add(Order order) {
        String sql = "INSERT INTO orders (user_id, product_id, destination, quantity, status) "
                + "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                order.getUserId(),
                order.getProductId(),
                order.getPayment_method(),
                order.getQuantity(),
                order.getStatus()
        );
    }
}
