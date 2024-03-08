package com.utopia.api.dao;

import com.utopia.api.entities.Favourite;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public class FavouritesDAO {
    private final JdbcTemplate jdbcTemplate;

    public FavouritesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Favourite> getAll(long userId) {
        String sql = "SELECT * FROM favourites WHERE user_id = ?";

        RowMapper<Favourite> rowMapper = (rs, rowNum) -> {
            Favourite favourite = new Favourite();
            favourite.setUserId(rs.getLong("user_id"));
            favourite.setProductId(rs.getLong("product_id"));
            favourite.setDate(rs.getTimestamp("date"));
            return favourite;
        };

        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public Favourite get(Long userId, Long productId) throws DataAccessException {
        String sql = "SELECT * FROM favourites WHERE user_id = ? AND product_id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Favourite.class),
                userId,
                productId
        );
    }

    public void add(Favourite favourite) throws DataAccessException {
        String sql = "INSERT INTO favourites (user_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, favourite.getUserId(), favourite.getProductId());
    }

    public void update(Favourite favourite) throws DataAccessException {
        String sql = "UPDATE favourites SET date = NOW() WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, favourite.getUserId(), favourite.getProductId());
    }


    public void remove(Favourite favourite) throws DataAccessException {
        String sql = "DELETE FROM favourites WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, favourite.getUserId(), favourite.getProductId());
    }

    public long getCountOfProduct(long productId) throws DataAccessException {
        String sql = "SELECT COUNT(*) FROM favourites WHERE product_id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, productId);
        return Optional.ofNullable(count).orElse(0L);
    }

    public boolean exists(Favourite favourite) throws DataAccessException {
        try {
            String sql = "SELECT COUNT(*) FROM favourites WHERE user_id = ? AND product_id = ?";
            Long count = jdbcTemplate.queryForObject(sql, Long.class,
                    favourite.getUserId(), favourite.getProductId());
            return count != null && count > 0;
        } catch (DataAccessException e) {
            throw e;
        }
    }
}
