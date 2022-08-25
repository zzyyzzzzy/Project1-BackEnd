package dev.zheng.services.userservice.exceptions;

public class PwdNotMatchException extends IllegalArgumentException{
    public PwdNotMatchException(String message){
        super(message);
    }
}
