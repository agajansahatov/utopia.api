package com.utopia.api.dao;

import com.utopia.api.entities.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RolesDAO {
    private final JdbcTemplate jdbcTemplate;

    public RolesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Role mapRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getShort("id"));
        role.setName(rs.getString("name"));
        return role;
    }

    public List<Role> getAll() throws DataAccessException {
        try {
            String sql = "SELECT * FROM roles";
            RowMapper<Role> rowMapper = (rs, rowNum) -> mapRole(rs);
            return jdbcTemplate.query(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }
}
