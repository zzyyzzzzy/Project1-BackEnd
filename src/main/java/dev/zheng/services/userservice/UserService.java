package dev.zheng.services.userservice;

import dev.zheng.entities.User;

public interface UserService {
    User registerUser(User user);
    User validateUser(String username, String password);
}
