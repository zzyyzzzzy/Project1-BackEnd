package dev.zheng.doas.userdao;

import dev.zheng.entities.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    // create
    User createUser(User user);
    List<User> getAllUsers();
    User getUserByUsername(String username);
    Map<String, String> updateUserStatus(int userId, boolean isApproved);
}
