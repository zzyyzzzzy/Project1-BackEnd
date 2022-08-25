package dev.zheng.doas.userdao;


import dev.zheng.entities.Complaint;
import dev.zheng.entities.Priority;
import dev.zheng.entities.User;
import dev.zheng.entities.UserTitle;
import dev.zheng.utils.ConnectionUtil;

import java.sql.*;
import java.util.List;

public class UserDaoPostgres implements UserDao {
//    id serial primary key,
//    fname varchar(50) not null,
//    lname varchar(50) not null,
//    user_name varchar(50) not null unique,
//    password varchar(50) not null,
//    title varchar(20) not null default 'CONSTITUENT',
//    approved boolean not null default false
    private User userBuilder(ResultSet rs) throws SQLException{
        User user = new User(rs.getInt("id"),rs.getString("fname"),
                rs.getString("lname"), rs.getString("user_name"),
                rs.getString("password"), UserTitle.valueOf(rs.getString("title")), rs.getBoolean("approved"));
        return user;
    }
    @Override
    public User createUser(User user) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into app_user values(default, ?, ?, ?, ?, default, default)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFname());
            ps.setString(2, user.getLname());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getPassword());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int generatedId = rs.getInt("id");
            user.setId(generatedId);
            return user;

        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Unable to create a user due to SQL exception occurred");
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from app_user where user_name=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = userBuilder(rs);
            return user;
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Unable to get a user due to SQL exception occurred");
            return null;
        }
    }

}
