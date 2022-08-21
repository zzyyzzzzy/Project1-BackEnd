package dev.zheng.handlers;

import com.google.gson.Gson;
import dev.zheng.entities.Meeting;
import dev.zheng.services.meetingservice.MeetingService;
import io.javalin.http.Handler;


public class MeetingHandler {
    private Gson gson = new Gson();
    public MeetingService meetingService;

    public MeetingHandler(MeetingService meetingService){this.meetingService = meetingService;}

    public Handler createMeeting = ctx -> {
        Meeting meeting = gson.fromJson(ctx.body(), Meeting.class);
        Meeting savedMeeting = meetingService.addMeeting(meeting);
        if (savedMeeting == null){
            ctx.status(500);
            ctx.result("Something went wrong");
            return;
        }
        ctx.status(201);
        ctx.result(gson.toJson(savedMeeting));
    };

    public Handler getAllMeetings = ctx -> {
        ctx.result(gson.toJson(meetingService.retrieveAllMeetings()));
    };
}
