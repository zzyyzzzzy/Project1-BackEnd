package dev.zheng.services.complaintservice;

import dev.zheng.entities.Complaint;

import java.util.List;

public interface ComplaintService {
    Complaint addComplaint(Complaint complaint);
    List<Complaint> retrieveAllComplaints();
}
