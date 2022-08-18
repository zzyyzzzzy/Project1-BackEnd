package dev.zheng.services.complaintservice.exceptions;

public class InvalidComplaintIdException extends IllegalArgumentException{
    public InvalidComplaintIdException(String message){
        super(message);
    }
}
