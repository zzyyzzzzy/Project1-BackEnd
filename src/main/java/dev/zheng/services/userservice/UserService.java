package dev.zheng.services.userservice;

import dev.zheng.entities.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User registerUser(User user);
    List<User> retrieveAllUsers();
    User validateUser(String username, String password);
    Map<String, String> patchUserStatus(int userId, String status);
}
