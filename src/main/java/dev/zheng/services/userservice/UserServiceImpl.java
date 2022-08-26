package dev.zheng.services.userservice;

import dev.zheng.doas.userdao.UserDao;
import dev.zheng.entities.User;
import dev.zheng.services.userservice.exceptions.InvalidUserStatus;
import dev.zheng.services.userservice.exceptions.PwdNotMatchException;
import dev.zheng.services.userservice.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService{
    UserDao userDao;

    public UserServiceImpl(UserDao userDao){this.userDao = userDao;}
    @Override
    public User registerUser(User user) {
        user.setApproved(false);
        return userDao.createUser(user);
    }

    @Override
    public List<User> retrieveAllUsers() {
        return userDao.getAllUsers();
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

    @Override
    public Map<String, String> patchUserStatus(int userId, String status) {
        if(status.equalsIgnoreCase("approve")) {
            return userDao.updateUserStatus(userId, true);
        } else if(status.equalsIgnoreCase("deny")){
            return userDao.updateUserStatus(userId, false);
        } else{
            throw new InvalidUserStatus("Invalid user status entered");
        }
    }
}
