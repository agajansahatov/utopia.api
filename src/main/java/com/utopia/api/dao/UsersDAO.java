package com.utopia.api.dao;

import com.utopia.api.entities.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UsersDAO {
    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(User user) throws DataAccessException {
        String sql = "INSERT INTO users (name, contact, image, password, address, balance, role, auth_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getContact(),
                user.getImage(),
                user.getPassword(),
                user.getAddress(),
                user.getBalance(),
                user.getRole(),
                new Timestamp((new Date()).getTime()));
    }

    public void delete(User user) throws DataAccessException {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, user.getId());
    }

    public void update(User user) throws DataAccessException {
        String sql = "UPDATE users SET name = ?, contact = ?, image = ?, " +
                "password = ?, address = ?, balance = ?, role = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getContact(),
                user.getImage(),
                user.getPassword(),
                user.getAddress(),
                user.getBalance(),
                user.getRole(),
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
                user.setRole(rs.getString("role"));
                user.setAuthTime(rs.getTimestamp("auth_time"));
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
                user.setRole(rs.getString("role"));
                user.setAuthTime(rs.getTimestamp("auth_time"));
                return user;
            };

            return jdbcTemplate.queryForObject(sql, rowMapper, contact);
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null when user with the given contact is not found
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public List<User> getUsers() {
        try {
            String sql = "SELECT * FROM users";
            RowMapper<User> rowMapper = (rs, rowNum) -> mapUser(rs);
            return jdbcTemplate.query(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setContact(rs.getString("contact"));
        user.setRole(rs.getString("role"));
        user.setImage(rs.getString("image"));
        user.setPassword(rs.getString("password"));
        user.setAddress(rs.getString("address"));
        user.setBalance(rs.getBigDecimal("balance"));
        user.setAuthTime(rs.getTimestamp("auth_time"));
        return user;
    }

    public boolean exists(String contact) throws DataAccessException {
        try {
            String sql = "SELECT COUNT(*) FROM users WHERE contact = ?";
            Long count = jdbcTemplate.queryForObject(sql, Long.class, contact);
            return Optional.ofNullable(count).orElse(0L) > 0;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public boolean exists(long id) throws DataAccessException {
        try {
            String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
            Long count = jdbcTemplate.queryForObject(sql, Long.class, id);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public long getUserCount() throws DataAccessException {
        try {
            String sql = "SELECT COUNT(*) FROM users";
            Long count = jdbcTemplate.queryForObject(sql, Long.class);
            return Optional.ofNullable(count).orElse(0L);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public String getRole(long userId) throws DataAccessException {
        try {
            String sql = "SELECT role FROM users WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, String.class, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public void setAuthTime(long userId, Timestamp authTime) throws DataAccessException {
        try {
            String sql = "UPDATE users SET auth_time = ? WHERE id = ?";
            jdbcTemplate.update(sql, authTime, userId);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public Timestamp getAuthTime(long userId) throws DataAccessException {
        try {
            String sql = "SELECT auth_time FROM users WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, Timestamp.class, userId);
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null when user with the given id is not found
        } catch (DataAccessException e) {
            throw e;
        }
    }
}