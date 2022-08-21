package dev.zheng.app;

import dev.zheng.doas.complaintdao.ComplaintDaoPostgres;
import dev.zheng.doas.meetingdao.MeetingDaoPostgres;
import dev.zheng.handlers.ComplaintHandler;
import dev.zheng.handlers.MeetingHandler;
import dev.zheng.services.complaintservice.ComplaintService;
import dev.zheng.services.complaintservice.ComplaintServiceImpl;
import dev.zheng.services.meetingservice.MeetingService;
import dev.zheng.services.meetingservice.MeetingServiceImpl;
import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create( config -> {
            config.enableCorsForAllOrigins();
        });
        ComplaintService complaintService = new ComplaintServiceImpl(new ComplaintDaoPostgres());
        ComplaintHandler complaintHandler = new ComplaintHandler(complaintService);

        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoPostgres());
        MeetingHandler meetingHandler = new MeetingHandler(meetingService);
        //complaints routes
        app.post("complaints", complaintHandler.createComplaint);
        app.get("complaints", complaintHandler.getAllComplaints);
        app.patch("complaints/{id}/{priority}", complaintHandler.updateComplaintPriority);
        app.patch("complaints/{complaint_id}/meeting/{meeting_id}", complaintHandler.updateAttachedMeeting);

        //meetings routes
        app.post("meetings", meetingHandler.createMeeting);
        app.get("meetings", meetingHandler.getAllMeetings);
        app.start();
    }
}
