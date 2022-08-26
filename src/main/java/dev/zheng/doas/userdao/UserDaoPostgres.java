package dev.zheng.doas.userdao;


import dev.zheng.entities.Complaint;
import dev.zheng.entities.Priority;
import dev.zheng.entities.User;
import dev.zheng.entities.UserTitle;
import dev.zheng.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoPostgres implements UserDao {
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
    public List<User> getAllUsers() {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from app_user";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<User> allUsers = new ArrayList<>();
            while(rs.next()){
                User user = userBuilder(rs);
                allUsers.add(user);
            }
            return allUsers;
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Unable to get all users due to SQL exception occurred");
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

    @Override
    public Map<String, String> updateUserStatus(int userId, boolean isApproved) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "update app_user set approved=? where id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, isApproved);
            ps.setInt(2, userId);
            Map<String, String> resMap = new HashMap<>();
            if(ps.executeUpdate() > 0){
                resMap.put("id", Integer.toString(userId));
                resMap.put("approved", String.valueOf(isApproved));
                return resMap;
            } else{
                System.out.println("Update Failed");
                return null;
            }
        }catch (SQLException err){
            err.printStackTrace();
            System.out.println("Unable to change the account status due to SQL exception occurred");
            return null;
        }
    }


}
