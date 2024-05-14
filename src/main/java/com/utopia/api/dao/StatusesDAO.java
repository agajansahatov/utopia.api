package com.utopia.api.dao;

import com.utopia.api.entities.Status;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StatusesDAO {
    private final JdbcTemplate jdbcTemplate;

    public StatusesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Status mapStatus(ResultSet rs) throws SQLException {
        Status status = new Status();
        status.setId(rs.getShort("id"));
        status.setName(rs.getString("name"));
        return status;
    }

    public List<Status> getAll() throws DataAccessException {
        try {
            String sql = "SELECT * FROM statuses";
            RowMapper<Status> rowMapper = (rs, rowNum) -> mapStatus(rs);
            return jdbcTemplate.query(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }
}
