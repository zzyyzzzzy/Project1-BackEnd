package dev.zheng.handlers;

import com.google.gson.Gson;
import dev.zheng.doas.complaintdao.ComplaintDao;
import dev.zheng.entities.Complaint;
import dev.zheng.services.complaintservice.ComplaintService;
import dev.zheng.services.complaintservice.exceptions.InvalidComplaintIdException;
import dev.zheng.services.complaintservice.exceptions.InvalidPriorityException;
import dev.zheng.services.complaintservice.exceptions.NullComplaintDescriptionsException;
import io.javalin.http.Handler;

import java.util.Map;

public class ComplaintHandler {
    private Gson gson = new Gson();
    public ComplaintService complaintService;

    public ComplaintHandler(ComplaintService complaintService){this.complaintService = complaintService;}

    public Handler createComplaint = ctx -> {
        Complaint complaint = gson.fromJson(ctx.body(), Complaint.class);
        try{
            Complaint savedComplaint = complaintService.addComplaint(complaint);
            ctx.status(201);
            ctx.result(gson.toJson(savedComplaint));
        } catch (NullComplaintDescriptionsException err){
            ctx.status(400);
            ctx.result("Description should not be empty");
        }
    };

    public Handler getAllComplaints = ctx -> {
        ctx.result(gson.toJson(complaintService.retrieveAllComplaints()));
    };

    public Handler updateComplaintPriority = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String priority  = ctx.pathParam("priority");
        try{
            Map<String, String> updatedComplaint = complaintService.updateComplaintPriority(id, priority);
            ctx.result(gson.toJson(updatedComplaint));
        } catch (InvalidComplaintIdException err){
            ctx.result("Complaint id not found");
            ctx.status(404);
        } catch(InvalidPriorityException err){
            ctx.result("Cannot to change to a invalid status");
            ctx.status(400);
        }
    };

    public Handler updateAttachedMeeting = ctx -> {
        int complaintId = Integer.parseInt(ctx.pathParam("complaint_id"));
        int meetingId = Integer.parseInt(ctx.pathParam("meeting_id"));
        if (complaintService.attachComplaintToMeeting(complaintId, meetingId)){
            ctx.result("Meeting Id " + meetingId + " successfully attached to complaint Id " + complaintId);
        } else{
            ctx.status(404);
            ctx.result("Cannot find the meeting or complaint");
        }
    };
}
