package dev.zheng.services.complaintservice.exceptions;

public class InvalidPriorityException extends IllegalArgumentException{
    public InvalidPriorityException(String message){
        super(message);
    }
}
