package dev.zheng.doas.userdao;

import dev.zheng.entities.User;

import java.util.List;

public interface UserDao {
    // create
    User createUser(User user);

    // update
    User updateUser(User user);

    // read
    User getOneUser(int id);
    List<User> getAllUsers();

    // delete
    boolean deleteUser(int id);
}
