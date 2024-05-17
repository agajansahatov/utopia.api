package com.utopia.api.dao;

import com.utopia.api.entities.Order;
import com.utopia.api.entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
public class OrdersDAO {
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public OrdersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setUserId(rs.getLong("user_id"));
        order.setProductId(rs.getLong("product_id"));
        order.setQuantity(rs.getInt("quantity"));
        order.setOrder_date(rs.getTimestamp("order_date"));
        order.setShipped_date(rs.getTimestamp("shipped_date"));
        order.setShipper_id(rs.getShort("shipper_id"));
        order.setPayment_method_id(rs.getShort("payment_method_id"));
        order.setStatus_id(rs.getShort("status_id"));
        return order;
    }

    public List<Order> getOrders(long userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        RowMapper<Order> rowMapper = (rs, rowNum) -> mapOrder(rs);
        return jdbcTemplate.query(sql, rowMapper, userId);
    }


    public void add(Order order) {
        String sql = "INSERT INTO orders (user_id, product_id, quantity, order_date, shipped_date, "
                + "shipper_id, payment_method_id, status_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                order.getUserId(),
                order.getProductId(),
                order.getQuantity(),
                order.getOrder_date(),
                order.getShipped_date(),
                order.getShipper_id(),
                order.getPayment_method_id(),
                order.getStatus_id()
        );
    }
}
