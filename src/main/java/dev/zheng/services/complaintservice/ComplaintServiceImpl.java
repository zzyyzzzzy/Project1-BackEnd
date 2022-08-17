package dev.zheng.services.complaintservice;

import dev.zheng.doas.complaintdao.ComplaintDao;
import dev.zheng.entities.Complaint;
import dev.zheng.services.complaintservice.exceptions.NullComplaintDescriptionsException;

import java.util.List;

public class ComplaintServiceImpl implements ComplaintService{

    ComplaintDao complaintDao;

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

}
