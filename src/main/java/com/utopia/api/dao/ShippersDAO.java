package com.utopia.api.dao;

import com.utopia.api.entities.Shipper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShippersDAO {
    private final JdbcTemplate jdbcTemplate;

    public ShippersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Shipper mapShipper(ResultSet rs) throws SQLException {
        Shipper shipper = new Shipper();
        shipper.setId(rs.getShort("id"));
        shipper.setName(rs.getString("name"));
        return shipper;
    }

    public List<Shipper> getAll() throws DataAccessException {
        try {
            String sql = "SELECT * FROM shippers";
            RowMapper<Shipper> rowMapper = (rs, rowNum) -> mapShipper(rs);
            return jdbcTemplate.query(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }
}
