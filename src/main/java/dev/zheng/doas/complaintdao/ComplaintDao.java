package dev.zheng.doas.complaintdao;

import dev.zheng.entities.Complaint;
import dev.zheng.entities.Priority;

import java.util.List;
import java.util.Map;

public interface ComplaintDao {
    Complaint createComplaint(Complaint complaint);
    Complaint getOneComplaint(int id);
    List<Complaint> getAllComplaints ();
    Map<String, String> updatePriority(int id, Priority priority);
    Complaint updateAttachedMeeting(int id, int meetId);

}
