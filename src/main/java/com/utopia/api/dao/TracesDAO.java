package com.utopia.api.dao;

import com.utopia.api.entities.Trace;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
        try {
            String sql = "SELECT * FROM traces WHERE user_id=?";
            RowMapper<Trace> rowMapper = (rs, rowNum) -> {
                Trace trace = new Trace();
                trace.setUserId(rs.getLong("user_id"));
                trace.setProductId(rs.getLong("product_id"));
                trace.setDate(rs.getTimestamp("date"));
                return trace;
            };
            return jdbcTemplate.query(sql, rowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public Trace get(Long userId, Long productId) {
        String sql = "SELECT * FROM traces WHERE user_id = ? AND product_id = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Trace.class),
                    userId,
                    productId
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public void add(Trace trace) throws DataAccessException {
        String sql = "INSERT INTO traces (user_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, trace.getUserId(), trace.getProductId());
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
        try {
            String countByUserProductSql = "SELECT COUNT(*) FROM traces WHERE user_id = ? AND product_id = ?";
            Long count = jdbcTemplate.queryForObject(countByUserProductSql, Long.class, trace.getUserId(), trace.getProductId());
            return count != null && count > 0;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public long getCountOfProduct(long productId) throws DataAccessException {
        String countByProductSql = "SELECT COUNT(*) FROM traces WHERE product_id = ?";
        Long count = jdbcTemplate.queryForObject(countByProductSql, Long.class, productId);
        return Optional.ofNullable(count).orElse(0L);
    }
}
