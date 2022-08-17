package dev.zheng.services.complaintservice.exceptions;

public class NullComplaintDescriptionsException extends IllegalArgumentException{
    public NullComplaintDescriptionsException(String message){
        super(message);
    }
}
