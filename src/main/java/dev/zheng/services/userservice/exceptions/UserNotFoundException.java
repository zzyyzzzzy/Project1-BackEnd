package dev.zheng.services.userservice.exceptions;

public class UserNotFoundException extends IllegalArgumentException{
    public UserNotFoundException(String message){
        super(message);
    }
}
