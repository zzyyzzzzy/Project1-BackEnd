package dev.zheng.services.complaintservice;

import dev.zheng.doas.complaintdao.ComplaintDao;
import dev.zheng.entities.Complaint;
import dev.zheng.entities.Priority;
import dev.zheng.services.complaintservice.exceptions.InvalidComplaintIdException;
import dev.zheng.services.complaintservice.exceptions.InvalidPriorityException;
import dev.zheng.services.complaintservice.exceptions.NullComplaintDescriptionsException;

import java.util.List;
import java.util.Map;

public class ComplaintServiceImpl implements ComplaintService{

    ComplaintDao complaintDao;

    private Priority convertPriorityToEnum(String priority){
        try{
            return Priority.valueOf(priority);
        } catch (IllegalArgumentException e){
            return null;
        }
    }

    public ComplaintServiceImpl(ComplaintDao complaintDao){
        this.complaintDao = complaintDao;
    }
    private void checkNull(Complaint complaint){
        if(complaint.getDescription() == null){
            throw new NullComplaintDescriptionsException("Descriptions cannot be empty");
        }
    }

    @Override
    public Complaint addComplaint(Complaint complaint) {
        checkNull(complaint);
        return complaintDao.createComplaint(complaint);
    }

    @Override
    public List<Complaint> retrieveAllComplaints() {
        return complaintDao.getAllComplaints();
    }

    @Override
    public Map<String, String> updateComplaintPriority(int id, String priority) {

        Complaint oldComplaint = complaintDao.getOneComplaint(id);
        if (oldComplaint == null){
            throw new InvalidComplaintIdException("Complaint id not found");
        }
        if(priority.equalsIgnoreCase("ignore")){
            priority = priority + "d";
        }
        String upperCasedPriority = priority.toUpperCase();
        Priority actualPriority = convertPriorityToEnum(upperCasedPriority);
        if(actualPriority == null){
            throw new InvalidPriorityException("Invalid priority status");
        }
        return complaintDao.updatePriority(id, actualPriority);
    }

}
