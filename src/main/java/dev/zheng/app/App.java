package dev.zheng.app;

import dev.zheng.doas.complaintdao.ComplaintDaoPostgres;
import dev.zheng.doas.meetingdao.MeetingDaoPostgres;
import dev.zheng.doas.userdao.UserDaoPostgres;
import dev.zheng.handlers.ComplaintHandler;
import dev.zheng.handlers.MeetingHandler;
import dev.zheng.handlers.UserHandler;
import dev.zheng.services.complaintservice.ComplaintService;
import dev.zheng.services.complaintservice.ComplaintServiceImpl;
import dev.zheng.services.meetingservice.MeetingService;
import dev.zheng.services.meetingservice.MeetingServiceImpl;
import dev.zheng.services.userservice.UserService;
import dev.zheng.services.userservice.UserServiceImpl;
import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create( config -> {
            config.enableDevLogging();
            config.enableCorsForAllOrigins();
        });
        ComplaintService complaintService = new ComplaintServiceImpl(new ComplaintDaoPostgres());
        ComplaintHandler complaintHandler = new ComplaintHandler(complaintService);

        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoPostgres());
        MeetingHandler meetingHandler = new MeetingHandler(meetingService);

        UserService userService = new UserServiceImpl(new UserDaoPostgres());
        UserHandler userHandler = new UserHandler(userService);
        //complaints routes
        app.post("complaints", complaintHandler.createComplaint);
        app.get("complaints", complaintHandler.getAllComplaints);
        app.patch("complaints/{id}/{priority}", complaintHandler.updateComplaintPriority);
        app.patch("complaints/{complaint_id}/meeting/{meeting_id}", complaintHandler.updateAttachedMeeting);

        //meetings routes
        app.post("meetings", meetingHandler.createMeeting);
        app.get("meetings", meetingHandler.getAllMeetings);
        app.start();

        //users routes
        app.get("users", userHandler.getAllUsers);
        app.post("users", userHandler.createUser);
        app.post("login", userHandler.loginUser);
        app.patch("users/{user_id}/{status}", userHandler.updateUserStatus);
    }
}
