package dev.zheng.handlers;

import com.google.gson.Gson;
import dev.zheng.doas.complaintdao.ComplaintDao;
import dev.zheng.entities.Complaint;
import dev.zheng.services.complaintservice.ComplaintService;
import dev.zheng.services.complaintservice.exceptions.NullComplaintDescriptionsException;
import io.javalin.http.Handler;

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
}
