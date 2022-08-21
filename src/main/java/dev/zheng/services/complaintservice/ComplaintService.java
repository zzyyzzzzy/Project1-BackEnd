package dev.zheng.services.complaintservice;

import dev.zheng.entities.Complaint;

import java.util.List;
import java.util.Map;

public interface ComplaintService {
    Complaint addComplaint(Complaint complaint);
    List<Complaint> retrieveAllComplaints();
    Map<String, String> updateComplaintPriority(int id, String priority);
    boolean attachComplaintToMeeting(int ComplaintId,int meetingId);
}
