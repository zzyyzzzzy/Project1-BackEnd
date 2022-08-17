package dev.zheng.doas.complaintdao;

import dev.zheng.entities.Complaint;

import java.util.List;

public interface ComplaintDao {
    Complaint createComplaint(Complaint complaint);
    List<Complaint> getAllComplaints ();
}
