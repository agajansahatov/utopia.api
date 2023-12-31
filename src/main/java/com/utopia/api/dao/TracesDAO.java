package com.utopia.api.dao;

import com.utopia.api.entities.Trace;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;
import java.util.Optional;

public class TracesDAO {
    private final JdbcTemplate jdbcTemplate;

    public TracesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Trace> getAll(long userId) throws DataAccessException {
        String sql = "SELECT * FROM traces WHERE user_id=?";
        RowMapper<Trace> rowMapper = (rs, rowNum) -> {
            Trace trace = new Trace();
            trace.setUserId(rs.getLong("user_id"));
            trace.setProductId(rs.getLong("product_id"));
            trace.setDate(rs.getTimestamp("date"));
            return trace;
        };

        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public void add(Trace trace) throws DataAccessException {
        String sql = "INSERT INTO traces (user_id, product_id, date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, trace.getUserId(), trace.getProductId(), trace.getDate());
    }

    public void update(Trace trace) throws DataAccessException {
        String sql = "UPDATE traces SET date = NOW() WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, trace.getUserId(), trace.getProductId());
    }

    public void remove(Trace trace) throws DataAccessException {
        String sql = "DELETE FROM traces WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, trace.getUserId(), trace.getProductId());
    }

    public boolean exists(Trace trace) throws DataAccessException {
        String countByUserProductSql = "SELECT COUNT(*) FROM traces WHERE user_id = ? AND product_id = ?";
        Long count = jdbcTemplate.queryForObject(countByUserProductSql, Long.class, trace.getUserId(), trace.getProductId());
        return Optional.ofNullable(count).orElse(0L) != 0;
    }

    public long getCountOfProduct(long productId) throws DataAccessException {
        String countByProductSql = "SELECT COUNT(*) FROM traces WHERE product_id = ?";
        Long count = jdbcTemplate.queryForObject(countByProductSql, Long.class, productId);
        return Optional.ofNullable(count).orElse(0L);
    }
}
