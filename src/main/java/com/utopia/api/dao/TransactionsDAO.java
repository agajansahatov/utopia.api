package com.utopia.api.dao;

import com.utopia.api.entities.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class TransactionsDAO {
    private final JdbcTemplate jdbcTemplate;

    //Constructor
    public TransactionsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Transaction> getTransactions(long userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ?";

        RowMapper<Transaction> rowMapper = (rs, rowNum) -> {
            Transaction transaction = new Transaction();
            transaction.setId(rs.getLong("id"));
            transaction.setUserId(rs.getLong("user_id")); // Corrected column name
            transaction.setProductId(rs.getLong("product_id"));
            transaction.setDestination(rs.getString("destination"));
            transaction.setQuantity(rs.getInt("quantity"));
            transaction.setStatus(rs.getString("status"));
            transaction.setDate(rs.getTimestamp("date"));
            return transaction;
        };

        return jdbcTemplate.query(sql, rowMapper, userId);
    }


    public void add(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, product_id, destination, quantity, status) "
                + "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                transaction.getUserId(),
                transaction.getProductId(),
                transaction.getDestination(),
                transaction.getQuantity(),
                transaction.getStatus()
        );
    }
}
