package dev.zheng.services.userservice;

import dev.zheng.doas.userdao.UserDao;
import dev.zheng.entities.User;
import dev.zheng.services.userservice.exceptions.PwdNotMatchException;
import dev.zheng.services.userservice.exceptions.UserNotFoundException;

public class UserServiceImpl implements UserService{
    UserDao userDao;

    public UserServiceImpl(UserDao userDao){this.userDao = userDao;}
    @Override
    public User registerUser(User user) {
        user.setApproved(false);
        return userDao.createUser(user);
    }

    @Override
    public User validateUser(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        if(!user.getPassword().equals(password)){
            throw new PwdNotMatchException("Password mismatch");
        }
        return user;
    }
}
