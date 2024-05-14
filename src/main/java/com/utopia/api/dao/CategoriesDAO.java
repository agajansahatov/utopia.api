package com.utopia.api.dao;

import com.utopia.api.entities.Category;
import com.utopia.api.entities.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoriesDAO {
    private final JdbcTemplate jdbcTemplate;

    public CategoriesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Category mapCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getShort("id"));
        category.setName(rs.getString("name"));
        return category;
    }

    public List<Category> getAll() throws DataAccessException {
        try {
            String sql = "SELECT * FROM categories";
            RowMapper<Category> rowMapper = (rs, rowNum) -> mapCategory(rs);
            return jdbcTemplate.query(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw e;
        }
    }
}
