package com.utopia.api.dao;

import com.utopia.api.entities.PaymentMethod;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PaymentMethodsDAO {
    private final JdbcTemplate jdbcTemplate;

    public PaymentMethodsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private PaymentMethod mapPaymentMethod(ResultSet rs) throws SQLException {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(rs.getShort("id"));
        paymentMethod.setName(rs.getString("name"));
        return paymentMethod;
    }

    public List<PaymentMethod> getAll() throws DataAccessException {
        try {
            String sql = "SELECT * FROM payment_methods";
            RowMapper<PaymentMethod> rowMapper = (rs, rowNum) -> mapPaymentMethod(rs);
            return jdbcTemplate.query(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }

}
