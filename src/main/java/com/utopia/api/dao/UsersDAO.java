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
        String sql = "CALL add_user(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getContact(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.getBalance(),
                user.getCountry(),
                user.getProvince(),
                user.getCity(),
                user.getAddress());
    }

    public void delete(Long userId) throws DataAccessException {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public void update(User user) throws DataAccessException {
        String sql = "UPDATE users SET contact = ?, firstname = ?, lastname = ?, " +
                "country = ?, province = ?, city = ?, address = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getContact(),
                user.getFirstname(),
                user.getLastname(),
                user.getCountry(),
                user.getProvince(),
                user.getCity(),
                user.getAddress(),
                user.getId());
    }

    public void updateByAdmin(User user) throws DataAccessException {
        String sql = "UPDATE users SET contact = ?, password = ?, role_id = ?, firstname = ?, lastname = ?, " +
                "balance = ?, country = ?, province = ?, city = ?, address = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getContact(),
                user.getPassword(),
                user.getRole(),
                user.getFirstname(),
                user.getLastname(),
                user.getBalance(),
                user.getCountry(),
                user.getProvince(),
                user.getCity(),
                user.getAddress(),
                user.getId());
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setContact(rs.getString("contact"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getShort("role"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setCountry(rs.getString("country"));
        user.setProvince(rs.getString("province"));
        user.setCity(rs.getString("city"));
        user.setAddress(rs.getString("address"));
        user.setBalance(rs.getBigDecimal("balance"));
        user.setAuthTime(rs.getTimestamp("auth_time"));
        return user;
    }

    public User getById(long id) throws DataAccessException {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            RowMapper<User> rowMapper = (rs, rowNum) -> mapUser(rs);
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
            RowMapper<User> rowMapper = (rs, rowNum) -> mapUser(rs);
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

    public Short getRole(long userId) throws DataAccessException {
        try {
            String sql = "SELECT role_id FROM users WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, Short.class, userId);
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