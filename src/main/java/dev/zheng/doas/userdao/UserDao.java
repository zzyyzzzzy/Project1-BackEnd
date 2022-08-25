package dev.zheng.doas.userdao;

import dev.zheng.entities.User;

import java.util.List;

public interface UserDao {
    // create
    User createUser(User user);

    User getUserByUsername(String username);
}
