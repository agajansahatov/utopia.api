package com.utopia.api.dao;

import com.utopia.api.entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.util.Optional;

public class UsersDAO {
    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(User user) {
        if(user.getBalance() == null) {
            user.setBalance(BigDecimal.valueOf(10000));
        }
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

    public void update(User user) {
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

    public User get(long id) {
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
    }

    public boolean exists(String contact, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE contact = ? AND password = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, contact, password);
        return Optional.ofNullable(count).orElse(0L) > 0;
    }

    public long getUserId(String contact, String password) {
        String sql = "SELECT id FROM users WHERE contact = ? AND password = ?";
        Long id = jdbcTemplate.queryForObject(sql, Long.class, contact, password);
        return Optional.ofNullable(id).orElse(-1L);
    }

}