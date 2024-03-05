package com.utopia.api.dao;

import com.utopia.api.entities.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;

public class UsersDAO {
    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(User user) throws DataAccessException {
        String sql = "INSERT INTO users (name, contact, image, password, address, balance) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getContact(),
                user.getImage(),
                user.getPassword(),
                user.getAddress(),
                user.getBalance());
    }

    public void update(User user) throws DataAccessException {
        String sql = "UPDATE users SET name = ?, contact = ?, image = ?, " +
                "password = ?, address = ?, balance = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getContact(),
                user.getImage(),
                user.getPassword(),
                user.getAddress(),
                user.getBalance(),
                user.getId());
    }

    public User getById(long id) throws DataAccessException {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";

            RowMapper<User> rowMapper = (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setContact(rs.getString("contact"));
                user.setImage(rs.getString("image"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setBalance(rs.getBigDecimal("balance"));
                return user;
            };

            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }


    public User getByContact(String contact) throws DataAccessException {
        try {
            String sql = "SELECT * FROM users WHERE contact = ?";

            RowMapper<User> rowMapper = (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setContact(rs.getString("contact"));
                user.setImage(rs.getString("image"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setBalance(rs.getBigDecimal("balance"));
                return user;
            };

            return jdbcTemplate.queryForObject(sql, rowMapper, contact);
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null when user with the given contact is not found
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public boolean exists(String contact) throws DataAccessException {
        String sql = "SELECT COUNT(*) FROM users WHERE contact = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, contact);
        return Optional.ofNullable(count).orElse(0L) > 0;
    }

    public boolean exists(long id) throws DataAccessException {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, id);
        return count != null && count > 0;
    }
}