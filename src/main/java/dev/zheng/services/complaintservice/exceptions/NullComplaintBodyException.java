package dev.zheng.services.complaintservice.exceptions;

public class NullComplaintBodyException extends IllegalArgumentException{
    public NullComplaintBodyException(String message){
        super(message);
    }
}
