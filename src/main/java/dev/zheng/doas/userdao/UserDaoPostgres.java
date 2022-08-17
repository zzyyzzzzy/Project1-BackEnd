package dev.zheng.doas.userdao;


import dev.zheng.entities.User;
import dev.zheng.utils.ConnectionUtil;

import java.sql.*;
import java.util.List;

public class UserDaoPostgres implements UserDao {
    @Override
    public User createUser(User user) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into users values(default, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getTitle().toString());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int generatedId = rs.getInt("id");
            user.setId(generatedId);
            return user;

        }catch (SQLException err){
            System.out.println("Unable to create a user due to SQL exception occurred");
            return null;
        }
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User getOneUser(int id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public boolean deleteUser(int id) {
        return false;
    }
}
