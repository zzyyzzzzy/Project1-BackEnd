package dev.zheng.services.userservice.exceptions;

public class InvalidUserStatus extends IllegalArgumentException{
    public InvalidUserStatus(String message){
        super(message);
    }
}
