package api.utopia.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import api.utopia.entities.User;
import java.util.List;

public class UserController {
    private List<User> users;
    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        String query = "SELECT * FROM users";
        this.users = jdbcTemplate.query(query, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setContact(rs.getString("contact"));
            user.setImage(rs.getString("image"));
            user.setPassword(rs.getString("password"));
            user.setAddress(rs.getString("address"));
            user.setBalance(rs.getString("balance"));
            return user;
        });
    }

    public User addUser(User user) {
        user.setBalance("999");
        for (User u : users){
            if(u.getContact().equals(user.getContact())){
                return null;
            }
        }

        String query = "INSERT INTO users (name, contact, image, password, address, balance) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(query,
                user.getName(),
                user.getContact(),
                user.getImage(),
                user.getPassword(),
                user.getAddress(),
                user.getBalance()
        );
        if (rowsAffected > 0){
            user.setId(users.size() + 1);
            users.add(user);
            return user;
        }

        return null;
    }

    public User updateUser(User user) {

        String sql = "UPDATE users " +
                "SET name=?, contact=?, image=?, password=?, address=?, balance=? " +
                "WHERE id=?";
        int rowsAffected = jdbcTemplate.update(sql,
                user.getName(),
                user.getContact(),
                user.getImage(),
                user.getPassword(),
                user.getAddress(),
                user.getBalance(),
                user.getId()
        );
        if(rowsAffected > 0) {
            for (User u : users){
                if(u.getId() == user.getId()) {
                    users.remove(u);
                    users.add(user);
                    return user;
                }
            }
        }

        return null;
    }

    public User exists(String contact, String password) {
        for (User u : users){
            if(u.getContact().equals(contact) && u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
    }

    public User getUser(long id) {
        for (User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

}
