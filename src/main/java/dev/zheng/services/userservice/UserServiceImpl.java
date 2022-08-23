package dev.zheng.services.userservice;

import dev.zheng.doas.userdao.UserDao;
import dev.zheng.entities.User;

public class UserServiceImpl implements UserService{
    UserDao userDao;

    public UserServiceImpl(UserDao userDao){this.userDao = userDao;}
    @Override
    public User registerUser(User user) {
        user.setApproved(false);
        return userDao.createUser(user);
    }
}
